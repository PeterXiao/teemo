package tasty.mushrooms.data.impl.excel;

import java.io.File;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

import tasty.mushrooms.commons.util.Assert;
import tasty.mushrooms.data.IRow;
import tasty.mushrooms.data.impl.AbstractReader;

/**
 * Excel Reader，DataProviderX默认Reader
 * 
 * @since 1.0.0
 */
public class ExcelReader extends AbstractReader<SheetData> {
	private String mFile;
	private Workbook mWorkBook;

	public ExcelReader(String file) {
		File f = new File(file);
		Assert.isTrue(f.exists() && f.isFile(), "Invalid file");
		mFile = file;
	}

	@Override
	public IRow readRow() {
		SheetData vData = getLocalData();
		int vRowIndex = vData.getCurrentIndex();
		if (vRowIndex < vData.getRowCount()) {
			Row vRow = vData.getSheet().getRow(vRowIndex);
			String[] vTitles = vData.getTitles();
			Cell[] vValues = new Cell[vTitles.length];
			for (int i = 0; i < vTitles.length; ++i) {
				Cell vCell = vRow.getCell(i, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				vValues[i] = vCell;
			}
			vData.setCurrentIndex(vRowIndex + 1);
			return new RowExcelImpl(vRowIndex, vTitles, vValues);
		}
		// read over, remove the local data
		removeLocalData();
		return null;
	}

	@Override
	public void close() {
		synchronized (this) {
			if (mWorkBook != null) {
				try {
					mWorkBook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				mWorkBook = null;
			}
		}
	}

	@Override
	protected void resetLocalData(SheetData data, String group) {
		String vCurrent = data.getCurrentGroup();
		if (!vCurrent.equals(group)) {
			setSheetData(data, group);
		} else {
			data.setCurrentIndex(1);
		}
	}

	@Override
	protected SheetData newLocalData(String group) {
		SheetData vData = new SheetData();
		setSheetData(vData, group);
		return vData;
	}

	private Workbook getWorkBook() {
		if (mWorkBook == null) {
			synchronized (this) {
				if (mWorkBook == null) {
					try {
						mWorkBook = WorkbookFactory.create(new File(mFile), null, true);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return mWorkBook;
	}

	private void setSheetData(SheetData data, String group) {
		data.setCurrentGroup(group);
		// start from 1
		data.setCurrentIndex(1);
		// get sheet
		Sheet vSheet = getWorkBook().getSheet(group);
		Assert.notNull(vSheet, "Can't get sheet with name: " + group);
		data.setSheet(vSheet);
		// get row number
		int vRowCount = vSheet.getLastRowNum() + 1;
		data.setRowCount(vRowCount);
		// get first row
		Row vRow = vSheet.getRow(0);
		Assert.notNull(vRow, "Invalid format: first row must be title");
		// get column number
		int vColumnCount = vRow.getLastCellNum();
		String[] vTitles = new String[vColumnCount];
		// read titles
		for (int i = 0; i < vColumnCount; ++i) {
			Cell vCell = vRow.getCell(i);
			vTitles[i] = vCell.getStringCellValue();
		}
		data.setTitles(vTitles);
	}

}
