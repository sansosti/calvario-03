package biz.sansosti.proyectocalvario;

import android.location.Location;

/**
 * Created by sergio on 23/05/2017.
 */

public class CheckPoint {
    private String name;
    private String fileName;
    private Location location;
    private String customBackground;

    public CheckPoint(String aName, String lat, String lon, String aFileName, String aCustomBackground) {
        this.name = aName;
        fileName = aFileName;
        location = new Location(this.name);
        location.setLatitude(Float.valueOf(lat));
        location.setLongitude(Float.valueOf(lon));
        customBackground = aCustomBackground;
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    public Location getLocation() {
        return location;
    }

    public String getCustomBackground() {
        return customBackground;
    }
}
