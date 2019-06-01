package edu.birzeit.insurance;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CAAPI {

    @GET("/api/v1/user/companies/")
    Observable<List<User>> getAllCompanies();
}
