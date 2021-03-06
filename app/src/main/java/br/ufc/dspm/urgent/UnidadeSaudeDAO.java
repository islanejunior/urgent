package br.ufc.dspm.urgent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UnidadeSaudeDAO extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UnidadesSaudev2.db";
    public static final int DATABASE_VERSION = 1;

    public UnidadeSaudeDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public UnidadeSaudeDAO(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder sql = new StringBuilder();

        sql.append("create table unidades_saude (");
        sql.append("id integer primary key autoincrement,");
        sql.append("latitude double,");
        sql.append("longitude double,");
        sql.append("nome text,");
        sql.append("endereco text,");
        sql.append("telefone text,");
        sql.append("tipo text)");

        db.execSQL(sql.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists unidades_saude");
        onCreate(db);

    }

    public void adicionarUnidadeSaude(UnidadeSaude unidadeSaude) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("latitude", unidadeSaude.getLatitude());
        contentValues.put("longitude", unidadeSaude.getLongitude());
        contentValues.put("nome", unidadeSaude.getNome());
        contentValues.put("endereco", unidadeSaude.getEndereco());
        contentValues.put("telefone", unidadeSaude.getTelefone());
        contentValues.put("tipo", unidadeSaude.getTipo());

        db.insert("unidades_saude", null, contentValues);

    }

    public ArrayList<UnidadeSaude> listUnidadeDeSaude() {

        ArrayList<UnidadeSaude> unidadeSaudeArrayList = new ArrayList<UnidadeSaude>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor result = db.rawQuery("select * from unidades_saude",
                null);

        if (result != null && result.getCount() > 0) {

            result.moveToFirst();

            while (result.isAfterLast() == false) {

                UnidadeSaude unidade = new PostoDeSaude(result.getDouble(1), result.getDouble(2));
                unidade.setNome(result.getString(3));
                unidade.setEndereco(result.getString(4));
                unidade.setTelefone(result.getString(5));
                unidadeSaudeArrayList.add(unidade);

                result.moveToNext();

            }

        }

        return unidadeSaudeArrayList;

    }

    public ArrayList<PostoDeSaude> listPostosDeSaude() {

        ArrayList<PostoDeSaude> postoDeSaudeList = new ArrayList<PostoDeSaude>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor result = db.rawQuery("select * from unidades_saude where tipo = 'posto de saude'",
                null);

        if (result != null && result.getCount() > 0) {

            result.moveToFirst();

            while (result.isAfterLast() == false) {

                PostoDeSaude posto = new PostoDeSaude(result.getDouble(1), result.getDouble(2));
                posto.setNome(result.getString(3));
                posto.setEndereco(result.getString(4));
                posto.setTelefone(result.getString(5));
                postoDeSaudeList.add(posto);

                result.moveToNext();

            }

        }

        return postoDeSaudeList;

    }

    public List<UPA> listUpas() {

        List<UPA> upas = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor result = db.rawQuery("select * from unidades_saude where tipo = 'upa'",
                null);

        if (result != null && result.getCount() > 0) {

            upas = new LinkedList<>();
            result.moveToFirst();

            while (result.isAfterLast() == false) {

                UPA upa = new UPA(result.getDouble(1), result.getDouble(2));
                upas.add(upa);

                result.moveToNext();

            }

        }

        return upas;

    }

}