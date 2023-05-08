package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.NewsClass;
import com.mobileclient.service.NewsClassService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class NewsClassDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明新闻分类id控件
	private TextView TV_newsClassId;
	// 声明新闻分类名称控件
	private TextView TV_newsClassName;
	/* 要保存的新闻分类信息 */
	NewsClass newsClass = new NewsClass(); 
	/* 新闻分类管理业务逻辑层 */
	private NewsClassService newsClassService = new NewsClassService();
	private int newsClassId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.newsclass_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看新闻分类详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_newsClassId = (TextView) findViewById(R.id.TV_newsClassId);
		TV_newsClassName = (TextView) findViewById(R.id.TV_newsClassName);
		Bundle extras = this.getIntent().getExtras();
		newsClassId = extras.getInt("newsClassId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				NewsClassDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    newsClass = newsClassService.GetNewsClass(newsClassId); 
		this.TV_newsClassId.setText(newsClass.getNewsClassId() + "");
		this.TV_newsClassName.setText(newsClass.getNewsClassName());
	} 
}
