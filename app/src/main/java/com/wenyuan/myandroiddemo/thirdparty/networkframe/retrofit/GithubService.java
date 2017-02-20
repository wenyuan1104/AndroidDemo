package com.wenyuan.myandroiddemo.thirdparty.networkframe.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by wenyuan on 2016/12/18 21:37.
 * Description:
 */

public interface GithubService {
    @GET("{owner}/{repo}/contents")
    Call<String> getData(@Path("owner") String owner, @Path("repo") String repo);
}
