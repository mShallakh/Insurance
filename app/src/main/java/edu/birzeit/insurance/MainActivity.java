package edu.birzeit.insurance;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.insureMeButton)
    Button insureMeButton;

    private RetrofitUtil retrofitUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        retrofitUtil = new RetrofitUtil();
    }

    @OnClick(R.id.insureMeButton)
    public void insureMe(){

        RequestWrapper requestWrapper = new RequestWrapper();
        //SharedPreferences
        String uniqueId = "123";
        try {
            requestWrapper.setData(CryptographyUtil.encrypt(Constants.COMPANY_PUBLIC_KEY.getBytes(), uniqueId.getBytes()));
            requestWrapper.setSignature(CryptographyUtil.encrypt(Constants.MY_PRIVATE_KEY.getBytes(), uniqueId.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        retrofitUtil.insureMe(requestWrapper)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseWrapper>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseWrapper responseWrapper) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
