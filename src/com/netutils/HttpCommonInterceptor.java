package com.netutils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ������
 * @author app
 *
 */
public class HttpCommonInterceptor implements Interceptor {
	private Map<String, String> mHeaderParamsMap = new HashMap<String, String>();

	public HttpCommonInterceptor() {
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		Log.d("HttpCommonInterceptor", "add common params");
		Request oldRequest = chain.request();
		// ����µĲ�������ӵ�url ��
		/*
		 * HttpUrl.Builder authorizedUrlBuilder = oldRequest.url() .newBuilder()
		 * .scheme(oldRequest.url().scheme()) .host(oldRequest.url().host());
		 */
		// �µ�����
		Request.Builder requestBuilder = oldRequest.newBuilder();
		requestBuilder.method(oldRequest.method(), oldRequest.body());

		// ��ӹ�������,��ӵ�header��
		if (mHeaderParamsMap.size() > 0) {
			for (Map.Entry<String, String> params : mHeaderParamsMap.entrySet()) {
				requestBuilder.header(params.getKey(), params.getValue());
			}
		}
		Request newRequest = requestBuilder.build();
		return chain.proceed(newRequest);
	}

	public static class Builder {
		HttpCommonInterceptor mHttpCommonInterceptor;

		public Builder() {
			mHttpCommonInterceptor = new HttpCommonInterceptor();
		}

		public Builder addHeaderParams(String key, String value) {
			mHttpCommonInterceptor.mHeaderParamsMap.put(key, value);
			return this;
		}

		public Builder addHeaderParams(String key, int value) {
			return addHeaderParams(key, String.valueOf(value));
		}

		public Builder addHeaderParams(String key, float value) {
			return addHeaderParams(key, String.valueOf(value));
		}

		public Builder addHeaderParams(String key, long value) {
			return addHeaderParams(key, String.valueOf(value));
		}

		public Builder addHeaderParams(String key, double value) {
			return addHeaderParams(key, String.valueOf(value));
		}

		public HttpCommonInterceptor build() {
			return mHttpCommonInterceptor;
		}
	}
}
