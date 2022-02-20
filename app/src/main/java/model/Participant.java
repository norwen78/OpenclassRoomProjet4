package model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Participant {

    private String firstName;
    private String lastName;
    private static String emailAddress = "@lamzone.com";

    private Participant(String firstName, String lastName, String emailAddress){
        this.firstName = firstName;
        this.lastName = lastName;
        Participant.emailAddress = emailAddress;
    }

    private static List<Participant> Dummy_Participant = Arrays.asList(
            new Participant("David","Goodenough", emailAddress),
            new Participant("Jean","Bruitage", emailAddress),
            new Participant("Julien","Dandonneau", emailAddress),
            new Participant("Gustave","Lombric", emailAddress),
            new Participant("Rose","Dwight", emailAddress),
            new Participant("Benedicte","Concombre", emailAddress),
            new Participant("Martine","liberee", emailAddress),
            new Participant("Jhonny","Money", emailAddress),
            new Participant("Bazil","Lombrick", emailAddress),
            new Participant("Francis","Kuck", emailAddress)
    );

    public static List listParticipants = addressGenerator();

    private static List addressGenerator() {
        List<String> tab = new ArrayList<>();
        for (Participant participant : Dummy_Participant) {
            tab.add(participant.firstName + participant.lastName + emailAddress);
        }
        return tab;
    }
}
