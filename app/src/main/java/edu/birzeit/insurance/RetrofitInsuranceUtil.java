package edu.birzeit.insurance;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInsuranceUtil {
    private String baseUrl = "http://168.61.49.87";
    private InsuranceAPI insuranceAPI;

    public RetrofitInsuranceUtil() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        insuranceAPI = retrofit.create(InsuranceAPI.class);
    }

    public Observable<ResponseWrapper> insureMe(RequestWrapper requestWrapper){

        return insuranceAPI.insureMe(requestWrapper);

    }


}
