package com.pc.core;

/**
 * @author pc
 * @Date 2020/12/4
 **/
public class UserDto extends BaseConverter<User> {

	private String name;

	private Integer age;

	public UserDto(Class<User> eClazz) {
		super(eClazz);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
