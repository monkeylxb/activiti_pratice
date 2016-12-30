package com.activiti.video;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * Created by liuxiaobao on 2016/12/29.
 */
public class HelloWorld {
    //默认通过activiti.cfg.xml
    private ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();
    //开始部署流程
    @Ignore
    @Test
    public void startDeployment(){
       RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .name("helloworld新手入门实例")
                .addClasspathResource("diagrams/helloworld.bpmn20.xml")
                .deploy();
        System.out.println("流程部署的Id是"+deployment.getId());
        System.out.println("流程部署的名字是"+deployment.getName());
    }

    /**
     * 开始流程
     */
    @Ignore
    @Test
    public void startProcess(){
        /**
         * 使用key启动的好处：默认启动最新版本
         * 比如去年部署了一个KEY为“helloworld”的流程，今年又部署了相同的key的流程
         * 按照key值启动，会按照最新部署的流程开始走
         */
        String processID="helloworld";//流程的ID
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processID);
        System.out.print("流程实例ID："+processInstance.getId());//12501
        System.out.print("流程定义ID："+processInstance.getProcessDefinitionId());// helloworld:1:10004

    }

    /**
     * 初始化创建
     * zhangsan  lisi wangwu  用户
     *
     */
    @Ignore
    @Test
    public void creatInit(){
        TaskService taskService=processEngine.getTaskService();
        List<Task> listTemp= taskService.createTaskQuery().taskAssignee("zhangsan").list();
        if (listTemp != null && listTemp.size() != 0) {
            for(Task task:listTemp){
                System.out.println("任务的id是："+task.getId());//12504
                System.out.println("任务的名称是："+task.getName());//提交申请
            }
        }else{
            System.out.println("没有任务");
        }
    }
    @Test
    public void completeMyProcess(){
        String processID = "17502";
        TaskService taskService=processEngine.getTaskService();
        taskService.complete(processID);
        System.out.print("完成任务");
    }

}
