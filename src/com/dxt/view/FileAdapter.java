package com.dxt.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.dxt.R;
public class FileAdapter extends BaseAdapter 
{
  private LayoutInflater mInflater;
  private Bitmap mIcon1,mIcon2,mIcon3,mIcon4,mIcon5,mIcon6;
  private List<String> items;
  private List<String> paths;
  private static List<Integer> CheckedPostions= new ArrayList<Integer>();
  public FileAdapter(Context context,List<String> it,List<String> pa)
  {
    mInflater = LayoutInflater.from(context);
    items = it;
    paths = pa;
    mIcon1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.back01);
    mIcon2 = BitmapFactory.decodeResource(context.getResources(),R.drawable.back02);
    mIcon3 = BitmapFactory.decodeResource(context.getResources(),R.drawable.folder);
    mIcon4 = BitmapFactory.decodeResource(context.getResources(),R.drawable.vieo);
    mIcon5 = BitmapFactory.decodeResource(context.getResources(),R.drawable.file);
    mIcon6 = BitmapFactory.decodeResource(context.getResources(),R.drawable.checked);
  }
  
  public int getCount()
  {
    return items.size();
  }

  public Object getItem(int position)
  {
    return items.get(position);
  }
  
  public long getItemId(int position)
  {
    return position;
  }
  
  public View getView(int position,View convertView,ViewGroup parent)
  {
    ViewHolder holder;
    
    if(convertView == null)
    {
      convertView = mInflater.inflate(R.layout.file_row, null);
      holder = new ViewHolder();
      holder.text = (TextView) convertView.findViewById(R.id.text);
      holder.fileicon = (ImageView) convertView.findViewById(R.id.icon);
      holder.checkIcon = (ImageView) convertView.findViewById(R.id.check);
      convertView.setTag(holder);
    }
    else
    {
      holder = (ViewHolder) convertView.getTag();
    }
    File f=new File(paths.get(position).toString());
    if(items.get(position).toString().equals("b1"))
    {
      holder.text.setText("返回根目录..");
      holder.fileicon.setImageBitmap(mIcon1);
    }
    else if(items.get(position).toString().equals("b2"))
    {
      holder.text.setText("返回上一层..");
      holder.fileicon.setImageBitmap(mIcon2);
    }
    else
    {
      holder.text.setText(f.getName());
      if(f.isDirectory())
      {
        holder.fileicon.setImageBitmap(mIcon3);
        holder.checkIcon.setImageBitmap(null);
      }
      else
      {
    	  if(getMIMEType(f).equals("video"))
	      {
	        holder.fileicon.setImageBitmap(mIcon4);
	        if(CheckedPostions.contains(position))
		    	holder.checkIcon.setImageBitmap(mIcon6);
	        else
	        	holder.checkIcon.setImageBitmap(null);
	      }
	      else
	      {
	    	holder.fileicon.setImageBitmap(mIcon5); 
	    	holder.checkIcon.setImageBitmap(null);
	      }
      }   
    }
    return convertView;
  }
  public  static void setCheckItem(List<Integer> CheckedPostions) 
  {
	  FileAdapter.CheckedPostions=CheckedPostions;
  }
  private class ViewHolder
  {
    TextView text;
    ImageView fileicon;
    ImageView checkIcon;
  }
  private String getMIMEType(File f) 
	{
		String type = "";
		String fName = f.getName();
		String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")||
		    end.equals("xmf") || end.equals("ogg") || end.equals("wav"))
		{
			type = "audio";
		} 
		else if (end.equals("3gp") || end.equals("mp4")||end.equals("rmvb")||end.equals("avi")||
				 end.equals("flv")||end.equals("rm")||end.equals("mkv")) 
		{
			type = "video";
		} 
		else if (end.equals("jpg") || end.equals("gif") || end.equals("png")||
				 end.equals("jpeg") || end.equals("bmp"))
		{
			type = "image";
		} 
		else 
		{
			type = "*";
		}
//		type += "/*";
		return type;
	}
}
