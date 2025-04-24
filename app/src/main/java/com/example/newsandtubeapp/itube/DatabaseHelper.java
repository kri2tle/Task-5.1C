package com.example.newsandtubeapp.itube;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "itube.db";
    private static final int DATABASE_VERSION = 1;
    
    // Tables
    private static final String TABLE_USERS = "users";
    private static final String TABLE_PLAYLIST = "playlist";
    
    // User columns
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";
    
    // Playlist columns
    private static final String COLUMN_VIDEO_ID = "video_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_THUMBNAIL_URL = "thumbnail_url";
    private static final String COLUMN_USER_EMAIL_FK = "user_email";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT UNIQUE,"
                + COLUMN_USER_PASSWORD + " TEXT"
                + ")";
        db.execSQL(createUsersTable);

        // Create playlist table
        String createPlaylistTable = "CREATE TABLE " + TABLE_PLAYLIST + "("
                + COLUMN_VIDEO_ID + " TEXT PRIMARY KEY,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_THUMBNAIL_URL + " TEXT,"
                + COLUMN_USER_EMAIL_FK + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_USER_EMAIL_FK + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_EMAIL + ")"
                + ")";
        db.execSQL(createPlaylistTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // User authentication methods
    public boolean registerUser(String name, String email, String password) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_NAME, name);
            values.put(COLUMN_USER_EMAIL, email);
            values.put(COLUMN_USER_PASSWORD, password); // In a real app, you should hash the password
            long result = db.insert(TABLE_USERS, null, values);
            db.close();
            return result != -1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateUser(String email, String password) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = {COLUMN_USER_EMAIL};
            String selection = COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ?";
            String[] selectionArgs = {email, password}; // In a real app, you should hash the password
            Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
            boolean exists = cursor.getCount() > 0;
            cursor.close();
            db.close();
            return exists;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean userExists(String email) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = {COLUMN_USER_EMAIL};
            String selection = COLUMN_USER_EMAIL + " = ?";
            String[] selectionArgs = {email};
            Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
            boolean exists = cursor.getCount() > 0;
            cursor.close();
            db.close();
            return exists;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Playlist methods
    public void addVideo(VideoModel video, String userEmail) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_VIDEO_ID, video.getVideoId());
            values.put(COLUMN_TITLE, video.getTitle());
            values.put(COLUMN_THUMBNAIL_URL, video.getThumbnailUrl());
            values.put(COLUMN_USER_EMAIL_FK, userEmail);
            db.insert(TABLE_PLAYLIST, null, values);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<VideoModel> getAllVideos(String userEmail) {
        List<VideoModel> videoList = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_PLAYLIST + " WHERE " + COLUMN_USER_EMAIL_FK + " = ?";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{userEmail});

            if (cursor.moveToFirst()) {
                do {
                    VideoModel video = new VideoModel(
                        cursor.getString(cursor.getColumnIndex(COLUMN_VIDEO_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_THUMBNAIL_URL))
                    );
                    videoList.add(video);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoList;
    }

    public void deleteVideo(String videoId, String userEmail) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_PLAYLIST, 
                     COLUMN_VIDEO_ID + " = ? AND " + COLUMN_USER_EMAIL_FK + " = ?", 
                     new String[]{videoId, userEmail});
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 