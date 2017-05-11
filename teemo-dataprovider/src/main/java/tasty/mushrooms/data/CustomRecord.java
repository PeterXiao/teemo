package tasty.mushrooms.data;

import java.util.HashMap;
import java.util.Map;

import tasty.mushrooms.commons.util.Assert;
import tasty.mushrooms.data.internal.resolvers.CustomRecordResolver;

/**
 * 在测试方法参数列表中添加该类型参数以记录自定义信息
 * 
 * @since 1.0.0
 * @see CustomRecordResolver
 */
public class CustomRecord {
	private Map<String, Object> mContainer = new HashMap<>();

	public void put(String key, Object value) {
		Assert.notNull(key, "Value must not be null");
		mContainer.put(key, value);
	}

	public Object get(String key) {
		return mContainer.get(key);
	}

}
