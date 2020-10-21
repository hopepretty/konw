package com.pc;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * 业务与工作流整合
 *
 * 主要是在  act_ru_execution表中的businessKey的字段存入
 *
 * 后面的任务表act_run_task是以act_ru_execution表中的数据为模板进行创建task节点任务的
 * act_ru_execution里面的每条数据相当于一个流程实例代表全局参数
 * @author pc
 * @Date 2020/9/2
 **/
public class ActivitiBusinessKeyOpr {

    /**
     * 开启流程时将业务id塞进去
     */
    @Test
    public void start() {
        //1、获取processEngine对象
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取runtimeService对象
        RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();
        //3、开启流程  参数表示为流程定义Key与业务数据ID（隐患信息ID）
        ProcessInstance myProcess_1 = runtimeService.startProcessInstanceByKey("myProcess_1", "123");
        System.out.println("流程实例业务ID：" + myProcess_1.getBusinessKey());
    }

}
