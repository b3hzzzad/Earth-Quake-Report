package th.o.earthquakereport;

public class Properties {
    private double mag;
    private String place, url, latitude, longitude;
    private long time;

    public double getMag() {
        return mag;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setMag(double mag) {
        this.mag = mag;
    }

    public String getPlace() {
        return place;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }
}
