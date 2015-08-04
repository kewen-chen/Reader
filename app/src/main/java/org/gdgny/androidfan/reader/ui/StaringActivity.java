package org.gdgny.androidfan.reader.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

import org.gdgny.androidfan.reader.R;
import org.gdgny.androidfan.reader.entry.MyBookPageFactory;
import org.gdgny.androidfan.reader.widget.MyPageWidget;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class StaringActivity extends Activity {
	private MyPageWidget mPageWidget;
	private Bitmap mCurrentPageBitmap, mNextPageBitmap;
	private Canvas mCurrentPageCanvas, mNextPageCanvas;
	private DisplayMetrics dm;
	private MyBookPageFactory pagefactory;
	private int id;
	private SharedPreferences sp;
	private int[] position = new int[]{0, 0};
	private int fontsize = 50;
	private LayoutInflater mInflater;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		mInflater = LayoutInflater.from(this);
		mPageWidget = new MyPageWidget(this);
		setContentView(mPageWidget);
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);    
		mCurrentPageBitmap = Bitmap.createBitmap(dm.widthPixels, dm.heightPixels, Bitmap.Config.ARGB_8888);
//		mNextPageBitmap = Bitmap.createBitmap(dm.widthPixels, dm.heightPixels, Bitmap.Config.ARGB_8888);
		mCurrentPageCanvas = new Canvas(mCurrentPageBitmap);
//		mNextPageCanvas = new Canvas(mNextPageBitmap);
		fontsize = sp.getInt("fontsize", 50);
		pagefactory = new MyBookPageFactory(dm.widthPixels, dm.heightPixels, fontsize);
		try {
			getWindow().addFlags(WindowManager.LayoutParams.class.getField("FLAG_NEEDS_MENU_KEY").getInt(null));
		}catch (NoSuchFieldException e) {
			// Ignore since this field won't exist in most versions of Android
		}catch (IllegalAccessException e) {
			Log.w("feelyou.info", "Could not access FLAG_NEEDS_MENU_KEY in addLegacyOverflowButton()", e);
		}
		
		try {
			Bundle bundle = this.getIntent().getExtras();
			id = bundle.getInt("bookid");
			position[0] = sp.getInt(id+"begin", 0);
			position[1] = sp.getInt(id+"end", 0);
//			Toast.makeText(getApplicationContext(),"fanyehhaha",Toast.LENGTH_LONG).show();
			copy(id);
			pagefactory.setBgBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.ff));
			pagefactory.openBook(this.getFilesDir().getPath() + "/" + id + ".txt", position);
			pagefactory.onDrow(mCurrentPageCanvas);

		} catch (Exception e) {
			// TODO: handle exception
		}
		mPageWidget.setDrawBitMap(mCurrentPageBitmap);
		mPageWidget.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(v == mPageWidget){
					if(event.getAction()== MotionEvent.ACTION_DOWN && event.getY() > dm.heightPixels*2/3){
						if(event.getX()>dm.widthPixels/2 ){
							pagefactory.nextPage();
							pagefactory.onDrow(mCurrentPageCanvas);
							mPageWidget.setDrawBitMap(mCurrentPageBitmap);												
						}else{
							pagefactory.prePage();
							pagefactory.onDrow(mCurrentPageCanvas);
							mPageWidget.setDrawBitMap(mCurrentPageBitmap);
						}
						mPageWidget.invalidate();
						return true;
					}
				}
				return false;
			}
		});

		mPageWidget.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				openOptionsMenu();
				return true;
			}
		});
	}
	private void copy(int id) {
		// TODO Auto-generated method stub
		try {
			String filePath = this.getFilesDir().getPath();
			File file = new File(filePath);
			if(!file.exists()){
				file.mkdir();
			}
			AssetManager assetManager = this.getAssets();
			if(!new File(filePath + "/" + id + ".txt").exists()){
				InputStream inputStream = assetManager.open(id + ".jpg");
				BufferedInputStream bis = new BufferedInputStream(inputStream);
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath + "/" + id + ".txt"));
				byte[] buffer = new byte[1024];
				int length = 0;
				while((length = bis.read(buffer)) > 0){
					bos.write(buffer, 0, length);
				}
				bis.close();
				bos.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		position = pagefactory.getPosition();
		Editor editor = sp.edit();
		editor.putInt(id+"begin", position[0]);
		editor.putInt(id+"end", position[1]);
		editor.commit();
		int fontSize = pagefactory.getTextFont();
		Editor editor2 = sp.edit();
		editor2.putInt("fontsize", fontSize);
		editor2.commit();
		
	}
//	@Override
//	public boolean onMenuItemSelected(int featureId, MenuItem item) {
//		// TODO Auto-generated method stub
//		switch (item.getItemId()) {
//		case 1:
//			showAlterDialog(1);
//			break;
//		case 2:
//			showAlterDialog(2);
//			break;
//		default:
//			break;
//		}
//		return super.onMenuItemSelected(featureId, item);
//	}
//	private void showAlterDialog(int position) {
//		Builder builder = new Builder(this);
//		final int i = position;
////		if(i == 1){
////			builder.setTitle(R.string.font);
////		}else if(i == 2){
////			builder.setTitle(R.string.persent);
//		}
//		View view = mInflater.inflate(R.layout.set_font, null);
//		final EditText et_font = (EditText) view.findViewById(R.id.et_font);
//		builder.setView(view);
//		builder.setPositiveButton(R.string.ok, new OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				String text = et_font.getText().toString();
//				if(text != null && !text.equals("")){
//					if(i == 1){
//						pagefactory.setTextFont(Integer.parseInt(text));
//					}else if(i == 2){
//						pagefactory.setPersent(Integer.parseInt(text));
//					}
//					pagefactory.onDrow(mCurrentPageCanvas);
//					mPageWidget.setDrawBitMap(mCurrentPageBitmap);
//					mPageWidget.invalidate();
//				}
//			}
//		});
//		builder.setNegativeButton(R.string.cancel, new OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//		builder.show();
//	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
//		menu.add(0,1,1, R.string.font);
//		menu.add(0,2,2, R.string.persent);
		return super.onCreateOptionsMenu(menu);
	}

	
	
}
