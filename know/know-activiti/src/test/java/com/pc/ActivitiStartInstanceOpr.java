package com.pc;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pc
 * @Date 2020/8/30
 **/
public class ActivitiStartInstanceOpr {

    /**
     * 开启一个流程
     */
    @Test
    public void startInstance() {
        //1、获取processEngine对象
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取runtimeService对象
        RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();
        //3、创建流程实例，也就是开启一个流程。通过流程key
        ProcessInstance myProcess_1 = runtimeService.startProcessInstanceByKey("myProcess_1");
        //4、输出信息
        System.out.println(myProcess_1.getDeploymentId());
        System.out.println(myProcess_1.getProcessDefinitionId());
        System.out.println(myProcess_1.getProcessDefinitionKey());
        System.out.println(myProcess_1.getProcessInstanceId());
        System.out.println(myProcess_1.getActivityId());
    }

    /**
     * 开启一个流程携带参数
     */
    @Test
    public void startInstanceWithParams() {
        //1、获取processEngine对象
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取runtimeService对象
        RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();
        //3、创建流程实例，也就是开启一个流程。通过流程key
//        Holiday holiday = new Holiday();
//        holiday.setNum(2f);
        Map<String, Object> params = new HashMap<>();
//        params.put("user1", "zhangsan");
//        params.put("holiday", holiday);
        //也可以刚启动的时候设置完所有的参数,也可以完成一个任务并且设置下一个任务的参数
//        params.put("user2", "lisi");
//        params.put("user3", "wangwu");
        ProcessInstance myProcess_1 = runtimeService.startProcessInstanceByKey("bingxing", params);
        //4、输出信息
        System.out.println(myProcess_1.getDeploymentId());
        System.out.println(myProcess_1.getProcessDefinitionId());
        System.out.println(myProcess_1.getProcessDefinitionKey());
        System.out.println(myProcess_1.getProcessInstanceId());
        System.out.println(myProcess_1.getId());
        System.out.println(myProcess_1.getActivityId());
    }


}
