package br.com.segredosgo.segredosgo.activities;

import android.Manifest;
import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;

import br.com.segredosgo.segredosgo.R;
import br.com.segredosgo.segredosgo.models.Segredo;
import br.com.segredosgo.segredosgo.models.SegredoDAO;

public class NewSecret extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_secret);

        Button btnEnviar = (Button) findViewById(R.id.btnEnviarSegredo);
        Button btnGaleria = (Button) findViewById(R.id.btnGaleria);
        Button btnCamera = (Button) findViewById(R.id.btnCamera);

        final EditText txtTitulo = (EditText) findViewById(R.id.txtTituloSegredo);
        final EditText txtDescricao = (EditText) findViewById(R.id.txtDescricaoSegredo);


        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureSecretPhoto();
            }
        });
        btnEnviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.v("Log", "enviar segredo");

                LocationManager locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                SegredoDAO dao = new SegredoDAO(getApplicationContext());
                Segredo segredo = new Segredo();
                segredo.setTitulo(String.valueOf(txtTitulo.getText()));
                segredo.setDescricao(String.valueOf(txtDescricao.getText()));
                segredo.setImagem(captureSecretPhoto());
                segredo.setLatitude(loc.getLatitude());
                segredo.setLongitude(loc.getLongitude());

                dao.insere(segredo);
                dao.close();

                Log.v("titulo", String.valueOf(segredo.getTitulo()));
                Log.v("descricao", String.valueOf(segredo.getDescricao()));
                Log.v("lat", ""+segredo.getLatitude());
                Log.v("lon", ""+segredo.getLongitude());

                finish();

            }
        });
    }

    @NonNull
    private String captureSecretPhoto() {
        File tempFile = null;
        Uri uri = Uri.fromFile(tempFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 1341);
        try {
            tempFile = File.createTempFile("my_app", ".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile.getAbsolutePath();
    }
}


