package tasty.mushrooms.data;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import tasty.mushrooms.commons.exception.TeemoInstantiationException;
import tasty.mushrooms.commons.util.Assert;
import tasty.mushrooms.commons.util.InstantiateUtil;
import tasty.mushrooms.data.annotations.Criteria;
import tasty.mushrooms.data.annotations.Filter;
import tasty.mushrooms.data.annotations.Key;
import tasty.mushrooms.data.annotations.Mapper;
import tasty.mushrooms.data.internal.resolvers.ArgumentResolverComposite;
import tasty.mushrooms.data.internal.resolvers.CustomRecordResolver;
import tasty.mushrooms.data.internal.resolvers.MappedArgumentResolver;
import tasty.mushrooms.data.internal.resolvers.NamedArgumentResolver;
import tasty.mushrooms.data.internal.resolvers.RowResolver;

/**
 * <p>
 * TestNG通用化数据驱动，支持并发
 * </p>
 * 
 * 支持使用外部数据源，支持行过滤，可根据测试方法参数列表自动生成测试数据，默认支持以下类型的参数：
 * <ul>
 * <li>带有{@link Key}注解的基本数据类型或java.lang.String、java.util.Date</li>
 * <li>带有{@link Mapper}注解的任意数据类型</li>
 * <li>{@link IRow}类型</li>
 * <li>{@link CustomRecord}类型</li>
 * </ul>
 * 
 * <p>
 * 使用方法
 * </p>
 * <ol>
 * <li>在使用前需先调用{@link #setConfig(DPXConfig)}方法设置配置信息，
 * 其中包括要使用的IDataReader、IGroupResolver以及全局过滤器等</li>
 * <li>在测试方法的Test注解中设置dataProvider为DataProviderX.NAME或
 * DataProviderX.NAME_LAZY，设置dataProviderClass为DataProviderX.class，如下所示
 * 
 * <pre>
 * &#64;Test(dataProvider = DataProviderX.NAME, dataProviderClass = DataProviderX.class)
 * </pre>
 * 
 * </li>
 * <li>在测试方法上添加{@link Filter}注解可添加过滤器；对于支持
 * 条件查询（如数据库）的IDataReader可使用{@link Criteria}来提高效率， 若同
 * 时设置了Filter和Criteria，则优先使用后者</li>
 * <li>可添加自定义的{@link IArgumentResolver}来解析其他类型的参数，
 * 调用DataProviderX.RESOLVERS.addaddResolver方法即可</li>
 * </ol>
 * 
 * @since 1.0.0
 * 
 * @see IDataReader
 * @see IGroupResolver
 * @see IRowFilter
 * @see Criteria
 * @see ArgumentResolverComposite
 */
public abstract class DataProviderX {
	/**
	 * 返回Object[][]的DataProvider
	 */
	public static final String NAME = "dpx";
	/**
	 * 返回Object[][]的DataProvider，启用并发
	 */
	public static final String NAME_PARALLEL = "dpx_parallel";
	/**
	 * 返回Iterator<Object[]>的DataProvider
	 */
	public static final String NAME_LAZY = "dpx_lazy";
	/**
	 * 返回Iterator<Object[]>的DataProvider，启用并发
	 */
	public static final String NAME_LAZY_PARALLEL = "dpx_lazy_parallel";

	public static final ArgumentResolverComposite RESOLVERS = new ArgumentResolverComposite();

	private static final Log logger = LogFactory.getLog(DataProviderX.class);

	private static DPXConfig mConfig;
	private static Method mLastMethod;

	static {
		RESOLVERS.addResolver(new NamedArgumentResolver());
		RESOLVERS.addResolver(new MappedArgumentResolver());
		RESOLVERS.addResolver(new RowResolver());
		RESOLVERS.addResolver(new CustomRecordResolver());
	}

	@DataProvider(name = NAME)
	public static Object[][] data(Method method, ITestContext cxt) {
		if (logger.isInfoEnabled()) {
			logger.info(method.getName() + " is using DataProviderX for Object[][]");
		}

		// 检查配置
		checkConfig();
		// 检查参数
		Parameter[] vParams = checkAndGetParam(method);
		// 方法变更时清除Resolver缓存
		clearResolverCache(method);
		// 获取数据分组名
		String vGroup = mConfig.getGroupResovler().resolve(method);
		logger.debug("Group Name: " + vGroup);
		// 处理Reader
		IDataReader vReader = mConfig.getReader();
		vReader.changeGroup(vGroup);
		boolean vUseCriteria = resetCriteria(vReader, method);
		IRowFilter vFilter = null;
		if (!vUseCriteria) {
			vFilter = getFilter(method);
			if (vFilter != null) {
				vFilter.setContext(new ProviderContext(method, vGroup, cxt));
			}
		}
		// 读取数据
		List<Object[]> vRows = new LinkedList<>();
		IRow vRow;
		while ((vRow = vReader.readRow()) != null) {
			if (vFilter != null && !vFilter.accept(vRow)) {
				continue;
			}
			vRows.add(resolveArguments(vParams, vRow));
		}

		Object[][] vRet = new Object[vRows.size()][];
		return vRows.toArray(vRet);
	}

	@DataProvider(name = NAME_PARALLEL, parallel = true)
	public static Object[][] dataParallel(Method method, ITestContext cxt) {
		return data(method, cxt);
	}

	@DataProvider(name = NAME_LAZY)
	public static Iterator<Object[]> dataLazy(Method method, ITestContext cxt) {
		if (logger.isInfoEnabled()) {
			logger.info(method.getName() + " is using DataProviderX for Iterator<Object[]>");
		}

		// 检查配置
		checkConfig();
		// 检查参数
		Parameter[] vParams = checkAndGetParam(method);
		// 方法变更时清除Resolver缓存
		clearResolverCache(method);
		// 获取数据分组名
		String vGroup = mConfig.getGroupResovler().resolve(method);
		logger.debug("Group Name: " + vGroup);
		// 处理Reader
		IDataReader vReader = mConfig.getReader();
		// 调用changeGroup方法的线程和调用Iterator方法的线程为同一个
		// 因此无需特殊处理
		vReader.changeGroup(vGroup);
		boolean vUseCriteria = resetCriteria(vReader, method);
		IRowFilter vFilter = null;
		if (!vUseCriteria) {
			vFilter = getFilter(method);
			if (vFilter != null) {
				vFilter.setContext(new ProviderContext(method, vGroup, cxt));
			}
		}
		return new DataIterator(vReader, vFilter, vParams);
	}

	@DataProvider(name = NAME_LAZY_PARALLEL, parallel = true)
	public static Iterator<Object[]> dataLazyParallel(Method method, ITestContext cxt) {
		return dataLazy(method, cxt);
	}

	/**
	 * 设置配置信息
	 * 
	 * @param config 配置信息
	 */
	public static void setConfig(DPXConfig config) {
		mConfig = config;
	}

	/*
	 * 检查配置是否为空
	 */
	private static void checkConfig() {
		Assert.state(mConfig != null, "Configuration is null, please set it first");
	}

	/*
	 * 检查方法是否有参数
	 */
	private static Parameter[] checkAndGetParam(Method method) {
		Parameter[] vParams = method.getParameters();
		Assert.isTrue(vParams.length > 0, "Method with no parameter can't use DataProviderX");
		return vParams;
	}

	/*
	 * 切换方法时清除Resolver缓存
	 */
	private synchronized static void clearResolverCache(Method method) {
		if (mLastMethod != method) {
			RESOLVERS.clearCache();
			mLastMethod = method;
		}
	}

	/*
	 * 设置查询条件，优先注解之后全局
	 */
	private static boolean resetCriteria(IDataReader reader, Method method) {
		// 先清除Reader中的Criteria
		reader.setCriteria(null);
		// 仅支持条件查询时Criteria才会生效
		if (reader.supportQuery()) {
			Criteria vCrit = method.getAnnotation(Criteria.class);
			String vCritStr;
			// 优先使用方法注解，然后使用全局
			if (vCrit == null || (vCritStr = vCrit.value()).length() == 0) {
				vCritStr = mConfig.getCriteria();
			}
			// 设置Criteria
			if (vCritStr != null && vCritStr.length() > 0) {
				reader.setCriteria(vCritStr);
				return true;
			}
		}
		return false;
	}

	/*
	 * 获取过滤器，优先注解之后全局
	 */
	private static IRowFilter getFilter(Method method) {
		Filter vAnnoFilter = method.getAnnotation(Filter.class);
		Class<? extends IRowFilter> vClazz;
		if (vAnnoFilter == null || (vClazz = vAnnoFilter.value()) == IRowFilter.class) {
			return mConfig.getRowFilter();
		}
		try {
			return InstantiateUtil.instantiate(vClazz, IRowFilter.class);
		} catch (TeemoInstantiationException e) {
			throw new RuntimeException(e.getCause());
		}
	}

	/*
	 * 解析生成数据
	 */
	static Object[] resolveArguments(Parameter[] params, IRow row) {
		Object[] vRet = new Object[params.length];
		for (int i = 0; i < params.length; ++i) {
			vRet[i] = RESOLVERS.resolve(params[i], row);
		}
		return vRet;
	}

}
