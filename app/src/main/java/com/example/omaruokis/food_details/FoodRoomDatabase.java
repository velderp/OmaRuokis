package com.example.omaruokis.food_details;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Annotated singleton to be a food Room database.
 * @author Mika
 */
@Database(entities = {FoodNameFi.class, EufdnameFi.class, ComponentValue.class, Favorite.class, Component.class, FoodEaten.class, UsersDay.class}, version = 2, exportSchema = false)
public abstract class FoodRoomDatabase extends RoomDatabase {
    public static final String FOOD_DATABASE_NAME = "food_database";
    public static final String TAG = "myMessage";
    public abstract FoodDao foodDao();
    private static FoodRoomDatabase INSTANCE;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.d(TAG, "migrate: Migrating db");
            // Tables are not altered.
            // Asset db is migrated for use of room
        }
    };

    public static FoodRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            Log.d(TAG, "getDatabase: INSTANCE == null 1");
            synchronized (FoodRoomDatabase.class){
                if (INSTANCE == null){
                    //Creating database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FoodRoomDatabase.class, FOOD_DATABASE_NAME)
                            .addMigrations(MIGRATION_1_2).addCallback(roomDatabaseBuilder).build();
                    Log.d(TAG, "getDatabase: INSTANCE == null 2");

                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomDatabaseBuilder = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            Log.d(TAG, "onOpen: room database open");
        }
    };
}
