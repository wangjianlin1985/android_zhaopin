package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.SpecialInfo;
import com.mobileclient.service.SpecialInfoService;
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
public class SpecialInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明专业id控件
	private TextView TV_specialId;
	// 声明专业名称控件
	private TextView TV_specialName;
	/* 要保存的专业信息 */
	SpecialInfo specialInfo = new SpecialInfo(); 
	/* 专业管理业务逻辑层 */
	private SpecialInfoService specialInfoService = new SpecialInfoService();
	private int specialId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.specialinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看专业详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_specialId = (TextView) findViewById(R.id.TV_specialId);
		TV_specialName = (TextView) findViewById(R.id.TV_specialName);
		Bundle extras = this.getIntent().getExtras();
		specialId = extras.getInt("specialId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SpecialInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    specialInfo = specialInfoService.GetSpecialInfo(specialId); 
		this.TV_specialId.setText(specialInfo.getSpecialId() + "");
		this.TV_specialName.setText(specialInfo.getSpecialName());
	} 
}
