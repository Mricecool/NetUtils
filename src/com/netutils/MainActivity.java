package com.netutils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button okHttp, volley, retrofit, retrofit_okhttp, rx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		okHttp = (Button) findViewById(R.id.okhttp);
		volley = (Button) findViewById(R.id.volley);
		retrofit = (Button) findViewById(R.id.retrofit);
		retrofit_okhttp = (Button) findViewById(R.id.retrofit_okhttp);
		rx = (Button) findViewById(R.id.rx);

		okHttp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this, OkHttpActivity.class));
			}
		});

		volley.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		retrofit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this, RetrofitActivity.class));
			}
		});

		retrofit_okhttp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this, RetrofitOKHttpActivity.class));
			}
		});

		rx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this, RxJavaActivity.class));
			}
		});
	}
}
