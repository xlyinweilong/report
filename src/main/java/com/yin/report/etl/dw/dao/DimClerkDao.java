package com.yin.report.etl.dw.dao;

import com.yin.report.etl.dw.common.DaoInterface;
import com.yin.report.etl.dw.entity.DimClerk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 营业员DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class DimClerkDao implements DaoInterface {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;

    /**
     * 查询全部营业员
     *
     * @return
     */
    public List<DimClerk> findAll() {
        return dynamicJdbcTemplate.query("SELECT clerk_sk,clerk_code FROM dim_clerk", (resultSet, i) -> {
            DimClerk dim = new DimClerk();
            dim.setClerkSk(resultSet.getLong("clerk_sk"));
            dim.setClerkCode(resultSet.getString("clerk_code"));
            return dim;
        });
    }

    @Override
    public Long insert(String[] code) {
        return this.insert(code[0]);
    }

    /**
     * 插入营业员
     *
     * @return
     */
    public Long insert(String clerkCode) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        dynamicJdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO dim_clerk(clerk_code) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, clerkCode);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    /**
     * 批量更新
     *
     * @param list
     */
    public void updateBatch(List<DimClerk> list) {
        List<Object[]> batch = new ArrayList<>();
        list.forEach(dim -> {
            Object[] values = new Object[]{
                    dim.getClerkCode(), dim.getClerkName(), dim.getClerkSex(),dim.getClerkBirthDate(),dim.getChannelCode(), dim.getClerkSk()
            };
            batch.add(values);
        });
        dynamicJdbcTemplate.batchUpdate("UPDATE dim_clerk SET clerk_code = ? ,clerk_name = ? ,clerk_sex=  ? ,clerk_birth_date=  ? ,channel_code=  ? WHERE clerk_sk =  ?", batch);
    }
}
