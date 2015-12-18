package com.mayer.lucas.draughtgame;

import android.app.Activity;
import android.location.GpsStatus;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.nio.ByteOrder;

/**
 * Created by lulz on 26/11/2015.
 */

public class MainActivity extends Activity {
    static Game game;
    static int size = 8;
    static TextView textViewTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        game = new Checkers();
        final Board board = (Board) findViewById(R.id.view);
        final Spinner spinnerSize = (Spinner) findViewById(R.id.spinnerSize);
        final Button buttonRandom = (Button) findViewById(R.id.button);
        textViewTurn = (TextView) findViewById(R.id.textViewTurn);

        ArrayAdapter<CharSequence> adapterSpinnerSize = ArrayAdapter.createFromResource(this, R.array.sizeBoard, android.R.layout.simple_spinner_item);
        adapterSpinnerSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSize.setAdapter(adapterSpinnerSize);

        spinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (spinnerSize.getSelectedItemPosition() == 0)
                    size = 8;
                if (spinnerSize.getSelectedItemPosition() == 1)
                    size = 6;
                if (spinnerSize.getSelectedItemPosition() == 2)
                    size = 10;
                if (spinnerSize.getSelectedItemPosition() == 3)
                    size = 12;
                if (spinnerSize.getSelectedItemPosition() == 4)
                    size = 14;
                if (spinnerSize.getSelectedItemPosition() == 5)
                    size = 16;
                if (spinnerSize.getSelectedItemPosition() == 6)
                    size = 18;
                if (spinnerSize.getSelectedItemPosition() == 7)
                    size = 20;
                Checkers.setCount(0);
                board.invalidate();
                game.start(size);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        buttonRandom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Checkers.getCount() == 0)
                    for (int i = 0; i < 3; i++) {
                        game.playRandom();
                        board.invalidate();
                    }
            }
        });
        game.start(size);
    }
}
