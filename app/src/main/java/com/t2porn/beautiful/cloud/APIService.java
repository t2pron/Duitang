package com.t2porn.beautiful.cloud;


import com.t2porn.beautiful.bean.ImageItem;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by t2porn on 2016/5/31.
 */
public interface APIService {

    String TULING_HOST = "http://www.tuling123.com/openapi/";
    String TULONG_KEY = "46b7bab90e0ede93c0789d37dcea7c5e";

    String Tiangou_Classify = "http://www.tngou.net/tnfs/api/classify";
    String Tiangou_list = "http://www.tngou.net/tnfs/api/list";
    String IMAGER_HOST = "http://www.tngou.net/tnfs/api/";
    String IMAGET_LOOK = "http://tnfs.tngou.net/img";


  /*  @POST("api")
    Observable<TULING> wordAPI(@Query("key") String key, @Query("info") String info,
                               @Query("userid") String userid);*/

    @POST("list")
    Observable<ImageItem> imagelist(@Query("page") int page, @Query("rows") int rows,
                                    @Query("id") int id);

    /**********************以下是测试api接口************************************/

}