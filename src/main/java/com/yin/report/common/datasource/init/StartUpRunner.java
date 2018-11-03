package com.yin.report.common.datasource.init;

import com.yin.report.common.datasource.service.DatasourceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 加载完成后初始化项目
 *
 * @author yin.weilong
 * @date 2018.11.02
 */

@Component
public class StartUpRunner implements ApplicationRunner {

    @Autowired
    protected DatasourceConfigService datasourceConfigService;

    @Override
    public void run(ApplicationArguments var1) throws Exception {
        datasourceConfigService.loadDataSourceConfig();
    }

}
