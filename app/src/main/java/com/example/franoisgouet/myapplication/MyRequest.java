package com.example.franoisgouet.myapplication;

import android.app.IntentService;
import android.content.Intent;

/**
 * @author françois GOUET
 */
public class MyRequest extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyRequest(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }
}
