package com.netutils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Part;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitOKHttpActivity extends Activity implements OnClickListener, UploadUtils {

	public static final String MULTIPART_FORM_DATA = "multipart/form-data";

	private Button r_o, r_o_pack, r_o_rx, r_o_upload;
	private TextView txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_retrofit_okhttp);
		r_o = (Button) findViewById(R.id.r_o);
		r_o_pack = (Button) findViewById(R.id.r_o_pack);
		r_o_rx = (Button) findViewById(R.id.r_o_rx);
		r_o_upload = (Button) findViewById(R.id.r_o_upload);
		r_o.setOnClickListener(this);
		r_o_pack.setOnClickListener(this);
		r_o_rx.setOnClickListener(this);
		r_o_upload.setOnClickListener(this);

		txt = (TextView) findViewById(R.id.txt);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// retrofit+okhttp请求
		case R.id.r_o:

			// 创建 OKHttpClient
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(10, TimeUnit.SECONDS);// 连接超时时间
			builder.writeTimeout(10, TimeUnit.SECONDS);// 写操作 超时时间
			builder.readTimeout(10, TimeUnit.SECONDS);// 读操作超时时间

			// 添加公共参数拦截器
			BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder()
					.addHeaderParam("userName", "")// 添加公共参数
					.addHeaderParam("device", "").build();

			builder.addInterceptor(basicParamsInterceptor);

			Retrofit retrofit = new Retrofit.Builder().client(builder.build()).baseUrl("http://192.168.1.137:8090/")
					.addConverterFactory(GsonConverterFactory.create()).build();
			RetrofitService2 retrofitService = retrofit.create(RetrofitService2.class);
			// Call<User> list = retrofitService.getUserInfo(66666, "我是測試數據",
			// 11, "123123", "长安区");

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("id", 9656325);
			params.put("name", "王宝强");
			params.put("age", 55);
			params.put("phone", "45855663");
			params.put("address", "在邢台");
			Call<User> list = retrofitService.getUserInfo(params);

			list.enqueue(new Callback<User>() {

				@Override
				public void onFailure(Call<User> arg0, Throwable arg1) {
					// TODO Auto-generated method stub
					Log.e("arg1", arg1.getMessage());
				}

				@Override
				public void onResponse(Call<User> arg0, Response<User> arg1) {
					// TODO Auto-generated method stub
					Log.e("arg", arg1.body().getUserName());
					txt.setText(arg1.body().getUserName() + arg1.body().getUserAddress());
				}

			});
			break;
		// retrofit多文件上传
		case R.id.r_o_upload:
			Retrofit retrofit2 = new Retrofit.Builder().baseUrl("http://192.168.1.137:8090/").build();
			// 创建上传的service实例
			RetrofitService2 service = retrofit2.create(RetrofitService2.class);

			File file = new File(Environment.getExternalStorageDirectory(), "ss.jpg");
			MultipartBody.Part body = prepareFilePart("files", file);
			List<MultipartBody.Part> p = new ArrayList<MultipartBody.Part>();
			p.add(body);
			p.add(body);

			// 最后执行异步请求操作
			Call<ResponseBody> call = service.uploadMultipleFiles(p);
			call.enqueue(new Callback<ResponseBody>() {
				@Override
				public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
					try {
						txt.setText(response.body().string());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onFailure(Call<ResponseBody> call, Throwable t) {
					Log.e("Upload error:", t.getMessage());
				}
			});
			break;
		case R.id.r_o_pack:

			TestService rs = RetrofitServiceManager.getInstance().create(TestService.class);
			Call<ResponseBody> l = rs.getInfo(0, 20);
			
			
			l.enqueue(new Callback<ResponseBody>() {

				@Override
				public void onFailure(Call<ResponseBody> arg0, Throwable arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onResponse(Call<ResponseBody> arg0, Response<ResponseBody> arg1) {			
					txt.setText(arg1.body().toString());
					
				}
			});

			break;
		case R.id.r_o_rx:
			Retrofit rt = new Retrofit.Builder().baseUrl("http://192.168.1.137:8090/")
					.addConverterFactory(GsonConverterFactory.create())
					.addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
			RetrofitService rService = rt.create(RetrofitService.class);
			// Call<User> list = retrofitService.getUserInfo(66666, "我是測試數據",
			// 11, "123123", "长安区");

			HashMap<String, Object> ps = new HashMap<String, Object>();
			ps.put("id", 9656325);
			ps.put("name", "王宝强");
			ps.put("age", 55);
			ps.put("phone", "45855663");
			ps.put("address", "在邢台");
			Subscription s = rService.getUserInfo(ps).subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<User>() {

						@Override
						public void onCompleted() {
							// TODO Auto-generated method stub

						}

						@Override
						public void onError(Throwable arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onNext(User arg0) {
							txt.setText(arg0.getUserName() + arg0.getUserAddress() + "me");
						}
					});
			break;

		default:
			break;
		}

	}

	@Override
	public RequestBody createPartFromString(String descriptionString) {
		return RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
	}

	@Override
	public Part prepareFilePart(String partName, File file) {
		// 为file建立RequestBody实例
		RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);

		// MultipartBody.Part借助文件名完成最终的上传
		return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
	}

	@Override
	public RequestBody prepareRequestBody(String partName, File file) {
		return RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
	}

}
