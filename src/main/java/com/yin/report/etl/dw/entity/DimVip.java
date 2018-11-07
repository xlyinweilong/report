package com.yin.report.etl.dw.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 仓库渠道维度
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"vipCode"})
public class DimVip {

    private Long vipSk;

    /**
     * 会员编号
     */
    private String vipCode;

    /**
     * 会员名称
     */
    private String vipName;

    /**
     * 会员等级
     */
    private String vipGrade;

    /**
     * 会员开卡时间
     */
    private Date vipStartDate;

    /**
     * 会员电话
     */
    private String vipTel;

    /**
     * 会员折扣
     */
    private BigDecimal vipDiscount;

    /**
     * 会员性别
     */
    private String vipSex;
}
