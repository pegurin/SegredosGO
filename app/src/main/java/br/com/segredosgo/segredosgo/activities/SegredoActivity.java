package br.com.segredosgo.segredosgo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;

import br.com.segredosgo.segredosgo.R;
import br.com.segredosgo.segredosgo.models.Segredo;
import br.com.segredosgo.segredosgo.models.SegredoDAO;

public class SegredoActivity extends AppCompatActivity {

    private Segredo segredo = new Segredo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segredo);

        ImageView imageView = (ImageView) findViewById(R.id.imgSegredoMarker);
        TextView txtTitulo = (TextView) findViewById(R.id.txtTituloMarker);
        TextView txtDescricao = (TextView) findViewById(R.id.txtDescricaoMarker);

        SegredoDAO dao = new SegredoDAO(getApplicationContext());
        segredo = dao.buscaID(getIntent().getLongExtra("id",-1));

        txtTitulo.setText(segredo.getTitulo());
        txtDescricao.setText(segredo.getDescricao());
    }
}
