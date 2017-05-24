package biz.sansosti.proyectocalvario;

import android.content.res.AssetFileDescriptor;
import android.location.Location;

/**
 * Created by sergio on 23/05/2017.
 */

public class CheckPoint {
    private String name;
    private Location location;
    private AssetFileDescriptor audio;
    private AssetFileDescriptor customBackgroundAudio;

    public CheckPoint(String aName, String lat, String lon, AssetFileDescriptor aAudio, AssetFileDescriptor aCustomBackgroundAudio) {
        name = aName;
        audio = aAudio;
        customBackgroundAudio = aCustomBackgroundAudio;

        location = new Location(name);
        location.setLatitude(Float.valueOf(lat));
        location.setLongitude(Float.valueOf(lon));
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public AssetFileDescriptor getAudio() {
        return audio;
    }

    public AssetFileDescriptor getCustomBackground() {
        return customBackgroundAudio;
    }
}
