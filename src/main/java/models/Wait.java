package models;

import java.io.Serializable;
import java.sql.Time;

public class Wait implements Serializable {
    private Time startTime;
    private Time endTime;
    private Location location;

    public Wait(Time startTime, Time endTime, Location location) {
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
