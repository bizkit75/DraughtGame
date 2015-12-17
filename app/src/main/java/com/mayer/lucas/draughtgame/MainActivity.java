package com.mayer.lucas.draughtgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.nio.ByteOrder;

/**
 * Created by lulz on 26/11/2015.
 */

public class MainActivity extends Activity {
    static Game game;

    static int size = 8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        game = new Checkers();
        final Board board = new Board(getBaseContext());
        final Spinner spinnerSize = (Spinner)findViewById(R.id.spinnerSize);
        Spinner spinnerShot = (Spinner)findViewById(R.id.spinnerShot);

        ArrayAdapter<CharSequence> adapterSpinnerSize = ArrayAdapter.createFromResource(this, R.array.sizeBoard, android.R.layout.simple_spinner_item);
        adapterSpinnerSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSize.setAdapter(adapterSpinnerSize);

        ArrayAdapter<CharSequence> adapterSpinnerShot = ArrayAdapter.createFromResource(this, R.array.randomShot, android.R.layout.simple_spinner_item);
        adapterSpinnerShot.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShot.setAdapter(adapterSpinnerShot);

        spinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Your code here
                if (spinnerSize.getSelectedItemPosition() == 1)
                    size = 6;
                if (spinnerSize.getSelectedItemPosition() == 2)
                    size = 8;
                if (spinnerSize.getSelectedItemPosition() == 3)
                    size = 10;
                if (spinnerSize.getSelectedItemPosition() == 4)
                    size = 12;
                if (spinnerSize.getSelectedItemPosition() == 5)
                    size = 14;
                if (spinnerSize.getSelectedItemPosition() == 6)
                    size = 16;
                if (spinnerSize.getSelectedItemPosition() == 7)
                    size = 18;
                if (spinnerSize.getSelectedItemPosition() == 8)
                    size = 20;

                game = new Checkers();
                game.start(size);
               System.out.println("hehe");
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        game.start(size);
    }
}
