package com.netutils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceManager {

	private static final int DEFAULT_TIME_OUT = 5;// ��ʱʱ�� 5s
	private static final int DEFAULT_READ_TIME_OUT = 10;
	private Retrofit mRetrofit;

	private RetrofitServiceManager() {
		// ���� OKHttpClient
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);// ���ӳ�ʱʱ��
																	// builder.writeTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//д����
																	// ��ʱʱ��
		builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);// ��������ʱʱ��
		// ��ӹ�������������
		HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
				.addHeaderParams("paltform", "android").addHeaderParams("userToken", "1234343434dfdfd3434")
				.addHeaderParams("userId", "123445").build();
		builder.addInterceptor(commonInterceptor);
		// ����Retrofit
		mRetrofit = new Retrofit.Builder().client(builder.build())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create()).baseUrl("http://api.douban.com/").build();
	}

	private static class SingletonHolder {
		private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
	}

	/**
	 * ��ȡRetrofitServiceManager
	 * 
	 * @return
	 */
	public static RetrofitServiceManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * ��ȡ��Ӧ��Service
	 * 
	 * @param service
	 *            Service �� class
	 * @param <T>
	 * @return
	 */
	public <T> T create(Class<T> service) {
		return mRetrofit.create(service);
	}
	
	private class MyCall<T> implements Call<T>{

		@Override
		public void cancel() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Call<T> clone() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void enqueue(Callback<T> arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Response<T> execute() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isCanceled() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isExecuted() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Request request() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
}
