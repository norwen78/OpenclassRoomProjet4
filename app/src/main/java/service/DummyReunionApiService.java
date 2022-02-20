package service;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.Reunion;

public class DummyReunionApiService implements ReunionApiService {
    private List<Reunion> reunions = DummyReunionGenerator.generateReunion();

    @Override
    public List<Reunion> getReunion() {
        return reunions;
    }

    @Override
    public void createReunion(Reunion reunion) {
        reunions.add(reunion);
    }

    @Override
    public void deleteReunion(Reunion reunion) {
        reunions.remove(reunion);
    }


    @Override
    public List<Reunion> getReunionDateFilter(Date date) {
        ArrayList<Reunion> result = new ArrayList<>();

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        for (int i = 0; i < reunions.size(); i++) {
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(reunions.get(i).getTimeStart());
            boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
            if (sameDay) result.add(reunions.get(i));

        }
        return result;

    }

    @Override
    public List<Reunion> getReunionRoomFilter(String room) {
        List<Reunion> mMeetingFiltered = new ArrayList<>();
        for (Reunion reunion : reunions) {
            if (reunion.getRoom().contains(room)) mMeetingFiltered.add(reunion);
        }
        return mMeetingFiltered;
    }

}
