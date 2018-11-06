package com.yin.report.etl.dw.common;

/**
 * SQL复用
 *
 * @author yin.weilong
 * @date 2018.11.06
 */
public class SqlCommon {

    /**
     * 生成尺码SQL
     *
     * @param count
     * @return
     */
    public static String createSizeSql(int count) {
        StringBuffer sb = new StringBuffer();
        String code = "S";
        for (int i = 1; i < count; i++) {
            sb.append(code).append(i).append(",");
        }
        sb.append(code).append(count);
        return sb.toString();
    }
}
