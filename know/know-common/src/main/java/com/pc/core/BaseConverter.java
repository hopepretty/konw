package com.pc.core;

import org.springframework.beans.BeanUtils;

/**
 * 转化基类
 *
 * @author pc
 * @Date 2020/12/4
 **/
public abstract class BaseConverter<E> {

	/**
	 * 需要转化类的类型
	 *
	 */
	private Class<E> eClazz;

	/**
	 * 需要子类传入类型
	 *
	 * @param eClazz
	 */
	protected BaseConverter(Class<E> eClazz) {
		this.eClazz = eClazz;
	}

	/**
	 * 将子类属性值转化成相应对象属性中去
	 *
	 * @return
	 */
	public E convertTo() {
		E e = null;
		try {
			e = eClazz.newInstance();
			BeanUtils.copyProperties(this, e);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return e;
	}

	/**
	 * 反转
	 *
	 * @param e
	 */
	public void convertBack(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		BeanUtils.copyProperties(e, this);
	}

	private static void main(String[] args) {
		UserDto userDto = new UserDto(User.class);
		userDto.setAge(12);
		userDto.setName("张三");

		User user = userDto.convertTo();
		System.out.println(user.getName());
		System.out.println(user.getAge());

		UserDto userDto1 = new UserDto(User.class);
		userDto1.convertBack(user);
		System.out.println(userDto1.getName());
		System.out.println(userDto1.getAge());
	}

}
