package tasty.mushrooms.finder.impl;

import tasty.mushrooms.finder.IContextSwitcher;

/**
 * 用于非混合型App
 * 
 * @since 1.0.0
 */
public class SingleContextSwitcher implements IContextSwitcher {

	@Override
	public boolean support(String context) {
		return true;
	}

	@Override
	public void switchContext(String context) {
	}

}
