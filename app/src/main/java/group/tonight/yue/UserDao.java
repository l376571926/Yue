package group.tonight.yue;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

@Dao
public interface UserDao {

    /**
     * ASC  升序
     * DESC 降序
     *
     * @return
     */
    @Query("SELECT * FROM user ORDER BY updateTime DESC")
    List<User> getAllUsers();

//    @Query("SELECT * FROM user WHERE id=:id")
//    User getUser(int id);

    @Query("SELECT * FROM user")
    Cursor getUserCursor();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<User> userLists);

    @Update
    void update(User... users);

    @Delete
    void delete(User... users);

    @Query("SELECT * FROM user WHERE qq=:qq")
    User getUsersByQq(String qq);

    @Query("SELECT * FROM user WHERE wx=:wx")
    User getUsersByWx(String wx);

//    @Query("SELECT * FROM user WHERE age=:age")
//    List<User> getUsersByAge(int age);

//    @Query("SELECT * FROM user WHERE age=:age LIMIT :max")
//    List<User> getUsersByAge(int max, int... age);

//    @Query("SELECT * FROM user " +
//            "WHERE User.email == :email "
//    )
//    List<User> findUserByEmail(String email);
}
