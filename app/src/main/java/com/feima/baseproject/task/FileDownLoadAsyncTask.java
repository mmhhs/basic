package com.feima.baseproject.task;

import android.app.Activity;
import android.view.View;
import android.widget.PopupWindow;

import com.feima.baseproject.R;
import com.feima.baseproject.listener.IOnDialogListener;
import com.feima.baseproject.listener.IOnProgressListener;
import com.feima.baseproject.util.BaseConstant;
import com.feima.baseproject.view.dialog.DialogUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.ByteArrayBuffer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class FileDownLoadAsyncTask extends BaseTask<Void, String, BaseConstant.TaskResult> {
	private String url = "";// 文件下载地址
	private Activity activity;//上下文
    private IOnProgressListener iOnProgressListener;//进度监听
    private String fileStorePath;//文件存储路径
    private long totalSize=0;//下载文件大小
    private boolean silenceDownload = false;//静默下载
    private String windowTitle = "";//标题
    private View parentView;//父类视图
    private DialogUtil dialogUtil;
    private PopupWindow popupWindow;
    //下载相关
    private HttpClient httpClient = new DefaultHttpClient();
    private FileOutputStream fileOutputStream=null;
    private CountingInputStream cis = null;

	public FileDownLoadAsyncTask(Activity activity, String url, String fileStorePath, boolean silenceDownload, View parentView, String windowTitle) {
		this.activity = activity;
        this.url = url;
        this.fileStorePath = fileStorePath;
        this.silenceDownload = silenceDownload;
        this.windowTitle = windowTitle;
        this.parentView = parentView;
        dialogUtil = new DialogUtil(activity);
        dialogUtil.setiOnDialogListener(new IOnDialogListener() {
            @Override
            public void onConfirm() {

            }

            @Override
            public void onCancel() {
                cancel(true);
            }

            @Override
            public void onOther() {

            }
        });
	}

	@Override
    public void onPreExecute() {
        try {
            if (iOnProgressListener!=null){
                iOnProgressListener.start();
            }
            if (!silenceDownload){
                popupWindow = dialogUtil.showDownloadDialog(parentView,false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
	}

	@Override
    public BaseConstant.TaskResult doInBackground(Void... params) {
		BaseConstant.TaskResult taskResult = BaseConstant.TaskResult.NOTHING;
		try {
			httpClient.getParams().setParameter(
					CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
                totalSize = entity.getContentLength();
				cis = new CountingInputStream(
						entity.getContent(), new ProgressListener() {
							@Override
							public void transferred(long transferedBytes) {
								publishProgress(""+(transferedBytes));
							}
						});
                File loadFile = new File(fileStorePath);
                if (!loadFile.exists()){
                    loadFile.createNewFile();
                }
				byte[] byteIn = toByteArray(cis, (int) totalSize);
                fileOutputStream = new FileOutputStream(loadFile);
                fileOutputStream.write(byteIn); //记得关闭输入流

                taskResult = BaseConstant.TaskResult.OK;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
            taskResult = BaseConstant.TaskResult.ERROR;
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
            taskResult = BaseConstant.TaskResult.ERROR;
		} catch (Exception e) {
			e.printStackTrace();
            taskResult = BaseConstant.TaskResult.ERROR;
		} finally {
            stopDownload();
		}
		return taskResult;
	}

	@Override
    public void onProgressUpdate(String... progress) {
        try {
            if (iOnProgressListener!=null){
                iOnProgressListener.transferred(progress[0], totalSize);
            }
            if (!silenceDownload){
                dialogUtil.updateProgressInfo(progress[0], totalSize);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
	}

	@Override
    public void onPostExecute(BaseConstant.TaskResult result) {
        if (iOnProgressListener!=null){
            iOnProgressListener.done();
        }
        try {
            if (popupWindow!=null){
                popupWindow.dismiss();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        switch(result){
            case OK:
                if (iOnProgressListener!=null){
                    iOnProgressListener.success(fileStorePath);
                }
                break;
            case ERROR:
                if (iOnProgressListener!=null){
                    iOnProgressListener.error(activity.getString(R.string.check_version_fail));
                }
                break;
            case CANCELLED:
                if (iOnProgressListener!=null){
                    iOnProgressListener.error(activity.getString(R.string.check_version_fail));
                }
                break;
            default:
                break;
        }
	}

	/**
	 * InputStream转化为Byte数组
	 * 
	 * @param instream
	 * @param contentLength
	 * @return
	 * @throws java.io.IOException
	 */
	public byte[] toByteArray(InputStream instream, int contentLength)
			throws IOException {
		if (instream == null) {
			return null;
		}
		try {
			if (contentLength < 0) {
				contentLength = 1024*4;
			}
			final ByteArrayBuffer buffer = new ByteArrayBuffer(contentLength);
			final byte[] tmp = new byte[1024*4];
			int l;
			while ((l = instream.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}
			return buffer.toByteArray();
		} finally {
			instream.close();
		}
	}

    public void setiOnProgressListener(IOnProgressListener iOnProgressListener) {
        this.iOnProgressListener = iOnProgressListener;
    }

    public void stopDownload(){
        try {
            if (cis!=null){
                try {
                    cis.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (httpClient != null && httpClient.getConnectionManager() != null) {
                httpClient.getConnectionManager().shutdown();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
