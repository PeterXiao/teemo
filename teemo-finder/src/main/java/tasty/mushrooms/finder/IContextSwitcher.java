package tasty.mushrooms.finder;

/**
 * 根据自定义语法切换上下文
 * 
 * @since 1.0.0
 * @see IBasicContextSwitcher
 */
public interface IContextSwitcher {

	/**
	 * 是否支持某个context表达式（如WEBVIEW[-1]、WEBVIEW[url.end(main.html)]等）
	 * 
	 * @param context 表达式
	 * @return 支持返回true，否则返回false
	 */
	boolean support(String context);

	/**
	 * 根据context表达式切换上下文
	 * 
	 * @param context 表达式
	 */
	void switchContext(String context);

}
