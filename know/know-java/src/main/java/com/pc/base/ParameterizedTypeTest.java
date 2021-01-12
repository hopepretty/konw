package com.pc.base;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 参数化对象测试
 *
 * @author pc
 * @Date 2020/12/4
 **/
public class ParameterizedTypeTest {

	public static void main(String[] args) {
		//获取对象的变量参数化类型
		Field[] declaredFields = PtBean.class.getDeclaredFields();
		for (Field declaredField : declaredFields) {
			Type genericType = declaredField.getGenericType();
			if (genericType instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) genericType;
				Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
				System.out.print("变量名称：" + parameterizedType.getTypeName());
				for (Type actualTypeArgument : actualTypeArguments) {
					System.out.print("变量参数类型：" + actualTypeArgument.getTypeName() + "  ");
				}
				System.out.println();
			}
		}
		//获取对象参数化变量的类型
		for (Field declaredField : declaredFields) {
			Type genericType = declaredField.getGenericType();
			if (genericType instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) genericType;
				System.out.println("变量名称：" + parameterizedType.getRawType().getTypeName());
			}
		}
	}

	/**
	 * 测试类
	 *
	 */
	public static class PtBean {

		List<String> list1;
		List list2;
		Map<String,Long> map1;
		Map map2;
		Map.Entry<Long,Short> map3;

		public List<String> getList1() {
			return list1;
		}

		public void setList1(List<String> list1) {
			this.list1 = list1;
		}

		public List getList2() {
			return list2;
		}

		public void setList2(List list2) {
			this.list2 = list2;
		}

		public Map<String, Long> getMap1() {
			return map1;
		}

		public void setMap1(Map<String, Long> map1) {
			this.map1 = map1;
		}

		public Map getMap2() {
			return map2;
		}

		public void setMap2(Map map2) {
			this.map2 = map2;
		}

		public Map.Entry<Long, Short> getMap3() {
			return map3;
		}

		public void setMap3(Map.Entry<Long, Short> map3) {
			this.map3 = map3;
		}
	}

}
