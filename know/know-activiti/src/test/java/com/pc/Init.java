package com.pc;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

/**
 * 1、排他网关只会选择一个为true的分支执行，按照id大小获取任务执行，选择最小的
 *      总结：排他网关只能选择一个分支来执行
 *
 * 2、并行网关允许将流程分成多条分支，也可以把多条分支汇聚到一起，并行网关的功能是基于进入和外出顺序流的
 *      fork分支：并行后的所有外出顺序流，为每个顺序流都创建一个分发分支
 *      join汇聚：所有到达并行网关，在此等待的进入分支，直到所有进入顺序流的分支都到达以后，流程就会通过汇聚网关
 *   与其他网关的主要区别是，并行网关不会解析条件，即使顺序流中定义了条件，也会被忽略
 *      并行网关的汇聚结点：说明有一个分支已经到汇聚，等待其它的分支到达
 *                          当所有分支任务都完成，都到达汇聚结点后，任务才会继续往下执行
 *    总结：所有分支到达汇聚结点，并行网关执行完成
 *          并行网关所有的分支都执行
 *
 * 3、包含网关：它其实是排他网关与并行网关的结合体，和排他网关一样，你可以在外出顺序流伤定义条件，包含网关会解析它们，
 *              但是主要的区别是包含网关可以选择多于一条顺序流，这和并行网关一样
 *            分支：条件为true开启分支流程
 *            汇聚：所有并行分支到达包含网关，会进入等待状态，直到每个包含流程token的进入顺序流的分支都到达，这是与并行
 *                  网关的最大不同。
 *
 *
 *
 * @author pc
 * @Date 2020/8/29
 **/
public class Init {

    /**
     * 创建表
     */
    @Test
    public void craeteTable() {
        //1、创建ProcessEngineConfiguration对象
        ProcessEngineConfiguration processEngineConfigurationFromResource =
                ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        //2、创建流程引擎
        ProcessEngine processEngine = processEngineConfigurationFromResource.buildProcessEngine();
    }

    /**
     * 创建表
     */
    @Test
    public void craeteTable2() {
        //1、创建流程引擎(配置文件名称必须是activiti.cfg.xml)
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(processEngine);
    }

}
