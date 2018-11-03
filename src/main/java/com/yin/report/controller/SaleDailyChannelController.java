package com.yin.report.controller;

import com.yin.report.common.datasource.config.DBIdentifier;
import com.yin.report.common.datasource.service.DatasourceConfigService;
import com.yin.report.etl.source.lijing.dao.CheckDao;
import com.yin.report.etl.source.lijing.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日报
 *
 * @author yin.weilong
 * @date 2018.10.20
 */
@RestController
public class SaleDailyChannelController {

//    @Autowired
//    @Qualifier("systemJdbcTemplate")
//    protected JdbcTemplate systemJdbcTemplate;

//    @Autowired
//    @Qualifier("sourceJdbcTemplate")
//    protected JdbcTemplate sourceJdbcTemplate;

    @Autowired
    private CheckService checkService;

    @Autowired
    private DatasourceConfigService datasourceConfigService;

    /**
     * 创建初始的200条数据
     *
     * @return
     */
    @RequestMapping("/greeting")
    public String createInit200() throws Exception{
        DBIdentifier.setProjectCode("erp_nuoqi");
//        DBIdentifier.setProjectCode("erp_yj");
        checkService.findCheckDetailList();
//        System.out.println(checkDao.findMaxSizeCount());
//        systemJdbcTemplate.execute("CREATE TABLE customers(" +
//                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

//        System.out.println(userMapper.getUsers());
//        saleDailyChannelService.createInit200();
//        saleDailyChannelService.updateSaleDailyChannel();
        return "ok";
    }
}
