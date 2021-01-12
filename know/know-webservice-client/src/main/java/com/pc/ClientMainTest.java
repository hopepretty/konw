package com.pc;

import com.pc.server.Server1;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

/**
 * 客户端
 *
 * @author pc
 * @Date 2020/12/9
 **/
public class ClientMainTest {

	public static void main(String[] args) {
//		test1();
		test2();
	}

	/**
	 * 1.代理类工厂的方式,需要拿到对方的接口地址, 同时需要引入接口
	 */
    public static void test2(){
        // 接口地址
        String address = "http://localhost:8080/services/ws/api?wsdl";
        // 代理工厂
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        // 设置代理地址
        jaxWsProxyFactoryBean.setAddress(address);
        // 设置接口类型
        jaxWsProxyFactoryBean.setServiceClass(Server1.class);
        // 创建一个代理接口实现
        Server1 us = (Server1) jaxWsProxyFactoryBean.create();
        // 数据准备
        String data = "hello world";
        // 调用代理接口的方法调用并返回结果
        String result = us.service1(data);
        System.out.println("返回结果:" + result);
    }

	/**
	 * 2. 动态调用
	 */
	public static void test1(){
		// 创建动态客户端
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient("http://localhost:8080/services/ws/api?wsdl");
		// 需要密码的情况需要加上用户名和密码
		// client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME, PASS_WORD));
		Object[] objects = new Object[0];
		try {
			// invoke("方法名",参数1,参数2,参数3....);
			//这里注意如果是复杂参数的话，要保证复杂参数可以序列化
			objects = client.invoke("service1", "hello world");
			System.out.println("返回数据:" + objects[0]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
	}

}
