package edu.birzeit.insurance;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {

    @POST("/api/v1/insurance/")
    Observable<ResponseWrapper> insureMe(@Body RequestWrapper requestWrapper);

}
