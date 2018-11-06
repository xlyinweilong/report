package com.yin.report.etl.dw.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 度量-渠道单据
 *
 * @author yin.weilong
 * @date 2018.11.05
 */
@Getter
@Setter
public class FactChannelBill {

    private Long channelBillSk;
    private Long channelSk;
    private Long goodsSk;
    private Long colorSk;
    private Long sizeSk;
    private Long dateSk;

    /**
     * 单据时间
     */
    private Date billDateFact;

    /**
     * 价格
     */
    private BigDecimal priceFact = BigDecimal.ZERO;

    /**
     * 吊盘价
     */
    private BigDecimal tagPriceFact = BigDecimal.ZERO;

    /**
     * 单据数量
     */
    private Integer billQuantityFact = 0;

    /**
     * 单据编号
     */
    private String billCodeFact;

    /**
     * 单据类型（1收货仓，-1退货仓，2调入，-2调出，3收供应商,-3退供应商）
     */
    private Integer billTypeFact;
}
