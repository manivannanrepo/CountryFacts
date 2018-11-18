package pes.wipro.com.countryfacts;

import android.arch.lifecycle.MutableLiveData;

import com.wipro.pes.countryfacts.CountryFactsApplication;
import com.wipro.pes.countryfacts.model.Facts;
import com.wipro.pes.countryfacts.model.Row;
import com.wipro.pes.countryfacts.view.CountryFactsActivity;
import com.wipro.pes.countryfacts.viewmodel.CountryFactsViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CountryFactsActivityTest {

    @Mock
    private CountryFactsViewModel countryFactsViewModel;

    @Mock
    private MutableLiveData<Facts> factsMutableLiveData = new MutableLiveData<>();

    @Mock
    private CountryFactsActivity countryFactsActivity;

    @Mock
    private CountryFactsApplication countryFactsApplication;

    @Before
    public void setUp()  {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLaunch() {
        Facts facts = new Facts();
        facts.setTitle("Title");
        Row row = new Row();
        row.setTitle("title1");
        row.setDescription("desc1");
        List<Row> rows = new ArrayList<>();
        rows.add(row);
        facts.setRows(rows);
        factsMutableLiveData.setValue(facts);

        Mockito.when(countryFactsViewModel.getFactsListLiveData(countryFactsActivity)).thenReturn(factsMutableLiveData);
        countryFactsViewModel.fetchCountryFacts(countryFactsActivity);
        Mockito.inOrder(countryFactsViewModel).verify(countryFactsViewModel).fetchCountryFacts(countryFactsActivity);
    }

    @After
    public void tearDown() {
        countryFactsViewModel = null;
    }
}