package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.QiyeProperty;
import com.mobileclient.service.QiyePropertyService;
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
public class QiyePropertyDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_id;
	// 声明企业性质名称控件
	private TextView TV_propertyName;
	/* 要保存的企业性质信息 */
	QiyeProperty qiyeProperty = new QiyeProperty(); 
	/* 企业性质管理业务逻辑层 */
	private QiyePropertyService qiyePropertyService = new QiyePropertyService();
	private int id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.qiyeproperty_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看企业性质详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_id = (TextView) findViewById(R.id.TV_id);
		TV_propertyName = (TextView) findViewById(R.id.TV_propertyName);
		Bundle extras = this.getIntent().getExtras();
		id = extras.getInt("id");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				QiyePropertyDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    qiyeProperty = qiyePropertyService.GetQiyeProperty(id); 
		this.TV_id.setText(qiyeProperty.getId() + "");
		this.TV_propertyName.setText(qiyeProperty.getPropertyName());
	} 
}
