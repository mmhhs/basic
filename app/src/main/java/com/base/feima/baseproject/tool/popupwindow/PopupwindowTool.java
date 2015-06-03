package com.base.feima.baseproject.tool.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.base.feima.baseproject.R;

public class PopupwindowTool {
	private static PopupwindowTool viewTool = null;
	private Context context;
	
	public PopupwindowTool(){
		
	}
	
	public PopupwindowTool(Context paramContext){
		this.context = paramContext;
	}
	
	public static synchronized PopupwindowTool getInstancePopupwindowTool()
	  {
	      viewTool = new PopupwindowTool();
	      PopupwindowTool viewTools = viewTool;
	      return viewTools;
	  }
	
	public void init() {
		onListItemClickListener = null;
		onSureClickListener = null;
	}
	
	private OnListItemClickListener onListItemClickListener;
	
	public interface OnListItemClickListener{
		public void onClick(int position);
	}	

	public void setOnListItemClickListener(
			OnListItemClickListener onListItemClickListener) {
		this.onListItemClickListener = onListItemClickListener;
	}

	private void doOnListItemClickListener(int position){
		if(onListItemClickListener!=null){
			this.onListItemClickListener.onClick(position);
		}		
	}
	
	private OnSureClickListener onSureClickListener;
	
	public interface OnSureClickListener{
		public void onClick(int position);
	}	

	public void setOnSureClickListener(
			OnSureClickListener onSureClickListener) {
		this.onSureClickListener = onSureClickListener;
	}

	private void doOnSureClickListener(int position){
		if(onSureClickListener!=null){
			this.onSureClickListener.onClick(position);
		}		
	}
	
	/**
	 * 显示list形式popupwindow
	 * @param context 上下文
	 * @param view 父类视图
	 * @param title 标题
	 * @param itemArray 每项数组
	 * @param dismissOutside 是否点击外部消失
	 * @param dismissKeyback 是否点击返回键消失
	 * @param animStyle 动画样式
	 */
	public void showListWindow(Context context,View view,String title,String[] itemArray,boolean dismissOutside,boolean dismissKeyback,int animStyle){
		PopupWindow pictureWindow = getListWindow(context,title,itemArray,dismissOutside,dismissKeyback,animStyle);
		pictureWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
	}

    public void showListWindow(Context context,View view,String title,String[] itemArray){
        PopupWindow pictureWindow = getListWindow(context,title,itemArray,true,true,0);
        pictureWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
	
	public PopupWindow getListWindow(Context context,String title,String[] itemArray,final boolean dismissOutside,boolean dismissKeyback,int animStyle) {
		View view = LayoutInflater.from(context).inflate(R.layout.base_popupwindow_list,null, false);
	    ListView listView = (ListView) view.findViewById(R.id.pop_list_listview);
	    TextView titleText = (TextView) view.findViewById(R.id.pop_list_title);
        LinearLayout popupwindowLinear = (LinearLayout) view.findViewById(R.id.pop_list_linear);
        PopupwindowListAdapter adapter = new PopupwindowListAdapter(context,itemArray);
        listView.setAdapter(adapter);
        if(!title.isEmpty()){
        	titleText.setText(title);
        }
        final PopupWindow popupWindow = new PopupWindow(view,LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);        	
		popupWindow.setFocusable(true);		
		popupWindow.setOutsideTouchable(false);
		view.getBackground().setAlpha(99);
		if(dismissKeyback){
			popupWindow.setBackgroundDrawable(new BitmapDrawable()); //使按返回键能够消失
		}
		if(animStyle>0){
			popupWindow.setAnimationStyle(animStyle);
		}		
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				doOnListItemClickListener(arg2);
				popupWindow.dismiss();
			}
			
		});
		popupwindowLinear.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				if(dismissOutside){
					popupWindow.dismiss();
				}				
			}
			
		});
		
		return popupWindow;
	}
	
	/**
	 * 显示list形式popupwindow
	 * @param context 上下文
	 * @param view 父类视图
	 * @param title 标题
	 * @param message 内容
	 * @param dismissOutside 是否点击外部消失
	 * @param dismissKeyback 是否点击返回键消失
	 * @param animStyle 动画样式
	 */
	public void showSureWindow(Context context,View view,String title,String message,boolean dismissOutside,boolean dismissKeyback,boolean showOne,int animStyle){
		PopupWindow pictureWindow = getSureWindow(context,title,message,dismissOutside,dismissKeyback,showOne,animStyle);
		pictureWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
	}

    public void showSureWindow(Context context,View view,String title,String message){
        PopupWindow pictureWindow = getSureWindow(context,title,message,true,true,false,0);
        pictureWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
	
	public PopupWindow getSureWindow(Context context,String title,String message,final boolean dismissOutside,boolean dismissKeyback,boolean showOne,int animStyle) {
		View view = LayoutInflater.from(context).inflate(R.layout.base_popupwindow_sure,null, false);
	    TextView titleText = (TextView) view.findViewById(R.id.pop_sure_title);
	    TextView messageText = (TextView) view.findViewById(R.id.pop_sure_content);
	    TextView cancleText = (TextView) view.findViewById(R.id.pop_sure_item1);
	    TextView sureText = (TextView) view.findViewById(R.id.pop_sure_item2);
	    View line = (View) view.findViewById(R.id.pop_sure_line);
        LinearLayout popupwindowLinear = (LinearLayout) view.findViewById(R.id.pop_sure_linear);
        if(!title.isEmpty()){
        	titleText.setText(title);
        }
        if(!message.isEmpty()){
        	messageText.setText(message);
        }
        if(showOne){
        	cancleText.setVisibility(View.GONE);
        	line.setVisibility(View.GONE);
        }
        final PopupWindow popupWindow = new PopupWindow(view,LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);        	
		popupWindow.setFocusable(true);		
		popupWindow.setOutsideTouchable(false);
		view.getBackground().setAlpha(99);
		if(dismissKeyback){
			popupWindow.setBackgroundDrawable(new BitmapDrawable()); //使按返回键能够消失
		}
		if(animStyle>0){
			popupWindow.setAnimationStyle(animStyle);
		}
		sureText.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				doOnSureClickListener(2);	
				popupWindow.dismiss();	
			}
			
		});
		cancleText.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				popupWindow.dismiss();								
			}
			
		});
		popupwindowLinear.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				if(dismissOutside){
					popupWindow.dismiss();
				}				
			}
			
		});
		
		return popupWindow;
	}
	

	
	/**
	 * 获取加载自适应界面
	 * @param context
	 * @param view
	 * @param loadsString
	 * @param backgroundDrawable
	 * @param animStyle
	 * @param paddingTop
	 * @param paddingBottom
	 * @return
	 */
	public static PopupWindow showLoadWindow(Context context, View view, String loadsString, int backgroundDrawable, int animStyle, int paddingTop, int paddingBottom){
		PopupWindow loadPopupWindow = getLoadWindow(context, loadsString, backgroundDrawable, animStyle, paddingTop, paddingBottom);
		loadPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		return loadPopupWindow;
	}

    public static PopupWindow showLoadWindow(Context context,View view){
        PopupWindow loadPopupWindow = getLoadWindow(context, context.getString(R.string.task_item2), 0, 0, 0, 0);
        loadPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        return loadPopupWindow;
    }
		
	public static PopupWindow getLoadWindow(Context context, String loadsString, int backgroundDrawable, int animStyle, int paddingTop, int paddingBottom) {
		View view = LayoutInflater.from(context).inflate(R.layout.base_popupwindow_load,null, false);
	    ImageView loadImageView = (ImageView) view.findViewById(R.id.pop_load_image);
	    TextView loadText = (TextView) view.findViewById(R.id.pop_load_text);
        LinearLayout popupwindowLinear = (LinearLayout) view.findViewById(R.id.pop_load_linear);
        LinearLayout popupwindowContainer = (LinearLayout) view.findViewById(R.id.pop_load_container);
        popupwindowContainer.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        popupwindowContainer.getBackground().setAlpha(100);
        popupwindowLinear.setBackgroundResource(R.drawable.base_shape_corner_white);
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.base_load_rotate);
        if(!loadsString.isEmpty()){
        	loadText.setText(loadsString);
        }    
        loadImageView.startAnimation(anim);
        if(backgroundDrawable!=0){
        	popupwindowLinear.setBackgroundResource(backgroundDrawable);
        }
        final PopupWindow popupWindow = new PopupWindow(view,LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);        	
		popupWindow.setFocusable(true);		
		popupWindow.setOutsideTouchable(false);
		if(animStyle>0){
			popupWindow.setAnimationStyle(animStyle);
		}				
		
		return popupWindow;
	}
	
	
	
	public PopupWindow showErrorWindow(Context context,View view,String errorString,int animStyle,int paddingTop,int paddingBottom){
		PopupWindow errorWindow = getErrorWindow(context,errorString,animStyle,paddingTop,paddingBottom);
		errorWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		return errorWindow;
	}

    public PopupWindow showErrorWindow(Context context,View view,String errorString){
        PopupWindow errorWindow = getErrorWindow(context,errorString,0,0,0);
        errorWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        return errorWindow;
    }
	
	public PopupWindow getErrorWindow(Context context,String errorString,int animStyle,int paddingTop,int paddingBottom) {
		View view = LayoutInflater.from(context).inflate(R.layout.base_popupwindow_error,null, false);
	    TextView titleText = (TextView) view.findViewById(R.id.pop_error_item1);
        LinearLayout popupwindowLinear = (LinearLayout) view.findViewById(R.id.pop_error_linear);
        LinearLayout popupwindowContainer = (LinearLayout) view.findViewById(R.id.pop_error_container);
        popupwindowContainer.setPadding(0, paddingTop, 0, paddingBottom);
        if(!errorString.isEmpty()){
        	titleText.setText(errorString);
        }
       
        final PopupWindow popupWindow = new PopupWindow(view,LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);        	
		popupWindow.setFocusable(true);		
		popupWindow.setOutsideTouchable(false);

		if(animStyle>0){
			popupWindow.setAnimationStyle(animStyle);
		}
		
		popupwindowLinear.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				doOnSureClickListener(0);				
			}
			
		});
		
		return popupWindow;
	}
	
	
	
	
	
	
}