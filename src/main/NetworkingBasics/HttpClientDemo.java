package main.NetworkingBasics;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import main.NetworkingBasics.ResultStructure.MultipleLineStructure;
import main.NetworkingBasics.ResultStructure.SubItem_01;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class HttpClientDemo {
    public static String getDataByUrl_POST(String url , String request) throws Exception{
        System.out.println("Url: "+url);
        /**
         * 建立连接，设置连接属性
         */
        HttpURLConnection connection = null;
        URL Url = new URL(url);
        connection = (HttpURLConnection) Url.openConnection();
        connection.setRequestMethod("POST");
        connection.addRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        connection.setRequestProperty("Content-Type","application/json");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setRequestProperty("Authorization"," Bearer fc58be57c46b32f9a2c32e5393684ac0");

        connection.connect();
        OutputStream outputStream = connection.getOutputStream();

        outputStream.write(request.getBytes());
        outputStream.flush();
        outputStream.close();

        /**
         *  获取连接状态，接受返回数据
         */
        int responseCode = -1;
        responseCode = connection.getResponseCode();

        if (responseCode == 200){
            System.out.println("连接成功");

            //读取请求返回的数据
            InputStream inputStream = connection.getInputStream();
            StringBuilder stringBuilder = new StringBuilder("");

            byte[] array = new byte[inputStream.available()];
            inputStream.read(array);
            stringBuilder.append(new String(array));
            //System.out.println(stringBuilder.toString());
            dataFormat(stringBuilder.toString());
        }
        else {
            System.out.println("Failed! ResponseCode is :"+responseCode);
        }
        return null;
    }
    public static void dataFormat(String result){
        JSONObject jsonObject = JSONObject.parseObject(result);
        System.out.println("服务器返回的数据如下:");
        System.out.println(jsonObject.get("status"));
        System.out.println(jsonObject.get("message"));
        System.out.println(jsonObject.get("sign"));
        JSONObject data = jsonObject.getJSONObject("data");

        JSONArray items = data.getJSONArray("items");
        if (items!=null) {
            for (Iterator<Object> iterator = items.iterator(); iterator.hasNext(); ) {
                Object o = iterator.next();
                System.out.println(o.toString());
            }
        }else {
            System.out.println(data);
        }

    }
    public static void main(String[] args) throws Exception{
        String url = "http://api.qixin.com/APITestService/v2/enterprise/searchList";
        //查询参数
        String requestStr = "{\"keyword\": \"小米科技\" ,  \"appkey\" : \"ada44bd0070711e6b8a865678b483fde\"}";
        getDataByUrl_POST(url,requestStr);
    }
}
