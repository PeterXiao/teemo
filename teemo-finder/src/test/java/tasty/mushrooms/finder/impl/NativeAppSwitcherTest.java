package tasty.mushrooms.finder.impl;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import tasty.mushrooms.finder.IBasicContextSwitcher;

public class NativeAppSwitcherTest {

	private static final String NATIVE_APP = "NATIVE_APP";

	@Test
	public void testSupport() {
		NativeAppSwitcher s = new NativeAppSwitcher(getBasicContextSwitcher(null));
		Assert.assertFalse(s.support("123321"));
		Assert.assertTrue(s.support(NATIVE_APP));
	}

	@Test
	public void testSwitchContextEqualCurrent() {
		IBasicContextSwitcher<?> bcs = getBasicContextSwitcher(NATIVE_APP);
		NativeAppSwitcher s = new NativeAppSwitcher(bcs);
		s.switchContext(NATIVE_APP);
		Mockito.verify(bcs, Mockito.times(0)).context(NATIVE_APP);
	}

	@Test
	public void testSwitchContextNotEqualCurrent() {
		IBasicContextSwitcher<?> bcs = getBasicContextSwitcher("123321");
		NativeAppSwitcher s = new NativeAppSwitcher(bcs);
		s.switchContext(NATIVE_APP);
		Mockito.verify(bcs).context(NATIVE_APP);
	}

	private IBasicContextSwitcher<?> getBasicContextSwitcher(String cxt) {
		IBasicContextSwitcher<?> bcs = Mockito.mock(IBasicContextSwitcher.class);
		Mockito.when(bcs.currentContextOrWindow()).thenReturn(cxt);
		return bcs;
	}

}
