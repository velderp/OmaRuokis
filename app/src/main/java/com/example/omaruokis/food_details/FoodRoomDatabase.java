package com.example.omaruokis.food_details;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;


@Database(entities = {FoodNameFi.class, EufdnameFi.class, ComponentValue.class, Favorite.class}, version = 2, exportSchema = false)
public abstract class FoodRoomDatabase extends RoomDatabase {
    public static final String TAG = "myMessage";
    public abstract FoodDao wordDao();

    private static FoodRoomDatabase INSTANCE;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.d(TAG, "migrate: Migrating db");
            // Tables are not altered
        }
    };

    public static FoodRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            Log.d(TAG, "getDatabase: INSTANCE == null 1");
            synchronized (FoodRoomDatabase.class){
                if (INSTANCE == null){
                    //Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FoodRoomDatabase.class, "word_database")
                            .addMigrations(MIGRATION_1_2).addCallback(sRoomDatabaseCallback).build();
                    Log.d(TAG, "getDatabase: INSTANCE == null 2");

                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            Log.d(TAG, "onOpen: room database open");
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{

        private final FoodDao mDao;

        PopulateDbAsync(FoodRoomDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {


            return null;
        }

    }
}
