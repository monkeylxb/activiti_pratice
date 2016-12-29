package com.activiti.video;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

/**
 * Created by liuxiaobao on 2016/12/29.
 */
public class HelloWorld {
    //默认通过activiti.cfg.xml
    private ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();
    //开始部署流程
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
}
