package com.lymeng.mengnetwork;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MengNetwork {


    private static MengNetwork instance;
    private static OkHttpClient httpClient;

    public static MengNetwork getInstance(){
        if(instance==null){
            instance = new MengNetwork();
            httpClient = new OkHttpClient();
        }
        return instance;
    }

    public OkHttpClient getHTTPClient(){
        return httpClient;
    }

    private Request getRequest(String url, String path){
        return new Request.Builder().url(url+path).build();
    }

    public void newCall(final String url, final String path, final Callback callback){

        getHTTPClient().newCall(getRequest(url, path)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(callback!=null){
                    callback.onFailure(call, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(callback!=null){
                    callback.onResponse(call, response);
                }
            }
        });

    }


}
