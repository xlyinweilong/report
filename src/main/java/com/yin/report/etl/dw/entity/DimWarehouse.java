package com.yin.report.etl.dw.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 仓库仓库维度
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"warehouseCode"})
public class DimWarehouse {

    private Long warehouseSk;

    /**
     * 渠道编号
     */
    private String warehouseCode;

    /**
     * 渠道名称
     */
    private String warehouseName;


}
