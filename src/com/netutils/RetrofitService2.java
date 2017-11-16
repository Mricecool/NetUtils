package com.netutils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface RetrofitService2 {

	// �ֶ��ٵ����
	@GET("/test/getParams")
	Call<User> getUserInfo(@Query("id") long id, @Query("name") String name, @Query("age") int age,
			@Query("phone") String phone, @Query("address") String address);

	// �ֶζ�ֱ����map
	@GET("/test/getParams")
	Call<User> getUserInfo(@QueryMap HashMap<String, Object> param);


	// �ϴ������ļ�
	@Multipart
	@POST("/test/upload")
	Call<ResponseBody> uploadFile(@Part MultipartBody.Part file);

	// �ϴ�����ļ�
	@Multipart
	@POST("/test/uploads")
	Call<ResponseBody> uploadMultipleFiles(@Part List<MultipartBody.Part> files);
}
