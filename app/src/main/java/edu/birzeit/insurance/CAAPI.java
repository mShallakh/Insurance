package edu.birzeit.insurance;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CAAPI {

    @GET("/api/v1/user/companies/")
    Observable<List<User>> getAllCompanies();

    @GET("/api/v1/user/private/{userId}/")
    Observable<ResponseWrapper> getPrivateKey(@Path("userId") String userId);
}
