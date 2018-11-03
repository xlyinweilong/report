package com.yin.report.common.datasource.config;

import com.yin.report.common.datasource.entity.DatasourceConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yin.weilong
 * @date 2018.11.01
 */
public class ProjectDBStorage {

    private Map<String, DatasourceConfig> dbMap = new HashMap<>();

    public DatasourceConfig getDatasourceConfig(String key) {
        return dbMap.get(key);
    }

    public void addDatasourceConfig(String key, DatasourceConfig datasourceConfig) {
        dbMap.put(key, datasourceConfig);
    }

    public void clearDatasourceConfig() {
        dbMap.clear();
    }

    private ProjectDBStorage() {
    }

    public static ProjectDBStorage instance() {
        return ProjectDBMgrBuilder.instance;
    }


    private static class ProjectDBMgrBuilder {
        private static ProjectDBStorage instance = new ProjectDBStorage();
    }
}
