package com.blade.demo.dao;

import com.blade.demo.pojo.TvSeries;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TvSeriesDao {

    @Select("select * frmo tv_series")
    public List<TvSeries> getAll();
}
