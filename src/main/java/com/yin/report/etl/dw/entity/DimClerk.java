package com.yin.report.etl.dw.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 仓库营业员维度
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"clerkCode"})
public class DimClerk {

    private Long clerkSk;

    /**
     * 营业员编号
     */
    private String clerkCode;

    /**
     * 营业员名字
     */
    private String clerkName;

    /**
     * 营业员性别
     */
    private String clerkSex;

    /**
     * 营业员生日
     */
    private String clerkBirthDate;

    /**
     * 所属渠道编号
     */
    private String channelCode;
}
