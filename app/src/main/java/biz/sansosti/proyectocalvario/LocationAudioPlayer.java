package biz.sansosti.proyectocalvario;

import android.content.res.AssetFileDescriptor;
import android.location.Location;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static biz.sansosti.proyectocalvario.Constants.DEBUG;
import static biz.sansosti.proyectocalvario.Constants.DISTANCE_THRESHOLD;
import static biz.sansosti.proyectocalvario.Constants.PAUSE_BACKGROUND_SOUND_IN_CHECKPOINT;

/**
 * Created by sergio on 23/05/2017.
 */

public class LocationAudioPlayer {

    // CheckPoints
    private CheckPoint[] checkPointsDefs;
    private int currentCheckPointIndex = -1;

    // Audio
    private MediaPlayer checkPointMediaPlayer;
    private MediaPlayer backgroundMediaPlayer;
    private AssetFileDescriptor currentBackgroundAudio;
    private AssetFileDescriptor currentAudio;

    public LocationAudioPlayer() {

        // Init checkPointMediaPlayer
        checkPointMediaPlayer = new MediaPlayer();
        checkPointMediaPlayer.setLooping(false);
        checkPointMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                if (PAUSE_BACKGROUND_SOUND_IN_CHECKPOINT) {
                    backgroundMediaPlayer.start();
                }
            }
        });
        // Init backgroundMediaPlayer
        currentBackgroundAudio = MainActivity.afdDefaultBackground;
        backgroundMediaPlayer = new MediaPlayer();
        backgroundMediaPlayer.setLooping(true);
        loadMediaPlayer(backgroundMediaPlayer, currentBackgroundAudio);

        populateCheckPoints();

    }

    public void loadMediaPlayer(MediaPlayer aPlayer, AssetFileDescriptor afdAudio) {
        try {
            aPlayer.setDataSource(afdAudio.getFileDescriptor(),afdAudio.getStartOffset(),afdAudio.getLength());
            aPlayer.prepare();
            aPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void populateCheckPoints(){

        if (!DEBUG) {
            checkPointsDefs = new CheckPoint[8];
            int cp = 0;
            checkPointsDefs[cp++] = new CheckPoint("02","-37.3294242","-59.1508588" ,MainActivity.afdPuesto02,MainActivity.afdFondoPajaritosEstereo);
            checkPointsDefs[cp++] = new CheckPoint("03","-37.329595" ,"-59.1511569" ,MainActivity.afdPuesto03,null);
            checkPointsDefs[cp++] = new CheckPoint("04","-37.3295102","-59.1518422" ,MainActivity.afdPuesto04,null); // sin pausa
            checkPointsDefs[cp++] = new CheckPoint("05","-37.3295676","-59.152393"  ,MainActivity.afdPuesto05,MainActivity.afdFondoPajaritosConAphex); // sin pausa (usar el nuevo background)
            checkPointsDefs[cp++] = new CheckPoint("06","-37.3293892","-59.1530269" ,MainActivity.afdPuesto06,null);
            checkPointsDefs[cp++] = new CheckPoint("07","-37.3297072","-59.1534959" ,MainActivity.afdPuesto07,null);
            checkPointsDefs[cp++] = new CheckPoint("08","-37.3291582","-59.1533802" ,MainActivity.afdPuesto08,null);
            checkPointsDefs[cp++] = new CheckPoint("09","-37.3288975","-59.1529821" ,MainActivity.afdPuesto09,MainActivity.afdFondoFin);
        } else {
            checkPointsDefs = new CheckPoint[8];
            int cp = 0;
            checkPointsDefs[cp++] = new CheckPoint("02","-37.333481","-59.135572" ,MainActivity.afdPuesto02,MainActivity.afdFondoPajaritosEstereo);
            checkPointsDefs[cp++] = new CheckPoint("03","-37.334551","-59.134913" ,MainActivity.afdPuesto03,null);
            checkPointsDefs[cp++] = new CheckPoint("04","-37.335051","-59.136249" ,MainActivity.afdPuesto04,null); // sin pausa
            checkPointsDefs[cp++] = new CheckPoint("05","-37.333930","-59.136975"  ,MainActivity.afdPuesto05,MainActivity.afdFondoPajaritosConAphex); // sin pausa (usar el nuevo background)
            checkPointsDefs[cp++] = new CheckPoint("06","-37.334183","-59.136003" ,MainActivity.afdPuesto06,null);
            checkPointsDefs[cp++] = new CheckPoint("07","-37.334367","-59.135492" ,MainActivity.afdPuesto07,null);
            checkPointsDefs[cp++] = new CheckPoint("08","-37.333843","-59.135744" ,MainActivity.afdPuesto08,null);
            checkPointsDefs[cp++] = new CheckPoint("09","-37.333678","-59.136319" ,MainActivity.afdPuesto09,MainActivity.afdFondoFin);
        }


    }

    public void onLocationChanged(Location location) {

        MainActivity.tv_user_shown_status.setText(R.string.running);

        MainActivity.tv_latitude.setText(String.valueOf(location.getLatitude()));
        MainActivity.tv_longitude.setText(String.valueOf(location.getLongitude()));

        if (!checkPointMediaPlayer.isPlaying()) {
            processNewLocation(location);
        } else {
            MainActivity.tv_user_shown_status.setText(R.string.playing);
        }

    }

    private void processNewLocation(final Location location) {
        float accuracy = location.getAccuracy();
        MainActivity.tv_status.setText(R.string.boyando);

        float minDistance = 99999999;
        int closestCheckPointIndex = -1;

        ArrayList<Location> locationList = new ArrayList<Location>();

        for (int i = 0; i < checkPointsDefs.length; i++) {
            locationList.add(checkPointsDefs[i].getLocation());
        }
        Collections.sort(locationList, new Comparator<Location>() {
            public int compare(Location obj1, Location obj2) {
                if (location.distanceTo(obj1) == location.distanceTo(obj2)) {
                    return 0;
                } else {
                    if (location.distanceTo(obj1) < location.distanceTo(obj2)) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        });

        for (int i = 0; i < checkPointsDefs.length; i++) {
            float distance = location.distanceTo(checkPointsDefs[i].getLocation());
            if (distance < minDistance) {
                minDistance = distance;
                closestCheckPointIndex = i;
            }

            MainActivity.mCheckPoints[i].setText(locationList.get(i).getProvider() + "\tDist: " + String.format("%1$.2f", location.distanceTo(locationList.get(i))));
        }

        boolean checkPointFound = ((minDistance <= DISTANCE_THRESHOLD) && (closestCheckPointIndex != -1));

        boolean checkPointIsNew = checkPointFound && (closestCheckPointIndex != currentCheckPointIndex);

        if (checkPointIsNew) {
            currentCheckPointIndex = closestCheckPointIndex;
            currentAudio = checkPointsDefs[currentCheckPointIndex].getAudio();
            MainActivity.tv_status.setText("At " + checkPointsDefs[currentCheckPointIndex].getName());
            MainActivity.tv_current_checkpoint.setText(checkPointsDefs[currentCheckPointIndex].getName());
            MainActivity.tv_closer_checkpoint.setText(String.format("%1$.0f", minDistance) + " mts.");

            if (checkPointMediaPlayer.isPlaying()) {
                checkPointMediaPlayer.stop();
            }
            checkPointMediaPlayer.reset();

            //Toast.makeText(this, "Location Updates Stopped",Toast.LENGTH_SHORT).show();

            try {
                checkPointMediaPlayer.setDataSource(currentAudio.getFileDescriptor(),currentAudio.getStartOffset(),currentAudio.getLength());
                checkPointMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            AssetFileDescriptor newBackgroundAudio = checkPointsDefs[currentCheckPointIndex].getCustomBackground();
            if (newBackgroundAudio != null) {
                if (backgroundMediaPlayer.isPlaying()) {
                    backgroundMediaPlayer.stop();
                }
                backgroundMediaPlayer.reset();
                backgroundMediaPlayer.setLooping(true);
                currentBackgroundAudio = newBackgroundAudio;
                try {
                    backgroundMediaPlayer.setDataSource(currentBackgroundAudio.getFileDescriptor(), currentBackgroundAudio.getStartOffset(), currentBackgroundAudio.getLength());
                    backgroundMediaPlayer.prepare();
                    backgroundMediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (PAUSE_BACKGROUND_SOUND_IN_CHECKPOINT) {
                backgroundMediaPlayer.pause();
            }
            checkPointMediaPlayer.start();
        } else if (!checkPointFound) {
            currentCheckPointIndex = -1;
            String masCercano = "";
            if (closestCheckPointIndex != -1) {
                masCercano = " (Más Cercano: " + checkPointsDefs[closestCheckPointIndex].getLocation().getProvider() + " a " + String.format("%1$.0f", minDistance) + " mts.)";
            }
            MainActivity.tv_current_checkpoint.setText(R.string.boyando);
            MainActivity.tv_closer_checkpoint.setText(masCercano);
        }

    }
}
