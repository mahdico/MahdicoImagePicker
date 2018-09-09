package ir.rubin.mahdicoimagepicker.util;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;

import ir.rubin.mahdicoimagepicker.MahdicoApp;
import ir.rubin.mahdicoimagepicker.listeners.onImageResizedListener;

public class ResizeImage {



	public static void doResize(Bitmap photo, onImageResizedListener listener){
		final onImageResizedListener mListener=listener;
		String fileName = GetFileName.getNewImageFileName()+".jpg";

		Bitmap out = Bitmap.createScaledBitmap(photo, 500, 500, false);
		final File path = new File(MahdicoApp.mContext.getExternalFilesDir("").getPath());
		if (!path.exists()) {
			path.mkdir();
		}
		File file = new File(path, fileName);
		FileOutputStream fOut;
		try {
			file.createNewFile();
			fOut = new FileOutputStream(file);
			out.compress(Bitmap.CompressFormat.PNG, 80, fOut);
			fOut.flush();
			fOut.close();
			out.recycle();
			mListener.onImageResized(fileName);


		} catch (Exception e) { // TODO
			e.printStackTrace();
		}




	}

}
