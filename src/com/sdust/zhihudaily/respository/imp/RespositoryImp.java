/**
 * �ļ�����RespositoryImp.java
 * ʱ�䣺2015��7��19������10:01:30
 * ���ߣ���ά��
 */
package com.sdust.zhihudaily.respository.imp;

import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.sdust.zhihudaily.bean.StartImage;
import com.sdust.zhihudaily.respository.interfaces.CacheRespository;
import com.sdust.zhihudaily.respository.interfaces.NetRespository;
import com.sdust.zhihudaily.respository.interfaces.Respository;

/**
 * ������RespositoryImp
 * ˵����
 */
public class RespositoryImp implements Respository{
	
	private CacheRespository mCacheResImp;
	
	private NetRespository mNetResImp;
	
	private Context mContext;
	public RespositoryImp(Context context) {
		mContext = context;
		mCacheResImp = new CacheRespositoryImp(mContext);
		mNetResImp = new NetRespositoryImp();
	}

	@Override
	public void getStartImage(final int height,final int width,final DisplayImageOptions options,final Callback<StartImage> callback) {
		mCacheResImp.getStartImage(height,width,new CacheRespository.Callback<StartImage>() {

			@Override
			public void success(StartImage image) {
				callback.success(image);
			}

			@Override
			public void failure(Exception e) {
				callback.failure(e);
			}
		});
		
		mNetResImp.getStartImage(height,width,new NetRespository.Callback<StartImage>() {

			@Override
			public void success(StartImage startImage) {
				mCacheResImp.saveStartImage(height, width, options, startImage);
			}

			@Override
			public void failure(Exception e) {
				callback.failure(e);
			}
		});
	}

}