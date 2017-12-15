package com.example.flo.gestionduparcvert.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.flo.gestionduparcvert.database.Probleme;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by flo on 25/11/2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    public static final String DATABASE_NAME="base.db";
    private Dao<Probleme,Integer> problemeDao;
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource,Probleme.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource,Probleme.class,true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Dao<Probleme,Integer> getProblemeDao() throws SQLException{
        if (problemeDao== null){
            problemeDao = getDao(Probleme.class);
        }
        return problemeDao;
    }
}
