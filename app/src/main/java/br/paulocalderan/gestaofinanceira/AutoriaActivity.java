package br.paulocalderan.gestaofinanceira;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AutoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoria);
    }

    public static void autoria(AppCompatActivity activity) {
        Intent intent = new Intent(activity, AutoriaActivity.class);
        activity.startActivity(intent);
    }
}