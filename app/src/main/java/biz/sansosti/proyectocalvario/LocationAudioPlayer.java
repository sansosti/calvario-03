package biz.sansosti.proyectocalvario;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.location.Location;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

import static biz.sansosti.proyectocalvario.Constants.DEFAULT_BACKGROUND_SOUND;
import static biz.sansosti.proyectocalvario.Constants.PAUSE_BACKGROUND_SOUND_IN_CHECKPOINT;

/**
 * Created by sergio on 23/05/2017.
 */
// Todo: extending AppCompatActivity crashes the app?
public class LocationAudioPlayer extends AppCompatActivity {

    // CheckPoints
    private CheckPoint[] checkPointsDefs;
    private int currentCheckPointIndex = -1;

    // Audio
    private MediaPlayer checkPointMediaPlayer;
    private MediaPlayer backgroundMediaPlayer;
    private String currentBackgroundAudioFileName = "";
    private String currentAudioFileName = "";

    // Location-related
    boolean locationUpdatesEnabled;

    public LocationAudioPlayer() {
        locationUpdatesEnabled = false;

        // Init checkPointMediaPlayer
        checkPointMediaPlayer = new MediaPlayer();
        checkPointMediaPlayer.setLooping(false);
        checkPointMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                locationUpdatesEnabled = true;
                if (PAUSE_BACKGROUND_SOUND_IN_CHECKPOINT) {
                    backgroundMediaPlayer.start();
                }
            }
        });
        // Init backgroundMediaPlayer
        currentBackgroundAudioFileName = DEFAULT_BACKGROUND_SOUND;
        backgroundMediaPlayer = new MediaPlayer();
        backgroundMediaPlayer.setLooping(true);
        loadMediaPlayer(backgroundMediaPlayer,currentAudioFileName);

        populateCheckPoints();

        locationUpdatesEnabled = true;
    }

    // Todo: I either need the caller's Context or need to load Assets without it (getResourceAsStream)
    // Todo: getAssets() crashes the app?
    public void loadMediaPlayer(MediaPlayer aPlayer,String SoundFileName) {
/*
        AssetFileDescriptor afd = null;
        try {
            afd = getAssets().openFd(SoundFileName);
            aPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            aPlayer.prepare();
            aPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
    }

    protected void populateCheckPoints(){
        checkPointsDefs = new CheckPoint[8];

        /*
        checkPointsDefs[0] = new CheckPoint("Oficina","-37.333103","-59.135204","voz_001.m4a");
        checkPointsDefs[1] = new CheckPoint("Facultad de Arte","-37.328081","-59.133929","fondo.mp3");
        checkPointsDefs[2] = new CheckPoint("Entrada Calvario","-37.3278462","-59.1523761","Lectura_poesias_parte_A_1.mp3");
        checkPointsDefs[3] = new CheckPoint("Cartel Arbol Silencio","-37.3279662","-59.1524336","Lectura_poesias_parte_B_1.mp3");
        checkPointsDefs[4] = new CheckPoint("Banios Caballeros","-37.3283066","-59.1524416","fondo.mp3");
        checkPointsDefs[5] = new CheckPoint("Capilla","-37.3288903","-59.152674","pipo.mp3");
        checkPointsDefs[6] = new CheckPoint("Arbol Gruta","-37.3291856","-59.152237","voz_001.m4a");
        checkPointsDefs[7] = new CheckPoint("Arbol Rampa","-37.3294647","-59.1525043","voz_002.m4a");
        checkPointsDefs[8] = new CheckPoint("Bifurcacion","-37.3291355","-59.1531591","Lectura_poesias_parte_A_1.mp3");
        checkPointsDefs[9] = new CheckPoint("Altar Escalinata","-37.3282713","-59.153429","Lectura_poesias_parte_B_1.mp3");
        checkPointsDefs[10] = new CheckPoint("Altar Cruz","-37.3287431","-59.154859","fondo.mp3");
        checkPointsDefs[11] = new CheckPoint("Azcuenaga y Juncal","-37.33483","-59.13166","Esquina_peron_y_uriburu_2.mp3");
        checkPointsDefs[12] = new CheckPoint("Lo de Paula","-37.33467","-59.13101","Lectura_poesias_parte_A_1.mp3");
        */

        // Todo: use sound resources instead of assets
        int cp = 0;
        //checkPointsDefs[i++] = new CheckPoint("01","-37.329463" ,"-59.150608"  ,"puesto01.mp3",null);
        checkPointsDefs[cp++] = new CheckPoint("02","-37.3294242","-59.1508588" ,"puesto02.mp3","fondo_pajaritos_estereo.mp3");
        checkPointsDefs[cp++] = new CheckPoint("03","-37.329595" ,"-59.1511569" ,"puesto03.mp3",null);
        checkPointsDefs[cp++] = new CheckPoint("04","-37.3295102","-59.1518422" ,"puesto04.mp3",null); // sin pausa
        checkPointsDefs[cp++] = new CheckPoint("05","-37.3295676","-59.152393"  ,"puesto05.mp3","fondo_pajaritos_con_aphex.mp3"); // sin pausa (usar el nuevo background)
        checkPointsDefs[cp++] = new CheckPoint("06","-37.3293892","-59.1530269" ,"puesto06.mp3",null);
        checkPointsDefs[cp++] = new CheckPoint("07","-37.3297072","-59.1534959" ,"puesto07.mp3",null);
        checkPointsDefs[cp++] = new CheckPoint("08","-37.3291582","-59.1533802" ,"puesto08.mp3",null);
        checkPointsDefs[cp++] = new CheckPoint("09","-37.3288975","-59.1529821" ,"puesto09.mp3",null);

        /*
        mCheckPoints = new TextView[checkPointsDefs.length];

        for (int i = 0; i < checkPointsDefs.length; i++) {
                 mCheckPoints[i] = new TextView(this);
            mCheckPoints[i].setId(i);
            mCheckPoints[i].setText(checkPointsDefs[i].getName());
            int checkPointStyle = R.style.TextAppearance_AppCompat_Inverse;
            if (Build.VERSION.SDK_INT >= 23) {
                mCheckPoints[i].setTextAppearance(checkPointStyle);
            } else {
                mCheckPoints[i].setTextAppearance(this, checkPointStyle);
            }

            TableRow parentRow = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            parentRow.setLayoutParams(lp);
            parentRow.addView(mCheckPoints[i]);

            TableLayout parentTable = (TableLayout) findViewById(R.id.tl_debug);
            parentTable.addView(parentRow,i);
        }
        */
    }

    public void onLocationChanged(Location location) {
        MainActivity.tv_main_text.setText(location.getLatitude() + " - " + location.getLongitude());
    }
}
