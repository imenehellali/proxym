package com.example.hella.proxym.Util;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpGet extends AsyncTask<String,Void,String>{

    private String stdURL="192.168.0.102:9000";
    private String serverURL;
    public String httpResult;

    public HttpGet(String serverURL) {
        this.serverURL = serverURL;
    }
    public HttpGet(){
        this.serverURL=stdURL;
    }

    @Override
    protected String doInBackground(String... strings) {
        BufferedReader inBuffer=null;
        String url=this.serverURL;
        String result ="fail";

        for(String parameter:strings){
            url=url + "/" + parameter;

        }
        try{
            URL getter=new URL(url);
            Log.d("INFORMATION", "URL: "+url);
            result = convertInputStreamToString(getter.openConnection().getInputStream());
            Log.d("INFORMATION", "GETTER: " + result);
        }catch(MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inBuffer!=null){
                try {
                    inBuffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        String line="";
        String result="";
        while((line= bufferedReader.readLine())!=null){
            result+=line;
        }
        inputStream.close();
        return result;
    }

    protected void onPostExecute(String result){
        httpResult=result;
    }

    public String getHttpResult(){
        return httpResult;
    }
}
