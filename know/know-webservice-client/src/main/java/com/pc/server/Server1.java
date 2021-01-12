package com.pc.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * webservice服务接口
 *
 * @author pc
 * @Date 2020/12/9
 **/
@WebService(name = "Server1", targetNamespace = "http://server.pc.com")
public interface Server1 {

	/**
     * 对外提供的接口服务
	 *
	 * @param data
     * @return
     */
	@WebMethod
	String service1(@WebParam String data);

}
