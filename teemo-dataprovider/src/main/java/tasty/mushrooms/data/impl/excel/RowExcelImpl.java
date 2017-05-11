package tasty.mushrooms.data.impl.excel;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

import tasty.mushrooms.data.IRow;

class RowExcelImpl implements IRow {
	private int mIndex;
	private String[] mTitles;
	private Cell[] mCells;

	public RowExcelImpl(int index, String[] titles, Cell[] cells) {
		mIndex = index;
		mTitles = titles;
		mCells = cells;
	}

	@Override
	public int getIndex() {
		return mIndex;
	}

	@Override
	public String[] getColumnNames() {
		return mTitles;
	}

	@Override
	public int getColumnNumbers() {
		return mTitles.length;
	}

	@Override
	public boolean getBoolean(String key) {
		Cell vCell = getCell(key);
		return getBooleanValue(vCell);
	}

	@Override
	public boolean getBoolean(int index) {
		Cell vCell = getCell(index);
		return getBooleanValue(vCell);
	}

	@Override
	public byte getByte(String key) {
		Cell vCell = getCell(key);
		return (byte) getNumbericValue(vCell, Byte.class);
	}

	@Override
	public byte getByte(int index) {
		Cell vCell = getCell(index);
		return (byte) getNumbericValue(vCell, Byte.class);
	}

	@Override
	public char getChar(String key) {
		Cell vCell = getCell(key);
		return getCharValue(vCell);
	}

	@Override
	public char getChar(int index) {
		Cell vCell = getCell(index);
		return getCharValue(vCell);
	}

	@Override
	public short getShort(String key) {
		Cell vCell = getCell(key);
		return (short) getNumbericValue(vCell, Short.class);
	}

	@Override
	public short getShort(int index) {
		Cell vCell = getCell(index);
		return (short) getNumbericValue(vCell, Short.class);
	}

	@Override
	public int getInt(String key) {
		Cell vCell = getCell(key);
		return (int) getNumbericValue(vCell, Integer.class);
	}

	@Override
	public int getInt(int index) {
		Cell vCell = getCell(index);
		return (int) getNumbericValue(vCell, Integer.class);
	}

	@Override
	public long getLong(String key) {
		Cell vCell = getCell(key);
		return (long) getNumbericValue(vCell, Long.class);
	}

	@Override
	public long getLong(int index) {
		Cell vCell = getCell(index);
		return (long) getNumbericValue(vCell, Long.class);
	}

	@Override
	public float getFloat(String key) {
		Cell vCell = getCell(key);
		return (float) getNumbericValue(vCell, Float.class);
	}

	@Override
	public float getFloat(int index) {
		Cell vCell = getCell(index);
		return (float) getNumbericValue(vCell, Float.class);
	}

	@Override
	public double getDouble(String key) {
		Cell vCell = getCell(key);
		return getNumbericValue(vCell, Double.class);
	}

	@Override
	public double getDouble(int index) {
		Cell vCell = getCell(index);
		return getNumbericValue(vCell, Double.class);
	}

	@Override
	public String getString(String key) {
		Cell vCell = getCell(key);
		return getStringValue(vCell);
	}

	@Override
	public String getString(int index) {
		Cell vCell = getCell(index);
		return getStringValue(vCell);
	}

	@Override
	public Date getDate(String key) {
		Cell vCell = getCell(key);
		return getDateValue(vCell);
	}

	@Override
	public Date getDate(int index) {
		Cell vCell = getCell(index);
		return getDateValue(vCell);
	}

	private int getTitleIndex(String title) {
		for (int i = 0; i < mTitles.length; ++i) {
			if (mTitles[i].equals(title))
				return i;
		}
		return -1;
	}

	private Cell getCell(String title) {
		int vIndex = getTitleIndex(title);
		if (vIndex == -1) {
			throw new IllegalArgumentException("No such column named [" + title + "]");
		}
		return mCells[vIndex];
	}

	private Cell getCell(int index) {
		if (index < 1 || index > mCells.length) {
			throw new IllegalArgumentException("Index out of bound, " + index + " of [1," + mCells.length + "]");
		}
		return mCells[index - 1];
	}

	private int getCellType(Cell cell) {
		int vType = cell.getCellType();
		if (vType == Cell.CELL_TYPE_FORMULA) {
			// 获取公式类型
			vType = cell.getCachedFormulaResultType();
		}
		return vType;
	}

	private boolean getBooleanValue(Cell cell) {
		switch (getCellType(cell)) {
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		case Cell.CELL_TYPE_NUMERIC:
			return Double.compare(cell.getNumericCellValue(), 0) != 0;
		case Cell.CELL_TYPE_STRING:
			return Boolean.valueOf(cell.getStringCellValue());
		default:
			return false;
		}
	}

	private char getCharValue(Cell cell) {
		int vType = getCellType(cell);
		switch (vType) {
		case Cell.CELL_TYPE_BLANK:
			return 0;
		case Cell.CELL_TYPE_NUMERIC:
			return (char) cell.getNumericCellValue();
		case Cell.CELL_TYPE_STRING:
			String vStr = cell.getStringCellValue();
			if (vStr.length() == 1)
				return vStr.charAt(0);
			else
				throw new IllegalArgumentException("Can't get char from String whose length > 1");
		default:
			throw new IllegalArgumentException("Can't get char from type " + vType);
		}
	}

	private double getNumbericValue(Cell cell, Class<? extends Number> clazz) {
		double vRet;
		int vType = getCellType(cell);
		switch (vType) {
		case Cell.CELL_TYPE_BLANK:
			vRet = 0;
			break;
		case Cell.CELL_TYPE_NUMERIC:
			vRet = cell.getNumericCellValue();
			break;
		case Cell.CELL_TYPE_STRING:
			String vStr = cell.getStringCellValue();
			vRet = Double.valueOf(vStr);
			break;
		default:
			throw new IllegalArgumentException("Can't get " + clazz.getSimpleName() + " from type " + vType);
		}
		return vRet;
	}

	private String getStringValue(Cell cell) {
		int vType = getCellType(cell);
		switch (vType) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case Cell.CELL_TYPE_NUMERIC:
			double vValue = cell.getNumericCellValue();
			if (doubleIsInt(vValue))
				return String.valueOf((int) vValue);
			return String.valueOf(vValue);
		case Cell.CELL_TYPE_BLANK:
			return "";
		default:
			throw new IllegalArgumentException("Can't get String from type " + vType);
		}
	}

	/*
	 * 判断是否为整数，防止转String时加小数
	 */
	private boolean doubleIsInt(double d) {
		return Double.compare(d, (int) d) == 0;
	}

	private Date getDateValue(Cell cell) {
		if (HSSFDateUtil.isCellDateFormatted(cell)) {
			return cell.getDateCellValue();
		}
		throw new IllegalArgumentException("Can't get Date because type of cell is not Date");
	}

}
