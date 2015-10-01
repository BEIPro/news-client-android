import android.util.Log;

/**
 * Created by songsubei on 29/09/15.
 */
public abstract class LogDebug {
    void parantLog(boolean DBG, String Tag, String logMsg)
    {
        Log.d(Tag, logMsg);
    }

    abstract void log(boolean DBG, String Tag, String logMsg);
}
