package edu.birzeit.insurance;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

class RetrofitCAUtil {
    private CAAPI caAPI;

    RetrofitCAUtil() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.200:8089")
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        caAPI = retrofit.create(CAAPI.class);
    }

    Observable<List<User>> getAllCompanies() {

        return caAPI.getAllCompanies();

    }

    public Observable<ResponseWrapper> getPrivateKey(String uniqueId) {
        return caAPI.getPrivateKey(uniqueId);
    }
}
