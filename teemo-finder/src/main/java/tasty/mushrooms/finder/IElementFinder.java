package tasty.mushrooms.finder;

import java.util.List;

/**
 * 元素查找器
 * 
 * @param <D> 原始Driver的类型
 * @param <E> 找到元素的类型
 */
public interface IElementFinder<D, E> extends IDriverWrapper<D> {

	/**
	 * 根据定位信息查找单个元素
	 * 
	 * @param locateInfo 定位信息
	 * @return 找到的元素对象
	 */
	E findElement(LocateInfo locateInfo);

	/**
	 * 根据定位信息查找多个元素
	 * 
	 * @param locateInfo 定位信息
	 * @return 找到的元素列表
	 */
	List<E> findElements(LocateInfo locateInfo);

}
