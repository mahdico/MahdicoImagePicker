package ir.rubin.mahdicoimagepicker.util;

import android.net.Uri;
import android.support.v4.content.FileProvider;

import java.io.File;

import ir.rubin.mahdicoimagepicker.MahdicoApp;

public class GetImageUri {

	public static Uri doGet(String path){
		return  FileProvider.getUriForFile(MahdicoApp.mContext,
				MahdicoApp.mContext.getApplicationContext().getPackageName() + ".provider",
				new File(MahdicoApp.mContext.getExternalFilesDir("").getPath(), path));
	}

}
