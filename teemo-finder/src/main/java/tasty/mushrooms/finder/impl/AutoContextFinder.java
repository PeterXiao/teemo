package tasty.mushrooms.finder.impl;

import java.util.List;

import tasty.mushrooms.commons.util.Assert;
import tasty.mushrooms.finder.IBasicContextSwitcher;
import tasty.mushrooms.finder.IContextSwitcher;
import tasty.mushrooms.finder.IElementFinder;
import tasty.mushrooms.finder.LocateInfo;
import tasty.mushrooms.finder.LocateStrategy;

/**
 * 自动切换上下文的元素查找器
 * 
 * @param <D> 原始驱动类型
 * @param <E> 找到的元素类型
 * @since 1.0.0
 */
public abstract class AutoContextFinder<D, E> implements IElementFinder<D, E>, IBasicContextSwitcher<D> {
	/**
	 * 上下文切换器集合，可根据需要添加或删除上下文切换器
	 */
	public static final ContextSwitcherComposite SWITCHERS = new ContextSwitcherComposite();

	/**
	 * 默认App为非混合型App，使用{@link SingleContextSwitcher}处理上下文切换请求
	 */
	public AutoContextFinder() {
		this(true);
	}

	/**
	 * 若为混合型App，使用{@link NativeAppSwitcher}、 {@link WebviewIndexSwitcher}切换上下文
	 * 若为非混合型App，使用{@link SingleContextSwitcher}切换上下文
	 * 
	 * @param onlyNativeApp 是否为非混合型App（是否仅包含NATIVE_APP）
	 */
	public AutoContextFinder(boolean onlyNativeApp) {
		if (onlyNativeApp) {
			SWITCHERS.addSwitcher(new SingleContextSwitcher());
		} else {
			addDefaultSwitchers();
		}
	}

	@Override
	public E findElement(LocateInfo locateInfo) {
		switchContext(locateInfo);
		return findElement(locateInfo.getStrategies(), locateInfo.isCache());
	}

	@Override
	public List<E> findElements(LocateInfo locateInfo) {
		switchContext(locateInfo);
		return findElements(locateInfo.getStrategies(), locateInfo.isCache());
	}

	/**
	 * 查找单个元素
	 * 
	 * @param strategies 查找方式列表
	 * @param cache 是否缓存找到的元素
	 * @return 找到的元素
	 */
	protected abstract E findElement(List<LocateStrategy> strategies, boolean cache);

	/**
	 * 查找多个元素
	 * 
	 * @param strategies 查找方式列表
	 * @param cache 是否缓存找到的元素
	 * @return 找到的元素列表
	 */
	protected abstract List<E> findElements(List<LocateStrategy> strategies, boolean cache);

	/*
	 * 使用设置的上下文切换器进行上下文切换
	 */
	private void switchContext(LocateInfo locateInfo) {
		String vContext = locateInfo.getContext();
		Assert.isTrue(SWITCHERS.support(vContext), "Unsupported context: " + vContext);
		SWITCHERS.switchContext(vContext);
	}

	/*
	 * 设置默认上下文切换器
	 */
	private void addDefaultSwitchers() {
		IContextSwitcher vNativeAppSwitcher = new NativeAppSwitcher(this);
		IContextSwitcher vWebviewIndexSwitcher = new WebviewIndexSwitcher<D>(this);
		SWITCHERS.addSwitcher(vNativeAppSwitcher, vWebviewIndexSwitcher);
	}

}
