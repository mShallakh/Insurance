package edu.birzeit.insurance;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

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

    @BindView(R.id.companyNameSpinner)
    Spinner companyNameSpinner;
    SharedPreferences prefs;
    ArrayList<User> companies;
    User selectedCompany;
    private RetrofitCAUtil retrofitCAUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        retrofitCAUtil = new RetrofitCAUtil();
        companies = new ArrayList<>();

        prefs = getSharedPreferences(Constants.MY_SHARED_PREFERENCES, MODE_PRIVATE);
        retrofitCAUtil.getPrivateKey("123")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseWrapper>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseWrapper responseWrapper) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("MyPrivateKey", responseWrapper.getResponse());
                        editor.apply();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        retrofitCAUtil.getAllCompanies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<User> users) {
                        companies.addAll(users);
                        selectedCompany = companies.get(0);
                        ArrayList<String> names = new ArrayList<>();
                        for (User company : companies)
                            names.add(company.getName());
                        String[] strings = names.toArray(new String[names.size()]);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_item, strings);
                        companyNameSpinner.setAdapter(adapter);
                        companyNameSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                selectedCompany = companies.get(position);
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

    @OnClick(R.id.insureMeButton)
    public void insureMe() {


        RequestWrapper requestWrapper = new RequestWrapper();
        //SharedPreferences
        String myPrivateKey = prefs.getString("MyPrivateKey", Constants.MY_PRIVATE_KEY);
        String uniqueId = "123";
        try {
            byte[] uniqueIdBytes = uniqueId.getBytes();
            requestWrapper.setData(CryptographyUtil.encrypt(companies.get(0).publicKey.getBytes(), uniqueIdBytes));
            requestWrapper.setSignature(CryptographyUtil.encrypt(myPrivateKey.getBytes(), uniqueIdBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        RetrofitInsuranceUtil retrofitInsuranceUtil = new RetrofitInsuranceUtil(selectedCompany.getCompanyURL());
        retrofitInsuranceUtil.insureMe(requestWrapper)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseWrapper>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseWrapper responseWrapper) {
                        Log.d("RESPONSE: ", responseWrapper.getResponse());
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
