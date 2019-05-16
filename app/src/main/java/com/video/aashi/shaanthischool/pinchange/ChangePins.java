package com.video.aashi.shaanthischool.pinchange;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.video.aashi.shaanthischool.APIUrl;
import com.video.aashi.shaanthischool.Login;
import com.video.aashi.shaanthischool.LoginAttempt;
import com.video.aashi.shaanthischool.Navigation;
import com.video.aashi.shaanthischool.R;
import com.video.aashi.shaanthischool.adapters.Interfaces.MyInterface;
import com.video.aashi.shaanthischool.adapters.post_class.Pins;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class ChangePins extends Fragment {

    EditText enter,renter;
    CardView sendcode;
    MyInterface loginInterface;
    Retrofit retrofit;
    OkHttpClient client;
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public  static final   String NAME_PREF = "pinValidate";
    String pin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_pins, container, false);

        enter =(EditText)view.findViewById(R.id.enters);
        renter =(EditText)view.findViewById(R.id.reenters);
        sendcode    =(CardView)view.findViewById(R.id.updatePin);

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().addInterceptor(logging).build();
        retrofit =   new Retrofit.Builder().baseUrl(APIUrl.MAIN_URL).addConverterFactory
                (GsonConverterFactory.create())
                .client(client)
                .build();
        loginInterface = retrofit.create(MyInterface.class);


        sharedPreferences =getActivity().getSharedPreferences(NAME_PREF, MODE_PRIVATE);
        editor =  sharedPreferences.edit();
        sendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (enter.getText().toString().isEmpty())
                {
                    enter.setError("Field can't be empty");
                }
                else if (renter.getText().toString().isEmpty())
                {
                    renter.setError("Field can't be empty");
                }
                else if (!enter.getText().toString().equals(renter.getText().toString()))
                {
                    Toast.makeText(getActivity(),"pin doesn't match",Toast.LENGTH_LONG).show();
                }
                else
                {
                       new updatePin().execute();
                       pin =  renter.getText().toString();
                }
            }

        });

        return  view;
    }

    class updatePin extends AsyncTask
    {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {

            progressDialog =new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            Call<ResponseBody> call = loginInterface.changePin(new Pins("0",Navigation.parentPin,
                    Navigation.parentMob,pin,Navigation.loginId ));
            Log.i("Tag","ChangePins"+ Navigation.userid+   Navigation.parentPin+
                    Navigation.parentMob+pin);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful())
                    {
                        if (!response.message().equals("Invalid credentials"))
                        {
                            String body= response.message();
                            try {
                                String message =response.body().string();
                                Toast.makeText(getActivity(),"Pin updated successfully..!",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getActivity(), LoginAttempt.class);
                                intent.putExtra("loginKey","1");
                                editor.clear();
                                editor.apply();
                                startActivity(intent);

                                SharedPreferences settings =
                                        getActivity().getSharedPreferences("com.example.xyz", 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString("myPin",pin);
                                editor.apply();
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Enter valid pin",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
            return null;
        }
    }

}
