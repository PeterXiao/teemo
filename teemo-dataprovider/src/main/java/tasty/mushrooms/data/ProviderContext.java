package tasty.mushrooms.data;

import java.lang.reflect.Method;

import org.testng.ITestContext;

/**
 * DataProvider上下文信息，为{@link IRowFilter}提供当前测试方法，数据分组名、TestNG上下文等信息
 * 
 * @since 1.0.0
 */
public class ProviderContext {
	private Method mMethod;
	private String mGroupName;
	private ITestContext mTestContext;

	public ProviderContext(Method method, String groupName, ITestContext testContext) {
		mMethod = method;
		mGroupName = groupName;
		mTestContext = testContext;
	}

	public Method getMethod() {
		return mMethod;
	}

	public void setMethod(Method method) {
		mMethod = method;
	}

	public String getGroupName() {
		return mGroupName;
	}

	public void setGroupName(String groupName) {
		mGroupName = groupName;
	}

	public ITestContext getTestContext() {
		return mTestContext;
	}

	public void setTestContext(ITestContext testContext) {
		mTestContext = testContext;
	}

}
