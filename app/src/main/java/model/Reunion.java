package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Reunion {

    private int color;

    private String room;

    private Date timeStart;

    private Date timeEnd;

    private String subject;

    private List<String> participantList;

    public Reunion(int color, String room, Date timeStart, Date timeEnd, String subject, List<String> participantList) {
        this.color = color;
        this.room = room;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.subject = subject;
        this.participantList = participantList;
    }

    public int getColor() {
        return color;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setColor(int color){
        this.color = color;
    }

    public String getRoom(){
        return room;
    }

    private String getSubject(){
        return subject;
    }

    public String getParticipantsList() {
        StringBuilder participants = new StringBuilder();
        for (String participant : participantList){
            participants.append(participant).append(",");
        }
        return participants.toString();
    }

    public String getInfo() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        return this.getRoom() + " - " + dateFormat.format(timeStart).replace(':', 'h') + " - " + this.getSubject();
    }

}
