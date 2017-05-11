package tasty.mushrooms.data;

import java.lang.reflect.Parameter;
import java.util.Iterator;

/**
 * 供TestNG使用的Iterator
 * 
 * @since 1.0.0
 */
public class DataIterator implements Iterator<Object[]> {
	private IDataReader mReader;
	private IRowFilter mFilter;
	private Parameter[] mParams;
	private IRow mCache;
	// 表示mCache是否被消耗，防止多次连续调用hasNext方法时返回错误信息
	private boolean mConsumed = true;

	public DataIterator(IDataReader reader, IRowFilter filter, Parameter[] params) {
		mReader = reader;
		mFilter = filter;
		mParams = params;
	}

	@Override
	public boolean hasNext() {
		boolean vRet = false;
		if (mConsumed) {
			do {
				mCache = mReader.readRow();
			} while (mCache != null && !(vRet = acceptRow(mCache)));
			mConsumed = false;
		} else {
			vRet = mCache != null;
		}
		return vRet;
	}

	@Override
	public Object[] next() {
		mConsumed = true;
		return DataProviderX.resolveArguments(mParams, mCache);
	}

	private boolean acceptRow(IRow row) {
		return mFilter == null || mFilter.accept(row);
	}

}
