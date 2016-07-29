package com.example.onlineimageviewer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	private ListView listViewImage;
	private ProgressBar progress;
	private ArrayList<ImageData> alImageTitle = new ArrayList<ImageData>();
	private ArrayAdapter<ImageData> adapter;
	private String filePath = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listViewImage = (ListView) findViewById(R.id.listView1);
		progress = (ProgressBar) findViewById(R.id.progressBar1);
		progress.setVisibility(View.GONE);
		alImageTitle.add(new ImageData("Iphone", "http://rahulsharma.xyz/iphone.png", "iphone.png"));
		alImageTitle.add(new ImageData("HTC", "http://rahulsharma.xyz/htc.png", "htc.png"));
		alImageTitle.add(new ImageData("Motorala", "http://rahulsharma.xyz/moto.png", "moto.png"));
		alImageTitle.add(new ImageData("Windows", "http://rahulsharma.xyz/win.png", "wim.png"));
		alImageTitle.add(new ImageData("Samsung", "http://rahulsharma.xyz/sam.png", "sam.png"));

		adapter = new ArrayAdapter<ImageData>(MainActivity.this, android.R.layout.simple_list_item_1, alImageTitle);
		listViewImage.setAdapter(adapter);

		listViewImage.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				ImageData clickedImage = (ImageData) alImageTitle.get(position);
				String url = clickedImage.getImageURL();
				String fileName = clickedImage.getImageFileName();
				ImageDownloadTask task = new ImageDownloadTask();
				task.execute(url, fileName);
			}
		});

	}

	class ImageDownloadTask extends AsyncTask<String, Void, String[]> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progress.setVisibility(View.VISIBLE);
		}

		@Override
		protected String[] doInBackground(String... params) {
			String url = params[0];
			String fileName = params[1];

			String array[] = new String[2];

			ApplicationInfo appInfo = getApplicationInfo();
			filePath = appInfo.dataDir + "/" + fileName;
			File file = new File(filePath);
			
			try {

				if (!file.exists()) {
				HttpGet httpGetRequest = new HttpGet(url);
				HttpClient httpClient = new DefaultHttpClient();

				Bitmap bmp = null;

				HttpResponse response = httpClient.execute(httpGetRequest);
				InputStream is = response.getEntity().getContent();
				bmp = BitmapFactory.decodeStream(is);
					FileOutputStream out = new FileOutputStream(file);
					bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
					out.flush();
					out.close();
				}

				array[0] = filePath;
				array[1] = fileName;

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			return array;
		}

		@Override
		protected void onPostExecute(String result[]) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			final Dialog dialog = new Dialog(MainActivity.this);
			dialog.setTitle(result[1]);
			dialog.setContentView(R.layout.dialog_view);
			ImageView image = (ImageView) dialog.findViewById(R.id.imageView1);
			Bitmap bitmap = BitmapFactory.decodeFile(result[0]);
			image.setImageBitmap(bitmap);

			Button btnClose = (Button) dialog.findViewById(R.id.button1);

			btnClose.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			progress.setVisibility(View.GONE);
			dialog.setCancelable(false);
			dialog.show();
		}
	}

}
