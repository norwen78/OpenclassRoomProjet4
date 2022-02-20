package service;

import static android.graphics.Color.rgb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import model.Participant;
import model.Reunion;

public class DummyReunionGenerator {
    private static int actualColor;

    public static int getActualColor() {
        return actualColor;
    }

    public static List<Reunion> DUMMY_REUNION = Arrays.asList(
            new Reunion(generateColor(), "Bowser", new Date(1642526982000L), new Date(1642527982000L),"Marketing", Participant.listParticipants),
            new Reunion(generateColor(), "luigi", new Date(1642786164000L), new Date(1642787164000L),"Marketing", Participant.listParticipants),
            new Reunion(generateColor(), "waluigi", generateStartTime(), generateEndTime(),"Marketing", Participant.listParticipants),
            new Reunion(generateColor(), "Yoshi", generateStartTime(), generateEndTime(),"Marketing", Participant.listParticipants),
            new Reunion(generateColor(), "Mario", generateStartTime(), generateEndTime(),"Marketing", Participant.listParticipants)
    );

    static List<Reunion> generateReunion() {
        return new ArrayList<>(DUMMY_REUNION);
    }

    public static int generateColor() {
        actualColor = rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
        return actualColor;
    }

    private static Date generateStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 0);
        return cal.getTime();
    }

    private static Date generateEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 0);
        return cal.getTime();
    }
}
