package tasty.mushrooms.finder.mock;

import java.util.LinkedList;
import java.util.List;

import org.mockito.Mockito;

import tasty.mushrooms.finder.IBasicContextSwitcher;

public abstract class MockUtil {

	public static IBasicContextSwitcher<Object> getBasicContextSwitcher(boolean useWindow, List<String> cxts,
			List<String> wins, StateHolder holder) {
		@SuppressWarnings("unchecked")
		IBasicContextSwitcher<Object> s = Mockito.mock(IBasicContextSwitcher.class);
		Mockito.when(s.useWindow()).thenReturn(useWindow);
		if (cxts != null)
			Mockito.when(s.contexts()).thenReturn(cxts);
		if (wins != null)
			Mockito.when(s.windows()).thenReturn(wins);
		if (holder != null) {
			Mockito.when(s.currentContextOrWindow()).then(holder);
			Mockito.doAnswer(holder).when(s).context(Mockito.anyString());
			Mockito.doAnswer(holder).when(s).window(Mockito.anyString());
		}
		return s;
	}

	public static List<String> getList(String... name) {
		List<String> ls = new LinkedList<>();
		for (String n : name) {
			ls.add(n);
		}
		return ls;
	}

}
