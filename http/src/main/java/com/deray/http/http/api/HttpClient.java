package com.deray.http.http.api;


/**
 * Created by Deray on 2017/5/5.
 */

public interface HttpClient {

/*    *//**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     * eg: http://gank.io/api/data/Android/10/1
     *//*
    //@GET("data/{type}/{pre_page}/{page}")
    //Observable<GankIoDataBean> getGankIoData(@Path("type") String id, @Path("page") int page, @Path("pre_page") int pre_page);

    @GET("data/Android/10/1")
    Observable<Bean> getAndroidInfo();*/
}
