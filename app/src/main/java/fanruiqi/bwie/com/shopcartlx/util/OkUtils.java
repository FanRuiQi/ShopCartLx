package fanruiqi.bwie.com.shopcartlx.util;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkUtils {
    private static OkUtils mOkUtils;
    Handler handler;
    private final OkHttpClient mOkHttpClient;

    private OkUtils() {

         handler = new Handler(Looper.getMainLooper());
         mOkHttpClient = new OkHttpClient.Builder()
                 .readTimeout(5000, TimeUnit.MILLISECONDS)
                 .writeTimeout(5000,TimeUnit.MILLISECONDS)
                 .connectTimeout(5000,TimeUnit.MILLISECONDS)
                .build();
    }

    public static OkUtils getInstance(){
        if (mOkUtils==null){
            synchronized (OkUtils.class){
                if (mOkUtils==null){
                    return mOkUtils=new OkUtils();
                }
            }
        }
        return mOkUtils;
    }

    public interface OnCallBack{
        void onFail();
        void onResponse(String json);
    }

    public void doGet(String url,final OnCallBack onCallBack){

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response!=null&&response.isSuccessful()){

                    final String json = response.body().string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                           onCallBack.onResponse(json);
                        }
                    });
                }
            }
        });
    }
}
