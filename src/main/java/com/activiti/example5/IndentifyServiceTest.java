package com.activiti.example5;


import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class IndentifyServiceTest {
	//使用默认的activiti.cfg.xml作为参数
	@Rule
	public ActivitiRule activitiRule=new ActivitiRule();
	//用户管理的API演示
	@Test
	public void testUser(){
		//获取IdentifyService实例
		IdentityService identityService=activitiRule.getIdentityService();
		//创建一个用户对象
		User user=identityService.newUser("001");
		user.setFirstName("Liu");
		user.setLastName("XiaoBao");
		user.setEmail("monkey_lxb@163.com");
		//保存用户到数据库
		identityService.saveUser(user);
		//验证用户是否保存成功
		User userInDb=identityService.createUserQuery()
					.userId("001")
					.singleResult();
		assertNotNull(userInDb);
	}
	@Test
	public void testGroup() {
		IdentityService identityService = activitiRule.getIdentityService();
		Group group = identityService.newGroup("007");
		group.setName("部门经理");
		group.setType("assigment");
		identityService.saveGroup(group);
		Group group1 = identityService.createGroupQuery().groupId("007").singleResult();
		assertNotNull(group1);
	}
	@Test
	public void testUserAndGroup(){
		IdentityService identityService=activitiRule.getIdentityService();
		//创建并保存组对象
		Group group=identityService.newGroup("deptLeader");
		group.setName("部门领导");
		group.setType("assignment");
		identityService.saveGroup(group);
		//创建并保存一个用户对象
		User user=identityService.newUser("001");
		user.setFirstName("Liu");
		user.setLastName("XiaoBao");
		user.setEmail("monkey_lxb@163.com");
		identityService.saveUser(user);
		//把用户Liuxiaobao加入到组部门领导中
		identityService.createMembership("001", "deptLeader");
		//查询属于部门领导组的用户
		User userInGroup= identityService.createUserQuery().memberOfGroup("deptLeader").singleResult();
		assertNotNull(userInGroup);
		//查询用户liuxiaobao所属组
		Group groupContainsLXB= identityService.createGroupQuery().groupMember("001").singleResult();
		assertEquals("deptLeader",groupContainsLXB.getId());
	}
}
