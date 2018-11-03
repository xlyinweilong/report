package com.yin.report.etl.dw.dao;

import com.yin.report.etl.dw.entity.DimDate;
import com.yin.report.etl.dw.entity.DimSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class DimDateDao {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;

    /**
     * 根据日期查询
     *
     * @return
     */
    public DimDate findByDate(Date date) {
        List<DimDate> list = dynamicJdbcTemplate.query("SELECT date_sk,date_date FROM dim_date WHERE date_date = ? ", new Object[]{date}, (resultSet, i) -> {
            DimDate dim = new DimDate();
            dim.setDateSk(resultSet.getLong("date_sk"));
            dim.setDateDate(resultSet.getDate("date_date"));
            return dim;
        });
        return list.isEmpty() ? null : list.get(0);
    }


    /**
     * 插入时间
     *
     * @return
     */
    public Long insert(Date date) throws IOException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        dynamicJdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO dim_date(date_date,date_year,date_month,date_day,date_week,date_week_of_year) VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, new java.sql.Date(date.getTime()));
            ps.setInt(2, calendar.get(Calendar.YEAR));
            ps.setInt(3, calendar.get(Calendar.MONTH) + 1);
            ps.setInt(4, calendar.get(Calendar.DAY_OF_MONTH));
            ps.setInt(5, calendar.get(Calendar.DAY_OF_WEEK));
            ps.setInt(6, calendar.get(Calendar.WEEK_OF_YEAR));
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
