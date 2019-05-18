package edu.birzeit.insurance;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.insureMeButton)
    Button insureMeButton;

    @BindView(R.id.changeCredentialsButton)
    Button changeCredentialsButton;

    @BindView(R.id.companyNameText)
    EditText companyNameText;

    private RetrofitInsuranceUtil retrofitInsuranceUtil;
    private RetrofitCAUtil retrofitCAUtil;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        retrofitInsuranceUtil = new RetrofitInsuranceUtil();
        retrofitCAUtil = new RetrofitCAUtil();

        prefs = getSharedPreferences(Constants.MY_SHARED_PREFERENCES, MODE_PRIVATE);
    }

    @OnClick(R.id.insureMeButton)
    public void insureMe() {

        retrofitCAUtil.getPublicKey(companyNameText.getText().toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseWrapper>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseWrapper responseWrapper) {

                        RequestWrapper requestWrapper = new RequestWrapper();
                        //SharedPreferences
                        String myPrivateKey = prefs.getString("MyPrivateKey", Constants.MY_PRIVATE_KEY);
                        String uniqueId = "123";
                        try {
                            byte[] uniqueIdBytes = uniqueId.getBytes();
                            requestWrapper.setData(CryptographyUtil.encrypt(responseWrapper.response.getBytes(), uniqueIdBytes));
                            requestWrapper.setSignature(CryptographyUtil.encrypt(myPrivateKey.getBytes(), uniqueIdBytes));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        retrofitInsuranceUtil.insureMe(requestWrapper)
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

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @OnClick(R.id.changeCredentialsButton)
    public void changeCredentials() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        alert.setMessage("Please enter the new private key that you received from CA");
        alert.setTitle("New Private Key");

        alert.setView(edittext);

        alert.setPositiveButton("Submit", (dialog, whichButton) -> {

            String newPrivateKey = edittext.getText().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("MyPrivateKey", newPrivateKey);
            editor.apply();
        });

        alert.setNegativeButton("Cancel", (dialog, whichButton) -> {

        });

        alert.show();
    }
}
