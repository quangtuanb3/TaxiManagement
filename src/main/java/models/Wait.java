package models;

import java.sql.Time;

public class Wait {
    private Time startTime;
    private Time endTime;
    private Location location;

    public Wait(Time startTime, Time endTime, Location location) {
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
