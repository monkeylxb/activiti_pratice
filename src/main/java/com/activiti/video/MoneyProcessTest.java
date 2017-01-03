package com.activiti.video;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by dagong2 on 2017/1/3.
 */
public class MoneyProcessTest {
    @Rule
    public ActivitiRule activitiRule=new ActivitiRule();
    /**
     * 部署流程
     */
    @Test
    public void deployMent(){
        RepositoryService repositoryService = activitiRule.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment().name("费用报销流程")
                .addClasspathResource("diagrams/moneyprocess.bpmn20.xml")
                .deploy();
        System.out.print("流程部署ID是:"+deployment.getId());    //流程部署ID是:30001
        System.out.print("流程部署name是:"+deployment.getName());//流程部署name是:费用报销流程
    }
    @Test
    public void startProcessByKey(){
        String defineKey="moneyprocess";//流程的ID
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(defineKey);
        System.out.println("流程实例的ID是："+processInstance.getId());
        System.out.println("流程部署的ID是："+processInstance.getDeploymentId());
        System.out.println("流程定义的ID是："+processInstance.getProcessDefinitionId());
   /*   流程实例的ID是：32501
        流程部署的ID是：null
        流程定义的ID是：moneyprocess:1:30004*/
    }
    @Test
    public void nextZJLProcess(){
        String taskID="35004";
        TaskService taskService = activitiRule.getTaskService();
        Integer money = (Integer) taskService.getVariable(taskID, "money");
        System.out.print(money);//没有和任务ID关联，所以查不到
        taskService.complete(taskID);
    }
}
