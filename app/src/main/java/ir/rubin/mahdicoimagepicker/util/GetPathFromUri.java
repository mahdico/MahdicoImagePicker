package ir.rubin.mahdicoimagepicker.util;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import ir.rubin.mahdicoimagepicker.MahdicoApp;

public class GetPathFromUri {
	/**
	 *
	 * @param contentUri
	 * @return
	 */
	public static String getRealPathFromURI(Uri contentUri) {
		String[] proj = {MediaStore.Audio.Media.DATA};
		Cursor cursor = MahdicoApp.mContext.getContentResolver().query(contentUri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}


}
