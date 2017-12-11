package br.com.segredosgo.segredosgo.activities;

import android.Manifest;
import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import br.com.segredosgo.segredosgo.R;
import br.com.segredosgo.segredosgo.models.Segredo;
import br.com.segredosgo.segredosgo.models.SegredoDAO;

public class NewSecret extends AppCompatActivity {

    private ImageView imageView;
    private String picturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_secret);

        Button btnEnviar = (Button) findViewById(R.id.btnEnviarSegredo);
        Button btnGaleria = (Button) findViewById(R.id.btnGaleria);
        Button btnCamera = (Button) findViewById(R.id.btnCamera);

        imageView = (ImageView) findViewById(R.id.imgSegredo);

        final EditText txtTitulo = (EditText) findViewById(R.id.txtTituloSegredo);
        final EditText txtDescricao = (EditText) findViewById(R.id.txtDescricaoSegredo);


        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, 1);
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
                segredo.setLatitude(loc.getLatitude());
                segredo.setLongitude(loc.getLongitude());
                segredo.setImagem(picturePath);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1){
            Uri selectedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            picturePath = c.getString(c.getColumnIndex(filePath[0]));
            c.close();

            Log.v("Log","path="+picturePath);
            Bitmap imagemGaleria = BitmapFactory.decodeFile(picturePath);
            Log.v("Log", ""+imagemGaleria);
            imageView.setImageBitmap(imagemGaleria);
        }
    }
}


