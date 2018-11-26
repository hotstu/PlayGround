package io.github.hotstu.archcomponent.paging;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

/**
 Choose the correct data source type

 It's important to connect to the data source that best handles your source data's structure:

 Use PageKeyedDataSource if pages you load embed next/previous keys. For example, if you're fetching social media posts from the network, you may need to pass a nextPage token from one load into a subsequent load.
 Use ItemKeyedDataSource if you need to use data from item N to fetch item N+1. For example, if you're fetching threaded comments for a discussion app, you might need to pass the ID of the last comment to get the contents of the next comment.
 Use PositionalDataSource if you need to fetch pages of data from any location you choose in your data store. This class supports requesting a set of data items beginning from whatever location you select. For example, the request might return the 20 data items beginning with location 1200
 */
public class PagingViewModel extends AndroidViewModel {
    public final LiveData<PagedList<String>> concertList;

    public PagingViewModel(@NonNull Application application) {
        super(application);
        DataSource.Factory<Integer, String> factory = new DataSource.Factory() {
            @Override
            public DataSource<Integer, String> create() {
                return new MyPositionDataSource<>();
            }
        };
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(20)
                .build();
        concertList = new LivePagedListBuilder<>(factory, config).build();
    }
}
