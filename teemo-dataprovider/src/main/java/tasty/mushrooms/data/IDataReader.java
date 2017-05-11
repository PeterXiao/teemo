package tasty.mushrooms.data;

/**
 * 以行为单位读取数据
 * 
 * @since 1.0.0
 */
public interface IDataReader {

	/**
	 * 切换数据分组
	 * 
	 * @param group 数据分组名
	 */
	void changeGroup(String group);

	/**
	 * 是否支持条件查询
	 * 
	 * @return 支持返回true，否则返回false
	 */
	boolean supportQuery();

	/**
	 * 设置查询条件
	 * 
	 * @param criteria 查询条件
	 */
	void setCriteria(String criteria);

	/**
	 * 读取下一行
	 * 
	 * @return 行数据，若已经无数据返回null
	 */
	IRow readRow();

	/**
	 * 关闭数据源
	 */
	void close();

}
