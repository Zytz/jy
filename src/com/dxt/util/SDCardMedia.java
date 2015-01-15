package com.dxt.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

/**
 * @author liangfei
 *
 */
public class SDCardMedia 
{
    private ContentResolver contentResolver;
    private static List<String> pathList;
    private static List<Map<String, Object>> videoDataList;
    static
    {
    	videoDataList = new ArrayList<Map<String, Object>>();
    	pathList=new ArrayList<String>();
    }
	public SDCardMedia(Context context) 
	{
		contentResolver = context.getContentResolver();
    }
	/**
	 * 获取SD卡的所有视频信息
	 */
	public List<Map<String, Object>> getAllVideos()
	{
	    //String[] projection = new String[]{MediaStore.Video.Media.TITLE};
		/*第一个参数为访问Provider的路径，
		 * 第二个参数为查询的列，null表示所有信息，
		 * 第三个参数为查询条件（通常是where xx=?的形式），null表示没有查询条件即查询所有
		 * 第四个参数为第三个参数中"?"的值（是String数组），null表示没有
		 * 第五个参数是排序
		 * */
		Cursor videoCursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null,
	    									       null,null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
	    while(videoCursor.moveToNext())
	    {        
	        String title = videoCursor.getString(videoCursor.getColumnIndex(MediaStore.Video.Media.TITLE));
	        String type = videoCursor.getString(videoCursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE));
	        long size = videoCursor.getLong(videoCursor.getColumnIndex(MediaStore.Video.Media.SIZE));
	        String path =videoCursor.getString(videoCursor.getColumnIndex(MediaStore.Video.Media.DATA));
	        Map<String, Object> videoMap = new HashMap<String, Object>();
	        videoMap.put("title", title);
	        videoMap.put("type", type);
	        videoMap.put("size", size);
	        videoMap.put("path", path);
	        if(videoDataList.isEmpty())//第一次添加时videoDataList为空
	        {
	           videoDataList.add(videoMap);
	           pathList.add(path);
	        }
	        else//之前添加过
	        {
		        if(!pathList.contains(path))//再次添加的文件不在列表中
		        {
		        	pathList.add(path);
		        	videoDataList.add(videoMap);
		        }
	        }
	    }
	    videoCursor.close();
	    if(!pathList.isEmpty())//如果用户手动删除了SD卡上的文件则将该信息从列表中删除
	    {
		    for (int i = 0; i < pathList.size(); i++) 
		    {
		    	File file=new File(pathList.get(i));
		    	if(!file.exists())
		    	{
		    		pathList.remove(i);
		    		videoDataList.remove(i);
		    	}
			}
	    }
	    return videoDataList;
	}
	public static boolean addVideos(String[] filepaths)
	{
		boolean exist=true;
		long size=0;
		String title=null;
		String path=null;
		String type=null;
		for (int i = 0; i < filepaths.length; i++) 
		{
			File file=new File(filepaths[i]);
			if(file.exists())
			{
				if(!pathList.contains(filepaths[i]))
				{
					size=file.length();
					title=file.getName().substring(0,file.getName().lastIndexOf("."));
					path=filepaths[i];
					type="video/"+path.substring(path.lastIndexOf(".")+1).toLowerCase();
					Map<String, Object> videoMap = new HashMap<String, Object>();
					videoMap.put("title", title);
			        videoMap.put("type", type);
			        videoMap.put("size", size);
			        videoMap.put("path", path);
			        videoDataList.add(videoMap);
			        pathList.add(path);
			        exist=false;
				}
			}
		}  
		return exist;
	}
	public static String[] getAllPaths() 
	{
		String[] paths=null;
		if(!pathList.isEmpty())
		{
			paths=new String[pathList.size()];
			for (int i = 0; i < pathList.size(); i++) 
			{
				paths[i] = pathList.get(i);
			}
			return paths;
		}
		return null;
	}
	public static int scanSDMedia(String rootpath)
	{
		int count=0;
		long size=0;
		String title=null;
		String path=null;
		String type=null;
		File f = new File(rootpath);
		File[] files = f.listFiles();
		if(files!=null)
		{
			for (int i = 0; i < files.length; i++) 
			{
				File file = files[i];
				if(file.isDirectory())
					count+=scanSDMedia(file.getPath());
				else
				{
					if(getMIMEType(files[i]).equals("video"))
					{
						path=file.getAbsolutePath();
						if(!pathList.contains(path))
						{
							title=file.getName().substring(0,file.getName().lastIndexOf("."));
							type="video/"+path.substring(path.lastIndexOf(".")+1).toLowerCase();
							size=file.length();
							Map<String, Object> videoMap = new HashMap<String, Object>();
							videoMap.put("title", title);
					        videoMap.put("type", type);
					        videoMap.put("size", size);
					        videoMap.put("path", path);
					        videoDataList.add(videoMap);
					        pathList.add(path);
					        count++;
						}
					}
				}
			 }
			return count;
		}
		else 
			return 0;
	}
	private static String getMIMEType(File f) 
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
		return type;
	}
}
