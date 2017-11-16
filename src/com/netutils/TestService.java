package com.netutils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TestService {
	
	@GET("/v2/movie/top250")
	public Call<ResponseBody> getInfo(@Query("start") int start,@Query("count") int count);
}
