package com.activiti.video;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * Created by liuxiaobao on 2016/12/28.
 * 创建数据库25张表
 */
public class TestActiviti {
    /**
     * 使用代码创建25张表
     */
    @Test
    public void createTable(){
        ProcessEngineConfiguration processEngineConfiguration=ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
        processEngineConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306?activiti?useUnicode=true&characterEncoding=utf-8");
        processEngineConfiguration.setJdbcUsername("root");
        processEngineConfiguration.setJdbcPassword("root");
        /** Checks the version of the DB schema against the library when
         * the process engine is being created and throws an exception
         * if the versions don't match. */
        //public static final String DB_SCHEMA_UPDATE_FALSE = "false";//不自动创建新表

        /** Creates the schema when the process engine is being created and
         * drops the schema when the process engine is being closed. */
       // public static final String DB_SCHEMA_UPDATE_CREATE_DROP = "create-drop"; //每次运行创建新表

        /** Upon building of the process engine, a check is performed and
         * an update of the schema is performed if it is necessary. */
        //public static final String DB_SCHEMA_UPDATE_TRUE = "true"; //设置自动对表结构进行改进和升级
        processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        ProcessEngine processEngine=processEngineConfiguration.buildProcessEngine();
    }
    @Test
    public void createTableByXml(){
        ProcessEngineConfiguration processEngineConfiguration=ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine=processEngineConfiguration.buildProcessEngine();
        assertNull(processEngine);
    }
}
