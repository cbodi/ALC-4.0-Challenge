package net.zealtechconsulting.alc4phase1challenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
private Button mButton;
private Button mButton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.button_about_alc);
        mButton2 = (Button) findViewById(R.id.button_my_profile);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAboutALC();
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyProfile();
            }
        });
    }

    private void openMyProfile() {
        Intent intent = new Intent(this, MyProfile.class);
        startActivity(intent);
    }

    private void openAboutALC() {
        Intent intent = new Intent(this, AboutALC.class);
        startActivity(intent);
    }
}
