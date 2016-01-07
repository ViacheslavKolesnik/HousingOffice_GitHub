package com.sigmaagile.housingoffice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Return extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);
    }
    public void onReturnStart(View view){
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }
}
