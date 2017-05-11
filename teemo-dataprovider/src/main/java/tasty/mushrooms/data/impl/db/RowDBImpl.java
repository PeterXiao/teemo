package tasty.mushrooms.data.impl.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;

import tasty.mushrooms.data.IRow;

class RowDBImpl implements IRow {
	private ResultSet mResult;
	private String[] mNames;

	public RowDBImpl(ResultSet result) {
		mResult = result;
	}

	@Override
	public int getIndex() {
		try {
			return mResult.getRow();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String[] getColumnNames() {
		if (mNames == null) {
			try {
				ResultSetMetaData vMd = mResult.getMetaData();
				mNames = new String[vMd.getColumnCount()];
				for (int i = 0; i < mNames.length; ++i) {
					mNames[i] = vMd.getColumnName(i + 1);
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return mNames;
	}

	@Override
	public int getColumnNumbers() {
		try {
			return mResult.getMetaData().getColumnCount();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean getBoolean(String key) {
		try {
			return mResult.getBoolean(key);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean getBoolean(int index) {
		try {
			return mResult.getBoolean(index);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte getByte(String key) {
		try {
			return mResult.getByte(key);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte getByte(int index) {
		try {
			return mResult.getByte(index);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public char getChar(String key) {
		try {
			return mResult.getString(key).charAt(0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public char getChar(int index) {
		try {
			return mResult.getString(index).charAt(0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public short getShort(String key) {
		try {
			return mResult.getShort(key);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public short getShort(int index) {
		try {
			return mResult.getShort(index);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int getInt(String key) {
		try {
			return mResult.getInt(key);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int getInt(int index) {
		try {
			return mResult.getInt(index);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long getLong(String key) {
		try {
			return mResult.getLong(key);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long getLong(int index) {
		try {
			return mResult.getLong(index);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public float getFloat(String key) {
		try {
			return mResult.getFloat(key);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public float getFloat(int index) {
		try {
			return mResult.getFloat(index);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public double getDouble(String key) {
		try {
			return mResult.getDouble(key);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public double getDouble(int index) {
		try {
			return mResult.getDouble(index);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getString(String key) {
		try {
			return mResult.getString(key);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getString(int index) {
		try {
			return mResult.getString(index);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Date getDate(String key) {
		try {
			return mResult.getTimestamp(key);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Date getDate(int index) {
		try {
			return mResult.getTimestamp(index);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
