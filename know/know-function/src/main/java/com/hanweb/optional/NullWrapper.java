package com.hanweb.optional;

import java.util.Objects;
import java.util.function.Function;

/**
 * 空指针包装类
 * 工具类，用于简化空指针的探测工作
 * 核心思想：将可能为null的value包装成NullWrapper对象，那么调用nullWrapper.xxx()方法肯定就不会发生NullPointerException
 * 核心方法：map()
 * map()方法由NullWrapper对象调用，实际操作的是nullWrapper内部的value，将value映射为指定的值（比如某个字段）
 * 如果value为null，调用empty()方法，返回包装了null的NullWrapper对象
 * 如果value不为null，执行mapper.apply(value)得到结果并调用ofNullable(T newValue)方法，返回包装了newValue的NullWrapper对象
 *
 * @author pc
 * @Date 2021/1/11
 **/
public final class NullWrapper<T> {

	/**
	 * 返回一个包装null的wrapper对象
	 *
	 */
	private static final NullWrapper<?> EMPTY = new NullWrapper<>();

	/**
	 * 包装值
	 *
	 */
	private final T value;

	/**
	 * 构造器，包装null
	 */
	private NullWrapper() {
		this.value = null;
	}

	/**
	 * 包装值
	 *
	 * @param value
	 */
	private NullWrapper(T value) {
		this.value = Objects.requireNonNull(value);
	}

	/**
	 * 返回null值包装类
	 *
	 * @param <T>
	 * @return
	 */
	public static <T> NullWrapper<T> empty() {
		return (NullWrapper<T>) EMPTY;
	}

	/**
	 * 包装一个非空值，如果参数为null则报空指针异常
	 *
	 * @param value
	 * @param <T>
	 * @return
	 */
	public static <T> NullWrapper<T> of(T value) {
		return new NullWrapper<>(value);
	}

	/**
	 * 包装一个允许为null的包装对象
	 *
	 * @param value
	 * @param <T>
	 * @return
	 */
	public static <T> NullWrapper<T> ofNullable(T value) {
		return value == null ? empty() : of(value);
	}

	/**
	 * 判断值是否存在
	 *
	 * @return
	 */
	public boolean isPresent() {
		return value != null;
	}

	/**
	 * 核心方法
	 * 调用者：NullWrapper对象A，内部含有value，可能为null，也可能不为null
	 * 如果value为null，调用empty()方法，返回包装了null的NullWrapper对象
	 * 如果value不为null，执行mapper.apply(value)得到结果并调用ofNullable(T newValue)方法，返回包装了newValue的NullWrapper对象
	 * 简而言之，每次调用map()，都会剥掉一层外壳，也意味着躲过了一次潜在的NullPointerException
	 *
	 * @param mapper
	 * @param <R>
	 * @return
	 */
	public <R> NullWrapper<R> map(Function<? super T, ? extends R> mapper) {
		Objects.requireNonNull(mapper);
		if (!isPresent()) {
			return empty();
		} else {
			return ofNullable(mapper.apply(value));
		}
	}

	/**
	 * 终端操作
	 *
	 * @param other
	 * @return
	 */
	public T orElse(T other) {
		return value != null ? value : other;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof NullWrapper)) {
			return false;
		}
		NullWrapper<?> other = (NullWrapper<?>) obj;
		return Objects.equals(value, other.value);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}

	@Override
	public String toString() {
		return value != null
				? String.format("Optional[%s]", value)
				: "Optional.empty";
	}

	/**
	 * 简写的最终代码
	 *
	 * @param username
	 * @return
	 */
//	public static String getDepartmentNameOfUser(String username) {
//		return NullWrapper.ofNullable(getUserByName(username))
//				.map(ResultTO::getData)
//				.map(User::getDepartment)
//				.map(Department::getName)
//				.orElse("未知部门");
//	}

}
