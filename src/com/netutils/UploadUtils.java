package com.netutils;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface UploadUtils {

	RequestBody createPartFromString(String descriptionString);

	MultipartBody.Part prepareFilePart(String partName, File fileUri);

	RequestBody prepareRequestBody(String partName, File fileUri);

}
