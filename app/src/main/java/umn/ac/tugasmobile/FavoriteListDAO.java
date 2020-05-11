package umn.ac.tugasmobile;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoriteListDAO {
    @Query("SELECT * FROM tblFavorite")
    LiveData<List<FavoriteList>> getAllFavorite();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FavoriteList fav);

    @Delete
    void delete(FavoriteList fav);

    @Update
    void update(FavoriteList fav);

    @Query("DELETE FROM tblFavorite")
    void deleteAll();
}