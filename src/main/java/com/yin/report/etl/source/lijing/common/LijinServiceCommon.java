package com.yin.report.etl.source.lijing.common;

import com.yin.report.etl.dw.common.Constant;
import com.yin.report.etl.dw.common.DaoInterface;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 丽晶服务重复调用
 *
 * @author yin.weilong
 * @date 2018.11.06
 */
public class LijinServiceCommon {

    public static Long getSk(String key, Map<String, Long> codeLongMap, DaoInterface daoInterface) {
        return LijinServiceCommon.getSk(key, null, codeLongMap, daoInterface);
    }

    public static Long getSk(String key, String subKey, Map<String, Long> codeLongMap, DaoInterface daoInterface) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        if (subKey != null && codeLongMap.containsKey(key + Constant.SEPARATE + subKey)) {
            return codeLongMap.get(key + Constant.SEPARATE + subKey);
        } else if (codeLongMap.containsKey(key)) {
            return codeLongMap.get(key);
        } else {
            //插入数据
            String[] keys = new String[2];
            keys[0] = key;
            keys[1] = subKey;
            Long id = daoInterface.insert(keys);
            if (subKey != null) {
                codeLongMap.put(key + Constant.SEPARATE + subKey, id);
            } else {
                codeLongMap.put(key, id);
            }
            return id;
        }
    }
}
