package com.pcjinrong.pcjr.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;

import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.api.ApiService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 更新版本
 * Created by Mario on 2016/7/4.
 */
public class UpdateManager {

	private Context mContext;
	private boolean cancel = false;
	private String appname = "PcjinrongApp.apk";
	private String updateurl = "https://m.pcjr.com/";

	private String apk_url = "";
	public UpdateManager(Context context) {
		this.mContext = context;
	}

	/**
	 * 检查软件是否有更新版本
	 * 
	 * @return
	 */
	public void check() {
		// 获取当前软件版本
		final int versionCode = getVersionCode(mContext);

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(updateurl)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		ApiService service = retrofit.create(ApiService.class);
		Call<JsonObject> call = service.getNewstVersion();
		call.enqueue(new Callback<JsonObject>() {
			@Override
			public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
				if (response.isSuccessful()) {
					JsonObject json = response.body();
					int version = json.get("Version").getAsInt();
					apk_url = json.get("Url").getAsString();
					if (version > versionCode) {
						showNoticeDialog();
					}
				}
			}
			@Override
			public void onFailure(Call<JsonObject> call, Throwable t) {
				Logger.e("获取版本号失败");
			}
		});

	}
	/**
	 * 获取软件版本号
	 * 
	 * @param context
	 * @return
	 */
	private int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			versionCode = context.getPackageManager().getPackageInfo(
					mContext.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 显示软件更新对话框
	 */
	private void showNoticeDialog() {
		// 构造对话框
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("软件更新");
		builder.setMessage("检测到新版本，立即更新吗？");
		// 更新
		builder.setPositiveButton("更新", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 显示下载对话框
				downLoadApk();
			}
		});
		// 稍后更新
		builder.setNegativeButton("以后", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		AlertDialog noticeDialog = builder.create();
		noticeDialog.show();
	}

	public File getFileFromServer(String path, ProgressDialog pd)
			throws Exception {
		// 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			//conn.setRequestProperty("Accept-Encoding", "identity");
			// 获取到文件的大小
			pd.setMax(conn.getContentLength());
			InputStream is = conn.getInputStream();
			File file = new File(Environment.getExternalStorageDirectory()
					+ "/Download", appname);
			// 名字
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len;
			int total = 0;
			while ((len = bis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				total += len;
				// 获取当前下载量
				pd.setProgress(total);
			}
			fos.close();
			bis.close();
			is.close();
			if (cancel) {
				return null;
			} else {
				return file;
			}
		} else {
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	protected void downLoadApk() {
		final ProgressDialog pd; // 进度条对话框
		pd = new ProgressDialog(mContext);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("正在下载更新");
		pd.setButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				pd.dismiss();
				cancel = true;
			}
		});
		pd.show();
		new Thread() {
			@Override
			public void run() {
				try {
					File file = getFileFromServer(apk_url, pd);
					sleep(1500);
					installApk(file);
					pd.dismiss(); // 结束掉进度条对话框
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 安装APK文件
	 */
	private void installApk(File file) {
		Intent intent = new Intent();
		// 执行动作
		intent.setAction(Intent.ACTION_VIEW);
		// 执行的数据类型
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		mContext.startActivity(intent);
	}
}
