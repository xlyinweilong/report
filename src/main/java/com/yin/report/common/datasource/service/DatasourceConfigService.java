package com.yin.report.common.datasource.service;

import com.yin.report.common.datasource.config.ProjectDBStorage;
import com.yin.report.common.datasource.dao.DatasourceConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author yin.weilong
 * @date 2018.11.02
 */

@Service
public class DatasourceConfigService {

    @Autowired
    private DatasourceConfigDao datasourceConfigDao;

    public void loadDataSourceConfig() {
        datasourceConfigDao.findAll().forEach(datasourceConfig -> {
            ProjectDBStorage.instance().addDatasourceConfig(datasourceConfig.getDbKey(), datasourceConfig);
        });
    }
}
