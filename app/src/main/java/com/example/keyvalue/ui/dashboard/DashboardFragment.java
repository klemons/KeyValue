package com.example.keyvalue.ui.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.keyvalue.R;

import java.util.regex.Matcher;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private boolean isTimerRunning;
    private CountDownTimer countDownTimer;
    public TextView textTime;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        textTime = root.findViewById(R.id.textTime);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button button = root.findViewById(R.id.mash_button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning) {

                }
                else {
                    startTimer();
                }
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                int currentScore = getResources().getInteger(R.integer.current_Score);
                int timesPressed = sharedPref.getInt(getString(R.string.saved_button_press_count_key), currentScore);

                int newTimesPressed = timesPressed + 1;

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(getString(R.string.saved_button_press_count_key), newTimesPressed);
                editor.commit();

                textView.setText("Button has been pressed " + Integer.toString(newTimesPressed) + " times!");
            }
        });

        return root;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                textTime.setText("Finished!");
            }
        }.start();

        isTimerRunning = true;
    }
}