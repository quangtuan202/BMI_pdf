package com.tuan.exportpdf;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.*;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface modelDao {
    @Insert(onConflict = REPLACE)
    void insertData(model model);
    @Insert(onConflict = IGNORE)
    void insertOrReplaceData(model model);
    @Update(onConflict = REPLACE)
    void updateData(model model);
    @Query("DELETE FROM bmi")
    void deleteAll();
    @Query("SELECT * FROM bmi WHERE bmi= :name")
    public List<model> findAllDataByName(String name);
    @Query("SELECT * FROM bmi")
    public List<model> findAllData();
}
