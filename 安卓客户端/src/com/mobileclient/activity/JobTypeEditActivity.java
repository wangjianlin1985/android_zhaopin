package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.JobType;
import com.mobileclient.service.JobTypeService;
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

public class JobTypeEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明职位分类idTextView
	private TextView TV_jobTypeId;
	// 声明分类名称输入框
	private EditText ET_typeName;
	protected String carmera_path;
	/*要保存的职位分类信息*/
	JobType jobType = new JobType();
	/*职位分类管理业务逻辑层*/
	private JobTypeService jobTypeService = new JobTypeService();

	private int jobTypeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.jobtype_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑职位分类信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_jobTypeId = (TextView) findViewById(R.id.TV_jobTypeId);
		ET_typeName = (EditText) findViewById(R.id.ET_typeName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		jobTypeId = extras.getInt("jobTypeId");
		/*单击修改职位分类按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取分类名称*/ 
					if(ET_typeName.getText().toString().equals("")) {
						Toast.makeText(JobTypeEditActivity.this, "分类名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_typeName.setFocusable(true);
						ET_typeName.requestFocus();
						return;	
					}
					jobType.setTypeName(ET_typeName.getText().toString());
					/*调用业务逻辑层上传职位分类信息*/
					JobTypeEditActivity.this.setTitle("正在更新职位分类信息，稍等...");
					String result = jobTypeService.UpdateJobType(jobType);
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
	    jobType = jobTypeService.GetJobType(jobTypeId);
		this.TV_jobTypeId.setText(jobTypeId+"");
		this.ET_typeName.setText(jobType.getTypeName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
