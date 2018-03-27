package com.run.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * http请求工具
 * @author  lordtan
 * @date 2018-3-27
 */
public class HttpUtil {

    /**
     * 向服务器发送post请求
     * @param url
     * @return
     */
    public static String sendRequest(String url, Object data) {

        try {
            FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    URL u = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) u.openConnection();
                    connection.setDoInput(true);//设置这个连接是否可以写入数据
                    connection.setDoOutput(true);//设置这个连接是否可以输出数据
                    connection.setRequestMethod("POST");//设置请求的方式
                    connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    connection.setConnectTimeout(10 * 1000); //10秒超时
                    connection.connect();

                    /**
                     * 向服务器端写数据
                     */
                    OutputStream out = connection.getOutputStream();//输出流，用来发送请求，http请求实际上直到这个函数里面才正式发送出去
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));//创建字符流对象并用高效缓冲流包装它，便获得最高的效率,发送的是字符串推荐用字符流，其它数据就用字节流
                    bw.write(JsonParser.toJson(data));//把json字符串写入缓冲区中
                    bw.flush();//刷新缓冲区，把数据发送出去，这步很重要
                    out.close();
                    bw.close();//使用完关闭

                    /**
                     * 判断请求是否ok，如果ok则向服务端写数据
                     */
                    if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                        InputStream in = connection.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf8"));
                        final StringBuilder sb = new StringBuilder();
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        return sb.toString();
                    }else{
                        return null;
                    }

                }
            });

            new Thread(task).start();

            return task.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("网络访问错误");
        }

    }
}
