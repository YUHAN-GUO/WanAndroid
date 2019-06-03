package com.gyh.wanandroid.data.retrofit;

import com.base.gyh.baselib.data.bean.HttpResult;
import com.gyh.wanandroid.data.bean.ArticleDataBean;
import com.gyh.wanandroid.data.bean.BannerDataBean;
import com.gyh.wanandroid.data.bean.KnowledgeArticleBean;
import com.gyh.wanandroid.data.bean.KnowledgeBean;
import com.gyh.wanandroid.data.bean.NavigationArticleBean;
import com.gyh.wanandroid.data.bean.ProjectArticleBean;
import com.gyh.wanandroid.data.bean.ProjectTreeBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*
 * created by taofu on 2018/11/27
 **/
public interface WanAndroidService {

    /***
     * 获取首页banner数据
     * @return
     */
    @GET("/banner/json")
    Observable<HttpResult<List<BannerDataBean>>> getHomeBannerData();

    /***
     * 获取首页文章列表数据
     * @return
     */
    @GET("article/list/{pageNo}/json")
    Observable<HttpResult<ArticleDataBean>> getArticlesData(@Path("pageNo") int num);

    /**
     * 体系
     *
     * @return
     */
    @GET("/tree/json")
    Observable<HttpResult<List<KnowledgeBean>>> getKnowledgeData();

    /**
     * 体系详情
     *
     * @param pageNo 页码从0开始
     * @param cid    从“/tree/json”获取
     * @return
     */
    @GET("/article/list/{pageNo}/json")
    Observable<HttpResult<KnowledgeArticleBean>> getKnowledgeArticles(@Path("pageNo") int pageNo, @Query("cid") int cid);


    /**
     * Project tree
     *
     * @return
     */
    @GET("/project/tree/json")
    Observable<HttpResult<List<ProjectTreeBean>>> getProjectsTree();


    /**
     * Project Article 页面
     *
     * @param pageNo
     * @param cid
     * @return
     */
    @GET("/project/list/{pageNo}/json")
    Observable<HttpResult<ProjectArticleBean>> getProjectArticles(@Path("pageNo") int pageNo, @Query("cid") int cid);


    /**
     * Navigation Data
     * @return
     */
    @GET("/navi/json")
    Observable<HttpResult<List<NavigationArticleBean>>> getNavigationData();

//    /***
//     * 收藏文章
//     * @param id
//     * @return
//     */
//    @POST("lg/collect/{id}/json")
//    Observable<BaseBean<Object>> collectArticle(@Path("id") int id);
//
//    /***
//     * 文章取消收藏
//     * @param id
//     * @return
//     */
//    @POST("lg/uncollect_originId/{id}/json")
//    Observable<BaseBean<Object>> cancleCollectArticle(@Path("id") int id);
//

//    @GET("/lg/collect/list/{pageNo}/json")
//    Observable<BaseBean<ArticleList>> getCollections(@Path("pageNo") int pageNo);
//
//    @POST("lg/uncollect/{id}/json")
//    @FormUrlEncoded
//    Observable<BaseBean<Object>> cancelCollectArticle(@Path("id") int id, @Field("originId") int originid);
//
//    @GET("/user/logout/json")
//    Observable<BaseBean<Object>> logout();
//
//    @GET("/hotkey/json")
//    Observable<BaseBean<List<HotKeyBean>>> getHotKeys();
//
//    @POST("/article/query/{page}/json")
//    @FormUrlEncoded
//    Observable<BaseBean<ArticleList>> queryArticlesByKey(@Path("page") int page, @Field("k") String key);
//
//    @GET("/tree/json")
//    Observable<BaseBean<List<KnowledgeBean>>> getKnowledgeData();
//

//
//    @POST("/user/login")
//    @FormUrlEncoded
//    Observable<BaseBean<LoginBean>> login(@Field("username") String userName, @Field("password") String password);
//
//    @POST("/user/register")
//    @FormUrlEncoded
//    Observable<BaseBean<LoginBean>> register(@Field("username") String userName, @Field("password") String password, @Field("repassword") String repssword);
//
}
