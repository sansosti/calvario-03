package biz.sansosti.proyectocalvario;

/**
 * Created by sergio on 23/05/2017.
 */

public class Constants {
    private static final Constants ourInstance = new Constants();

    public static Constants getInstance() {
        return ourInstance;
    }

    private Constants() {
    }

    public static final String DEFAULT_BACKGROUND_SOUND = "fondo_inicio.mp3";
    public static final boolean PAUSE_BACKGROUND_SOUND_IN_CHECKPOINT = true;
}
