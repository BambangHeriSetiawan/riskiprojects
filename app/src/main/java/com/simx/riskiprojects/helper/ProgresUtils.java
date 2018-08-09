package com.simx.riskiprojects.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.widget.TextView;
import com.simx.riskiprojects.R;

/**
 * User: simx Date: 09/08/18 11:14
 */
public class ProgresUtils {
	private static ProgresUtils instance;
	private static Dialog dialog;
	private static AlertDialog alertDialog;
	public static ProgresUtils getInstance() {
		if (instance == null){
			instance = new ProgresUtils();
		}
		return instance;
	}

	/**
	 * Dialog Loading without message
	 * @param activity
	 */
	public  void showLodingDialog(Activity activity){
		dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(false);
		dialog.setContentView(R.layout.custom_dialog_no_title);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.show();
	}

	/**
	 * Dialog Loading with message
	 * @param activity
	 */
	public  void showLodingDialogMsg(Activity activity,String msg){
		dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.custom_dilog_message);
		TextView textView = dialog.findViewById(R.id.tv_message);
		textView.setText(msg);
		dialog.show();
	}
	public  void showLodingDialogMsgBtn(Activity activity,String msg, DialogInterface.OnClickListener onClickListener){
		dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.custom_dilog_message);
		TextView textView = dialog.findViewById(R.id.tv_message);
		textView.setText(msg);
		dialog.show();
	}



	public  void dismisDialog(){
		if (dialog != null){
			if (dialog.isShowing()){
				dialog.dismiss();
			}
		}
	}
}

