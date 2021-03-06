package br.com.segredosgo.segredosgo.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pegurin on 10/12/17.
 */

public class SegredoDAO extends SQLiteOpenHelper {

    public SegredoDAO(Context context){
        super (context, "Segredo", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Segredo (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT NOT NULL, descricao TEXT, imagem TEXT, latitude INTEGER NOT NULL, longitude INTEGER NOT NULL, like INTEGER, deslike INTEGER);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Segredo";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Segredo segredo){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put("titulo",segredo.getTitulo());
        dados.put("descricao",segredo.getDescricao());
        dados.put("imagem",segredo.getImagem());
        dados.put("latitude",segredo.getLatitude());
        dados.put("longitude",segredo.getLongitude());
        dados.put("like", segredo.getLike());
        dados.put("deslike", segredo.getDeslike());

        db.insert("Segredo", null, dados);
    }

    public void deleta(Segredo segredo) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {segredo.getId().toString()};
        db.delete("Segredo", "id = ?", params);
    }

    public List<Segredo> buscaSegredos(){
        String sql = "SELECT * FROM Segredo;";
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(sql, null);

        List<Segredo> segredos = new ArrayList<Segredo>();
        while (c.moveToNext()){
            Segredo segredo = new Segredo();

            segredo.setId(c.getLong(c.getColumnIndex("id")));
            segredo.setTitulo(c.getString(c.getColumnIndex("titulo")));
            segredo.setDescricao(c.getString(c.getColumnIndex("descricao")));
            segredo.setImagem(c.getString(c.getColumnIndex("imagem")));
            segredo.setLatitude(c.getDouble(c.getColumnIndex("latitude")));
            segredo.setLongitude(c.getDouble(c.getColumnIndex("longitude")));
            segredo.setLike(c.getInt(c.getColumnIndex("like")));
            segredo.setDeslike(c.getInt(c.getColumnIndex("deslike")));

            segredos.add(segredo);
        }
        c.close();

        return segredos;
    }

    public Segredo buscaID(Long id){
        String sql = "SELECT * FROM Segredo;";
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(sql, null);

        Segredo segredo = new Segredo();
        while (c.moveToNext()){

            if (c.getLong(c.getColumnIndex("id")) == id) {

                segredo.setId(c.getLong(c.getColumnIndex("id")));
                segredo.setTitulo(c.getString(c.getColumnIndex("titulo")));
                segredo.setDescricao(c.getString(c.getColumnIndex("descricao")));
                segredo.setImagem(c.getString(c.getColumnIndex("imagem")));
                segredo.setLatitude(c.getDouble(c.getColumnIndex("latitude")));
                segredo.setLongitude(c.getDouble(c.getColumnIndex("longitude")));
                segredo.setLike(c.getInt(c.getColumnIndex("like")));
                segredo.setDeslike(c.getInt(c.getColumnIndex("deslike")));
            }

        }
        c.close();

        return segredo;
    }

    public Segredo buscaTitulo(String titulo){
        String sql = "SELECT * FROM Segredo;";
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(sql, null);

        Segredo segredo = new Segredo();
        while (c.moveToNext()){

            if (c.getString(c.getColumnIndex("titulo")).equals(titulo)) {

                segredo.setId(c.getLong(c.getColumnIndex("id")));
                segredo.setTitulo(c.getString(c.getColumnIndex("titulo")));
                segredo.setDescricao(c.getString(c.getColumnIndex("descricao")));
                segredo.setImagem(c.getString(c.getColumnIndex("imagem")));
                segredo.setLatitude(c.getDouble(c.getColumnIndex("latitude")));
                segredo.setLongitude(c.getDouble(c.getColumnIndex("longitude")));
                segredo.setLike(c.getInt(c.getColumnIndex("like")));
                segredo.setDeslike(c.getInt(c.getColumnIndex("deslike")));
            }

        }
        c.close();

        return segredo;
    }

    public void curtirSegredo(Long id){
        String sql = "SELECT * FROM Segredo;";
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(sql, null);

        Segredo segredo = new Segredo();
        while (c.moveToNext()){

            if (c.getLong(c.getColumnIndex("id")) == id) {
                ContentValues data=new ContentValues();
                data.put("like", c.getInt(c.getColumnIndex("like")) + 1);
                db.update("Segredo", data,"id = "+id,null);
            }

        }
        c.close();
    }

    public void denunciarSegredo(Long id){
        String sql = "SELECT * FROM Segredo;";
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(sql, null);

        Segredo segredo = new Segredo();
        while (c.moveToNext()){

            if (c.getLong(c.getColumnIndex("id")) == id) {
                ContentValues data=new ContentValues();
                data.put("deslike", c.getInt(c.getColumnIndex("deslike")) + 1);
                db.update("Segredo", data,"id = "+id,null);
            }

        }
        c.close();
    }

}
