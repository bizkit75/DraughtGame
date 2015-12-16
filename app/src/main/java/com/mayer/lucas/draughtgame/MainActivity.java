package com.mayer.lucas.draughtgame;

import android.app.Activity;
import android.os.Bundle;

import java.nio.ByteOrder;

/**
 * Created by lulz on 26/11/2015.
 */

public class MainActivity extends Activity {
    static Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        game = new Checkers();
        game.start(8);
    }
}
