package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.SpecialInfo;
import com.mobileclient.service.SpecialInfoService;
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

public class SpecialInfoEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明专业idTextView
	private TextView TV_specialId;
	// 声明专业名称输入框
	private EditText ET_specialName;
	protected String carmera_path;
	/*要保存的专业信息*/
	SpecialInfo specialInfo = new SpecialInfo();
	/*专业管理业务逻辑层*/
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
		setContentView(R.layout.specialinfo_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑专业信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_specialId = (TextView) findViewById(R.id.TV_specialId);
		ET_specialName = (EditText) findViewById(R.id.ET_specialName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		specialId = extras.getInt("specialId");
		/*单击修改专业按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取专业名称*/ 
					if(ET_specialName.getText().toString().equals("")) {
						Toast.makeText(SpecialInfoEditActivity.this, "专业名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_specialName.setFocusable(true);
						ET_specialName.requestFocus();
						return;	
					}
					specialInfo.setSpecialName(ET_specialName.getText().toString());
					/*调用业务逻辑层上传专业信息*/
					SpecialInfoEditActivity.this.setTitle("正在更新专业信息，稍等...");
					String result = specialInfoService.UpdateSpecialInfo(specialInfo);
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
	    specialInfo = specialInfoService.GetSpecialInfo(specialId);
		this.TV_specialId.setText(specialId+"");
		this.ET_specialName.setText(specialInfo.getSpecialName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
