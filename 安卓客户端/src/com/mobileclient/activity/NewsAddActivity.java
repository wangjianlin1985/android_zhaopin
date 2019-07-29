package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class NewsAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.news_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加新闻公告");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
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
		// 设置下拉列表的风格
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
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加新闻公告按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取标题*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(NewsAddActivity.this, "标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					news.setTitle(ET_title.getText().toString());
					/*验证获取新闻内容*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(NewsAddActivity.this, "新闻内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					news.setContent(ET_content.getText().toString());
					/*验证获取发布时间*/ 
					if(ET_publishDate.getText().toString().equals("")) {
						Toast.makeText(NewsAddActivity.this, "发布时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_publishDate.setFocusable(true);
						ET_publishDate.requestFocus();
						return;	
					}
					news.setPublishDate(ET_publishDate.getText().toString());
					/*调用业务逻辑层上传新闻公告信息*/
					NewsAddActivity.this.setTitle("正在上传新闻公告信息，稍等...");
					String result = newsService.AddNews(news);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
