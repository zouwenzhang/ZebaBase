package com.zeba.base.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore.Images.ImageColumns;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapTools {
	/**
	 * 压缩图片
	 * 
	 * @param filepath
	 * @param maxlenth最大的一边压缩为多少像素
	 * @return
	 */
	public static Bitmap CompressPictureToBitmap(String filepath, int maxlenth) {
		Options opt = new Options();
		opt.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filepath, opt);
		int max = 0;
		if (opt.outHeight > opt.outWidth) {
			max = opt.outHeight;
		} else {
			max = opt.outWidth;
		}
		if (max > maxlenth) {
			opt.inSampleSize = (int) ((max * 1.0) / maxlenth);
			opt.inJustDecodeBounds = false;
			Bitmap temp = BitmapFactory.decodeFile(filepath, opt);
			return temp;
		}
		opt.inJustDecodeBounds = false;
		Bitmap temp = BitmapFactory.decodeFile(filepath, opt);
		return temp;
	}
	
	/**
	 * 压缩图片
	 * 
	 * @param filepath
	 * @param max
	 * @return
	 */
	public static Bitmap CompressPictureToBitmapBySize(String filepath, float maxM) {
		Options opt = new Options();
		opt.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filepath, opt);
		int newHeight=opt.outHeight;
		int newWidth=opt.outWidth;
		int sz=1;
		float sm=0;
		while(true){
			if(newWidth/sz<=maxM){
				break;
			}
			sz*=2;
		}
		if(sz==1){
			opt.inSampleSize = sz;
			opt.inJustDecodeBounds = false;
			Bitmap temp = BitmapFactory.decodeFile(filepath, opt);
			return temp;
		}
		opt.inSampleSize = sz;
		opt.inJustDecodeBounds = false;
		Bitmap temp = BitmapFactory.decodeFile(filepath, opt);
		return temp;
//		float bs=1f;
//		int maxl=temp.getHeight();
//		if(temp.getWidth()>temp.getHeight()){
//			maxl=temp.getWidth();
//		}
//		if(maxl>maxM){
//			bs=maxl/maxM;
//		}
//		if(bs>1){
//			newHeight/=bs;
//			newWidth/=bs;
//		}else{
//			newHeight*=bs;
//			newWidth*=bs;
//		}
//		Loggers.e(Loggers.Type_Http,"sw="+temp.getWidth()+",sh="+temp.getHeight()+"bs="+bs+",sm="+sm+",sz="+sz+",nh="+newHeight+",nw="+newWidth);
//		Bitmap tmep2=Bitmap.createScaledBitmap(temp, newWidth, newHeight, true);
//		temp.recycle();
//		return tmep2;
	}

	/**
	 * 裁剪为边长为length，区域为center
	 * */
	public static Bitmap CropPictureToCenter(Bitmap becrop, int length) {
		if (becrop == null || length == 0) {
			return becrop;
		}
		Bitmap tempcrop = null;
		Bitmap temp = null;
		if (becrop.getWidth() > becrop.getHeight()) {
			float size = (becrop.getHeight() * 1f) / length;
			int dw = (int) (becrop.getWidth() / size);
			temp = Bitmap.createScaledBitmap(becrop, dw, length, true);
			int x = temp.getWidth() / 2 - length / 2;
			tempcrop = Bitmap.createBitmap(temp, x, 0, length, length);
		} else {
			try {
				float size = (becrop.getWidth() * 1f) / length;
				int dh = (int) (becrop.getHeight() / size);
				temp = Bitmap.createScaledBitmap(becrop, length, dh, true);
				int y = temp.getHeight() / 2 - length / 2;
				// 修复可能因y+length>temp.getHeight()造成的错误
				if (y + length > temp.getHeight()) {
					y = temp.getHeight() - length;
				}
				// 修复可能因y<0造成的错误
				if (y < 0) {
					y = 0;
				}
				tempcrop = Bitmap.createBitmap(temp, 0, y, length, length);
			} catch (Exception e) {
				return becrop;
			}
		}
		return tempcrop;
	}

	/**
	 * 按比例裁剪，区域为center
	 * */
	public static Bitmap CropPictureToBiLiCenter(Bitmap becrop, float bw,
			float bh) {
		if (becrop == null || bw == 0 || bh == 0) {
			return becrop;
		}
		Bitmap tempcrop = null;
		Bitmap temp = null;
		int x = 0;
		int y = 0;
		int w = 0;
		int h = 0;
		boolean isClipW = true;
		if (becrop.getWidth() > becrop.getHeight()) {
			w = (int) (becrop.getHeight() * bw / bh);
			if (w > becrop.getWidth()) {
				isClipW = false;
			}
		} else {
			isClipW = false;
			h = (int) (becrop.getWidth() * bh / bw);
			if (h > becrop.getHeight()) {
				isClipW = true;
			}
		}
		if (isClipW) {
			w = (int) (becrop.getHeight() * bw / bh);
			x = becrop.getWidth() / 2 - w / 2;
			h = becrop.getHeight();
			if (x + w > becrop.getWidth()) {
				x = becrop.getWidth() - w;
			}
		} else {
			h = (int) (becrop.getWidth() * bh / bw);
			y = becrop.getHeight() / 2 - h / 2;
			w = becrop.getWidth();
			if (y + h > becrop.getHeight()) {
				y = becrop.getHeight() - h;
			}
		}
		tempcrop = Bitmap.createBitmap(becrop, x, y, w, h);
		// if(becrop.getWidth()>becrop.getHeight()){
		// float size=(becrop.getHeight()*1f)/length;
		// int dw=(int) (becrop.getWidth()/size);
		// temp=Bitmap.createScaledBitmap(becrop, dw, length, true);
		// int x=temp.getWidth()/2-length/2;
		// tempcrop=Bitmap.createBitmap(temp, x, 0, length,length);
		// }else{
		// try{
		// float size=(becrop.getWidth()*1f)/length;
		// int dh=(int) (becrop.getHeight()/size);
		// temp=Bitmap.createScaledBitmap(becrop, length, dh, true);
		// int y=temp.getHeight()/2-length/2;
		// //修复可能因y+length>temp.getHeight()造成的错误
		// if(y+length>temp.getHeight()){
		// y=temp.getHeight()-length;
		// }
		// //修复可能因y<0造成的错误
		// if(y<0){
		// y=0;
		// }
		// tempcrop=Bitmap.createBitmap(temp, 0, y, length,length);
		// }catch(Exception e){
		// return becrop;
		// }
		// }
		return tempcrop;
	}

	/**
	 * 获取压缩后的图片，占用内存6M左右
	 * */
	public static Bitmap getCompressPicture(String pa) {
		float bsm = getBitmapSizeInMemory(pa);
		if (bsm > 6) {
			// BitmapFactory.Options opt = new Options();
			// opt.inJustDecodeBounds = true;
			// BitmapFactory.decodeFile(pa, opt);
			// int p=1;
			// float size=bsm;
			// do{
			// p=p*2;
			// size=(opt.outWidth/p)*(opt.outHeight/p)*getBitmapConfig(opt);
			// }while(size>5);
			int sz = (int) (bsm / 6);
			int s = sz % 2;
			sz = sz - s;
			// Log.e("zwz","���ֵ1:"+sz+",���ֵ2="+p);
			return CompressPicture(pa, sz);
		}
		return CompressPicture(pa, 1);
	}

	@SuppressLint("NewApi")
	public static long getBitmapsize(Bitmap bitmap) {
		// 12���ϰ汾
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			return bitmap.getByteCount();
		}
		// 1���ϰ汾
		return bitmap.getRowBytes() * bitmap.getHeight();

	}

	public static Bitmap CompressPicture(String pa, int size) {
		Options opt = new Options();
		opt.inJustDecodeBounds = false;
		opt.inSampleSize = size;
		Bitmap bmp = BitmapFactory.decodeFile(pa, opt);
		return bmp;
	}

	/** 读取图片文件 */
	public static Bitmap CompressPicture(String pa) {
		Options opt = new Options();
		opt.inJustDecodeBounds = false;
		Bitmap bmp = BitmapFactory.decodeFile(pa, opt);
		return bmp;
	}

	public static String getRealFilePath(final Context context, final Uri uri) {
		if (null == uri)
			return null;
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
			data = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			Cursor cursor = context.getContentResolver().query(uri,
					new String[] { ImageColumns.DATA }, null, null, null);
			if (null != cursor) {
				if (cursor.moveToFirst()) {
					int index = cursor.getColumnIndex(ImageColumns.DATA);
					if (index > -1) {
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}

	public static int getBitmapConfig(Config config) {
		int jc = 1;
		if (config == Config.ALPHA_8) {
			jc = 1;
		} else if (config == Config.ARGB_4444) {
			jc = 2;
		} else if (config == Config.ARGB_8888) {
			jc = 4;
		} else if (config == Config.RGB_565) {
			jc = 2;
		}
		return jc;
	}

	public static float getBitmapSizeInMemory(String path) {
		Options opt = new Options();
		opt.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opt);
		return opt.outHeight * opt.outWidth
				* getBitmapConfig(opt.inPreferredConfig) / 1024f / 1024f;
	}

	public static float getBitmapMBSizeInRect(int w, int h, Config config) {
		return w * h * getBitmapConfig(config) / 1024f / 1024f;
	}

	public static float getBitmapSizeInMemory(Bitmap path) {
		return getBitmapSize(path) / 1024f / 1024f;
	}

	@SuppressLint("NewApi")
	public static int getBitmapSize(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // API 19
			return bitmap.getAllocationByteCount();
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {// API
																			// 12
			return bitmap.getByteCount();
		}
		return bitmap.getRowBytes() * bitmap.getHeight(); // earlier version
	}

	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// ��ȡ��Ŀ¼
		}
		return sdDir.toString();
	}

	/**
	 * �����β���ͼƬ
	 */
	public static Bitmap ImageCrop(Bitmap bitmap) {
		int w = bitmap.getWidth(); // �õ�ͼƬ�Ŀ?��
		int h = bitmap.getHeight();

		int wh = w > h ? h : w;// ���к���ȡ����������߳�

		int retX = w > h ? (w - h) / 2 : 0;// ����ԭͼ��ȡ�������Ͻ�x���
		int retY = w > h ? 0 : (h - w) / 2;

		// ��������ǹؼ�
		return Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
	}

	/** 保存图片格式为jpeg */
	public static void saveBitmap(Bitmap bitmap, String outpath) {
		File f = new File(outpath);
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/** 将bitmap转换为圆形 */
	public static Bitmap getRoundBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = bitmap.getWidth() / 2;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/** 将bitmap转换为圆形 */
	public static Bitmap getRoundBitmap(Bitmap bitmap,float radius) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, radius, radius, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/** 图片按比例大小压缩方法（根据Bitmap图片压缩） */
	public static Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		Options newOpts = new Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		newOpts.inPreferredConfig = Config.ARGB_8888;// 降低图片从ARGB888到RGB565
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	/** 质量压缩法 */
	public static Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 80) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			options -= 10;// 每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * 保持长宽比缩小Bitmap
	 * 
	 * @param bitmap
	 * @param maxWidth
	 * @param maxHeight
	 * @return
	 */
	public static Bitmap resizeBitmap(Bitmap bitmap, int max) {

		int originWidth = bitmap.getWidth();
		int originHeight = bitmap.getHeight();
		
		if(max>=originWidth&&max>=originHeight){
			return bitmap;
		}
		
		int maxOrigin=originHeight;
		if(originWidth>originHeight){
			maxOrigin=originWidth;
		}
		
		float bei=max*1f/maxOrigin;
		int width = (int) (originWidth*bei);
		int height = (int) (originHeight*bei);

		bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);

		return bitmap;
	}
}
