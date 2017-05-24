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

    public static final boolean DEBUG = false;
    public static final boolean PAUSE_BACKGROUND_SOUND_IN_CHECKPOINT = true;
    public static final int DISTANCE_THRESHOLD = 20;
    public static final int MAX_CHECKPOINTS_DISPLAY_LIST = 20;
}
