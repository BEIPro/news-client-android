package com.song.normalclient.Debug;

import android.util.Log;

/**
 * Created by songsubei on 29/09/15.
 */
public abstract class LogDebug {
    public void parantLog(boolean DBG, String Tag, String logMsg)
    {
        Log.d(Tag, logMsg);
    }

    public abstract void log(boolean DBG, String Tag, String logMsg);
}
