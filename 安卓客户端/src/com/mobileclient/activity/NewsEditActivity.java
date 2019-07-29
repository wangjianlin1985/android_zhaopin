package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.News;
import com.mobileclient.service.NewsService;
import com.mobileclient.domain.NewsClass;
import com.mobileclient.service.NewsClassService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class NewsEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明新闻idTextView
	private TextView TV_newsId;
	// 声明新闻分类下拉框
	private Spinner spinner_newsClassObj;
	private ArrayAdapter<String> newsClassObj_adapter;
	private static  String[] newsClassObj_ShowText  = null;
	private List<NewsClass> newsClassList = null;
	/*新闻分类管理业务逻辑层*/
	private NewsClassService newsClassService = new NewsClassService();
	// 声明标题输入框
	private EditText ET_title;
	// 声明新闻内容输入框
	private EditText ET_content;
	// 声明发布时间输入框
	private EditText ET_publishDate;
	protected String carmera_path;
	/*要保存的新闻公告信息*/
	News news = new News();
	/*新闻公告管理业务逻辑层*/
	private NewsService newsService = new NewsService();

	private int newsId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.news_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑新闻公告信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_newsId = (TextView) findViewById(R.id.TV_newsId);
		spinner_newsClassObj = (Spinner) findViewById(R.id.Spinner_newsClassObj);
		// 获取所有的新闻分类
		try {
			newsClassList = newsClassService.QueryNewsClass(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int newsClassCount = newsClassList.size();
		newsClassObj_ShowText = new String[newsClassCount];
		for(int i=0;i<newsClassCount;i++) { 
			newsClassObj_ShowText[i] = newsClassList.get(i).getNewsClassName();
		}
		// 将可选内容与ArrayAdapter连接起来
		newsClassObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, newsClassObj_ShowText);
		// 设置图书类别下拉列表的风格
		newsClassObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_newsClassObj.setAdapter(newsClassObj_adapter);
		// 添加事件Spinner事件监听
		spinner_newsClassObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				news.setNewsClassObj(newsClassList.get(arg2).getNewsClassId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_newsClassObj.setVisibility(View.VISIBLE);
		ET_title = (EditText) findViewById(R.id.ET_title);
		ET_content = (EditText) findViewById(R.id.ET_content);
		ET_publishDate = (EditText) findViewById(R.id.ET_publishDate);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		newsId = extras.getInt("newsId");
		/*单击修改新闻公告按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取标题*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(NewsEditActivity.this, "标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					news.setTitle(ET_title.getText().toString());
					/*验证获取新闻内容*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(NewsEditActivity.this, "新闻内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					news.setContent(ET_content.getText().toString());
					/*验证获取发布时间*/ 
					if(ET_publishDate.getText().toString().equals("")) {
						Toast.makeText(NewsEditActivity.this, "发布时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_publishDate.setFocusable(true);
						ET_publishDate.requestFocus();
						return;	
					}
					news.setPublishDate(ET_publishDate.getText().toString());
					/*调用业务逻辑层上传新闻公告信息*/
					NewsEditActivity.this.setTitle("正在更新新闻公告信息，稍等...");
					String result = newsService.UpdateNews(news);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    news = newsService.GetNews(newsId);
		this.TV_newsId.setText(newsId+"");
		for (int i = 0; i < newsClassList.size(); i++) {
			if (news.getNewsClassObj() == newsClassList.get(i).getNewsClassId()) {
				this.spinner_newsClassObj.setSelection(i);
				break;
			}
		}
		this.ET_title.setText(news.getTitle());
		this.ET_content.setText(news.getContent());
		this.ET_publishDate.setText(news.getPublishDate());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
