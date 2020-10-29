package com.sevketbuyukdemir.colorseekbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telecom.TelecomManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView colorSeekBarTextView;
    ColorSeekBar colorSeekBar;
    TextView imageViewTextView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorSeekBarTextView = findViewById(R.id.colorSeekBarTextView);
        colorSeekBar = findViewById(R.id.colorSeekBar);
        imageViewTextView = findViewById(R.id.imageViewTextView);
        imageView = findViewById(R.id.imageView);

        colorSeekBarTextView.setText("Color Seek Bar: ");
        imageViewTextView.setText("Your color: ");

        colorSeekBar.setOnColorSeekBarTouchListener(new ColorSeekBar.OnColorSeekBarTouchListener() {
            @Override
            public void onStartTrackingTouch(ColorSeekBar colorSeekBar, int WHICH_COLOR) {
                imageView.setBackgroundColor(WHICH_COLOR);
                colorSeekBarTextView.setTextColor(WHICH_COLOR);
                imageViewTextView.setTextColor(WHICH_COLOR);

            }

            @Override
            public void onMoveTrackingTouch(ColorSeekBar colorSeekBar, int WHICH_COLOR) {
                imageView.setBackgroundColor(WHICH_COLOR);
                colorSeekBarTextView.setTextColor(WHICH_COLOR);
                imageViewTextView.setTextColor(WHICH_COLOR);
            }

            @Override
            public void onStopTrackingTouch(ColorSeekBar colorSeekBar, int WHICH_COLOR) {
                imageView.setBackgroundColor(WHICH_COLOR);
                colorSeekBarTextView.setTextColor(WHICH_COLOR);
                imageViewTextView.setTextColor(WHICH_COLOR);
            }
        });
    }
}