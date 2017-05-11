package tasty.mushrooms.data.impl.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class SQLData {
	private Connection mConn;
	private String mTable;
	private String mWhere;
	private ResultSet mResult;
	private Statement mStmt;

	public Connection getConnection() {
		return mConn;
	}

	public void setConnection(Connection connection) {
		mConn = connection;
	}

	public String getTable() {
		return mTable;
	}

	public void setTable(String table) {
		mTable = table;
	}

	public String getWhere() {
		return mWhere;
	}

	public void setWhere(String where) {
		mWhere = where;
	}

	public ResultSet getResult() throws SQLException {
		if (mResult == null) {
			mStmt = mConn.createStatement();
			mResult = mStmt.executeQuery(getSQL());
		}
		return mResult;
	}

	public void resetResultAndStatment() throws SQLException {
		if (mResult != null) {
			mResult.close();
			mResult = null;
		}
		if (mStmt != null) {
			mStmt.close();
			mStmt = null;
		}
	}

	private String getSQL() {
		String vWhere = mWhere != null ? " where " + mWhere : "";
		return "select * from " + mTable + vWhere + ";";
	}

}
