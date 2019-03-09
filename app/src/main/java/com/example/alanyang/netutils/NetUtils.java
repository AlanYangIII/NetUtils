package com.example.alanyang.netutils;

import android.accounts.NetworkErrorException;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class NetUtils {

    private static final NetUtils ourInstance = new NetUtils();

    private static ExecutorService threadPool = Executors.newCachedThreadPool();//一个项目就只创建一个线程池

    public static NetUtils getInstance() {
        synchronized (NetUtils.class) {}
        return ourInstance;
    }

    private NetUtils(){}

    public interface Callback {
        void onResponse(String response);
        void onFailed(String response);
    }

    public void excute(User user, Callback callback) {
        //对线程池是否开启做一次判断
        if (threadPool.isShutdown()) {
            threadPool = Executors.newCachedThreadPool();
            doRequest(user, callback);
        } else {
            doRequest(user, callback);
        }
    }

    public void doRequest(final User user, final Callback callback){
        threadPool.execute(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(user.getUrl());
                    HttpURLConnection connection =(HttpURLConnection)url.openConnection();
                    if("POST".equals(user.getMethod())){    //POST
                        connection.setConnectTimeout(3 * 1000);
                        connection.setReadTimeout(3 * 1000);
                        connection.setDoInput(true);
                        connection.setDoOutput(true);
                        connection.setUseCaches(false);
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Content-Type",user.getProperty());//设置Content-Type
                        connection.setRequestProperty("Accept-Charset", "UTF-8");//设置字符编码
                        connection.connect();
                        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                        out.write(user.getParams().getBytes());
                        out.flush();
                        out.close();
                        InputStream is = connection.getInputStream();
                        String response = getStringFromInputStream(is);
                        if (connection.getResponseCode() == 200) {
                            Log.d("RegisteredActivity", response);
                            callback.onResponse(response);
                            if (connection != null) {
                                connection.disconnect();// 关闭连接
                            }
                        } else {
                            callback.onFailed(response);
                            connection.disconnect();// 关闭连接
//                            throw new NetworkErrorException("\"response status is \" + responseCode");
                        }

                    }else{  //GET
                        connection.setConnectTimeout(3 * 1000);
                        connection.setReadTimeout(3 * 1000);
                        connection.setDoInput(true);
                        connection.setDoOutput(true);
                        connection.setUseCaches(false);
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Content-Type",user.getProperty());//设置Content-Type
                        connection.setRequestProperty("Accept-Charset", "UTF-8");//设置字符编码
                        connection.connect();
                        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                        out.write(user.getParams().getBytes());
                        out.flush();
                        out.close();
                        InputStream is = connection.getInputStream();
                        String response = getStringFromInputStream(is);
                        if (connection.getResponseCode() == 200) {
                            Log.d("RegisteredActivity", response);
                            callback.onResponse(response);
                            if (connection != null) {
                                connection.disconnect();// 关闭连接
                            }
                        } else {
                            callback.onFailed(response);
                            connection.disconnect();// 关闭连接
                            throw new NetworkErrorException("\"response status is \" + responseCode");
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
    }


    private static String getStringFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 模板代码 必须熟练
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();// 把流中的数据转换成字符串,采用的编码是utf-8(模拟器默认编码)
        os.close();
        return state;
    }
}