package com.pc;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程实例操作
 * @author pc
 * @Date 2020/9/5
 **/
public class ActivitiProcessInstanceOpr {

    /**
     * 流程定义挂起操作
     */
    @Test
    public void instanceSuspend() {
        //1、获取processEngine对象
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取runtimeService与repositoryService对象
        RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();
        //3、获取流程实例对象
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionKey("myProcess_1").singleResult();
        boolean suspended = processInstance.isSuspended();
        //激活或挂起某个流程
        if (suspended) {
            runtimeService.activateProcessInstanceById(processInstance.getId());
            System.out.println("流程已激活");
        } else {
            runtimeService.suspendProcessInstanceById(processInstance.getId());
            System.out.println("流程已挂起");
        }
    }

    /**
     * 流程实例挂起操作
     */
    @Test
    public void instanceSuspendByUser() {
        //1、获取processEngine对象
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取taskService与repositoryService对象
        TaskService taskService = defaultProcessEngine.getTaskService();
        //3、获取runtimeService操作挂起与激活
        RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();
        //4、获取流程实例对象  查询当前用户的流程进行审批
        Task task = taskService.createTaskQuery().processDefinitionKey("myProcess_1").taskAssignee("zhangsan").singleResult();
        boolean suspended = task.isSuspended();
        //5、激活或挂起某个流程
        if (suspended) {
            runtimeService.activateProcessInstanceById(task.getProcessInstanceId());
            System.out.println("流程已激活");
        } else {
            runtimeService.suspendProcessInstanceById(task.getProcessInstanceId());
            System.out.println("流程已挂起");
        }
    }

    /**
     * 获取代办
     */
    @Test
    public void queryTask() {
        //1、获取processEngine对象
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取taskService对象
        TaskService taskService = defaultProcessEngine.getTaskService();
        //3、通过某个流程key、流程处理人查询当前人的任务列表
        TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKey("holiday")
                .taskAssignee("lisi");
        //可以对taskQuery进行继续筛选
        List<Task> list = taskQuery.orderByTaskCreateTime().desc().active().list();
        for (Task task : list) {
            System.out.println("流程实例ID：" + task.getProcessInstanceId());
            System.out.println("任务ID：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
            System.out.println("任务key：" + task.getTaskDefinitionKey());
        }
    }

    /**
     * 获取组任务并拾取
     */
    @Test
    public void queryCandidateUserTask() {
        //1、获取processEngine对象
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取taskService对象
        TaskService taskService = defaultProcessEngine.getTaskService();
        //3、通过某个流程key、流程处理人查询当前人的任务列表
        TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKey("holiday")
                .taskCandidateUser("lisi");
        //可以对taskQuery进行继续筛选
        Task task = taskQuery.orderByTaskCreateTime().desc().active().singleResult();
        if (task != null) {
            //当组任务被拾取后，其他组用户(包括自己)将不会看到已拾取的组任务了
            taskService.claim(task.getId(), "lisi");
            System.out.println("任务拾取成功");
        } else {
            System.out.println("无组任务");
        }
    }

    /**
     * 完成任务
     * 1、首先taskQuery查询当前用户是否拥有任务
     * 2、拿到taskId，根据taskId完成任务
     */
    @Test
    public void completeTask() {
        //1、获取processEngine对象
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取taskService对象
        TaskService taskService = defaultProcessEngine.getTaskService();
        //3、审批任务
        Task task = taskService.createTaskQuery().processDefinitionKey("bingxing").taskAssignee("tengteng").singleResult();
        if (task != null) {
            Map<String, Object> params = new HashMap<>();
//            params.put("cu1", "lisi,wangwu");
            params.put("num", 8);
            taskService.addComment(task.getId(), null, "任务完成的很好，同意");
            taskService.complete(task.getId(), params);
        } else {
            System.out.println("无任务");
        }
    }

    /**
     * 设置某个流程实例全局参数
     * 全局参数（整个实例流程起作用）与本地参数（此次任务节点起作用）
     */
    @Test
    public void setGlobalVariable() {
        //1、获取processEngine对象
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取runService
        RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();
        //3、设置variable  参数含义：流程实例id，参数key，参数value
        Holiday holiday = new Holiday();
        holiday.setNum(2f);
        runtimeService.setVariable("2501", "holiday", holiday);
        //设置多值
//        runtimeService.setVariables("", map);
    }

    /**
     * 组任务办理
     * 第一步  查询组任务
     *    指定候选人，查询该候选人当前的待办任务
     * 第二步  拾取（claim）任务
     *    该组任务的所有候选人都能拾取。将候选人的组任务变成个人任务，原来的候选人就变成了该任务的负责人
     *     如果拾取后不想办理的，需要将已拾取的个人任务归还到组任务里面区，将个人任务变成组任务
     * 第三步   查询个人任务
     *     查询方式同个人任务部分，根据assignee查询用户负责的个人任务
     * 第四步    办理个人任务
     *
     */
    @Test
    public void candidateUser() {
        //归还组任务
        //taskService.setAssignee(taskId, null); 将任务的执行人设置为null
        //1、获取processEngine对象
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取taskService对象
        TaskService taskService = defaultProcessEngine.getTaskService();
        //3、审批任务
        Task task = taskService.createTaskQuery().processDefinitionKey("holiday").taskAssignee("wangwu").singleResult();
        if (task != null) {
            //这里我们也可以将任务委托给其他负责（不是组任务的人也行）,任务交接可以这么做
            taskService.setAssignee(task.getId(), null);
        }
    }

}
