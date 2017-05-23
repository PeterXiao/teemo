package tasty.mushrooms.finder;

import java.util.List;
import java.util.Objects;

/**
 * 定位信息
 * 
 * @since 1.0.0
 */
public class LocateInfo {
	private static final String DEFAULT_CONTEXT = "NATIVE_APP";
	private String context;
	private List<LocateStrategy> strategies;
	private boolean cache;

	public LocateInfo(List<LocateStrategy> strategies) {
		this(DEFAULT_CONTEXT, strategies, false);
	}

	public LocateInfo(String context, List<LocateStrategy> strategies) {
		this(context, strategies, false);
	}

	public LocateInfo(String context, List<LocateStrategy> strategies, boolean cache) {
		this.context = Objects.requireNonNull(context, "Context must not be null");
		this.strategies = Objects.requireNonNull(strategies, "Strategies must not be null");
		this.cache = cache;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public List<LocateStrategy> getStrategies() {
		return strategies;
	}

	public void setStrategies(List<LocateStrategy> strategies) {
		this.strategies = strategies;
	}

	public boolean isCache() {
		return cache;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}

}
