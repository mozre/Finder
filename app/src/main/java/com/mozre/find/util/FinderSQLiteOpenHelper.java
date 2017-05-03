package com.mozre.find.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by MOZRE on 2016/6/29.
 */
public class FinderSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "FinderSQLiteOpenHelper";
    private static final String DB_NAME = "finder_info.db";
    private static final int VERSION_CODE = 2;
    public static final String TB_ARTICLE_DATA = "tab_article";
    public static final String TB_FILM_DATA = "tab_film";
    public static final String TB_MINE_DATA = "tab_mine";

    public FinderSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION_CODE);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        String initaritclesql = "CREATE TABLE " + TB_ARTICLE_DATA + " (" + " id  INTEGER PRIMARY KEY AUTOINCREMENT,seconds varchar(20),username varchar(64),posttime varchar(40),iconaddress varchar(64),title varchar(128),description VARCHAR(64),detail VARCHAR(20000),image varchar(64),review INTEGER,forward INTEGER,flower INTEGER)";
        String articleSql = "CREATE TABLE " + TB_ARTICLE_DATA +
                "( id  INTEGER PRIMARY KEY AUTOINCREMENT,seconds varchar(20),username varchar(64),posttime varchar(40),iconaddress varchar(64),title varchar(128),description VARCHAR(64),detail VARCHAR(20000),image varchar(64),review INTEGER,forward INTEGER,flower INTEGER)";
        String initFilmSql = "CREATE TABLE " + TB_FILM_DATA +
                "( id  INTEGER PRIMARY KEY AUTOINCREMENT,seconds varchar(20),username varchar(64),posttime varchar(40),iconaddress varchar(64),title varchar(128),description VARCHAR(64),detail VARCHAR(20000),image varchar(64),review INTEGER,forward INTEGER,flower INTEGER)";
        String removeFlagSQL = "DROP TABLE IF EXISTS flags";
        String createFlagSql = "CREATE TABLE flags (username varchar(64)  PRIMARY KEY,articleflag VARCHAR(20),filmflag VARCHAR(20),musicflag VARCHAR(20),hot flag VARCHAR(20))";
        String mineSql = "CREATE TABLE " + TB_MINE_DATA +
                "( id  INTEGER PRIMARY KEY AUTOINCREMENT,seconds varchar(20),username varchar(64),posttime varchar(40),iconaddress varchar(64),title varchar(128),description VARCHAR(64),detail VARCHAR(20000),image varchar(64),review INTEGER,forward INTEGER,flower INTEGER)";
        String removeArticle = "DROP TABLE IF EXISTS " + TB_ARTICLE_DATA;
        String removeFilm = "DROP TABLE IF EXISTS " + TB_FILM_DATA;
        String removeMine = "DROP TABLE IF EXISTS " + TB_MINE_DATA;
        sqLiteDatabase.execSQL(removeArticle);
        sqLiteDatabase.execSQL(removeFilm);
        sqLiteDatabase.execSQL(removeFlagSQL);
        sqLiteDatabase.execSQL(articleSql);
        sqLiteDatabase.execSQL(initFilmSql);
        sqLiteDatabase.execSQL(createFlagSql);
        sqLiteDatabase.execSQL(removeMine);
        sqLiteDatabase.execSQL(mineSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }
}
