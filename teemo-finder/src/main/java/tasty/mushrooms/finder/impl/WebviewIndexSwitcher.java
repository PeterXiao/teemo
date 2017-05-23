package tasty.mushrooms.finder.impl;

import java.util.List;
import java.util.regex.Pattern;

import tasty.mushrooms.finder.IBasicContextSwitcher;

/**
 * 根据索引值切换WEBVIEW，索引可为负数，表示倒数第几个
 * 
 * @param <D> 原始驱动类型
 * @since 1.0.0
 */
public final class WebviewIndexSwitcher<D> extends AbstractWebviewSwitcher<D> {
	private static final Pattern mPattern = Pattern.compile("^WEBVIEW\\[-?[0-9]+\\]$");

	public WebviewIndexSwitcher(IBasicContextSwitcher<D> switcher) {
		super(switcher);
	}

	@Override
	public void switchContext(String context) {
		List<String> vCxt = getWebivewContextsOrWindows();
		int vIndex = getIndex(context, vCxt);
		String vName = vCxt.get(vIndex);
		safeContextOrWindow(vName);
	}

	@Override
	protected Pattern getPattern() {
		return mPattern;
	}

	private int getIndex(String cxt, List<String> cxts) {
		String vNumStr = getContent(cxt);
		int vNum = Integer.valueOf(vNumStr);
		if (vNum < 0) {
			int vSize = cxts.size();
			vNum = vSize + vNum;
			if (vNum < 0 && vNum >= vSize) {
				throw new IllegalArgumentException("Index out of bound [0-" + vSize + "]");
			}
		}
		return vNum;
	}

}
