package tasty.mushrooms.data.internal.resolvers;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import tasty.mushrooms.data.IArgumentResolver;
import tasty.mushrooms.data.IRow;

public class ArgumentResolverComposite implements IArgumentResolver {
	private List<IArgumentResolver> mResolvers = new LinkedList<>();
	private Map<Parameter, IArgumentResolver> mResolverCache = new HashMap<>();

	@Override
	public boolean support(Parameter param) {
		return getResolver(param) != null;
	}

	@Override
	public Object resolve(Parameter param, IRow row) {
		IArgumentResolver vResolver = getResolver(param);
		if (vResolver == null) {
			throw new IllegalArgumentException("Unknown parameter type [" + param.getType().getName() + "]");
		}
		return vResolver.resolve(param, row);
	}

	public ArgumentResolverComposite addResolver(IArgumentResolver resolver) {
		if (resolver != null)
			mResolvers.add(resolver);
		return this;
	}

	public ArgumentResolverComposite addResolvers(IArgumentResolver... resolvers) {
		if (resolvers != null) {
			for (IArgumentResolver resolver : resolvers) {
				mResolvers.add(resolver);
			}
		}
		return this;
	}

	public void clearResolvers() {
		mResolvers.clear();
	}

	public void clearCache() {
		mResolverCache.clear();
	}

	public ArgumentResolverComposite addResolvers(List<? extends IArgumentResolver> resolvers) {
		if (resolvers != null) {
			for (IArgumentResolver resolver : resolvers) {
				mResolvers.add(resolver);
			}
		}
		return this;
	}

	private IArgumentResolver getResolver(Parameter param) {
		IArgumentResolver vResolver = mResolverCache.get(param);
		if (vResolver == null) {
			for (IArgumentResolver r : mResolvers) {
				if (r.support(param)) {
					vResolver = r;
					mResolverCache.put(param, r);
					break;
				}
			}
		}
		return vResolver;
	}

}
