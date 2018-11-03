package com.yin.report.etl.dw.common;

/**
 * 接口DAO
 *
 * @author yin.weilong
 * @date 2018.11.03
 */
public interface DaoInterface {

    /**
     * 插入数据
     *
     * @param code
     * @return
     */
    Long insert(String[] code);
}
