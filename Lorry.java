package university.sathyabama.lorrymanagement;

/**
 * Created by rishikumar on 11/05/18.
 */

public class Lorry {
    private String id;
    private float temp;
    private boolean door;
    private double lat,lon;

    public Lorry(String id, float temp, boolean door, double lat, double lon) {
        this.id = id;
        this.temp = temp;
        this.door = door;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public float getTemp() {
        return temp;
    }

    public boolean isDoor() {
        return door;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
