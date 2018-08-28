package group.tonight.yue;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    private static final String DB_NAME = "UserDatabase.db";

    public abstract UserDao getUserDao();

    private static UserDatabase INSTANCE;

    public static UserDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), UserDatabase.class, DB_NAME)
//                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static UserDatabase get() {
        return INSTANCE;
    }

//    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE 'User' RENAME TO '_User_old_20180828'");
//
//            database.execSQL("CREATE TABLE IF NOT EXISTS `User` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `qq` TEXT, `wx` TEXT, `phone` TEXT, `address` TEXT, `remark` TEXT, `createTime` INTEGER NOT NULL, `updateTime` INTEGER NOT NULL)");
//
//            database.execSQL("INSERT INTO 'User' ('id', 'qq', 'wx', 'phone', 'address', 'remark', 'createTime', 'updateTime') SELECT 'id', 'qq', 'wx', 'phone', 'address', 'remark', 'createTime', 'updateTime' FROM '_User_old_20180828'");
//
////            database.execSQL("UPDATE 'sqlite_sequence' SET seq = 3 WHERE name = 'Student'");
//
//
//        }
//    };
}
