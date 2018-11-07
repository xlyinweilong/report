package com.yin.report.etl.dw.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 仓库颜色维度
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"colorCode"})
public class DimColor {

    private Long colorSk;

    /**
     * 颜色编号
     */
    private String colorCode;

    /**
     * 颜色名称
     */
    private String colorName;

}
