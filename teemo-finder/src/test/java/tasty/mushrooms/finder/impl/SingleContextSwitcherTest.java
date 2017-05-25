package tasty.mushrooms.finder.impl;

import org.junit.Assert;
import org.junit.Test;

public class SingleContextSwitcherTest {

	@Test
	public void testSupport() {
		SingleContextSwitcher s = new SingleContextSwitcher();
		Assert.assertTrue(s.support(null));
		Assert.assertTrue(s.support("123321"));
		Assert.assertTrue(s.support("NATIVE_APP"));
	}

	@Test
	public void testSwitchContext() {
		SingleContextSwitcher s = new SingleContextSwitcher();
		s.switchContext(null);
		s.switchContext("123321");
		s.switchContext("NATIVE_APP");
	}

}
