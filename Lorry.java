package university.sathyabama.lorrymanagement;

/**
 * Created by rishikumar on 11/05/18.
 */

public class Lorry {
    private String id;
    private double temp;
    private int door;
    private double lat,lon;

    public Lorry(String id, double temp, int door, double lat, double lon) {
        this.id = id;
        this.temp = temp;
        this.door = door;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public double getTemp() {
        return temp;
    }

    public int getDoor() {
        return door;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

}
