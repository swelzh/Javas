package com.blade.demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


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



    /*
    *   只有这里的@Valid，Dto里面的Valid才会生效，否则不生效！
    * */
    @PostMapping
    public TvSeriesDto insertOne(@Valid @RequestBody TvSeriesDto tvSeriesDto){
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
                         @RequestParam(value="delete_reason", required=false) String deleteReason,
                                         @RequestBody Map<String,String>  map) throws  Exception{
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

    /*
    *   上传图片、文件
    * */
    @PostMapping(value="/{id}/photos",consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addPhoto(@PathVariable int id,
                         @RequestParam("photo") MultipartFile imgFile) throws Exception{
        if (log.isTraceEnabled()){
            log.trace("接受到文件 "+ id +"收到文件: "+ imgFile.getOriginalFilename());
        }

        FileOutputStream fos = new FileOutputStream("target/" + imgFile.getOriginalFilename());
        IOUtils.copy(imgFile.getInputStream(), fos);
        fos.close();
    }

    /*
    *   下载图片、文件
    * */
    @GetMapping(value="/{id}/icon", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[]  getIcon(@PathVariable int id) throws Exception {
        if (log.isTraceEnabled()){
            log.trace("getIcon("+id+")");
        }
        String iconFile = "target/1.jpeg";
        InputStream is = new FileInputStream(iconFile);
        return IOUtils.toByteArray(is);
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
