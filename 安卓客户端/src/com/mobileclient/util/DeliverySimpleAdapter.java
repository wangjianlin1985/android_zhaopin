package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.JobService;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.service.DeliveryStateService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class DeliverySimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public DeliverySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.delivery_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_deliveryId = (TextView)convertView.findViewById(R.id.tv_deliveryId);
	  holder.tv_jobObj = (TextView)convertView.findViewById(R.id.tv_jobObj);
	  holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
	  holder.tv_deliveryTime = (TextView)convertView.findViewById(R.id.tv_deliveryTime);
	  holder.tv_stateObj = (TextView)convertView.findViewById(R.id.tv_stateObj);
	  /*设置各个控件的展示内容*/
	  holder.tv_deliveryId.setText("投递id：" + mData.get(position).get("deliveryId").toString());
	  holder.tv_jobObj.setText("投递的职位：" + (new JobService()).GetJob(Integer.parseInt(mData.get(position).get("jobObj").toString())).getPositionName());
	  holder.tv_userObj.setText("投递人：" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getName());
	  holder.tv_deliveryTime.setText("投递时间：" + mData.get(position).get("deliveryTime").toString());
	  holder.tv_stateObj.setText("投递状态：" + (new DeliveryStateService()).GetDeliveryState(Integer.parseInt(mData.get(position).get("stateObj").toString())).getStateName());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_deliveryId;
    	TextView tv_jobObj;
    	TextView tv_userObj;
    	TextView tv_deliveryTime;
    	TextView tv_stateObj;
    }
} 
