package umn.ac.tugasmobile;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class FavoriteViewModel extends AndroidViewModel {
    private FavoriteRepository favoriteRepository;
    private LiveData<List<FavoriteList>> daftarFavorite;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        favoriteRepository = new FavoriteRepository(application);
        daftarFavorite = favoriteRepository.getDaftarFavorite();
    }


    LiveData<List<FavoriteList>> getDaftarFavorite() {
        return this.daftarFavorite;
    }

    public void insert(FavoriteList fav) {
        favoriteRepository.insert(fav);
    }

    public void deleteAll() {
        favoriteRepository.deleteAll();
    }

    public void delete(FavoriteList fav) {
        favoriteRepository.delete(fav);
    }

    public void update(FavoriteList fav) {
        favoriteRepository.update(fav);
    }
}

