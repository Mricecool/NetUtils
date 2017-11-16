package com.netutils;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface RetrofitService {

	@GET("/test/getList")
	public Call<List<User>> getUsers();
	
	@GET("/test/getList")
	public Call<ResponseBody> getUsers2();

	@POST("/test/postParams")
	@FormUrlEncoded//POST��ʽ�˱�ǩ����� �ֶζ�ʱ��ʹ��fieldMap
	public Call<User> getUser(@Field("id") long id, @Field("name") String name, @Field("age") int age,
			@Field("phone") String phone, @Field("address") String address);


	// �ֶζ�ֱ����map
	@GET("/test/getParams")
	Observable<User> getUserInfo(@QueryMap HashMap<String, Object> param);

}
