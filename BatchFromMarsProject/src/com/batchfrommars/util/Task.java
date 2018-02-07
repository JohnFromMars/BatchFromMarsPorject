package com.batchfrommars.util;

import com.batchfrommars.component.ComponentII;

/**
 * 
 * @author user Task object include component, task name and task id
 *
 */
public class Task {

	private ComponentII component;
	private TaskName taskName;
	private int taskId;

	public Task() {
	}

	public Task(ComponentII component, TaskName taskName, int taskId) {
		this.component = component;
		this.taskName = taskName;
		this.taskId = taskId;
	}

	public ComponentII getComponent() {
		return component;
	}

	public void setComponent(ComponentII component) {
		this.component = component;
	}

	public TaskName getTaskName() {
		return taskName;
	}

	public void setTaskName(TaskName taskName) {
		this.taskName = taskName;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String toString() {
		return "Task [taskName=" + taskName + ", taskId=" + taskId + "]";
	}

}
