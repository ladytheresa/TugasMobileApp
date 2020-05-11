package umn.ac.tugasmobile;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class FavoriteRepository {
    private FavoriteListDAO daoFavorite;
    private LiveData<List<FavoriteList>> daftarFavorite;

    FavoriteRepository(Application app) {
        FavoriteRoomDatabase db =
                FavoriteRoomDatabase.getDatabase(app);
        daoFavorite = db.daoFavorite();
        daftarFavorite = daoFavorite.getAllFavorite();
    }

    LiveData<List<FavoriteList>> getDaftarFavorite() {
        return this.daftarFavorite;
    }

    public void insert(FavoriteList fav) {
        new insertAsyncTask(daoFavorite).execute(fav);
    }

    public void deleteAll() {
        new deleteAllAsyncTask(daoFavorite).execute();
    }

    public void delete(FavoriteList fav) {
        new deleteAsyncTask(daoFavorite).execute(fav);
    }

    public void update(FavoriteList fav) {
        new updateAsyncTask(daoFavorite).execute(fav);
    }

    private static class insertAsyncTask extends
            AsyncTask<FavoriteList, Void, Void> {
        private FavoriteListDAO asyncDaoFavorite;

        insertAsyncTask(FavoriteListDAO dao) {
            this.asyncDaoFavorite = dao;
        }

        @Override
        protected Void doInBackground(final FavoriteList... favorite) {
            asyncDaoFavorite.insert(favorite[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends
            AsyncTask<Void, Void, Void> {
        private FavoriteListDAO asyncDaoFavorite;

        deleteAllAsyncTask(FavoriteListDAO dao) {
            this.asyncDaoFavorite = dao;
        }

        @Override
        protected Void doInBackground(final Void... voids) {
            asyncDaoFavorite.deleteAll();
            return null;
        }
    }

    private static class deleteAsyncTask extends
            AsyncTask<FavoriteList, Void, Void> {
        private FavoriteListDAO asyncDaoFavorite;

        deleteAsyncTask(FavoriteListDAO dao) {
            this.asyncDaoFavorite = dao;
        }

        @Override
        protected Void doInBackground(final FavoriteList... favorites) {
            asyncDaoFavorite.delete(favorites[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends
            AsyncTask<FavoriteList, Void, Void> {
        private FavoriteListDAO asyncDaoFavorite;

        updateAsyncTask(FavoriteListDAO dao) {
            this.asyncDaoFavorite = dao;
        }

        @Override
        protected Void doInBackground(final FavoriteList... favorites) {
            asyncDaoFavorite.update(favorites[0]);
            return null;
        }
    }
}