package com.pc;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.junit.Test;

import java.util.List;

/**
 * 历史数据操作
 * @author pc
 * @Date 2020/9/2
 **/
public class ActivitiHistoryOpr {

    //1、获取流程引擎
    ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 历史活动流程查询
     */
    @Test
    public void query() {
        //2、获取historyService
        HistoryService historyService = defaultProcessEngine.getHistoryService();
        //3、获取HistoricActivityInstanceQuery
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        //4、查询历史流程结果
        List<HistoricActivityInstance> list = historicActivityInstanceQuery.processInstanceId("2501").orderByHistoricActivityInstanceStartTime().asc().list();
        //4、输出结果
        for (HistoricActivityInstance historicActivityInstance : list) {
            System.out.println("流程actId：" + historicActivityInstance.getActivityId());
            System.out.println("流程actKey：" + historicActivityInstance.getActivityName());
            System.out.println("流程处理人：" + historicActivityInstance.getAssignee());
            System.out.println("流程结束时间：" + historicActivityInstance.getEndTime());
        }
    }

    /**
     * 查询某用户的历史任务
     */
    @Test
    public void queryHistoryTask() {
        //2、获取historyService
        HistoryService historyService = defaultProcessEngine.getHistoryService();
//        List<HistoricTaskInstance> pengcheng = historyService.createHistoricTaskInstanceQuery().taskAssignee("pengcheng").list();
        //根据业务id查看流程历史任务
        List<HistoricTaskInstance> pengcheng = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey("").list();
        for (HistoricTaskInstance historicTaskInstance : pengcheng) {
            System.out.println(historicTaskInstance);
        }
    }

}
