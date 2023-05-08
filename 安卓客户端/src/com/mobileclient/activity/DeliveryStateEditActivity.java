package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.DeliveryState;
import com.mobileclient.service.DeliveryStateService;
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

public class DeliveryStateEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明投递状态idTextView
	private TextView TV_stateId;
	// 声明投递状态输入框
	private EditText ET_stateName;
	protected String carmera_path;
	/*要保存的投递状态信息*/
	DeliveryState deliveryState = new DeliveryState();
	/*投递状态管理业务逻辑层*/
	private DeliveryStateService deliveryStateService = new DeliveryStateService();

	private int stateId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.deliverystate_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑投递状态信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_stateId = (TextView) findViewById(R.id.TV_stateId);
		ET_stateName = (EditText) findViewById(R.id.ET_stateName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		stateId = extras.getInt("stateId");
		/*单击修改投递状态按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取投递状态*/ 
					if(ET_stateName.getText().toString().equals("")) {
						Toast.makeText(DeliveryStateEditActivity.this, "投递状态输入不能为空!", Toast.LENGTH_LONG).show();
						ET_stateName.setFocusable(true);
						ET_stateName.requestFocus();
						return;	
					}
					deliveryState.setStateName(ET_stateName.getText().toString());
					/*调用业务逻辑层上传投递状态信息*/
					DeliveryStateEditActivity.this.setTitle("正在更新投递状态信息，稍等...");
					String result = deliveryStateService.UpdateDeliveryState(deliveryState);
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
	    deliveryState = deliveryStateService.GetDeliveryState(stateId);
		this.TV_stateId.setText(stateId+"");
		this.ET_stateName.setText(deliveryState.getStateName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
