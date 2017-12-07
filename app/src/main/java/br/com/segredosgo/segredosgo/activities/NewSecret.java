package br.com.segredosgo.segredosgo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import br.com.segredosgo.segredosgo.R;

public class NewSecret extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_secret);

        Button btnEnviar = (Button) findViewById(R.id.btnEnviarSegredo);
        btnEnviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.v("Log", "enviar segredo");
            }
        });
    }
}


