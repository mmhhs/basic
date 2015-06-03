package com.base.feima.baseproject.net;

import com.base.feima.baseproject.util.LogUtil;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class Httpclient {
	public static final String TAG = "Httpclient Result";
	
	
	
	 /**
     * 以Post方法访问
     * @param url 请求地址
     * @param argsMap 携带的参数
     * @return  String 返回结果
     * @throws Exception
     */
    public static String POSTMethod(String url,Map<String, Object> argsMap) throws Exception{
        byte[] dataByte = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        //设置参数
        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(setHttpParams(argsMap), "UTF-8");
        httpPost.setEntity(encodedFormEntity);
        // 执行请求
        HttpResponse httpResponse = httpClient.execute(httpPost);
        // 获取返回的数据
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null) {
            byte[] responseBytes = getData(httpEntity);
            dataByte = responseBytes;
            httpPost.abort();
        }
        //将字节数组转换成为字符串
        String result = bytesToString(dataByte);
        LogUtil.d("arg= " + new JSONObject(argsMap).toString());
        LogUtil.d("" + url + "= " + result);
        return result;
    }
     
    /**
     * 以Get方法访问
     * @param url 请求地址
     * @return String
     * @throws Exception
     */
    public static String GETMethod(String url,Map<String, Object> argsMap) throws Exception{
        byte[] dataByte = null;
        HttpClient httpClient = new DefaultHttpClient();
        //为GET请求链接构造参数
        url = formatGetParameter(url,argsMap);
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null) {
            byte[] responseBytes = getData(httpEntity);
            dataByte = responseBytes;
            httpGet.abort();
        }
        //将字节数组转换成为字符串
        String result = bytesToString(dataByte);
        LogUtil.d("arg= " + new JSONObject(argsMap).toString());
        LogUtil.d("" + url + "= " + result);
        return result;
    }
     
    /**
     * 以Put方法访问
     * @param url 请求地址
     * @param argsMap 携带的参数
     * @return String
     * @throws Exception
     */
    public static String PUTMethod(String url,Map<String, Object> argsMap)throws Exception{
        byte[] dataByte = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
        //设置参数
        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(setHttpParams(argsMap), "UTF-8");
        httpPut.setEntity(encodedFormEntity);
        // 执行请求
        HttpResponse httpResponse = httpClient.execute(httpPut);
        // 获取返回的数据
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null) {
            byte[] responseBytes = getData(httpEntity);
            dataByte = responseBytes;
            httpPut.abort();
        }
        //将字节数组转换成为字符串
        String result = bytesToString(dataByte);
        LogUtil.d("arg= " + new JSONObject(argsMap).toString());
        LogUtil.d("" + url + "= " + result);
        return result;
    }
     
    /**
     * 构造GET请求地址的参数拼接
     * @param url
     * @param argsMap
     * @return String
     */
    private static String formatGetParameter(String url,Map<String, Object> argsMap){
        if (url!=null && url.length()>0) {
            if (!url.endsWith("?")) {
                url = url +"?";
            }
             
            if (argsMap!=null && !argsMap.isEmpty()) {
                Set<Entry<String, Object>> entrySet = argsMap.entrySet();
                Iterator<Entry<String, Object>> iterator = entrySet.iterator();
                while(iterator.hasNext()){
                    Entry<String, Object> entry = iterator.next();
                    if (entry!=null) {
                        String key = entry.getKey();
                        String value = (String) entry.getValue();
                        url = url + key + "=" + value;
                        if (iterator.hasNext()) {
                            url = url +"&";
                        }
                    }
                }
            }
        }
        return url;
    }
     
    /**
     * 获取数据
     * @param httpEntity
     * @return
     * @throws Exception
     */
    private static byte[] getData(HttpEntity httpEntity) throws Exception{
        BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(httpEntity);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bufferedHttpEntity.writeTo(byteArrayOutputStream);
        byte[] responseBytes = byteArrayOutputStream.toByteArray();
        return responseBytes;
    }
     
    /**
     * 设置HttpPost请求参数
     * @param argsMap
     * @return BasicHttpParams
     */
    private static List<BasicNameValuePair> setHttpParams(Map<String, Object> argsMap){
        List<BasicNameValuePair> nameValuePairList = new ArrayList<BasicNameValuePair>();
        //设置请求参数
        if (argsMap!=null && !argsMap.isEmpty()) {
            Set<Entry<String, Object>> set = argsMap.entrySet();
            Iterator<Entry<String, Object>> iterator = set.iterator();
            while(iterator.hasNext()){
                Entry<String, Object> entry = iterator.next();
                BasicNameValuePair basicNameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                nameValuePairList.add(basicNameValuePair);
            }
        }
        return nameValuePairList;
    }
     
    /**
     * 将字节数组转换成字符串
     * @param bytes
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    private static String bytesToString(byte[] bytes) throws UnsupportedEncodingException{
        if (bytes!=null) {
            String returnStr = new String(bytes,"utf-8");
            return returnStr;
        }
        return null;
    }

	/**
	 * 提交参数里有文件的数据
	 * @param<MultipartEntity>
	 *
	 * @param url
	 *            服务器地址
	 * @param param
	 *            参数
	 * @return 服务器返回结果
	 * @throws Exception
	 */
	public static String uploadSubmitFile2(String url, Map<String, Object> param,
			File file,String key) throws Exception {
		HttpPost post = new HttpPost(url);
		MultipartEntity entity = new MultipartEntity();
		if (param != null && !param.isEmpty()) {
			for (Entry<String, Object> entry : param.entrySet()) {
				if (entry.getValue() != null
						&& ((String) entry.getValue()).trim().length() > 0) {
					entity.addPart(entry.getKey(),
							new StringBody((String) entry.getValue(),Charset.forName("UTF-8")));
				}
			}
		}
		// 添加文件参数
		if (file != null && file.exists()) {
			entity.addPart(key, new FileBody(file));
		}
		post.setEntity(entity);
		HttpClient httpClient = new DefaultHttpClient();//发送请求
		HttpResponse response = httpClient.execute(post);
		int stateCode = response.getStatusLine().getStatusCode();
		StringBuffer sb = new StringBuffer();
		if (stateCode == HttpStatus.SC_OK) {
			HttpEntity result = response.getEntity();
			if (result != null) {
				InputStream is = result.getContent();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				String tempLine;
				while ((tempLine = br.readLine()) != null) {
					sb.append(tempLine);
				}
			}
		}
		post.abort();
        LogUtil.d("arg= " + new JSONObject(param).toString());
		LogUtil.d("" + url + "= " + sb.toString());
		return sb.toString();
	}
	/**
	 * 提交参数里有文件的数据 多个文件
	 * @param
	 *
	 * @param url
	 *            服务器地址
	 * @param param
	 *            参数
	 * @return 服务器返回结果
	 * @throws Exception
	 */
	public static String uploadSubmitFiles2(String url, Map<String, Object> param,
			List<File> files,String key) throws Exception {
		HttpPost post = new HttpPost(url);
		MultipartEntity entity = new MultipartEntity();
		if (param != null && !param.isEmpty()) {
			for (Entry<String, Object> entry : param.entrySet()) {
				if (entry.getValue() != null
						&& ((String) entry.getValue()).trim().length() > 0) {
					entity.addPart(entry.getKey(),
							new StringBody((String) entry.getValue(),Charset.forName("UTF-8")));
				}
			}
		}
		// 添加文件参数
		for(int i=0;i<files.size() ;i++){
			if (files.get(i) != null && files.get(i).exists()) {
				entity.addPart(key, new FileBody(files.get(i)));
				
			}
		}
		post.setEntity(entity);
		HttpClient httpClient = new DefaultHttpClient();//发送请求
		HttpResponse response = httpClient.execute(post);
		int stateCode = response.getStatusLine().getStatusCode();
		StringBuffer sb = new StringBuffer();
		if (stateCode == HttpStatus.SC_OK) {
			HttpEntity result = response.getEntity();
			if (result != null) {
				InputStream is = result.getContent();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				String tempLine;
				while ((tempLine = br.readLine()) != null) {
					sb.append(tempLine);
				}
			}
		}
		post.abort();
        LogUtil.d("arg= " + new JSONObject(param).toString());
		LogUtil.d("" + url + "= " + sb.toString());
		return sb.toString();
	}

    /**
     * cookie容器
     * 当服务器需要设置session时，别忘了给请求设置cookie以及保存cookie
     *
     */
    private static HashMap<String,String> cookieContiner =new HashMap<String,String>() ;

    /**
     * 保存Cookie
     * @param httpResponse
     */
    private static void SaveCookies(HttpResponse httpResponse)
    {
        Header[] headers = httpResponse.getHeaders("Set-Cookie");
        String headerstr=headers.toString();
        if (headers == null)
            return;

        for(int i=0;i<headers.length;i++)
        {
            String cookie=headers[i].getValue();
            String[]cookievalues=cookie.split(";");
            for(int j=0;j<cookievalues.length;j++)
            {
                String[] keyPair=cookievalues[j].split("=");
                String key=keyPair[0].trim();
                String value=keyPair.length>1?keyPair[1].trim():"";
                cookieContiner.put(key, value);
            }
        }
    }

    /**
     * 增加Get Cookie
     * @param request
     */
    private static void addGetCookies(HttpGet request)
    {
        StringBuilder sb = new StringBuilder();
        Iterator iter = cookieContiner.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            String val = entry.getValue().toString();
            sb.append(key);
            sb.append("=");
            sb.append(val);
            sb.append(";");
        }
        request.addHeader("cookie", sb.toString());
    }

    /**
     * 增加Post Cookie
     * @param request
     */
    private static void addPostCookies(HttpPost request)
    {
        StringBuilder sb = new StringBuilder();
        Iterator iter = cookieContiner.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            String val = entry.getValue().toString();
            sb.append(key);
            sb.append("=");
            sb.append(val);
            sb.append(";");
        }
        request.addHeader("cookie", sb.toString());
    }

    /**
     * 增加Put Cookie
     * @param request
     */
    private static void addPutCookies(HttpPut request)
    {
        StringBuilder sb = new StringBuilder();
        Iterator iter = cookieContiner.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            String val = entry.getValue().toString();
            sb.append(key);
            sb.append("=");
            sb.append(val);
            sb.append(";");
        }
        request.addHeader("cookie", sb.toString());
    }
	
}