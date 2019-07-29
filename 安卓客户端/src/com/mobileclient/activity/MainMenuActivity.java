package com.mobileclient.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainMenuActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("手机客户端-主界面");
        setContentView(R.layout.main_menu);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        
        AnimationSet set = new AnimationSet(false);
        Animation animation = new AlphaAnimation(0,1);
        animation.setDuration(500);
        set.addAnimation(animation);
        
        animation = new TranslateAnimation(1, 13, 10, 50);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        animation = new RotateAnimation(30,10);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        animation = new ScaleAnimation(5,0,2,0);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        LayoutAnimationController controller = new LayoutAnimationController(set, 1);
        
        gridview.setLayoutAnimation(controller);
        
        gridview.setAdapter(new ImageAdapter(this));
    }
    
    // 继承BaseAdapter
    public class ImageAdapter extends BaseAdapter {
    	
    	LayoutInflater inflater;
    	
    	// 上下文
        private Context mContext;
        
        // 图片资源数组
        private Integer[] mThumbIds = {
                R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon
        };
        private String[] menuString = {"求职者管理","学历管理","企业管理","企业性质管理","企业行业管理","职位管理","职位分类管理","专业管理","职位投递管理","投递状态管理","求职管理","新闻公告管理","新闻分类管理"};

        // 构造方法
        public ImageAdapter(Context c) {
            mContext = c;
            inflater = LayoutInflater.from(mContext);
        }
        // 组件个数
        public int getCount() {
            return mThumbIds.length;
        }
        // 当前组件
        public Object getItem(int position) {
            return null;
        }
        // 当前组件id
        public long getItemId(int position) {
            return 0;
        }
        // 获得当前视图
        public View getView(int position, View convertView, ViewGroup parent) { 
        	View view = inflater.inflate(R.layout.gv_item, null);
			TextView tv = (TextView) view.findViewById(R.id.gv_item_appname);
			ImageView iv = (ImageView) view.findViewById(R.id.gv_item_icon);  
			tv.setText(menuString[position]); 
			iv.setImageResource(mThumbIds[position]); 
			  switch (position) {
				case 0:
					// 求职者管理监听器
					view.setOnClickListener(userInfoLinstener);
					break;
				case 1:
					// 学历管理监听器
					view.setOnClickListener(schoolRecordLinstener);
					break;
				case 2:
					// 企业管理监听器
					view.setOnClickListener(qiyeLinstener);
					break;
				case 3:
					// 企业性质管理监听器
					view.setOnClickListener(qiyePropertyLinstener);
					break;
				case 4:
					// 企业行业管理监听器
					view.setOnClickListener(qiyeProfessionLinstener);
					break;
				case 5:
					// 职位管理监听器
					view.setOnClickListener(jobLinstener);
					break;
				case 6:
					// 职位分类管理监听器
					view.setOnClickListener(jobTypeLinstener);
					break;
				case 7:
					// 专业管理监听器
					view.setOnClickListener(specialInfoLinstener);
					break;
				case 8:
					// 职位投递管理监听器
					view.setOnClickListener(deliveryLinstener);
					break;
				case 9:
					// 投递状态管理监听器
					view.setOnClickListener(deliveryStateLinstener);
					break;
				case 10:
					// 求职管理监听器
					view.setOnClickListener(jobWantLinstener);
					break;
				case 11:
					// 新闻公告管理监听器
					view.setOnClickListener(newsLinstener);
					break;
				case 12:
					// 新闻分类管理监听器
					view.setOnClickListener(newsClassLinstener);
					break;

			 
				default:
					break;
				} 
			return view; 
        }
       
    }
    
    OnClickListener userInfoLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动求职者管理Activity
			intent.setClass(MainMenuActivity.this, UserInfoListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener schoolRecordLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动学历管理Activity
			intent.setClass(MainMenuActivity.this, SchoolRecordListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener qiyeLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动企业管理Activity
			intent.setClass(MainMenuActivity.this, QiyeListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener qiyePropertyLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动企业性质管理Activity
			intent.setClass(MainMenuActivity.this, QiyePropertyListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener qiyeProfessionLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动企业行业管理Activity
			intent.setClass(MainMenuActivity.this, QiyeProfessionListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener jobLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动职位管理Activity
			intent.setClass(MainMenuActivity.this, JobListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener jobTypeLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动职位分类管理Activity
			intent.setClass(MainMenuActivity.this, JobTypeListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener specialInfoLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动专业管理Activity
			intent.setClass(MainMenuActivity.this, SpecialInfoListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener deliveryLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动职位投递管理Activity
			intent.setClass(MainMenuActivity.this, DeliveryListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener deliveryStateLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动投递状态管理Activity
			intent.setClass(MainMenuActivity.this, DeliveryStateListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener jobWantLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动求职管理Activity
			intent.setClass(MainMenuActivity.this, JobWantListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener newsLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动新闻公告管理Activity
			intent.setClass(MainMenuActivity.this, NewsListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener newsClassLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动新闻分类管理Activity
			intent.setClass(MainMenuActivity.this, NewsClassListActivity.class);
			startActivity(intent);
		}
	};


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 1, "重新登入");  
		menu.add(0, 2, 2, "退出"); 
		return super.onCreateOptionsMenu(menu); 
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 1) {//重新登入 
			Intent intent = new Intent();
			intent.setClass(MainMenuActivity.this,
					LoginActivity.class);
			startActivity(intent);
		} else if (item.getItemId() == 2) {//退出
			System.exit(0);  
		} 
		return true; 
	}
}
