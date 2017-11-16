package com.netutils;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpActivity extends Activity {

	private Button ok_get, ok_post, ok_upload;
	private TextView txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_okhttp);
		txt = (TextView) findViewById(R.id.txt);
		ok_get = (Button) findViewById(R.id.ok_get);
		ok_post = (Button) findViewById(R.id.ok_post);
		ok_upload = (Button) findViewById(R.id.ok_upload);
		ok_get.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 创建okHttpClient对象
				OkHttpClient.Builder b = new OkHttpClient().newBuilder();
				// 创建一个Request
				final Request request = new Request.Builder().url("http://192.168.1.137:8090/test/getList").build();
				// new call
				OkHttpClient mOkHttpClient=b.build();
				Call call = mOkHttpClient.newCall(request);
				// 请求加入调度
				call.enqueue(new Callback() {

					@Override
					public void onFailure(Call arg0, IOException arg1) {

					}

					@Override
					public void onResponse(Call arg0, Response response) throws IOException {
						//回调在子线程
						final String res = response.body().string();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								txt.setText(res);
							}
						});

					}
				});

			}
		});
		ok_post.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*
				 * application/x-www-form-urlencoded 数据是个普通表单
				 * multipart/form-data 数据里有文件 application/json 数据是个json
				 */

				OkHttpClient okHttpClient = new OkHttpClient();
				RequestBody body = new FormBody.Builder().add("id", "1002").add("name", "你好").add("age", "12")
						.add("phone", "").add("address", "").build();
				Request request = new Request.Builder().url("http://192.168.1.137:8090/test/postParams").post(body)
						.build();

				Call call = okHttpClient.newCall(request);
				// 请求加入调度
				call.enqueue(new Callback() {

					@Override
					public void onFailure(Call arg0, IOException arg1) {

					}

					@Override
					public void onResponse(Call arg0, Response response) throws IOException {
						final String res = response.body().string();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								txt.setText(res);
							}
						});
					}
				});
			}
		});

		ok_upload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				OkHttpClient okHttpClient = new OkHttpClient();
				File file = new File(Environment.getExternalStorageDirectory(), "ss.jpg");
				RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file",
						file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file)).build();

				Request request = new Request.Builder().url("http://192.168.1.137:8090/test/upload").post(body).build();

				Call call = okHttpClient.newCall(request);
				// 请求加入调度
				call.enqueue(new Callback() {

					@Override
					public void onFailure(Call arg0, IOException arg1) {

					}

					@Override
					public void onResponse(Call arg0, Response response) throws IOException {
						final String res = response.body().string();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								txt.setText(res);
							}
						});
					}
				});
			}
		});
	}

}
