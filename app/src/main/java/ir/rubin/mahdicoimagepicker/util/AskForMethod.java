package ir.rubin.mahdicoimagepicker.util;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;

import ir.rubin.mahdicoimagepicker.R;
import ir.rubin.mahdicoimagepicker.listeners.OnMethodSelectedListener;

/**
 * @author mahdico
 */
public class AskForMethod {
		
	public static void doAsk(Activity mAct){
		final OnMethodSelectedListener mListener = (OnMethodSelectedListener) mAct;
		final Dialog mDialog = new Dialog(mAct);
		mDialog.setContentView(R.layout.layout_picmethod);
		mDialog.findViewById(R.id.btn_camera).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						mListener.onCam();
						mDialog.dismiss();
					}
				});
		mDialog.findViewById(R.id.btn_gallery).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						mListener.onGallery();
						mDialog.dismiss();
					}
				});
		mDialog.show();
	}

}
