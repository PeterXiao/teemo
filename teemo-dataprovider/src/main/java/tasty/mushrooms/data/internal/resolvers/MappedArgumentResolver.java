package tasty.mushrooms.data.internal.resolvers;

import java.lang.reflect.Parameter;

import tasty.mushrooms.commons.exception.TeemoInstantiationException;
import tasty.mushrooms.commons.util.InstantiateUtil;
import tasty.mushrooms.data.IArgumentResolver;
import tasty.mushrooms.data.IRow;
import tasty.mushrooms.data.IRowMapper;
import tasty.mushrooms.data.annotations.Mapper;

public class MappedArgumentResolver implements IArgumentResolver {
	private static final int DEFAULT_SIZE = 5;
	private IRowMapper[] mCache = new IRowMapper[DEFAULT_SIZE];
	private int mIndex;

	@Override
	public boolean support(Parameter param) {
		return param.isAnnotationPresent(Mapper.class);
	}

	@Override
	public Object resolve(Parameter param, IRow row) {
		Class<?> vClazz = param.getType();
		Object vRet = getMapper(param).map(row, vClazz);
		if (!vClazz.isInstance(vRet)) {
			throw new IllegalArgumentException("Invalid type of return value");
		}
		return vRet;
	}

	private IRowMapper getMapper(Parameter param) {
		Mapper vAnno = param.getAnnotation(Mapper.class);
		Class<? extends IRowMapper> vClazz = vAnno.value();
		IRowMapper vMapper = getMapperFromCache(vClazz);
		if (vMapper == null) {
			vMapper = createMapper(vClazz);
		}
		return vMapper;
	}

	private IRowMapper getMapperFromCache(Class<? extends IRowMapper> clazz) {
		for (IRowMapper m : mCache) {
			if (m != null && clazz == m.getClass()) {
				return m;
			}
		}
		return null;
	}

	private synchronized IRowMapper createMapper(Class<? extends IRowMapper> clazz) {
		IRowMapper vMapper = null;
		try {
			vMapper = InstantiateUtil.instantiate(clazz);
		} catch (TeemoInstantiationException e) {
			throw new RuntimeException(e.getCause());
		}
		mCache[mIndex++] = vMapper;
		if (mIndex == mCache.length) {
			mIndex = 0;
		}
		return vMapper;
	}

}
