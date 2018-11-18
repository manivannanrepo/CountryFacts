package com.wipro.pes.countryfacts.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.wipro.pes.countryfacts.CountryFactsApplication;
import com.wipro.pes.countryfacts.model.Facts;
import com.wipro.pes.countryfacts.R;

import com.wipro.pes.countryfacts.network.RetrofitModule;
import com.wipro.pes.countryfacts.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryFactsViewModel extends AndroidViewModel {

    private final MutableLiveData<Facts> factsListLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> networkStatus = new MutableLiveData<>();
    private CountryFactsApplication countryFactsApplication;

    public CountryFactsViewModel(@NonNull Application application) {
        super(application);
        this.countryFactsApplication = (CountryFactsApplication) application;
    }

    public MutableLiveData<Facts> getFactsListLiveData(Context context) {

        fetchCountryFacts(context);

        return factsListLiveData;
    }

    public void fetchCountryFacts(Context context) {
        if (Utils.getInstance().isNetworkConnected(context)) {
            Call<Facts> factsCall = new RetrofitModule(countryFactsApplication.getString(R.string.base_url)).provideApiService().getFacts();
            factsCall.enqueue(new Callback<Facts>() {
                @Override
                public void onResponse(@NonNull Call<Facts> call, @NonNull Response<Facts> response) {
                    factsListLiveData.setValue(response.body());
                    networkStatus.setValue(true);
                }

                @Override
                public void onFailure(@NonNull Call<Facts> call, @NonNull Throwable throwable) {
                    networkStatus.setValue(false);
                    Toast.makeText(countryFactsApplication.getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            networkStatus.setValue(false);
            Toast.makeText(context, context.getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    public MutableLiveData<Boolean> getNetworkStatus() {
        return networkStatus;
    }
}
