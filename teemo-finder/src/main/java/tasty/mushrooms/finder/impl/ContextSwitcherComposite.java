package tasty.mushrooms.finder.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import tasty.mushrooms.finder.IContextSwitcher;

/**
 * 上下文切换器组合
 * 
 * @since 1.0.0
 * @see SingleContextSwitcher
 * @see NativeAppSwitcher
 * @see WebviewIndexSwitcher
 * @see WebviewValueSwitcher
 */
public class ContextSwitcherComposite implements IContextSwitcher {
	private List<IContextSwitcher> mSwitchers = new LinkedList<>();
	private Map<String, IContextSwitcher> mSwitcherCache = new ConcurrentHashMap<>();

	@Override
	public boolean support(String context) {
		return getContextSwitcher(context) != null;
	}

	@Override
	public void switchContext(String context) {
		IContextSwitcher vSwitcher = getContextSwitcher(context);
		if (vSwitcher == null) {
			throw new IllegalArgumentException("There is no IContextSwitcher supported for " + context);
		}
		vSwitcher.switchContext(context);
	}

	public ContextSwitcherComposite addSwitcher(IContextSwitcher switcher) {
		if (switcher != null) {
			mSwitchers.add(switcher);
		}
		return this;
	}

	public ContextSwitcherComposite addSwitcher(IContextSwitcher... switchers) {
		for (IContextSwitcher s : switchers) {
			if (s != null) {
				mSwitchers.add(s);
			}
		}
		return this;
	}

	public ContextSwitcherComposite clearSwitcher() {
		mSwitchers.clear();
		return this;
	}

	private IContextSwitcher getContextSwitcher(String context) {
		IContextSwitcher vSwitcher = mSwitcherCache.get(context);
		if (vSwitcher == null) {
			for (IContextSwitcher s : mSwitchers) {
				if (s.support(context)) {
					mSwitcherCache.put(context, s);
					return s;
				}
			}
		}
		return null;
	}

}
