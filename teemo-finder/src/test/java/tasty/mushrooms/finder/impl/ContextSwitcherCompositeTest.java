package tasty.mushrooms.finder.impl;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import tasty.mushrooms.finder.IContextSwitcher;

public class ContextSwitcherCompositeTest {
	private static final String CXT = "test_cxt";

	@Test
	public void testSupportEmpty() {
		ContextSwitcherComposite csc = new ContextSwitcherComposite();
		Assert.assertFalse(csc.support(CXT));
	}

	@Test
	public void testSupportOne() {
		ContextSwitcherComposite csc = new ContextSwitcherComposite();
		IContextSwitcher cs = getMockSwitcher();
		csc.addSwitcher(cs);
		Assert.assertTrue(csc.support(CXT));

		ContextSwitcherComposite csc2 = new ContextSwitcherComposite();
		IContextSwitcher cs2 = getMockSwitcher(false);
		csc2.addSwitcher(cs2);
		Assert.assertFalse(csc2.support(CXT));
	}

	@Test
	public void testSupportMoreFalse() {
		ContextSwitcherComposite csc = new ContextSwitcherComposite();
		IContextSwitcher cs1 = getMockSwitcher(false);
		IContextSwitcher cs2 = getMockSwitcher(false);
		IContextSwitcher cs3 = getMockSwitcher(false);
		csc.addSwitcher(cs1, cs2, cs3);
		Assert.assertFalse(csc.support(CXT));
		Mockito.verify(cs1).support(CXT);
		Mockito.verify(cs2).support(CXT);
		Mockito.verify(cs3).support(CXT);
	}

	@Test
	public void testSupportMoreTrue() {
		ContextSwitcherComposite csc = new ContextSwitcherComposite();
		IContextSwitcher cs1 = getMockSwitcher(false);
		IContextSwitcher cs2 = getMockSwitcher(true);
		IContextSwitcher cs3 = getMockSwitcher(false);
		csc.addSwitcher(cs1, cs2, cs3);
		Assert.assertTrue(csc.support(CXT));
		Mockito.verify(cs1).support(CXT);
		Mockito.verify(cs2).support(CXT);
		Mockito.verify(cs3, Mockito.times(0)).support(CXT);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSwitchContextUnsupported() {
		ContextSwitcherComposite csc = new ContextSwitcherComposite();
		IContextSwitcher cs1 = getMockSwitcher(false);
		IContextSwitcher cs2 = getMockSwitcher(false);
		IContextSwitcher cs3 = getMockSwitcher(false);
		csc.addSwitcher(cs1, cs2, cs3);
		csc.switchContext(CXT);
	}

	@Test
	public void testSwitchContext() {
		ContextSwitcherComposite csc = new ContextSwitcherComposite();
		IContextSwitcher cs1 = getMockSwitcher(false);
		IContextSwitcher cs2 = getMockSwitcher(false);
		IContextSwitcher cs3 = getMockSwitcher(true);
		csc.addSwitcher(cs1, cs2, cs3);
		csc.switchContext(CXT);
		Mockito.verify(cs1, Mockito.times(0)).switchContext(CXT);
		Mockito.verify(cs2, Mockito.times(0)).switchContext(CXT);
		Mockito.verify(cs3).switchContext(CXT);
	}

	@Test
	public void testAddSwitcherIContextSwitcher() {
		ContextSwitcherComposite csc = new ContextSwitcherComposite();
		IContextSwitcher cs1 = getMockSwitcher();
		csc.addSwitcher(cs1);
		Assert.assertTrue(csc.support(CXT));
		Mockito.verify(cs1).support(CXT);

		ContextSwitcherComposite csc2 = new ContextSwitcherComposite();
		IContextSwitcher cs2 = getMockSwitcher(false);
		csc2.addSwitcher(cs2);
		Assert.assertFalse(csc2.support(CXT));
		Mockito.verify(cs2).support(CXT);
	}

	@Test
	public void testAddSwitcherIContextSwitcherArray() {
		ContextSwitcherComposite csc = new ContextSwitcherComposite();
		IContextSwitcher cs1 = getMockSwitcher(false);
		IContextSwitcher cs2 = getMockSwitcher(true);
		IContextSwitcher cs3 = getMockSwitcher(true);
		csc.addSwitcher(cs1, cs2, cs3);
		csc.support(CXT);
		Mockito.verify(cs1).support(CXT);
		Mockito.verify(cs2).support(CXT);
		Mockito.verify(cs3, Mockito.times(0)).support(CXT);
	}

	@Test
	public void testAddSwitcherIContextSwitcherArrayNull() {
		ContextSwitcherComposite csc = new ContextSwitcherComposite();
		csc.addSwitcher(null, null);
		Assert.assertFalse(csc.support(CXT));
	}

	@Test
	public void testClearSwitcherEmpty() {
		ContextSwitcherComposite csc = new ContextSwitcherComposite();
		csc.clearSwitcher();
		Assert.assertFalse(csc.support(CXT));
	}

	@Test
	public void testClearSwitcher() {
		ContextSwitcherComposite csc = new ContextSwitcherComposite();
		csc.addSwitcher(getMockSwitcher());
		csc.addSwitcher(getMockSwitcher());
		csc.clearSwitcher();
		Assert.assertFalse(csc.support(CXT));
	}

	private IContextSwitcher getMockSwitcher(boolean ret) {
		IContextSwitcher cs = Mockito.mock(IContextSwitcher.class);
		Mockito.when(cs.support(CXT)).thenReturn(ret);
		return cs;
	}

	private IContextSwitcher getMockSwitcher() {
		return getMockSwitcher(true);
	}

}
