package umn.ac.tugasmobile;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    private RecyclerView rv;
    private FavoriteViewModel favVM;
    private static final int REQUEST_TAMBAH = 1;
    private static final int REQUEST_EDIT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        rv=(RecyclerView) findViewById(R.id.rvFavorite);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);

        final FavoriteListAdapter adapter = new FavoriteListAdapter(this);
        rv.setAdapter(adapter);

        favVM = ViewModelProviders.of(this).get(FavoriteViewModel.class);

        favVM.getDaftarFavorite().observe(this,
                new Observer<List<FavoriteList>>(){
                    @Override
                    public void onChanged(List<FavoriteList> favs){
                        adapter.setDaftarFavorite(favs);
                    }
                });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder
                                                 viewHolder, int direction) {
                        int posisi = viewHolder.getAdapterPosition();
                        FavoriteList fav = adapter.getFavoriteAtPosition(posisi);
                        if(direction==ItemTouchHelper.LEFT){
                            Toast.makeText(FavoriteActivity.this, "User dengan nama "+
                                    fav.getNama_lengkap()+" telah dihapus", Toast.LENGTH_LONG).show();
                            favVM.delete(fav);
                        }
                        else if(direction== ItemTouchHelper.RIGHT){
                            Toast.makeText(FavoriteActivity.this, "User dengan nama "+
                                    fav.getNama_lengkap()+" telah dihapus", Toast.LENGTH_LONG).show();
                            favVM.delete(fav);
                        }
                    }
                }
        );
        helper.attachToRecyclerView(rv);

    }
    @Override
    public void onActivityResult(int reqCode, int resultCode,
                                 Intent data){
        super.onActivityResult(reqCode, resultCode, data);
        if(resultCode == RESULT_OK){
            FavoriteList fav = (FavoriteList) data.getSerializableExtra("FAVORITE");
            if(reqCode==REQUEST_TAMBAH){
                favVM.insert(fav);
            }
            if(reqCode==REQUEST_EDIT){
                favVM.update(fav);
            }
        }
        rv.getAdapter().notifyDataSetChanged();
    }

}
