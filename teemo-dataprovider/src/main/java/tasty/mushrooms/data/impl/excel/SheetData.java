package tasty.mushrooms.data.impl.excel;

import org.apache.poi.ss.usermodel.Sheet;

class SheetData {
	private Sheet mSheet;
	private String mCurrentGroup;
	private int mCurrentIndex;
	private int mRowCount;
	private String[] mTitles;

	public Sheet getSheet() {
		return mSheet;
	}

	public void setSheet(Sheet sheet) {
		mSheet = sheet;
	}

	public String getCurrentGroup() {
		return mCurrentGroup;
	}

	public void setCurrentGroup(String currentGroup) {
		mCurrentGroup = currentGroup;
	}

	public int getCurrentIndex() {
		return mCurrentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		mCurrentIndex = currentIndex;
	}

	public int getRowCount() {
		return mRowCount;
	}

	public void setRowCount(int rowCount) {
		mRowCount = rowCount;
	}

	public String[] getTitles() {
		return mTitles;
	}

	public void setTitles(String[] titles) {
		mTitles = titles;
	}

}
