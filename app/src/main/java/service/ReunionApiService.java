package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import model.Reunion;

public interface ReunionApiService {

    List<Reunion> getReunion();
    void createReunion(Reunion reunion);
    void deleteReunion(Reunion reunion);
    List<Reunion> getReunionDateFilter(Date date);
    List<Reunion> getReunionRoomFilter(String room);
}
