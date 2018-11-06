package com.yin.report.etl.dw.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 度量-渠道库存
 *
 * @author yin.weilong
 * @date 2018.11.05
 */
@Getter
@Setter
public class FactChannelStock {

    private Long stockSk;
    private Long channelSk;
    private Long goodsSk;
    private Long colorSk;
    private Long sizeSk;

    /**
     * 单据数量
     */
    private Integer stockQuantityFact = 0;

}
