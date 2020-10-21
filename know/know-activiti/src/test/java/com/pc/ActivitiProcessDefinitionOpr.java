package com.pc;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;

import java.util.List;

/**
 * 流程定义查询
 * @author pc
 * @Date 2020/9/2
 **/
public class ActivitiProcessDefinitionOpr {

    /**
     * 流程定义查询
     */
    @Test
    public void query() {
        //1、获取流程引擎
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取repositoruService对象
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        //3、获取processDefinitionQuery对象
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        //4、设置查询条件
        ProcessDefinitionQuery myProcess_1 = processDefinitionQuery.processDefinitionKey("myProcess_1").orderByProcessDefinitionVersion().desc();
        List<ProcessDefinition> list = myProcess_1.list();
        //5、输出结果
        for (ProcessDefinition processDefinition : list) {
            System.out.println("流程部署ID：" + processDefinition.getDeploymentId());
            System.out.println("流程定义ID：" + processDefinition.getId());
            System.out.println("流程定义KEY：" + processDefinition.getKey());
            System.out.println("流程定义名称：" + processDefinition.getName());
        }
    }

    /**
     * 删除流程定义
     */
    @Test
    public void removeProceessDefinition() {
        //1、获取流程引擎
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取repositoruService对象
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        //3、如果有流程实例正在执行，使用下面方法是不可以删除流程定义信息的
        repositoryService.deleteDeployment("1");
        //可以使用下面方法进行级联删除
        repositoryService.deleteDeployment("1", true);
    }

    /**
     * 激活或挂起所有流程定义与流程实例
     * 挂起的结果：1、流程定义无法发起新的流程实例
     *              2、会挂起该流程定义下的u所有流程实例
     */
    @Test
    public void activateOrSuspend() {
        //1、获取流程引擎
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取repositoruService对象
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        //3、获取流程定义对象
        ProcessDefinition myProcess_1 = repositoryService.createProcessDefinitionQuery().processDefinitionKey("myProcess_1").singleResult();
        //4、操作对象
        boolean suspended = myProcess_1.isSuspended();
        if (suspended) {
            //三个参数：流程定义ID， 是否激活， 激活时间点
            repositoryService.activateProcessDefinitionById(myProcess_1.getId(), true, null);
            System.out.println("已全部激活");
        } else {
            //三个参数：流程定义ID， 是否挂起， 激活时间点
            repositoryService.suspendProcessDefinitionById(myProcess_1.getId(), true, null);
            System.out.println("已全部挂起");
        }
    }

}
