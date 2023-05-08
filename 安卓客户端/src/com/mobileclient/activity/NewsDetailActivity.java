package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.News;
import com.mobileclient.service.NewsService;
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
public class NewsDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明新闻id控件
	private TextView TV_newsId;
	// 声明新闻分类控件
	private TextView TV_newsClassObj;
	// 声明标题控件
	private TextView TV_title;
	// 声明新闻内容控件
	private TextView TV_content;
	// 声明发布时间控件
	private TextView TV_publishDate;
	/* 要保存的新闻公告信息 */
	News news = new News(); 
	/* 新闻公告管理业务逻辑层 */
	private NewsService newsService = new NewsService();
	private NewsClassService newsClassService = new NewsClassService();
	private int newsId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.news_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看新闻公告详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_newsId = (TextView) findViewById(R.id.TV_newsId);
		TV_newsClassObj = (TextView) findViewById(R.id.TV_newsClassObj);
		TV_title = (TextView) findViewById(R.id.TV_title);
		TV_content = (TextView) findViewById(R.id.TV_content);
		TV_publishDate = (TextView) findViewById(R.id.TV_publishDate);
		Bundle extras = this.getIntent().getExtras();
		newsId = extras.getInt("newsId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				NewsDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    news = newsService.GetNews(newsId); 
		this.TV_newsId.setText(news.getNewsId() + "");
		NewsClass newsClassObj = newsClassService.GetNewsClass(news.getNewsClassObj());
		this.TV_newsClassObj.setText(newsClassObj.getNewsClassName());
		this.TV_title.setText(news.getTitle());
		this.TV_content.setText(news.getContent());
		this.TV_publishDate.setText(news.getPublishDate());
	} 
}
