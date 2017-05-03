package com.mozre.find.module.middle;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by MOZRE on 2016/6/21.
 */
public class UpdateUserIcon extends AppCompatActivity{

    private static final String TAG = "UpdateUserIcon";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {

        super.onCreate(savedInstanceState, persistentState);
        Log.d(TAG, "onCreate: here");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }
}
