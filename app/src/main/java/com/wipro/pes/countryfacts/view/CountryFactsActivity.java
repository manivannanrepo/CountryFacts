package com.wipro.pes.countryfacts.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wipro.pes.countryfacts.model.Facts;
import com.wipro.pes.countryfacts.R;
import com.wipro.pes.countryfacts.view.adapter.CountryFactsRecyclerViewAdapter;
import com.wipro.pes.countryfacts.viewmodel.CountryFactsViewModel;


public class CountryFactsActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView countryFactsRecyclerView;
    private CountryFactsViewModel countryFactsViewModel;
    private CountryFactsRecyclerViewAdapter countryFactsRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_facts);
        initUI();
        setupRecyclerView();
        setupListeners();
        setupData();
    }

    private void setupListeners() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                countryFactsViewModel.fetchCountryFacts(CountryFactsActivity.this);
            }
        });
    }

    private void setupRecyclerView() {
        countryFactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        countryFactsRecyclerViewAdapter = new CountryFactsRecyclerViewAdapter(this);
        countryFactsRecyclerView.setAdapter(countryFactsRecyclerViewAdapter);
    }

    /**
     * Setup LiveData Observers. Observe Country Facts
     */
    private void setupData() {
        countryFactsViewModel = ViewModelProviders.of(this).get(CountryFactsViewModel.class);
        countryFactsViewModel.getFactsListLiveData(this).observe(this, new Observer<Facts>() {
            @Override
            public void onChanged(@Nullable Facts facts) {
                updateCountryFacts(facts);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        countryFactsViewModel.getNetworkStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean status) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void updateCountryFacts(Facts facts) {
        if (facts != null) {
            setupTitle(facts.getTitle());
            countryFactsRecyclerViewAdapter.setData(facts.getRows());
            countryFactsRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    private void setupTitle(String title) {
        if (getSupportActionBar() != null && title != null)
            getSupportActionBar().setTitle(title);
    }

    private void initUI() {
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        countryFactsRecyclerView = findViewById(R.id.country_facts_rv);
    }
}
