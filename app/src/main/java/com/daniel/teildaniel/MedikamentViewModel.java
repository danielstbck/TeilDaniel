package com.daniel.teildaniel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MedikamentViewModel extends AndroidViewModel {

    private MedikamentRepository repository;
    private LiveData<List<Medikament>> allMedikamente;

    public MedikamentViewModel(@NonNull Application application) {
        super(application);
        repository = new MedikamentRepository(application);
        allMedikamente = repository.getAllMedikamente();
    }

    public void insert(Medikament medikament) {
        repository.insert(medikament);
    }

    public void update(Medikament medikament) {
        repository.update(medikament);
    }

    public void delete(Medikament medikament) {
        repository.delete(medikament);
    }

    public void deleteAllMedikamente() {
        repository.deleteAllMedikamente();
    }

    public LiveData<List<Medikament>> getAllMedikamente() {
        return allMedikamente;
    }
}
