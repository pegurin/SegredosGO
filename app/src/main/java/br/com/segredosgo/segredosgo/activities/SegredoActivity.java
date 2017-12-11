package br.com.segredosgo.segredosgo.activities;


import android.content.DialogInterface;

import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

        final SegredoDAO dao = new SegredoDAO(getApplicationContext());
        segredo = dao.buscaID(getIntent().getLongExtra("id",-1));

        Log.v("Log", ""+segredo.getImagem());
        imageView.setImageBitmap(BitmapFactory.decodeFile(segredo.getImagem()));
        txtTitulo.setText(segredo.getTitulo());
        txtDescricao.setText(segredo.getDescricao());

        Button btnLike = (Button) findViewById(R.id.btnLike);
        Button btnDeslike = (Button) findViewById(R.id.btnDeslike);

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.curtirSegredo(segredo.getId());
                segredo = dao.buscaID(getIntent().getLongExtra("id",-1));
                Log.v("like", ""+segredo.getLike());
            }
        });

        btnDeslike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (segredo.getDeslike()>=2) {

                    new AlertDialog.Builder(v.getContext())
                        .setTitle("Aviso!")
                        .setMessage("Este segredo será apagado caso confirme.")
                        .setPositiveButton("Excluir",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dao.deleta(segredo);
                                    Log.v("Log", "Segredo deletado");
                                    finish();
                                }
                            })
                        .setNegativeButton("Cancelar", null)
                        .show();

                }else {
                    dao.denunciarSegredo(segredo.getId());
                    segredo = dao.buscaID(getIntent().getLongExtra("id",-1));
                }

            }
        });
    }
}
