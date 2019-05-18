package edu.birzeit.insurance;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CAAPI {

    @GET("/api/v1/publicKey/{companyName}/")
    Observable<ResponseWrapper> getPublicKey(@Path("companyName") String companyName);
}
