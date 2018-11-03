package com.yin.report.etl.dw.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售事实
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Getter
@Setter
public class FactSale {

    private Long saleSk;
    private Long channelSk;
    private Long goodsSk;
    private Long colorSk;
    private Long sizeSk;
    private Long vipSk;
    private Long clerkSk;
    private Long dateSk;

    /**
     * 单据时间
     */
    private Date billDate;

    /**
     * 销售数量
     */
    private Integer saleQuantityFact = 0;

    /**
     * 成本金额
     */
    private BigDecimal costAmountFact = BigDecimal.ZERO;

    /**
     * 销售吊盘价
     */
    private BigDecimal saleTagPriceFact = BigDecimal.ZERO;

    /**
     * 销售单价
     */
    private BigDecimal salePriceFact = BigDecimal.ZERO;

    /**
     * 单据编号
     */
    private String billCode;

}
