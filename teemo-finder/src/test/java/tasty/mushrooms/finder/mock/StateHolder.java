package tasty.mushrooms.finder.mock;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class StateHolder implements Answer<String> {
	public String currentContext;
	public String currentWindow;

	public StateHolder(String currentContext) {
		this.currentContext = currentContext;
	}

	@Override
	public String answer(InvocationOnMock invocation) throws Throwable {
		String name = invocation.getMethod().getName();
		if ("context".equals(name)) {
			currentContext = (String) invocation.getArguments()[0];
		} else if ("window".equals(name)) {
			currentWindow = (String) invocation.getArguments()[0];
		} else if ("currentContextOrWindow".equals(name)) {
			if (currentWindow != null)
				return currentWindow;
			return currentContext;
		}
		return null;
	}

}