package tasty.mushrooms.data;

import tasty.mushrooms.commons.util.Assert;
import tasty.mushrooms.data.impl.DefaultGroupResolver;
import tasty.mushrooms.data.impl.excel.ExcelReader;

/**
 * DataProviderX配置信息
 * 
 * @since 1.0.0
 */
public class DPXConfig {
	private IDataReader mReader;
	private IGroupResolver mGroupResovler;
	private IRowFilter mRowFilter;
	private String mCriteria;

	public DPXConfig(IDataReader reader, IGroupResolver groupResovler) {
		Assert.isTrue(reader != null, "IDataReader must not be null");
		Assert.isTrue(groupResovler != null, "GroupResolver must not be null");
		mReader = reader;
		mGroupResovler = groupResovler;
	}

	public DPXConfig(IDataReader reader, IGroupResolver groupResovler, IRowFilter rowFilter, String criteria) {
		this(reader, groupResovler);
		mRowFilter = rowFilter;
		mCriteria = criteria;
	}

	/**
	 * 生成默认配置，使用{@link ExcelReader}、{@link DefaultGroupResolver}
	 * 
	 * @param excel Excel文件路径
	 * @return
	 */
	public static DPXConfig buildDefault(String excel) {
		IDataReader vReader = new ExcelReader(excel);
		IGroupResolver vGr = new DefaultGroupResolver();
		DPXConfig vConfig = new DPXConfig(vReader, vGr);
		return vConfig;
	}

	public IDataReader getReader() {
		return mReader;
	}

	public void setReader(IDataReader reader) {
		mReader = reader;
	}

	public IGroupResolver getGroupResovler() {
		return mGroupResovler;
	}

	public void setGroupResovler(IGroupResolver groupResovler) {
		mGroupResovler = groupResovler;
	}

	public IRowFilter getRowFilter() {
		return mRowFilter;
	}

	public void setRowFilter(IRowFilter rowFilter) {
		mRowFilter = rowFilter;
	}

	public String getCriteria() {
		return mCriteria;
	}

	public void setCriteria(String criteria) {
		mCriteria = criteria;
	}

}
