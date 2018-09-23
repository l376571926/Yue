package group.tonight.yue;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GirlDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Girl girl);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Girl> girl);
    /**
     * ASC  升序
     * DESC 降序
     *
     * @return
     */
    @Query("SELECT * FROM Girl ORDER BY updateTime DESC")
    List<Girl> getAllUsers();

    @Query("SELECT * FROM Girl ORDER BY updateTime DESC")
    LiveData<List<Girl>> getAllUsersLiveData();

    @Update
    void update(Girl girl);
}
