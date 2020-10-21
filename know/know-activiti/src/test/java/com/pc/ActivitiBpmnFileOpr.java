package com.pc;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 资源文件操作
 * 1、从act_ge_bytearray表中读取资源文件数据
 * 2、将文件保存指定目录
 *
 * 需求：用户想看流程是怎么走的
 * @author pc
 * @Date 2020/9/2
 **/
public class ActivitiBpmnFileOpr {

    /**
     * 操作文件
     */
    @Test
    public void test() throws Exception {
        //1、获取流程引擎
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取repositoryServiced对象
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        //3、获取流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("myProcess_1").singleResult();
        //4、获取流程定义ID
        String deploymentId = processDefinition.getDeploymentId();
        //5、获取bpmn资源文件信息
        InputStream resourceAsStream = repositoryService.getResourceAsStream(deploymentId, processDefinition.getResourceName());
        //6、获取png图片信息
//        repositoryService.getResourceAsStream(deploymentId, processDefinition.getDiagramResourceName());
        //7、操作输入流
        OutputStream outputStream = new FileOutputStream("D:\\test\\activiti\\" + processDefinition.getResourceName());
        IOUtils.copy(resourceAsStream, outputStream);
        //8、关闭流
        resourceAsStream.close();
        outputStream.close();
    }

}
