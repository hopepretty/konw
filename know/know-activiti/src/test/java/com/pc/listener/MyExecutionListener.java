package com.pc.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;

/**
 * 监听器
 *
 * @author pc
 * @Date 2021/1/28
 **/
public class MyExecutionListener implements ExecutionListener, TaskListener {

	/**
	 * 全局监听器
	 *
	 * @param delegateExecution
	 */
	@Override
	public void notify(DelegateExecution delegateExecution) {
		//获取事件名称
		String eventName = delegateExecution.getEventName();
		if ("start".equals(eventName)) {
			System.out.println("start=========");
		}else if ("end".equals(eventName)) {
			System.out.println("end=========");
		} else if ("task".equals(eventName)) {
			System.out.println("task=========");
		}
	}

	/**
	 * 节点监听器
	 *
	 * @param delegateTask
	 */
	@Override
	public void notify(DelegateTask delegateTask) {
		//获取事件名称
		String eventName = delegateTask.getEventName();
		if ("create".endsWith(eventName)) {
			System.out.println("create=========");
		} else if ("assignment".endsWith(eventName)) {
			System.out.println("assignment========");
		} else if ("complete".endsWith(eventName)) {
			System.out.println("complete===========");
		} else if ("delete".endsWith(eventName)) {
			System.out.println("delete=============");
		}
	}
}
