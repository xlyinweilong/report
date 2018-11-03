package com.yin.report.etl.dw.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 仓库渠道维度
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Getter
@Setter
public class DimChannel {

    private Long channelSk;

    /**
     * 渠道编号
     */
    private String channelCode;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 渠道城市
     */
    private String channelCity;
}
