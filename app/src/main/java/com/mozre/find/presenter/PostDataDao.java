package com.mozre.find.presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mozre.find.domain.PostArticleData;
import com.mozre.find.domain.User;
import com.mozre.find.util.FinderSQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MOZRE on 2016/6/29.
 */
public class PostDataDao {

    private FinderSQLiteOpenHelper sqLiteOpenHelper;
    private final static String insertArticleData = "INSERT INTO " + FinderSQLiteOpenHelper.TB_ARTICLE_DATA + " (seconds,username,posttime,iconaddress,title,description,detail,image,forward,review,flower)VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private final static String selectArticleData = "SELECT * FROM tab_article WHERE seconds>?";
    private final static String initFlag = "INSERT INTO flags (username,articleflag,filmflag,musicflag,hot) VALUES(?,0,0,0,0)";
    private final static String updateArticleFlag = "update flags set articleflag=? where username=?";
    private final static String selectArticleFlag = "SELECT * FROM flags WHERE username=?";
    private final static String selectMoreArticleData = "SELECT * FROM " + FinderSQLiteOpenHelper.TB_ARTICLE_DATA + " WHERE seconds<?";
    private final static String selectMoreArticleData_t = "SELECT * FROM " + FinderSQLiteOpenHelper.TB_ARTICLE_DATA + " WHERE seconds<? GROUP BY seconds LIMIT 5";
    private final static String insertMineData = "INSERT INTO " + FinderSQLiteOpenHelper.TB_MINE_DATA + " (seconds,username,posttime,iconaddress,title,description,detail,image,forward,review,flower)VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private final static String delMineTab = "delete from " + FinderSQLiteOpenHelper.TB_MINE_DATA;
    private final static String selectMineTab = "select * from sqlite_sequence";
    private final static String updateMineTab = "update sqlite_sequence set seq=0 where name=" + FinderSQLiteOpenHelper.TB_MINE_DATA;
    private final static String del_tab = "DROP TABLE IF EXISTS " + FinderSQLiteOpenHelper.TB_MINE_DATA;
    private final static String create_tab = "CREATE TABLE " + FinderSQLiteOpenHelper.TB_MINE_DATA +
            "( id  INTEGER PRIMARY KEY AUTOINCREMENT,seconds varchar(20),username varchar(64),posttime varchar(40),iconaddress varchar(64),title varchar(128),description VARCHAR(64),detail VARCHAR(20000),image varchar(64),review INTEGER,forward INTEGER,flower INTEGER)";
    private final static String selectMineTabData = "SELECT * FROM " + FinderSQLiteOpenHelper.TB_MINE_DATA + " GROUP BY seconds";

    public PostDataDao(Context mContext) {
        sqLiteOpenHelper = new FinderSQLiteOpenHelper(mContext);
    }

    public void insertArticleData(List<PostArticleData> mDatas) throws Exception {
//seconds,username,posttime,iconaddress,title,description,detail,image,review,forward,flower
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        try {
            for (PostArticleData data : mDatas) {
                sqLiteDatabase.execSQL(insertArticleData,
                        new Object[]{data.getSeconds(), data.getUsername(),
                                data.getPostTime(), data.getImageUri(), data.getTitle(),
                                data.getDescription(), data.getDetail(), data.getImage(),
                                data.getReview(), data.getForward(), data.getFlower()});
            }
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }

    }

    public List<PostArticleData> selectArticleData(long seconds) throws Exception {

        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        List<PostArticleData> mDatas = null;
        Cursor cursor = null;
        PostArticleData data = null;

        try {
            cursor = sqLiteDatabase.rawQuery(selectArticleData, new String[]{String.valueOf(seconds)});
            if (cursor.getCount() > 0) {
                mDatas = new ArrayList<>();
            }
            while (cursor.moveToNext()) {
                data = new PostArticleData();
// CREATE TABLE tab_article( id  INTEGER PRIMARY KEY AUTOINCREMENT,seconds varchar(20),
// username varchar(64),posttime varchar(40),iconaddress varchar(64),title varchar(128),
// description VARCHAR(64),detail VARCHAR(20000),image varchar(64),review INTEGER,forward INTEGER,flower INTEGER)
                data.setImageUri(cursor.getString(cursor.getColumnIndex("iconaddress")));
                data.setSeconds(cursor.getLong(cursor.getColumnIndex("seconds")));
                data.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                data.setPostTime(cursor.getString(cursor.getColumnIndex("posttime")));
                data.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                data.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                data.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
                data.setImage(cursor.getString(cursor.getColumnIndex("image")));
                data.setReview(cursor.getInt(cursor.getColumnIndex("review")));
                data.setForward(cursor.getInt(cursor.getColumnIndex("forward")));
                data.setFlower(cursor.getInt(cursor.getColumnIndex("flower")));
                mDatas.add(data);
            }
        } finally {

        }
        return mDatas;
    }

    public void initFlags() throws Exception {
        User user = new User();
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
            sqLiteDatabase.execSQL(initFlag, new Object[]{user.getUserName()});
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
    }

    public void updateArticleFlags(long seconds) throws Exception {
        SQLiteDatabase sqLiteDatabase = null;
        User user = new User();
        try {
            sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
            sqLiteDatabase.execSQL(updateArticleFlag, new Object[]{seconds, user.getUserName()});
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }

    }

    public long selectArticleFlags() throws Exception {
        SQLiteDatabase sqLiteDatabase = null;
        User user = new User();
        Cursor cursor = null;
        long seconds = 0;
        try {
            sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
            cursor = sqLiteDatabase.rawQuery(selectArticleFlag, new String[]{user.getUserName()});
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    seconds = cursor.getLong(cursor.getColumnIndex("articleflag"));
                }
            }
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
        return seconds;
    }

    public List<PostArticleData> selectMoreArticleData(Long seconds) throws Exception {
        List<PostArticleData> mDatas = null;
        PostArticleData data = null;
        Cursor cursor = null;
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        try {
            cursor = sqLiteDatabase.rawQuery(selectMoreArticleData_t, new String[]{String.valueOf(seconds)});
//            sqLiteDatabase.query("tab_article", null, "seconds", new String[]{String.valueOf(seconds)}, "seconds", null, "seconds");
            if (cursor == null || cursor.getCount() < 1) {
                return mDatas;
            }
            mDatas = new ArrayList<>();
            while (cursor.moveToNext()) {
                data = new PostArticleData();
                data.setImageUri(cursor.getString(cursor.getColumnIndex("iconaddress")));
                data.setSeconds(cursor.getLong(cursor.getColumnIndex("seconds")));
                data.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                data.setPostTime(cursor.getString(cursor.getColumnIndex("posttime")));
                data.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                data.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                data.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
                data.setImage(cursor.getString(cursor.getColumnIndex("image")));
                data.setReview(cursor.getInt(cursor.getColumnIndex("review")));
                data.setForward(cursor.getInt(cursor.getColumnIndex("forward")));
                data.setFlower(cursor.getInt(cursor.getColumnIndex("flower")));
                mDatas.add(data);
            }
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }


        return mDatas;
    }

    public void insertMineData(List<PostArticleData> mDatas) throws Exception {
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        try {


            sqLiteDatabase.execSQL(del_tab);
            sqLiteDatabase.execSQL(create_tab);
//            sqLiteDatabase.execSQL(delMineTab);
//            sqLiteDatabase.execSQL(selectMineTab);
//            sqLiteDatabase.execSQL(updateMineTab);
            for (PostArticleData data : mDatas) {
                sqLiteDatabase.execSQL(insertMineData,
                        new Object[]{data.getSeconds(), data.getUsername(),
                                data.getPostTime(), data.getImageUri(), data.getTitle(),
                                data.getDescription(), data.getDetail(), data.getImage(),
                                data.getReview(), data.getForward(), data.getFlower()});
            }
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }

    }

    public List<PostArticleData> selectMineData() {
        List<PostArticleData> mDatas = null;
        PostArticleData data;
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery(selectMineTabData, null);
            if (cursor.getCount() > 0) {
                mDatas = new ArrayList<>();
                while (cursor.moveToNext()) {
                    data = new PostArticleData();
                    data.setImageUri(cursor.getString(cursor.getColumnIndex("iconaddress")));
                    data.setSeconds(cursor.getLong(cursor.getColumnIndex("seconds")));
                    data.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                    data.setPostTime(cursor.getString(cursor.getColumnIndex("posttime")));
                    data.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    data.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    data.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
                    data.setImage(cursor.getString(cursor.getColumnIndex("image")));
                    data.setReview(cursor.getInt(cursor.getColumnIndex("review")));
                    data.setForward(cursor.getInt(cursor.getColumnIndex("forward")));
                    data.setFlower(cursor.getInt(cursor.getColumnIndex("flower")));
                    mDatas.add(data);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return mDatas;
    }
}
