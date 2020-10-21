package com.pc;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * 使用监听器绑定参数
 * @author pc
 * @Date 2020/9/5
 **/
public class ActivitiListenerProcess {

    public class MyListener implements TaskListener {
        @Override
        public void notify(DelegateTask delegateTask) {
            System.out.println("MyListener在监听...");
            delegateTask.setAssignee("zhangsan");
        }
    }

}
