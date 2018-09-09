package ir.rubin.mahdicoimagepicker.util;

import android.content.Context;
import android.net.Uri;

import java.io.File;

import ir.rubin.mahdicoimagepicker.MahdicoApp;

public class GetFileName {

	public static Uri mImageCaptureUri;
	public static String mTempImagePath;

	public static String ImageFolder = MahdicoApp.mContext.getExternalFilesDir("").getPath() + "/";
	public static String tempImageFolder = MahdicoApp.mContext.getExternalFilesDir("").getPath() + "/";


	public static String getNewImageFileName() {
		String fileName = "";
		fileName = String.valueOf(System.currentTimeMillis());
		return fileName;
	}

	public static File getTempImageFile(Context context, String imageFileName) {
		final File path = new File(MahdicoApp.mContext.getExternalFilesDir("").getPath() + "/");
		if (!path.exists()) {
			path.mkdir();
		}
		return new File(path, imageFileName + ".jpg");
	}

	public static void setTempImagePath(String name) {
		mTempImagePath = name + ".jpg";
	}
	
}
