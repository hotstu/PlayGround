package io.github.hotstu.archcomponent.paging;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.slab.loader.logger.Log;

import io.github.hotstu.archcomponent.R;

/**
 * @author hglf
 * @since 2018/9/29
 */
public class PagingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paging);
        RecyclerView v = findViewById(R.id.recycler);
        PagingViewModel pagingViewModel = ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new PagingViewModel(getApplication());
            }
        }).get(PagingViewModel.class);
        final MyPagedAdapter adapter =  new MyPagedAdapter();
        v.setLayoutManager(new LinearLayoutManager(this));
        v.setAdapter(adapter);
        pagingViewModel.concertList.observe(this, new Observer<PagedList<String>>() {
            @Override
            public void onChanged(@Nullable PagedList<String> strings) {
                Log.d("test", "" + strings) ;
                adapter.submitList(strings);
            }
        });
    }
}
