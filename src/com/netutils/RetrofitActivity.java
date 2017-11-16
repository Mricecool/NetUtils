package com.netutils;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends Activity{
	
	private Button retro_get, retro_post, retro_upload;
	private TextView txt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_retrofit);
		txt = (TextView) findViewById(R.id.txt);
		retro_get = (Button) findViewById(R.id.retro_get);
		retro_post = (Button) findViewById(R.id.retro_post);
		retro_upload = (Button) findViewById(R.id.retro_upload);
		
		final Gson gson = new GsonBuilder()
			      //配置你的Gson
			      .setDateFormat("yyyy-MM-dd hh:mm:ss")
			      .create();
		
		retro_get.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Retrofit retrofit = new Retrofit.Builder()  //01:获取Retrofit对象
                        .baseUrl("http://192.168.1.137:8090/") //02采用链式结构绑定Base url
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();//03执行操作
				RetrofitService retrofitService=retrofit.create(RetrofitService.class);
				Call<List<User>> list=retrofitService.getUsers();
				list.enqueue(new Callback<List<User>>() {


					@Override
					public void onFailure(Call<List<User>> arg0, Throwable arg1) {
						// TODO Auto-generated method stub
						Log.e("arg1", arg1.getMessage());
					}

					@Override
					public void onResponse(Call<List<User>> arg0, Response<List<User>> arg1) {
						// TODO Auto-generated method stub
						Log.e("arg", arg1.body().get(0).getUserName());
						txt.setText(arg1.body().get(0).getUserName());
					}
					
					
					
				});
			}
		});
		
		retro_post.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Retrofit retrofit = new Retrofit.Builder()  //01:获取Retrofit对象
                        .baseUrl("http://192.168.1.137:8090/") //02采用链式结构绑定Base url
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();//03执行操作
				RetrofitService retrofitService=retrofit.create(RetrofitService.class);
				Call<ResponseBody> list=retrofitService.getUsers2();
				list.enqueue(new Callback<ResponseBody>() {


					@Override
					public void onFailure(Call<ResponseBody> arg0, Throwable arg1) {
						// TODO Auto-generated method stub
						Log.e("arg1", arg1.getMessage());
					}

					@Override
					public void onResponse(Call<ResponseBody> arg0, Response<ResponseBody> arg1) {
						// TODO Auto-generated method stub

						try {
							txt.setText(arg1.body().string());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				});
				
			}
		});
		
		retro_upload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Retrofit retrofit = new Retrofit.Builder()  //01:获取Retrofit对象
                        .baseUrl("http://192.168.1.137:8090/") //02采用链式结构绑定Base url
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();//03执行操作
				RetrofitService retrofitService=retrofit.create(RetrofitService.class);
				Call<User> list=retrofitService.getUser(22222,"sdfsdfs",1,"1111","我是个好人");
				list.enqueue(new Callback<User>() {


					@Override
					public void onFailure(Call<User> arg0, Throwable arg1) {
						// TODO Auto-generated method stub
						Log.e("arg1", arg1.getMessage());
					}

					@Override
					public void onResponse(Call<User> arg0, Response<User> arg1) {
						// TODO Auto-generated method stub
						txt.setText(arg1.body().getUserAddress());
					}
					
					
					
				});
				
			}
		});
	}
	
	

}
