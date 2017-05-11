# teemo-dataprovider

> TestNG通用数据驱动，简化DataProvider的使用

- 自动识别参数类型和个数
- 支持外部数据源
- 支持并发
- 支持行过滤

# 用法

## 设置DataProviderX配置信息

配置信息中可以设置IDataReader、IGroupResolver、IRowFilter、Criteria

1. IDataReader用于读取数据源，框架提供了两种默认实现分别用于读取Excel和数据库：
   - tasty.mushrooms.data.impl.excel.ExcelReader
   - tasty.mushrooms.data.impl.db.DBReader
2. IGroupResolver用于根据当前测试方法解析其数据分组名，框架默认实现为
   - tasty.mushrooms.data.impl.DefaultGroupResolver
3. IRowFilter为全局过滤器，若测试方法上添加了 @Filter 注解，则此全局过滤器会被覆盖
4. Criteria为全局查询条件，仅对支持条件查询的数据源（如数据库）生效，若测试方法上添加了 @Criteria 注解，则此全局查询条件会被覆盖

示例代码：

```java
// 使用默认配置：IDataReader使用ExcelReader，IGroupResolver使用DefaultGroupResolver
DPXConfig config = DPXConfig.buildDefault("data.xls"); 
DataProviderX.setConfig(config);

// 也可以自己创建
IDataReader reader = new DBReader("jdbc:sqlite:data.db", "org.sqlite.JDBC");
IGroupResolver groupResovler = new DefaultGroupResolver();
DPXConfig config = new DPXConfig(reader, groupResovler);
DataProviderX.setConfig(config);
```

## 设置测试方法注解

示例代码：

```java
@Test(dataProvider = DataProviderX.NAME, dataProviderClass = DataProviderX.class)
```

DataProviderX提供了4个DataProvider方法，均可通过常量引用，分别是

| 常量                             | 说明                                           |
| -------------------------------- | ---------------------------------------------- |
| DataProviderX.NAME               | 返回Object[][]的DataProvider                   |
| DataProviderX.NAME_PARALLEL      | 返回Object[][]的DataProvider，启用并发         |
| DataProviderX.NAME_LAZY          | 返回Iterator<Object[]>的DataProvider           |
| DataProviderX.NAME_LAZY_PARALLEL | 返回Iterator<Object[]>的DataProvider，启用并发 |

## 设置数据分组名

在使用DefaultGroupResolver时，可以在测试方法上添加@DataGroup注解来声明该方法的分组名，否则将使用 ***类名_方法名*** 作为分组名；若使用其他的 IGroupResolver，请按照其规则设置数据分组名

## 支持的参数类型

DataProviderX 默认支持以下类型的参数：

- 带有 @Key 注解的基本数据类型或java.lang.String、java.util.Date
- 带有 @Mapper 注解的任意数据类型
- IRow 类型
- CustomRecord 类型

可以通过添加 IArgumentResolver 的实现类来让 DataProviderX 支持更多类型的参数，步骤如下：

1. 创建一个类，实现 IArgumentResolver 接口，定义支持的参数格式和解析过程
2. 调用 DataProviderX.RESOLVERS.addResolver() 方法添加刚才的类

示例代码：

```java
/**
 * 解析String[]类型的参数
 */
public class ArrayResolver implements IArgumentResolver {

	@Override
	public boolean support(Parameter param) {
		return param.getType() == String[].class;
	}

	@Override
	public Object resolve(Parameter param, IRow row) {
		int len = row.getColumnNumbers();
		String[] arr = new String[len];
		for (int i = 0; i < len; ++i) {
			arr[i] = row.getString(i);
		}
		return arr;
	}

}

// 在使用DataProviderX前调用以下代码即可添加对String[]类型参数的支持
DataProviderX.RESOLVERS.addResolver(new ArrayResolver());
```

## 数据过滤

过滤数据有两种方式：

1. 在 DPXConfig 中设置全局过滤器或全局查询条件
2. 在测试方法上添加 @Filter 或 @Criteria 注解声明过滤器或查询条件

*注：测试方法上声明的过滤属性将会覆盖全局过滤属性*

Criteria 和 Filter 的区别

1. Filter 是先读取数据，之后根据过滤规则决定丢弃或使用该数据，适用于txt等不支持支持条件查询的数据源
2. Criteria 是直接根据条件读取数据，不需在读取后处理数据，适用于数据库等支持条件查询的数据源