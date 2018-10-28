package com.blade.demo;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/tvseries")
public class TvSeriesController {

    private final Log log = LogFactory.getLog(TvSeriesController.class);


    @GetMapping
    public List<TvSeriesDto> getAll() {
        if(log.isTraceEnabled()) {
            log.trace("getAll() ");
        }

        List<TvSeriesDto> list = new ArrayList<>();
        list.add(createWestWorld());
        list.add(createPoi());
        return list;
    }

    @GetMapping("/{id}")
    public TvSeriesDto getOne(@PathVariable int id){
        if(log.isTraceEnabled()) {
            log.trace("getOne " + id);
        }
        if(id == 101) {
            return createWestWorld();
        }else if(id == 102) {
            return createPoi();
        }else {
            throw new ResourceNotFoundException();
        }
    }

    @PostMapping
    public TvSeriesDto insertOne(@RequestBody TvSeriesDto tvSeriesDto){
        if (log.isTraceEnabled()){
            log.trace("insert tvSeriesDto到数据库, 传递进来的参数是"+ tvSeriesDto);
        }
        tvSeriesDto.setId(9999);
        return tvSeriesDto;
    }

    @PutMapping("/{id}")
    public TvSeriesDto updateOne(@PathVariable int id,@RequestBody TvSeriesDto tvSeriesDto){
        if (log.isTraceEnabled()){
            log.trace("updateOne: "+ id);
        }
        if(id == 101 || id == 102) {
            //TODO 数据库操作
            return createWestWorld();
        }else {
            throw new ResourceNotFoundException();
        }
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteOne(@PathVariable int id, HttpServletRequest request,
                                         @RequestBody Map<String,String>  map,
                         @RequestParam(value="delete_reason", required=false) String deleteReason) throws  Exception{
        if (log.isTraceEnabled()){
            log.trace("deleteOne: "+ id);
        }

        Map<String, String> result = new HashMap<>();
        if(id == 101 || id == 102) {
            System.out.print(deleteReason);
            System.out.print(map);
            result.put("message","电影"+id+"被删除（原因：" +deleteReason +")");
        }else {
            throw new RuntimeException();
        }
        return result;
    }


    /**
     * 创建电视剧“Person of Interest",仅仅方便此节做展示其他方法用，以后章节把数据存储到数据库后，会删除此方法
     */
    private TvSeriesDto createPoi() {
        Calendar c = Calendar.getInstance();
        c.set(2011, Calendar.SEPTEMBER, 22, 0, 0, 0);
        return new TvSeriesDto(102, "Person of Interest", 5, c.getTime());
    }
    /**
     * 创建电视剧“West World",仅仅方便此节做展示其他方法用，以后章节把数据存储到数据库后，会删除此方法
     */
    private TvSeriesDto createWestWorld() {
        Calendar c = Calendar.getInstance();
        c.set(2016, Calendar.OCTOBER, 2, 0, 0, 0);
        return new TvSeriesDto(101, "West World", 1, c.getTime());
    }



}
