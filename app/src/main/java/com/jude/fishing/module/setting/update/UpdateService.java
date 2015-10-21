package com.jude.fishing.module.setting.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.jude.fishing.R;
import com.jude.utils.JUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class UpdateService extends Service {

	private NotificationManager notificationManager;
	private Notification notification;

	private int notification_id = 1003;

	private DownloadAsyncTask task;
	RemoteViews contentView;

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		JUtils.Log("Download onStartCommand");
		if(intent.getExtras()==null&&task!=null){
			task.stop();
			task=null;
			JUtils.Toast("下载已取消");
			return  START_NOT_STICKY;
		}
		String title = intent.getExtras().getString("title");
		String url = intent.getExtras().getString("url");
		String filepath = intent.getExtras().getString("path");
		String filename = intent.getExtras().getString("name");

		if(url!=null&&JUtils.isNetWorkAvilable()&&task==null){
			JUtils.Log("onStartCommand url:"+url+"  filepath:"+filename+"  filename:"+filename);
			createNotification(title);
			task = new DownloadAsyncTask();
			task.execute(url,filepath,filename);
		}
		return  START_NOT_STICKY;
	}


	@Override
	public void onDestroy() {
		JUtils.Log("onDestroy");
		task.cancel(true);
		task=null;
		super.onDestroy();
	}

	public void createNotification(String title) {
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notification = new Notification();

		contentView = new RemoteViews(getPackageName(),
				R.layout.setting_update_notification);
		contentView.setTextViewText(R.id.notificationTitle, title);
		contentView.setTextViewText(R.id.notificationPercent, "0%");
		contentView.setProgressBar(R.id.notificationProgress, 100, 0, false);

		notification.contentView = contentView;
		notification.icon = R.mipmap.logo;
		
		Intent stopIntent = new Intent(this, UpdateService.class);
		PendingIntent pendingIntent = PendingIntent.getService(this, 0, stopIntent, 0);
		
		notification.deleteIntent = pendingIntent;
		notificationManager.notify(notification_id, notification);

	}


	private void updateNotification(int downloadCount) {
		contentView.setTextViewText(R.id.notificationPercent, downloadCount
				+ "%");
		contentView.setProgressBar(R.id.notificationProgress, 100,
				downloadCount, false);
		notificationManager.notify(notification_id, notification);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}


	class DownloadAsyncTask extends AsyncTask<String,Integer,Boolean>{
		private String path;
		private String name;
		private boolean cancelUpdate;
		
		private void stop(){
			cancelUpdate = true;
		}
		
		@Override
		protected Boolean doInBackground(String... params) {
			JUtils.Log("StartDownLoadTask");
			path = params[1];
			name = params[2];
			boolean finish = false;
			try{
				URL url = new URL(params[0]);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(5000);
				if(conn.getResponseCode() == 200) {
					File f = new File(params[1]);
					if (!f.isDirectory()) {
						f.mkdirs();
					}
					InputStream is = conn.getInputStream();
					int length = conn.getContentLength();
					File file = new File(path+name);
					FileOutputStream fos = new FileOutputStream(file);
					int count = 0;
					byte buf[] = new byte[1024];
					int progress = 0;
					int progress_pre = 0;
					do {
						int numread = is.read(buf);
						count += numread;
						progress = (int) (((float) count / length) * 100);
						if(progress != progress_pre){
							JUtils.Log("progress"+progress);
							publishProgress(progress);
							progress_pre = progress;
						}
						
						if (numread <= 0) {				
							break;
						}
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);
					fos.flush();
					fos.close();
					is.close();
					finish = true;
				}
			} catch (Exception e) {
				JUtils.Log(e.getLocalizedMessage());
				finish = false;
			}				
			return finish;
		}

		@Override
		protected void onPostExecute(Boolean finish) {
			JUtils.Log("FinishDownLoadTask");
			notificationManager.cancel(notification_id);
			if(finish&&!cancelUpdate){
				Intent intent = new Intent(Intent.ACTION_VIEW);
	            intent.setDataAndType(  
	                    Uri.fromFile(new File(path+name)),  
	                    "application/vnd.android.package-archive");  
	            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            startActivity(intent);
			}else{
				JUtils.Toast("下载错误");
			}
		}


		@Override
		protected void onProgressUpdate(Integer... values) {
			updateNotification(values[0]);
		}

	}
}
