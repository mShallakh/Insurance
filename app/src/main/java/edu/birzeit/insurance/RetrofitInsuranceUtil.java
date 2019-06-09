package edu.birzeit.insurance;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

class RetrofitInsuranceUtil {
    private InsuranceAPI insuranceAPI;

    RetrofitInsuranceUtil(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl + ":8089")
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        insuranceAPI = retrofit.create(InsuranceAPI.class);
    }

    Observable<ResponseWrapper> insureMe(RequestWrapper requestWrapper) {
        return insuranceAPI.insureMe(requestWrapper);
    }


}
