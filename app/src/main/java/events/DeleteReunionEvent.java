package events;

import model.Reunion;

public class DeleteReunionEvent {
    public Reunion reunion;


    public DeleteReunionEvent(Reunion reunion) {
        this.reunion = reunion;
    }
}
