package com.wipro.pes.countryfacts.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.wipro.pes.countryfacts.R;
import com.wipro.pes.countryfacts.model.Facts;
import com.wipro.pes.countryfacts.util.Utils;
import com.wipro.pes.countryfacts.viewmodel.CountryFactsViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CountryFactsActivityTest {

    @Rule
    public final ActivityTestRule<CountryFactsActivity> countryFactsActivityActivityTestRule = new ActivityTestRule<>(CountryFactsActivity.class);

    private CountryFactsActivity countryFactsActivity = null;


    @Before
    public void setUp() {
        countryFactsActivity = countryFactsActivityActivityTestRule.getActivity();
    }


    /**
     * Test if SwipeRefreshLayout, recycler view, recycler view's adapter are not null.
     * Test network connection status.
     * Perform swipe refresh to load data in recycler view.
     * Check if the API call response is not null and holding values.
     * compare recycle view's list items with API call's response.
     *
     * @throws InterruptedException
     */
    @SuppressWarnings("JavaDoc")
    @Test
    public void testLaunch() throws InterruptedException {
        SwipeRefreshLayout swipeRefreshLayout = countryFactsActivity.findViewById(R.id.swipe_refresh_layout);
        final RecyclerView recyclerView = countryFactsActivity.findViewById(R.id.country_facts_rv);

        assertNotNull(swipeRefreshLayout);
        assertNotNull(recyclerView);
        assertNotNull(recyclerView.getAdapter());

        assertTrue(countryFactsActivity.getString(R.string.network_error), Utils.getInstance().isNetworkConnected(countryFactsActivity));

        Espresso.onView(ViewMatchers.withId(R.id.swipe_refresh_layout)).perform(ViewActions.swipeDown());

        final CountDownLatch signal = new CountDownLatch(1);
        final Facts[] facts = new Facts[1];
        CountryFactsViewModel countryFactsViewModel = ViewModelProviders.of(countryFactsActivity).get(CountryFactsViewModel.class);
        countryFactsViewModel.getFactsListLiveData(countryFactsActivity).observe(countryFactsActivity, new Observer<Facts>() {
            @Override
            public void onChanged(@Nullable Facts fact_local) {
                facts[0] = fact_local;
                assertNotNull(facts[0]);
                assertNotNull(facts[0].getRows());
            }
        });

        countryFactsViewModel.getNetworkStatus().observe(countryFactsActivity, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                signal.countDown();
            }
        });

        signal.await();
        assertNotNull(recyclerView.getAdapter());
        assertNotEquals(0, recyclerView.getAdapter().getItemCount());
        assertEquals(facts[0].getRows().size(), recyclerView.getAdapter().getItemCount());

    }

    @After
    public void tearDown() throws Exception {
        countryFactsActivity = null;
    }
}