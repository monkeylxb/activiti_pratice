package com.activiti.example5;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by liuxiaobao on 2016/12/27.
 */
public class UserAndGroupInUserTaskTest {
    //使用默认的activiti.cfg.xml作为参数
    @Rule
    public ActivitiRule activitiRule=new ActivitiRule();
    @Before
    public void setUp(){
        IdentityService identityService=activitiRule.getIdentityService();
        Group group = identityService.newGroup("deptLeader");
        group.setName("jingli");
        group.setType("assignment");
        identityService.saveGroup(group);//保存组
        User user =identityService.newUser("001");
        user.setFirstName("Liu");
        user.setLastName("XiaoBao");
        user.setPassword("123456");
        identityService.saveUser(user);
        User user1=identityService.newUser("002");
        user1.setFirstName("Lei");
        user1.setLastName("ShuangMing");
        user1.setPassword("123456");
        identityService.saveUser(user1);
        identityService.createMembership("001","deptLeader");//建立关系
        identityService.createMembership("002","deptLeader");//建立关系
        System.out.println("创建关系成功");
    }
    @Test
    @Deployment(resources = {"diagrams/userAndGroupInUserTask.bpmn20.xml"})
    public void testUserAndGroupInUserTask(){
        RuntimeService runtimeService=activitiRule.getRuntimeService();
        ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("userAndGroupInUserTask");//启动流程
        assertNotNull(processInstance);//验证流程是否启动
        TaskService taskService=activitiRule.getTaskService();
        Task xiaobaoTask= taskService.createTaskQuery().taskCandidateUser("001").singleResult();
        assertNotNull(xiaobaoTask);//查询用户的任务
        taskService.claim(xiaobaoTask.getId(),"001");//签收任务
        Task xiaoleiTask=taskService.createTaskQuery().taskCandidateUser("002").singleResult();
        //assertNotNull(xiaoleiTask);//因为xiaobao已经签收了任务，所以小雷失去了拥有这个任务的权限
    }
    @After
    public void afterInvokeTestMethod(){
        IdentityService identityService=activitiRule.getIdentityService();
        identityService.deleteMembership("001","deptLeader");
        identityService.deleteMembership("002","deptLeader");
        identityService.deleteUser("001");
        identityService.deleteUser("002");
        identityService.deleteGroup("deptLeader");
        System.out.println("删除成功");
    }
}
