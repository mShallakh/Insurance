package edu.birzeit.insurance;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitCAUtil {
    private String baseUrl = "http://168.61.49.87";
    private CAAPI caAPI;

    public RetrofitCAUtil() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        caAPI = retrofit.create(CAAPI.class);
    }

    public Observable<List<User>> getAllCompanies(){

        return caAPI.getAllCompanies();

    }

}
