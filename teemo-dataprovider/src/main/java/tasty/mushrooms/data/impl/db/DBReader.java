package tasty.mushrooms.data.impl.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

import tasty.mushrooms.data.IRow;
import tasty.mushrooms.data.impl.AbstractReader;

public class DBReader extends AbstractReader<SQLData> {
	private BasicDataSource mDataSource;

	public DBReader(String url, String driverClassName) {
		mDataSource = new BasicDataSource();
		mDataSource.setUrl(url);
		mDataSource.setDriverClassName(driverClassName);
	}

	@Override
	public boolean supportQuery() {
		return true;
	}

	@Override
	public void setCriteria(String criteria) {
		getLocalData().setWhere(criteria);
	}

	@Override
	public IRow readRow() {
		try {
			SQLData vData = getLocalData();
			ResultSet vRs = vData.getResult();
			if (vRs.next()) {
				return new RowDBImpl(vRs);
			}
			// read over, close ResultSet and Statment
			vData.resetResultAndStatment();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		// remove local data
		removeLocalData();
		return null;
	}

	@Override
	public void close() {
		try {
			mDataSource.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void resetLocalData(SQLData data, String group) {
		String vCurrent = data.getTable();
		if (!vCurrent.equals(group)) {
			data.setTable(group);
		}
		try {
			data.resetResultAndStatment();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected SQLData newLocalData(String group) {
		SQLData vData = new SQLData();
		try {
			vData.setConnection(mDataSource.getConnection());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		vData.setTable(group);
		return vData;
	}

}
