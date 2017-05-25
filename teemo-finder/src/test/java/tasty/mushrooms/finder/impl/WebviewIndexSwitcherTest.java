package tasty.mushrooms.finder.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import tasty.mushrooms.finder.IBasicContextSwitcher;

public class WebviewIndexSwitcherTest {

	@Test
	public void testGetPattern() {
		WebviewIndexSwitcher<?> s = new WebviewIndexSwitcher<>(getBasicContextSwitcher(0));
		Pattern p = s.getPattern();
		Assert.assertNotNull(p);
		Assert.assertTrue(p.matcher("WEBVIEW[1]").matches());
		Assert.assertTrue(p.matcher("WEBVIEW[-1]").matches());
		Assert.assertFalse(p.matcher("WEBVIEW[]").matches());
	}

	@Test
	public void testSwitchContext() {
		IBasicContextSwitcher<?> bcs = getBasicContextSwitcher(3);
		WebviewIndexSwitcher<?> s = new WebviewIndexSwitcher<>(bcs);
		s.switchContext("WEBVIEW[2]");
		Assert.assertEquals("webview-3", bcs.currentContextOrWindow());
		s.switchContext("WEBVIEW[0]");
		Assert.assertEquals("webview-1", bcs.currentContextOrWindow());
		s.switchContext("WEBVIEW[1]");
		Assert.assertEquals("webview-2", bcs.currentContextOrWindow());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSwitchContextOutofBound() {
		WebviewIndexSwitcher<?> s = new WebviewIndexSwitcher<>(getBasicContextSwitcher(1));
		s.switchContext("WEBVIEW[2]");
	}

	@Test
	public void testSwitchContextNegative() {
		IBasicContextSwitcher<?> bcs = getBasicContextSwitcher(3);
		WebviewIndexSwitcher<?> s = new WebviewIndexSwitcher<>(bcs);
		s.switchContext("WEBVIEW[-3]");
		Assert.assertEquals("webview-1", bcs.currentContextOrWindow());
		s.switchContext("WEBVIEW[-2]");
		Assert.assertEquals("webview-2", bcs.currentContextOrWindow());
		s.switchContext("WEBVIEW[-1]");
		Assert.assertEquals("webview-3", bcs.currentContextOrWindow());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSwitchContextNegativeOutofBound() {
		WebviewIndexSwitcher<?> s = new WebviewIndexSwitcher<>(getBasicContextSwitcher(3));
		s.switchContext("WEBVIEW[-4]");
	}

	private IBasicContextSwitcher<?> getBasicContextSwitcher(int num) {
		IBasicContextSwitcher<?> bcs = Mockito.mock(IBasicContextSwitcher.class);
		Mockito.when(bcs.useWindow()).thenReturn(false);
		if (num > 0) {
			List<String> ls = new ArrayList<>();
			for (int i = 1; i <= num; ++i) {
				ls.add("webview-" + i);
			}
			ls.add("NATIVE_APP");
			Mockito.when(bcs.contexts()).thenReturn(ls);
			Mockito.doAnswer(new Ans(bcs)).when(bcs).context(Mockito.anyString());
		}
		return bcs;
	}

	/*
	 * a class to set current context when context(String) is called
	 */
	class Ans implements Answer<Object> {
		private IBasicContextSwitcher<?> bcs;

		public Ans(IBasicContextSwitcher<?> bcs) {
			this.bcs = bcs;
		}

		@Override
		public Object answer(InvocationOnMock invocation) throws Throwable {
			String cxt = (String) invocation.getArguments()[0];
			Mockito.when(bcs.currentContextOrWindow()).thenReturn(cxt);
			return null;
		}

	}

}
