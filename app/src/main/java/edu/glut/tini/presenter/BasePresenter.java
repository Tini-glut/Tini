package edu.glut.tini.presenter;

import android.os.Handler;
import android.os.Looper;

public interface BasePresenter {
    Handler handler = new android.os.Handler(Looper.getMainLooper());

    default void uiThread(Runnable runnable) {
        handler.post(runnable);
    }
}
