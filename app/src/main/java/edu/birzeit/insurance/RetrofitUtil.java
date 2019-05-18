package edu.birzeit.insurance;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitUtil {
    private String baseUrl = "http://168.61.49.87";
    private API api;

    public RetrofitUtil() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        api = retrofit.create(API.class);
    }

    public Observable<ResponseWrapper> insureMe(RequestWrapper requestWrapper){

        return api.insureMe(requestWrapper);

    }


}
