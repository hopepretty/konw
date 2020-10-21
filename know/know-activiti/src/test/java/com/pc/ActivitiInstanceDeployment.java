package com.pc;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

/**
 *
 *      act_hi_actinst  已完成的活动信息
 *      act_hi_identitylink  参与者信息
 *      act_hi_procinst   流程实例
 *      act_hi_taskinst   任务实例
 *      act_hi_execution   执行表
 *      act_hi_identitylink  参与者信息
 *      act_hi_task   任务
 *
 * @author pc
 * @Date 2020/8/30
 **/
public class ActivitiInstanceDeployment {

    /**
     * 流程部署
     */
    @Test
    public void test() {
        //1、创建ProcessEngine对象
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        //2、创建repositoryService实例
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment().disableSchemaValidation()
                                .addClasspathResource("bpm/bingxing.bpmn")
                                .name("请假流程").deploy();
        //3、输出部署结果
        System.out.println(deploy.getId());
//        System.out.println(deploy.getKey());
        System.out.println(deploy.getName());
    }

}
