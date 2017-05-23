package tasty.mushrooms.finder.impl;

import tasty.mushrooms.finder.IContextSwitcher;
import tasty.mushrooms.finder.IBasicContextSwitcher;

/**
 * 用于切换NATIVE_APP
 * 
 * @since 1.0.0
 */
public class NativeAppSwitcher implements IContextSwitcher {
	private static final String NATIVE_APP = "NATIVE_APP";
	private IBasicContextSwitcher<?> mNativeSwitcher;

	public NativeAppSwitcher(IBasicContextSwitcher<?> nativeSwitcher) {
		mNativeSwitcher = nativeSwitcher;
	}

	@Override
	public boolean support(String context) {
		return NATIVE_APP.equals(context);
	}

	@Override
	public void switchContext(String context) {
		String vCurrent = mNativeSwitcher.currentContextOrWindow();
		if (!NATIVE_APP.equals(vCurrent)) {
			mNativeSwitcher.context(context);
		}
	}

}
