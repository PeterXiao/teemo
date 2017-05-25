package tasty.mushrooms.finder.impl;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import tasty.mushrooms.finder.IBasicContextSwitcher;
import tasty.mushrooms.finder.mock.MockUtil;
import tasty.mushrooms.finder.mock.StateHolder;

public class WebviewValueSwitcherTest {

	@Test
	public void testGetPattern() {
		IBasicContextSwitcher<Object> bcs = MockUtil.getBasicContextSwitcher(false, null, null, null);
		WebviewValueSwitcher<Object> s = new WebviewValueSwitcher<>("url", bcs, getValueGetter());
		Pattern p = s.getPattern();
		Assert.assertNotNull(p);
		Assert.assertTrue(p.matcher("WEBVIEW[url.start(123)]").matches());
		Assert.assertTrue(p.matcher("WEBVIEW[url.end(123)]").matches());
		Assert.assertFalse(p.matcher("WEBVIEW[url.start()]").matches());
	}

	@Test
	public void testSwitchContext() {
		List<String> cxts = MockUtil.getList("NATIVE_APP", "WEBVIEW_1", "WEBVIEW_2", "WEBVIEW_3");
		StateHolder holder = new StateHolder("NATIVE_APP");
		IBasicContextSwitcher<Object> bcs = MockUtil.getBasicContextSwitcher(false, cxts, null, holder);
		WebviewValueSwitcher<Object> s = new WebviewValueSwitcher<>("url", bcs, getValueGetter());
		s.switchContext("WEBVIEW[url.start(WEBVIEW_2)]");
		Assert.assertEquals("WEBVIEW_2", bcs.currentContextOrWindow());
		s.switchContext("WEBVIEW[url.end(url/WEBVIEW_1)]");
		Assert.assertEquals("WEBVIEW_1", bcs.currentContextOrWindow());
		s.switchContext("WEBVIEW[url.contain(3)]");
		Assert.assertEquals("WEBVIEW_3", bcs.currentContextOrWindow());
		s.switchContext("WEBVIEW[url.pattern(.*[0-9]$)]");
		Assert.assertEquals("WEBVIEW_1", bcs.currentContextOrWindow());
	}

	@Test(expected = IllegalStateException.class)
	public void testSwitchContextNoMatch() {
		List<String> cxts = MockUtil.getList("NATIVE_APP", "WEBVIEW_1", "WEBVIEW_2", "WEBVIEW_3");
		StateHolder holder = new StateHolder("NATIVE_APP");
		IBasicContextSwitcher<Object> bcs = MockUtil.getBasicContextSwitcher(false, cxts, null, holder);
		WebviewValueSwitcher<Object> s = new WebviewValueSwitcher<>("url", bcs, getValueGetter());
		s.switchContext("WEBVIEW[url.pattern(^[0-9])]");
		Mockito.verify(bcs, Mockito.times(3)).context(Mockito.anyString());
		Assert.assertEquals("WEBVIEW_3", bcs.currentContextOrWindow());
	}

	@Test
	public void testSwitchWindow() {
		// current is native_app
		List<String> cxts = MockUtil.getList("NATIVE_APP", "WEBVIEW_2");
		List<String> wins = MockUtil.getList("CWINDOW-1", "CWINDOW-2", "CWINDOW-3");
		StateHolder holder = new StateHolder("NATIVE_APP");
		IBasicContextSwitcher<Object> bcs = MockUtil.getBasicContextSwitcher(true, cxts, wins, holder);
		WebviewValueSwitcher<Object> s = new WebviewValueSwitcher<>("url", bcs, getValueGetter());
		s.switchContext("WEBVIEW[url.start(CWINDOW-2)]");
		Assert.assertEquals("CWINDOW-2", bcs.currentContextOrWindow());
		s.switchContext("WEBVIEW[url.end(url/CWINDOW-1)]");
		Assert.assertEquals("CWINDOW-1", bcs.currentContextOrWindow());
		s.switchContext("WEBVIEW[url.contain(3)]");
		Assert.assertEquals("CWINDOW-3", bcs.currentContextOrWindow());
		s.switchContext("WEBVIEW[url.pattern(.*[0-9]$)]");
		Assert.assertEquals("CWINDOW-1", bcs.currentContextOrWindow());
	}

	@Test
	public void testSwitchWindow2() {
		// current is webview
		List<String> cxts = MockUtil.getList("NATIVE_APP", "WEBVIEW_2");
		List<String> wins = MockUtil.getList("CWINDOW-1", "CWINDOW-2", "CWINDOW-3");
		StateHolder holder = new StateHolder("WEBVIEW_2");
		IBasicContextSwitcher<Object> bcs = MockUtil.getBasicContextSwitcher(true, cxts, wins, holder);
		WebviewValueSwitcher<Object> s = new WebviewValueSwitcher<>("url", bcs, getValueGetter());
		s.switchContext("WEBVIEW[url.start(CWINDOW-2)]");
		Assert.assertEquals("CWINDOW-2", bcs.currentContextOrWindow());
		s.switchContext("WEBVIEW[url.end(url/CWINDOW-1)]");
		Assert.assertEquals("CWINDOW-1", bcs.currentContextOrWindow());
		s.switchContext("WEBVIEW[url.contain(3)]");
		Assert.assertEquals("CWINDOW-3", bcs.currentContextOrWindow());
		s.switchContext("WEBVIEW[url.pattern(.*[0-9]$)]");
		Assert.assertEquals("CWINDOW-1", bcs.currentContextOrWindow());
	}

	@Test(expected = IllegalStateException.class)
	public void testSwitchWindowNoMatch() {
		List<String> cxts = MockUtil.getList("NATIVE_APP", "WEBVIEW_2");
		List<String> wins = MockUtil.getList("CWINDOW-1", "CWINDOW-2", "CWINDOW-3");
		StateHolder holder = new StateHolder("NATIVE_APP");
		IBasicContextSwitcher<Object> bcs = MockUtil.getBasicContextSwitcher(true, cxts, wins, holder);
		WebviewValueSwitcher<Object> s = new WebviewValueSwitcher<>("url", bcs, getValueGetter());
		s.switchContext("WEBVIEW[url.pattern(^[0-9])]");
		Mockito.verify(bcs, Mockito.times(3)).window(Mockito.anyString());
		Assert.assertEquals("CWINDOW-3", bcs.currentContextOrWindow());
	}

	private Function<IBasicContextSwitcher<Object>, String> getValueGetter() {
		return new Function<IBasicContextSwitcher<Object>, String>() {
			@Override
			public String apply(IBasicContextSwitcher<Object> t) {
				// current/url/current
				String c = t.currentContextOrWindow();
				return c + "/url/" + c;
			}
		};
	}

}
