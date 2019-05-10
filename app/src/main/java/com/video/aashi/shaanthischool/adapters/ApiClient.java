package com.video.aashi.shaanthischool.adapters;

import com.video.aashi.shaanthischool.fragments.HomePage;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {







   public static Retrofit retrofit = null;
        public static Retrofit getApiCLient() {

            if (retrofit == null) {
                retrofit = new Retrofit.Builder().baseUrl(HomePage.url).addConverterFactory
                        (GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }