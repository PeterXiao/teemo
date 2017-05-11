package tasty.mushrooms.data;

/**
 * 行过滤器
 * 
 * @since 1.0.0
 */
public interface IRowFilter {

	/**
	 * 是否接受该行数据
	 * 
	 * @param row 待处理的行数据
	 * @return 接受返回true，否则返回false
	 */
	boolean accept(IRow row);

	/**
	 * 设置上下文信息，可在方法中保存ProviderContext对象到本地，以便在{@link #accept(IRow)}方法中使用
	 * 
	 * @param cxt 上下文对象
	 * @return
	 */
	void setContext(ProviderContext cxt);

}
