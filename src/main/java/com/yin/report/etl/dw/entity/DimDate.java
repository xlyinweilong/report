package com.yin.report.etl.dw.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 仓库时间维度
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Getter
@Setter
public class DimDate {

    private Long dateSk;

    /**
     * 日期
     */
    private Date dateDate;

    /**
     * 年份
     */
    private Integer dateYear;

    /**
     * 月份
     */
    private Integer dateMonth;

    /**
     * 日期
     */
    private Integer dateDay;

    /**
     * 周几
     */
    private Integer dateWeek;

    /**
     * 本年第几周
     */
    private Integer dateWeekOfYear;

}
