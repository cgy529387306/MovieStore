package com.android.mb.movie.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.android.mb.movie.R;
import com.android.mb.movie.retrofit.http.exception.ApiException;
import com.android.mb.movie.utils.DynamicTimeFormat;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;

/**
 * 主要用来获取全局的Context
 * @author cgy
 *
 */
public class MBApplication extends Application {
	
	private static final String TAG = MBApplication.class.getSimpleName();
	
	private static Context sInstance;

	static {
		//启用矢量图兼容
		AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
		//设置全局默认配置（优先级最低，会被其他设置覆盖）
		SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
			@Override
			public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
				//全局设置（优先级最低）
				layout.setEnableAutoLoadMore(true);
				layout.setEnableOverScrollDrag(false);
				layout.setEnableOverScrollBounce(true);
				layout.setEnableLoadMoreWhenContentNotFull(true);
				layout.setEnableScrollContentWhenRefreshed(true);
			}
		});
		SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
			@NonNull
			@Override
			public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
				//全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
				layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);

				return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
			}
		});
	}

	/**
	 * 获取全局Application对象
	 * @since 2013.08.02 修改错误提示内容为Application by pcqpcq
	 * @return
	 */
	public static Context getInstance(){
		if (sInstance == null) {
			throw new RuntimeException("Application must be init");
		}
		return sInstance;
	}

    /**
     * 初始化
     * <p>若未配置manifest可使用此方法初始化</p>
     * @param application 全局context
     */
    public static void init(Context application) {
        sInstance = application;
    }

    @Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
		PlayerFactory.setPlayManager(IjkPlayerManager.class);//ijk模式
	}
	
}
