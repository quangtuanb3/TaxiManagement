package models;

import java.io.Serializable;

public class Distance implements Serializable {
    private String depart;
    private String arrive;
    private Double distance;

    public Distance(String depart, String arrive, Double distance) {
        this.depart = depart;
        this.arrive = arrive;
        this.distance = distance;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
