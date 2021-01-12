package com.pc.config;

import com.pc.server.Server1;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * webservice配置类
 *
 * @author pc
 * @Date 2020/12/9
 **/
@Configuration
public class WebServiceConfig {

	@Autowired
	private Server1 server1;

	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
		return new SpringBus();
	}

	/**
	 * 此方法作用是改变项目中服务名的前缀名，此处127.0.0.1或者localhost不能访问时，请使用ipconfig查看本机ip来访问
	 * 此方法被注释后, 即不改变前缀名(默认是services), wsdl访问地址为 http://127.0.0.1:8080/services/ws/api?wsdl
	 * 去掉注释后wsdl访问地址为：http://127.0.0.1:8080/soap/ws/api?wsdl
	 * http://127.0.0.1:8080/soap/列出服务列表 或 http://127.0.0.1:8080/soap/ws/api?wsdl 查看实际的服务
	 * 新建Servlet记得需要在启动类添加注解：@ServletComponentScan
	 *
	 * 如果启动时出现错误：not loaded because DispatcherServlet Registration found non dispatcher servlet dispatcherServlet
	 * 可能是springboot与cfx版本不兼容。
	 * 同时在spring boot2.0.6之后的版本与xcf集成，不需要在定义以下方法，直接在application.properties配置文件中添加：
	 * cxf.path=/service（默认是services）
	 */
	//@Bean
	//public ServletRegistrationBean dispatcherServlet() {
	//    return new ServletRegistrationBean(new CXFServlet(), "/soap/*");
	//}

	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(springBus(), server1);
		endpoint.publish("/ws/api");
		return endpoint;
	}

}
