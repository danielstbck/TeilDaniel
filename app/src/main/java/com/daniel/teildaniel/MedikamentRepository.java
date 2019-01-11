package com.daniel.teildaniel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class MedikamentRepository {

    private MedikamentDao medikamentDao;
    private LiveData<List<Medikament>> allMedikamente;

    public MedikamentRepository(Application application) {
        MedikamentDatabase database = MedikamentDatabase.getInstance(application);
        medikamentDao = database.medikamentDao();
        allMedikamente = medikamentDao.getAllMedikamente();
    }

    public void insert(Medikament medikament) {
         new InsertMedikamentAsyncTask(medikamentDao).execute(medikament);
    }

    public void update(Medikament medikament) {
        new UpdateMedikamentAsyncTask(medikamentDao).execute(medikament);
    }

    public void delete(Medikament medikament) {
        new DeleteMedikamentAsyncTask(medikamentDao).execute(medikament);
    }

    public void deleteAllMedikamente() {
        new DeleteAllMedikamenteAsyncTask(medikamentDao).execute();
    }

    public LiveData<List<Medikament>> getAllMedikamente() {
        return allMedikamente;
    }

    private static class InsertMedikamentAsyncTask extends AsyncTask<Medikament, Void, Void> {
        private MedikamentDao medikamentDao;

        private InsertMedikamentAsyncTask(MedikamentDao medikamentDao) {
            this.medikamentDao = medikamentDao;
        }

        @Override
        protected Void doInBackground(Medikament... medikamente) {
            medikamentDao.insert(medikamente[0]);
            return null;
        }
    }

    private static class UpdateMedikamentAsyncTask extends AsyncTask<Medikament, Void, Void> {
        private MedikamentDao medikamentDao;

        private UpdateMedikamentAsyncTask(MedikamentDao medikamentDao) {
            this.medikamentDao = medikamentDao;
        }

        @Override
        protected Void doInBackground(Medikament... medikamente) {
            medikamentDao.update(medikamente[0]);
            return null;
        }
    }

    private static class DeleteMedikamentAsyncTask extends AsyncTask<Medikament, Void, Void> {
        private MedikamentDao medikamentDao;

        private DeleteMedikamentAsyncTask(MedikamentDao medikamentDao) {
            this.medikamentDao = medikamentDao;
        }

        @Override
        protected Void doInBackground(Medikament... medikamente) {
            medikamentDao.delete(medikamente[0]);
            return null;
        }
    }

    private static class DeleteAllMedikamenteAsyncTask extends AsyncTask<Void, Void, Void> {
        private MedikamentDao medikamentDao;

        private DeleteAllMedikamenteAsyncTask(MedikamentDao medikamentDao) {
            this.medikamentDao = medikamentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            medikamentDao.deleteAllMedikamente();
            return null;
        }
    }



}
