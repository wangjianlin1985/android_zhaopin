package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.QiyePropertyService;
import com.mobileclient.service.QiyeProfessionService;
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

public class QiyeSimpleAdapter extends SimpleAdapter { 
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

    public QiyeSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.qiye_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_qiyeUserName = (TextView)convertView.findViewById(R.id.tv_qiyeUserName);
	  holder.tv_qiyeName = (TextView)convertView.findViewById(R.id.tv_qiyeName);
	  holder.tv_qiyePropertyObj = (TextView)convertView.findViewById(R.id.tv_qiyePropertyObj);
	  holder.tv_qiyeProfessionObj = (TextView)convertView.findViewById(R.id.tv_qiyeProfessionObj);
	  holder.tv_qiyeScale = (TextView)convertView.findViewById(R.id.tv_qiyeScale);
	  holder.tv_connectPerson = (TextView)convertView.findViewById(R.id.tv_connectPerson);
	  holder.tv_telephone = (TextView)convertView.findViewById(R.id.tv_telephone);
	  /*设置各个控件的展示内容*/
	  holder.tv_qiyeUserName.setText("企业账号：" + mData.get(position).get("qiyeUserName").toString());
	  holder.tv_qiyeName.setText("企业名称：" + mData.get(position).get("qiyeName").toString());
	  holder.tv_qiyePropertyObj.setText("企业性质：" + (new QiyePropertyService()).GetQiyeProperty(Integer.parseInt(mData.get(position).get("qiyePropertyObj").toString())).getPropertyName());
	  holder.tv_qiyeProfessionObj.setText("企业行业：" + (new QiyeProfessionService()).GetQiyeProfession(Integer.parseInt(mData.get(position).get("qiyeProfessionObj").toString())).getProfessionName());
	  holder.tv_qiyeScale.setText("企业规模：" + mData.get(position).get("qiyeScale").toString());
	  holder.tv_connectPerson.setText("联系人：" + mData.get(position).get("connectPerson").toString());
	  holder.tv_telephone.setText("联系电话：" + mData.get(position).get("telephone").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_qiyeUserName;
    	TextView tv_qiyeName;
    	TextView tv_qiyePropertyObj;
    	TextView tv_qiyeProfessionObj;
    	TextView tv_qiyeScale;
    	TextView tv_connectPerson;
    	TextView tv_telephone;
    }
} 
