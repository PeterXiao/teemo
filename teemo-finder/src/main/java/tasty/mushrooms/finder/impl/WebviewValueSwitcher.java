package tasty.mushrooms.finder.impl;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;

import tasty.mushrooms.finder.IBasicContextSwitcher;

/**
 * 根据某个属性值（如url、title等）切换WEBVIEW，语法为WEBVIEW[属性名.start|end|contain|pattern(参数值)]
 * 
 * @param <D> 原始驱动类型
 * @since 1.0.0
 */
public class WebviewValueSwitcher<D> extends AbstractWebviewSwitcher<D> {
	private static Pattern mPattern;
	private Function<IBasicContextSwitcher<D>, String> mValueGetter;

	/**
	 * 
	 * @param name 属性名
	 * @param switcher 基本切换器
	 * @param valueGetter 属性值获取器
	 */
	public WebviewValueSwitcher(String name, IBasicContextSwitcher<D> switcher,
			Function<IBasicContextSwitcher<D>, String> valueGetter) {
		super(switcher);
		name = Objects.requireNonNull(name, "Name must not be null");
		mPattern = Pattern.compile("^WEBVIEW\\[" + name + "\\.(start|end|contain|pattern)\\(.+\\)\\]$");
		this.mValueGetter = Objects.requireNonNull(valueGetter, "ValueGetter must not be null");
	}

	@Override
	public void switchContext(String context) {
		String vContent = getContent(context);
		int vDotPos = vContent.indexOf('.');
		int vBracketPos = vContent.indexOf('(', vDotPos);
		String vMethodName = vContent.substring(vDotPos + 1, vBracketPos);

		ValueMatchMethod vMethod = ValueMatchMethod.fromString(vMethodName);
		String vParam = vContent.substring(vBracketPos + 1, vContent.length() - 1);

		List<String> vCxts = getWebivewContextsOrWindows();
		String vValue;

		for (String c : vCxts) {
			safeContextOrWindow(c);
			vValue = getValue(mValueGetter);
			if (vMethod.matches(vValue, vParam)) {
				break;
			}
		}
	}

	@Override
	protected Pattern getPattern() {
		return mPattern;
	}

	/*
	 * 获取属性值
	 */
	private String getValue(Function<IBasicContextSwitcher<D>, String> func) {
		return Objects.requireNonNull(func, "Func must not be null").apply(mSwitcher);
	}

	private enum ValueMatchMethod {
		START {
			@Override
			public boolean matches(String url, String param) {
				return url.startsWith(param);
			}
		},
		END {
			@Override
			public boolean matches(String url, String param) {
				return url.endsWith(param);
			}
		},
		CONTAIN {
			@Override
			public boolean matches(String url, String param) {
				return url.contains(param);
			}
		},
		PATTERN {
			@Override
			public boolean matches(String url, String param) {
				Pattern p = Pattern.compile(param);
				return p.matcher(url).matches();
			}
		};

		public abstract boolean matches(String url, String param);

		public static ValueMatchMethod fromString(String method) {
			for (ValueMatchMethod m : ValueMatchMethod.values()) {
				if (method.equals(m.name().toLowerCase())) {
					return m;
				}
			}
			return null;
		}

	}

}
