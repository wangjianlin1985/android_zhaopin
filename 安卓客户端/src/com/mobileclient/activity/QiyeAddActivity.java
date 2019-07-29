package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Qiye;
import com.mobileclient.service.QiyeService;
import com.mobileclient.domain.QiyeProperty;
import com.mobileclient.service.QiyePropertyService;
import com.mobileclient.domain.QiyeProfession;
import com.mobileclient.service.QiyeProfessionService;
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
public class QiyeAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明企业账号输入框
	private EditText ET_qiyeUserName;
	// 声明登录密码输入框
	private EditText ET_password;
	// 声明企业名称输入框
	private EditText ET_qiyeName;
	// 声明企业资质输入框
	private EditText ET_qiyeQualify;
	// 声明企业性质下拉框
	private Spinner spinner_qiyePropertyObj;
	private ArrayAdapter<String> qiyePropertyObj_adapter;
	private static  String[] qiyePropertyObj_ShowText  = null;
	private List<QiyeProperty> qiyePropertyList = null;
	/*企业性质管理业务逻辑层*/
	private QiyePropertyService qiyePropertyService = new QiyePropertyService();
	// 声明企业行业下拉框
	private Spinner spinner_qiyeProfessionObj;
	private ArrayAdapter<String> qiyeProfessionObj_adapter;
	private static  String[] qiyeProfessionObj_ShowText  = null;
	private List<QiyeProfession> qiyeProfessionList = null;
	/*企业行业管理业务逻辑层*/
	private QiyeProfessionService qiyeProfessionService = new QiyeProfessionService();
	// 声明企业规模输入框
	private EditText ET_qiyeScale;
	// 声明联系人输入框
	private EditText ET_connectPerson;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明企业邮箱输入框
	private EditText ET_email;
	// 声明企业地址输入框
	private EditText ET_address;
	protected String carmera_path;
	/*要保存的企业信息*/
	Qiye qiye = new Qiye();
	/*企业管理业务逻辑层*/
	private QiyeService qiyeService = new QiyeService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.qiye_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加企业");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_qiyeUserName = (EditText) findViewById(R.id.ET_qiyeUserName);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_qiyeName = (EditText) findViewById(R.id.ET_qiyeName);
		ET_qiyeQualify = (EditText) findViewById(R.id.ET_qiyeQualify);
		spinner_qiyePropertyObj = (Spinner) findViewById(R.id.Spinner_qiyePropertyObj);
		// 获取所有的企业性质
		try {
			qiyePropertyList = qiyePropertyService.QueryQiyeProperty(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int qiyePropertyCount = qiyePropertyList.size();
		qiyePropertyObj_ShowText = new String[qiyePropertyCount];
		for(int i=0;i<qiyePropertyCount;i++) { 
			qiyePropertyObj_ShowText[i] = qiyePropertyList.get(i).getPropertyName();
		}
		// 将可选内容与ArrayAdapter连接起来
		qiyePropertyObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, qiyePropertyObj_ShowText);
		// 设置下拉列表的风格
		qiyePropertyObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_qiyePropertyObj.setAdapter(qiyePropertyObj_adapter);
		// 添加事件Spinner事件监听
		spinner_qiyePropertyObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				qiye.setQiyePropertyObj(qiyePropertyList.get(arg2).getId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_qiyePropertyObj.setVisibility(View.VISIBLE);
		spinner_qiyeProfessionObj = (Spinner) findViewById(R.id.Spinner_qiyeProfessionObj);
		// 获取所有的企业行业
		try {
			qiyeProfessionList = qiyeProfessionService.QueryQiyeProfession(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int qiyeProfessionCount = qiyeProfessionList.size();
		qiyeProfessionObj_ShowText = new String[qiyeProfessionCount];
		for(int i=0;i<qiyeProfessionCount;i++) { 
			qiyeProfessionObj_ShowText[i] = qiyeProfessionList.get(i).getProfessionName();
		}
		// 将可选内容与ArrayAdapter连接起来
		qiyeProfessionObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, qiyeProfessionObj_ShowText);
		// 设置下拉列表的风格
		qiyeProfessionObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_qiyeProfessionObj.setAdapter(qiyeProfessionObj_adapter);
		// 添加事件Spinner事件监听
		spinner_qiyeProfessionObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				qiye.setQiyeProfessionObj(qiyeProfessionList.get(arg2).getId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_qiyeProfessionObj.setVisibility(View.VISIBLE);
		ET_qiyeScale = (EditText) findViewById(R.id.ET_qiyeScale);
		ET_connectPerson = (EditText) findViewById(R.id.ET_connectPerson);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_email = (EditText) findViewById(R.id.ET_email);
		ET_address = (EditText) findViewById(R.id.ET_address);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加企业按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取企业账号*/
					if(ET_qiyeUserName.getText().toString().equals("")) {
						Toast.makeText(QiyeAddActivity.this, "企业账号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_qiyeUserName.setFocusable(true);
						ET_qiyeUserName.requestFocus();
						return;
					}
					qiye.setQiyeUserName(ET_qiyeUserName.getText().toString());
					/*验证获取登录密码*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(QiyeAddActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					qiye.setPassword(ET_password.getText().toString());
					/*验证获取企业名称*/ 
					if(ET_qiyeName.getText().toString().equals("")) {
						Toast.makeText(QiyeAddActivity.this, "企业名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_qiyeName.setFocusable(true);
						ET_qiyeName.requestFocus();
						return;	
					}
					qiye.setQiyeName(ET_qiyeName.getText().toString());
					/*验证获取企业资质*/ 
					if(ET_qiyeQualify.getText().toString().equals("")) {
						Toast.makeText(QiyeAddActivity.this, "企业资质输入不能为空!", Toast.LENGTH_LONG).show();
						ET_qiyeQualify.setFocusable(true);
						ET_qiyeQualify.requestFocus();
						return;	
					}
					qiye.setQiyeQualify(ET_qiyeQualify.getText().toString());
					/*验证获取企业规模*/ 
					if(ET_qiyeScale.getText().toString().equals("")) {
						Toast.makeText(QiyeAddActivity.this, "企业规模输入不能为空!", Toast.LENGTH_LONG).show();
						ET_qiyeScale.setFocusable(true);
						ET_qiyeScale.requestFocus();
						return;	
					}
					qiye.setQiyeScale(ET_qiyeScale.getText().toString());
					/*验证获取联系人*/ 
					if(ET_connectPerson.getText().toString().equals("")) {
						Toast.makeText(QiyeAddActivity.this, "联系人输入不能为空!", Toast.LENGTH_LONG).show();
						ET_connectPerson.setFocusable(true);
						ET_connectPerson.requestFocus();
						return;	
					}
					qiye.setConnectPerson(ET_connectPerson.getText().toString());
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(QiyeAddActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					qiye.setTelephone(ET_telephone.getText().toString());
					/*验证获取企业邮箱*/ 
					if(ET_email.getText().toString().equals("")) {
						Toast.makeText(QiyeAddActivity.this, "企业邮箱输入不能为空!", Toast.LENGTH_LONG).show();
						ET_email.setFocusable(true);
						ET_email.requestFocus();
						return;	
					}
					qiye.setEmail(ET_email.getText().toString());
					/*验证获取企业地址*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(QiyeAddActivity.this, "企业地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					qiye.setAddress(ET_address.getText().toString());
					/*调用业务逻辑层上传企业信息*/
					QiyeAddActivity.this.setTitle("正在上传企业信息，稍等...");
					String result = qiyeService.AddQiye(qiye);
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
