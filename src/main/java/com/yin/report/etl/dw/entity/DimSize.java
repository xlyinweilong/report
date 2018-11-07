package com.yin.report.etl.dw.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 仓库尺码维度
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"sizeCode","sizeClass"})
public class DimSize {

    private Long sizeSk;

    /**
     * 尺码编号
     */
    private String sizeCode;

    /**
     * 尺码名称
     */
    private String sizeName;

    /**
     * 尺码组
     */
    private String sizeClass;

}
