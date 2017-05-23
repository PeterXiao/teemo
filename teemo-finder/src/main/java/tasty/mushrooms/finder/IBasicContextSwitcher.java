package tasty.mushrooms.finder;

import java.util.List;

/**
 * 切换上下文的基本操作，用以辅助IContextSwitcher完成其功能
 * 
 * @param <D> 原始Driver的类型
 * @since 1.0.0
 * @see IContextSwitcher
 */
public interface IBasicContextSwitcher<D> extends IDriverWrapper<D> {

	/**
	 * webview是否需要在window中识别（例如使用Appium测试Android时，多个webview只能通过window识别而不是context）
	 * 
	 * @return
	 */
	boolean useWindow();

	/**
	 * 切换context
	 * 
	 * @param cxt
	 */
	void context(String cxt);

	/**
	 * 切换window
	 * 
	 * @param win
	 */
	void window(String win);

	/**
	 * 获取当前context或window名，当{@link #useWindow()}返回true且当前处于webview中时应返回window名，否则返回context名
	 * 
	 * @return
	 */
	String currentContextOrWindow();

	/**
	 * 获取context列表
	 * 
	 * @return
	 */
	List<String> contexts();

	/**
	 * 获取window列表
	 * 
	 * @return
	 */
	List<String> windows();

}
