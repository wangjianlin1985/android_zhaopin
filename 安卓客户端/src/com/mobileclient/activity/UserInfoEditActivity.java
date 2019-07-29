package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.SchoolRecord;
import com.mobileclient.service.SchoolRecordService;
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

public class UserInfoEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明用户名TextView
	private TextView TV_user_name;
	// 声明登录密码输入框
	private EditText ET_password;
	// 声明姓名输入框
	private EditText ET_name;
	// 声明性别输入框
	private EditText ET_gender;
	// 出版出生日期控件
	private DatePicker dp_birthDate;
	// 声明用户照片图片框控件
	private ImageView iv_userPhoto;
	private Button btn_userPhoto;
	protected int REQ_CODE_SELECT_IMAGE_userPhoto = 1;
	private int REQ_CODE_CAMERA_userPhoto = 2;
	// 声明学历下拉框
	private Spinner spinner_myShoolRecord;
	private ArrayAdapter<String> myShoolRecord_adapter;
	private static  String[] myShoolRecord_ShowText  = null;
	private List<SchoolRecord> schoolRecordList = null;
	/*学历管理业务逻辑层*/
	private SchoolRecordService schoolRecordService = new SchoolRecordService();
	// 声明毕业学校输入框
	private EditText ET_schoolName;
	// 声明工作经验输入框
	private EditText ET_workYears;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明邮箱输入框
	private EditText ET_email;
	// 声明现居地址输入框
	private EditText ET_address;
	// 声明求职意向输入框
	private EditText ET_qzyx;
	// 声明工作经历输入框
	private EditText ET_gzjl;
	// 声明教育经历输入框
	private EditText ET_jyjl;
	// 声明自我评价输入框
	private EditText ET_zwpj;
	// 声明注册时间输入框
	private EditText ET_regTime;
	protected String carmera_path;
	/*要保存的求职者信息*/
	UserInfo userInfo = new UserInfo();
	/*求职者管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();

	private String user_name;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.userinfo_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑求职者信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_user_name = (TextView) findViewById(R.id.TV_user_name);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_gender = (EditText) findViewById(R.id.ET_gender);
		dp_birthDate = (DatePicker)this.findViewById(R.id.dp_birthDate);
		iv_userPhoto = (ImageView) findViewById(R.id.iv_userPhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_userPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UserInfoEditActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_userPhoto);
			}
		});
		btn_userPhoto = (Button) findViewById(R.id.btn_userPhoto);
		btn_userPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_userPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_userPhoto);  
			}
		});
		spinner_myShoolRecord = (Spinner) findViewById(R.id.Spinner_myShoolRecord);
		// 获取所有的学历
		try {
			schoolRecordList = schoolRecordService.QuerySchoolRecord(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int schoolRecordCount = schoolRecordList.size();
		myShoolRecord_ShowText = new String[schoolRecordCount];
		for(int i=0;i<schoolRecordCount;i++) { 
			myShoolRecord_ShowText[i] = schoolRecordList.get(i).getSchooRecordName();
		}
		// 将可选内容与ArrayAdapter连接起来
		myShoolRecord_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, myShoolRecord_ShowText);
		// 设置图书类别下拉列表的风格
		myShoolRecord_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_myShoolRecord.setAdapter(myShoolRecord_adapter);
		// 添加事件Spinner事件监听
		spinner_myShoolRecord.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				userInfo.setMyShoolRecord(schoolRecordList.get(arg2).getId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_myShoolRecord.setVisibility(View.VISIBLE);
		ET_schoolName = (EditText) findViewById(R.id.ET_schoolName);
		ET_workYears = (EditText) findViewById(R.id.ET_workYears);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_email = (EditText) findViewById(R.id.ET_email);
		ET_address = (EditText) findViewById(R.id.ET_address);
		ET_qzyx = (EditText) findViewById(R.id.ET_qzyx);
		ET_gzjl = (EditText) findViewById(R.id.ET_gzjl);
		ET_jyjl = (EditText) findViewById(R.id.ET_jyjl);
		ET_zwpj = (EditText) findViewById(R.id.ET_zwpj);
		ET_regTime = (EditText) findViewById(R.id.ET_regTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		user_name = extras.getString("user_name");
		/*单击修改求职者按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取登录密码*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					userInfo.setPassword(ET_password.getText().toString());
					/*验证获取姓名*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					userInfo.setName(ET_name.getText().toString());
					/*验证获取性别*/ 
					if(ET_gender.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "性别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_gender.setFocusable(true);
						ET_gender.requestFocus();
						return;	
					}
					userInfo.setGender(ET_gender.getText().toString());
					/*获取出版日期*/
					Date birthDate = new Date(dp_birthDate.getYear()-1900,dp_birthDate.getMonth(),dp_birthDate.getDayOfMonth());
					userInfo.setBirthDate(new Timestamp(birthDate.getTime()));
					if (!userInfo.getUserPhoto().startsWith("upload/")) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						UserInfoEditActivity.this.setTitle("正在上传图片，稍等...");
						String userPhoto = HttpUtil.uploadFile(userInfo.getUserPhoto());
						UserInfoEditActivity.this.setTitle("图片上传完毕！");
						userInfo.setUserPhoto(userPhoto);
					} 
					/*验证获取毕业学校*/ 
					if(ET_schoolName.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "毕业学校输入不能为空!", Toast.LENGTH_LONG).show();
						ET_schoolName.setFocusable(true);
						ET_schoolName.requestFocus();
						return;	
					}
					userInfo.setSchoolName(ET_schoolName.getText().toString());
					/*验证获取工作经验*/ 
					if(ET_workYears.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "工作经验输入不能为空!", Toast.LENGTH_LONG).show();
						ET_workYears.setFocusable(true);
						ET_workYears.requestFocus();
						return;	
					}
					userInfo.setWorkYears(ET_workYears.getText().toString());
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					userInfo.setTelephone(ET_telephone.getText().toString());
					/*验证获取邮箱*/ 
					if(ET_email.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "邮箱输入不能为空!", Toast.LENGTH_LONG).show();
						ET_email.setFocusable(true);
						ET_email.requestFocus();
						return;	
					}
					userInfo.setEmail(ET_email.getText().toString());
					/*验证获取现居地址*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "现居地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					userInfo.setAddress(ET_address.getText().toString());
					/*验证获取求职意向*/ 
					if(ET_qzyx.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "求职意向输入不能为空!", Toast.LENGTH_LONG).show();
						ET_qzyx.setFocusable(true);
						ET_qzyx.requestFocus();
						return;	
					}
					userInfo.setQzyx(ET_qzyx.getText().toString());
					/*验证获取工作经历*/ 
					if(ET_gzjl.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "工作经历输入不能为空!", Toast.LENGTH_LONG).show();
						ET_gzjl.setFocusable(true);
						ET_gzjl.requestFocus();
						return;	
					}
					userInfo.setGzjl(ET_gzjl.getText().toString());
					/*验证获取教育经历*/ 
					if(ET_jyjl.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "教育经历输入不能为空!", Toast.LENGTH_LONG).show();
						ET_jyjl.setFocusable(true);
						ET_jyjl.requestFocus();
						return;	
					}
					userInfo.setJyjl(ET_jyjl.getText().toString());
					/*验证获取自我评价*/ 
					if(ET_zwpj.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "自我评价输入不能为空!", Toast.LENGTH_LONG).show();
						ET_zwpj.setFocusable(true);
						ET_zwpj.requestFocus();
						return;	
					}
					userInfo.setZwpj(ET_zwpj.getText().toString());
					/*验证获取注册时间*/ 
					if(ET_regTime.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "注册时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_regTime.setFocusable(true);
						ET_regTime.requestFocus();
						return;	
					}
					userInfo.setRegTime(ET_regTime.getText().toString());
					/*调用业务逻辑层上传求职者信息*/
					UserInfoEditActivity.this.setTitle("正在更新求职者信息，稍等...");
					String result = userInfoService.UpdateUserInfo(userInfo);
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
	    userInfo = userInfoService.GetUserInfo(user_name);
		this.TV_user_name.setText(user_name);
		this.ET_password.setText(userInfo.getPassword());
		this.ET_name.setText(userInfo.getName());
		this.ET_gender.setText(userInfo.getGender());
		Date birthDate = new Date(userInfo.getBirthDate().getTime());
		this.dp_birthDate.init(birthDate.getYear() + 1900,birthDate.getMonth(), birthDate.getDate(), null);
		byte[] userPhoto_data = null;
		try {
			// 获取图片数据
			userPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + userInfo.getUserPhoto());
			Bitmap userPhoto = BitmapFactory.decodeByteArray(userPhoto_data, 0, userPhoto_data.length);
			this.iv_userPhoto.setImageBitmap(userPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < schoolRecordList.size(); i++) {
			if (userInfo.getMyShoolRecord() == schoolRecordList.get(i).getId()) {
				this.spinner_myShoolRecord.setSelection(i);
				break;
			}
		}
		this.ET_schoolName.setText(userInfo.getSchoolName());
		this.ET_workYears.setText(userInfo.getWorkYears());
		this.ET_telephone.setText(userInfo.getTelephone());
		this.ET_email.setText(userInfo.getEmail());
		this.ET_address.setText(userInfo.getAddress());
		this.ET_qzyx.setText(userInfo.getQzyx());
		this.ET_gzjl.setText(userInfo.getGzjl());
		this.ET_jyjl.setText(userInfo.getJyjl());
		this.ET_zwpj.setText(userInfo.getZwpj());
		this.ET_regTime.setText(userInfo.getRegTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_userPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_userPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_userPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_userPhoto.setImageBitmap(booImageBm);
				this.iv_userPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.userInfo.setUserPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_userPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_userPhoto.setImageBitmap(bm); 
				this.iv_userPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			userInfo.setUserPhoto(filename); 
		}
	}
}
