package com.example.maru;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import static android.graphics.Color.rgb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import di.DI;
import model.Reunion;
import service.DummyReunionGenerator;
import service.ReunionApiService;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private ReunionApiService mService;

    @Before
    public void setup() {mService = DI.getNewReunionApiService();
    }

    @Test
    public void getMeetingWithSuccess() {
        List<Reunion> reunion = mService.getReunion();
        List<Reunion> expectedReunion = DummyReunionGenerator.DUMMY_REUNION;
        assertThat(reunion, IsIterableContainingInAnyOrder.containsInAnyOrder(Objects.requireNonNull(expectedReunion.toArray())));
    }

    @Test
    public void createMeetingWithSuccess() {
        Date dateStart = new Date();
        Date dateEnd = new Date();

        List<String> list = new ArrayList<>(Arrays.asList("1@1.1", "2@2.2"));
        Reunion test = new Reunion(rgb(100, 150, 200), "Salle A", dateStart, dateEnd, "test", list);
        mService.createReunion(test);
        assertTrue(mService.getReunion().contains(test));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Reunion reunionToDelete = mService.getReunion().get(0);
        mService.deleteReunion(reunionToDelete);
        assertFalse(mService.getReunion().contains(reunionToDelete));
    }

    @Test
    public void checkingDateFilter() {
        List<String> list = new ArrayList<>(Arrays.asList("1@1.1", "2@2.2"));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
        Reunion test = new Reunion(rgb(100, 150, 200), "Salle A", cal.getTime(), cal.getTime(), "test", list);
        mService.createReunion(test);

        assertTrue(mService.getReunionDateFilter(cal.getTime()).contains(test));

    }
}