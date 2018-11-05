package com.yin.report.etl.dw.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 仓库货品维度
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"goodsCode"})
public class DimGoods {

    private Long goodsSk;

    /**
     * 货品编号
     */
    private String goodsCode;

    /**
     * 货品名称
     */
    private String goodsName;

    /**
     * 图片地址
     */
    private String goodsImageUrl;

    /**
     * 尺码组
     */
    private String sizeClass;

    /**
     * 货品品牌
     */
    private String goodsBrand;

    /**
     * 货品品类
     */
    private String goodsCategory;

    /**
     * 货品品类2级
     */
    private String goodsCategory2;

    /**
     * 货品系列
     */
    private String goodsSeries;

    /**
     * 货品风格
     */
    private String goodsStyle;

    /**
     * 货品款式
     */
    private String goodsPattern;

    /**
     * 货品年份
     */
    private String goodsYear;

    /**
     * 货品季节
     */
    private String goodsSeason;

    /**
     * 供应商编号
     */
    private String supplyCode;

    /**
     * 供应商名称
     */
    private String supplyName;

    /**
     * 上市时间
     */
    private String marketTime;

}
