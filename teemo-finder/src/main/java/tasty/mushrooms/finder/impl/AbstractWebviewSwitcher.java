package tasty.mushrooms.finder.impl;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import tasty.mushrooms.finder.IContextSwitcher;
import tasty.mushrooms.finder.IBasicContextSwitcher;

/**
 * 用于切换WEBVIEW的抽象类
 * 
 * @param <D> 原始驱动类型
 * @since 1.0.0
 */
public abstract class AbstractWebviewSwitcher<D> implements IContextSwitcher {
	private static final String WEBVIEW_PREFIX = "WEBVIEW_";
	private String mWebviewName;
	protected IBasicContextSwitcher<D> mSwitcher;

	public AbstractWebviewSwitcher(IBasicContextSwitcher<D> switcher) {
		mSwitcher = switcher;
	}

	@Override
	public boolean support(String context) {
		return getPattern().matcher(context).matches();
	}

	/**
	 * 获取正则，用于判断是否支持上下文表达式
	 * 
	 * @return
	 */
	protected abstract Pattern getPattern();

	/**
	 * 获取context列表或window列表
	 * 
	 * @return
	 */
	protected List<String> getWebivewContextsOrWindows() {
		List<String> vRet;
		if (mSwitcher.useWindow()) {
			switchToWebview();
			vRet = mSwitcher.windows();
		} else {
			vRet = mSwitcher.contexts();
			vRet.remove("NATIVE_APP");
		}
		return vRet;
	}

	/**
	 * 切换context或window，若目标与当前相同则无动作
	 * 
	 * @param name
	 */
	protected void safeContextOrWindow(String name) {
		name = Objects.requireNonNull(name, "Context name must not be null");
		if (name.equals(mSwitcher.currentContextOrWindow())) {
			return;
		}

		if (mSwitcher.useWindow()) {
			switchToWebview();
			mSwitcher.window(name);
		} else {
			mSwitcher.context(name);
		}
	}

	/**
	 * 获取WEBVIEW[]表达式中括号内的内容
	 * 
	 * @param cxt
	 * @return
	 */
	protected String getContent(String cxt) {
		return cxt.substring(WEBVIEW_PREFIX.length(), cxt.length() - 1);
	}

	private void switchToWebview() {
		if (isInNativeApp()) {
			String vCxt = getWebviewContextName();
			mSwitcher.context(vCxt);
		}
	}

	private synchronized String getWebviewContextName() {
		if (mWebviewName == null) {
			List<String> vCxts = mSwitcher.contexts();
			for (String c : vCxts) {
				if (c.startsWith(WEBVIEW_PREFIX)) {
					mWebviewName = c;
					return c;
				}
			}
			throw new IllegalStateException("There is no webview");
		}
		return mWebviewName;
	}

	private boolean isInNativeApp() {
		return "NATIVE_APP".equals(mSwitcher.currentContextOrWindow());
	}

}
