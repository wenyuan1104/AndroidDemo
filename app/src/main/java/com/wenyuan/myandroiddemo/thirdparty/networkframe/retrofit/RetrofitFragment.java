package com.wenyuan.myandroiddemo.thirdparty.networkframe.retrofit;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wenyuan.myandroiddemo.BaseFragment;
import com.wenyuan.myandroiddemo.R;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetrofitFragment extends BaseFragment implements View.OnClickListener, Callback<String> {

    private Button mButStart;
    private TextView mTextShow;
    private Call<String> mCall;

    public RetrofitFragment() {
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_retrofit;
    }

    @Override
    protected void initView(View view) {
        mButStart = (Button) view.findViewById(R.id.but_start);
        mButStart.setOnClickListener(this);
        mTextShow = (TextView) view.findViewById(R.id.text_show_retrofit);
    }

    @Override
    protected void initObject() {
        //initRetrofit();
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()//1、创建Retrofit对象
                .baseUrl("https://api.github.com/repos/")//2、添加访问地址
                .addConverterFactory(new Converter.Factory() {//3、添加转换器
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return new Converter<ResponseBody, String>() {
                            @Override
                            public String convert(ResponseBody value) throws IOException {
                                return value.string();
                            }
                        };
                    }
                })
                .build();//4、执行操作
        GithubService githubService = retrofit.create(GithubService.class);//获取API接口的实现类的实例对象
        mCall = githubService.getData("wenyuan1104", "AndroidDemo");
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_start:
                startRetrofit();
                break;
            default:
                break;
        }
    }

    /**
     *
     */
    private void startRetrofit() {
        initRetrofit();
        mTextShow.setText("loading......");
        //if (mCall.isExecuted())
        //    mCall.cancel();
        mCall.enqueue(this);
        //mCall.execute();
        //mCall.request();
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        mTextShow.setText(response.body());
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        mTextShow.setText("请求失败：".concat(call.request().url().toString()));
    }
}
