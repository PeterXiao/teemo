package tasty.mushrooms.finder.impl;

import java.util.List;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import tasty.mushrooms.finder.IBasicContextSwitcher;
import tasty.mushrooms.finder.mock.MockUtil;
import tasty.mushrooms.finder.mock.StateHolder;

public class AbstractWebviewSwitcherTest {

	@Test
	public void testGetWebivewContexts() {
		List<String> ls = MockUtil.getList("NATIVE_APP", "WEBVIEW_1", "WEBVIEW_2");
		IBasicContextSwitcher<Object> bcs = MockUtil.getBasicContextSwitcher(false, ls, null, null);
		TestSwitcher s = new TestSwitcher(bcs);
		List<String> cxts = s.getWebivewContextsOrWindows();
		Assert.assertEquals(2, cxts.size());
		Assert.assertEquals("WEBVIEW_1", cxts.get(0));
		Assert.assertEquals("WEBVIEW_2", cxts.get(1));
		Mockito.verify(bcs).contexts();
		Mockito.verify(bcs, Mockito.times(0)).windows();
	}

	@Test
	public void testGetWebivewWindows() {
		// current context is NATIVE_APP
		List<String> cxts = MockUtil.getList("NATIVE_APP", "WEBVIEW_1");
		List<String> wins = MockUtil.getList("CWINDOW-1", "CWINDOW-2");
		StateHolder holder = new StateHolder("NATIVE_APP");
		IBasicContextSwitcher<Object> bcs = MockUtil.getBasicContextSwitcher(true, cxts, wins, holder);
		TestSwitcher s = new TestSwitcher(bcs);
		List<String> result = s.getWebivewContextsOrWindows();
		Assert.assertEquals("WEBVIEW_1", bcs.currentContextOrWindow());
		Assert.assertEquals(2, result.size());
		Assert.assertEquals(wins.get(0), result.get(0));
		Assert.assertEquals(wins.get(1), result.get(1));

		// only switch context once
		s.getWebivewContextsOrWindows();
		s.getWebivewContextsOrWindows();
		Mockito.verify(bcs).context(Mockito.anyString());
		Mockito.verify(bcs).contexts();
	}

	@Test
	public void testGetWebivewWindows2() {
		// current context is WEBVIEW_1
		List<String> wins = MockUtil.getList("CWINDOW-1", "CWINDOW-2");
		StateHolder holder = new StateHolder("WEBVIEW_1");
		IBasicContextSwitcher<Object> bcs = MockUtil.getBasicContextSwitcher(true, null, wins, holder);
		TestSwitcher s = new TestSwitcher(bcs);
		List<String> result = s.getWebivewContextsOrWindows();
		Assert.assertEquals(2, result.size());
		Assert.assertEquals(wins.get(0), result.get(0));
		Assert.assertEquals(wins.get(1), result.get(1));

		// should not switch context
		s.getWebivewContextsOrWindows();
		s.getWebivewContextsOrWindows();
		Mockito.verify(bcs, Mockito.times(0)).context(Mockito.anyString());
		Mockito.verify(bcs, Mockito.times(0)).contexts();
	}

	@Test
	public void testSafeContextOrWindowNull() {
		TestSwitcher s = new TestSwitcher(null);
		try {
			s.safeContextOrWindow(null);
		} catch (NullPointerException e) {
			Assert.assertEquals("Context name must not be null", e.getMessage());
		}
	}

	@Test
	public void testSafeContext() {
		StateHolder holder = new StateHolder("NATIVE_APP");
		IBasicContextSwitcher<Object> bcs = MockUtil.getBasicContextSwitcher(false, null, null, holder);
		TestSwitcher s = new TestSwitcher(bcs);
		s.safeContextOrWindow("WEBVIEW_1");
		Assert.assertEquals("WEBVIEW_1", bcs.currentContextOrWindow());
		Mockito.verify(bcs).context(Mockito.anyString());
	}

	@Test
	public void testSafeContextEqual() {
		StateHolder holder = new StateHolder("WEBVIEW_1");
		IBasicContextSwitcher<Object> bcs = MockUtil.getBasicContextSwitcher(false, null, null, holder);
		TestSwitcher s = new TestSwitcher(bcs);
		s.safeContextOrWindow("WEBVIEW_1");
		Assert.assertEquals("WEBVIEW_1", bcs.currentContextOrWindow());
		Mockito.verify(bcs, Mockito.times(0)).context(Mockito.anyString());
	}

	@Test
	public void testSafeWindow() {
		// current context is NATIVE_APP
		List<String> cxts = MockUtil.getList("NATIVE_APP", "WEBVIEW_1");
		List<String> wins = MockUtil.getList("CWINDOW-1", "CWINDOW-2");
		StateHolder holder = new StateHolder("NATIVE_APP");
		IBasicContextSwitcher<Object> bcs = MockUtil.getBasicContextSwitcher(true, cxts, wins, holder);
		TestSwitcher s = new TestSwitcher(bcs);
		s.safeContextOrWindow("CWINDOW-2");
		Assert.assertEquals("CWINDOW-2", bcs.currentContextOrWindow());
		Assert.assertEquals("WEBVIEW_1", holder.currentContext);
		Mockito.verify(bcs).contexts();
		Mockito.verify(bcs).context(Mockito.anyString());
		Mockito.verify(bcs).window(Mockito.anyString());
	}

	@Test
	public void testSafeWindow2() {
		// current context is WEBVIEW
		StateHolder holder = new StateHolder("CWINDOW-1");
		IBasicContextSwitcher<Object> bcs = MockUtil.getBasicContextSwitcher(true, null, null, holder);
		TestSwitcher s = new TestSwitcher(bcs);
		s.safeContextOrWindow("CWINDOW-2");
		Assert.assertEquals("CWINDOW-2", bcs.currentContextOrWindow());
		Mockito.verify(bcs).window(Mockito.anyString());
	}

	@Test
	public void testSafeWindowEqual() {
		StateHolder holder = new StateHolder("CWINDOW-1");
		IBasicContextSwitcher<Object> bcs = MockUtil.getBasicContextSwitcher(true, null, null, holder);
		TestSwitcher s = new TestSwitcher(bcs);
		s.safeContextOrWindow("CWINDOW-1");
		Assert.assertEquals("CWINDOW-1", bcs.currentContextOrWindow());
		Mockito.verify(bcs, Mockito.times(0)).context(Mockito.anyString());
		Mockito.verify(bcs, Mockito.times(0)).window(Mockito.anyString());
	}

	@Test
	public void testGetContent() {
		TestSwitcher s = new TestSwitcher(null);
		Assert.assertEquals("", s.getContent("WEBVIEW[]"));
		Assert.assertEquals("-1", s.getContent("WEBVIEW[-1]"));
		Assert.assertEquals("0", s.getContent("WEBVIEW[0]"));
		Assert.assertEquals("url.start(123)", s.getContent("WEBVIEW[url.start(123)]"));
	}

}

class TestSwitcher extends AbstractWebviewSwitcher<Object> {

	public TestSwitcher(IBasicContextSwitcher<Object> switcher) {
		super(switcher);
	}

	@Override
	public void switchContext(String context) {
	}

	@Override
	protected Pattern getPattern() {
		return null;
	}

}
