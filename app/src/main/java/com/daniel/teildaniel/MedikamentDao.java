package com.daniel.teildaniel;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MedikamentDao {

    @Insert
    void insert(Medikament medikament);

    @Update
    void update(Medikament medikament);

    @Delete
    void delete(Medikament medikament);

    @Query("DELETE FROM medikament_table")
    void deleteAllMedikamente();

    @Query("select * from medikament_table")
    LiveData<List<Medikament>> getAllMedikamente();
}
