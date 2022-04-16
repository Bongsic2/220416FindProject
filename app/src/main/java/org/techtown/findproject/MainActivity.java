package org.techtown.findproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    Button cButton;
    Button pButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cButton = findViewById(R.id.cButton);
        cButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showParents(ChildActivity.class);
            }
        });
        pButton = findViewById(R.id.pButton);
        pButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChild(ParentsActivity.class);
            }
        });

    }

    private void showParents(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void showChild(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}






