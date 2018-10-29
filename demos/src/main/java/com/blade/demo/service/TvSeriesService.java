package com.blade.demo.service;

import com.blade.demo.pojo.TvSeries;
import com.blade.demo.dao.TvSeriesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TvSeriesService {

    @Autowired
    TvSeriesDao tvSeriesDao;

    public List<TvSeries> getAllTvSeries(){
        return tvSeriesDao.getAll();
    }
}
