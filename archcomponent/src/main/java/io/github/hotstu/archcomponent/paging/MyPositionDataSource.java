package io.github.hotstu.archcomponent.paging;

import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;

import com.example.slab.loader.logger.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hglf
 * @since 2018/9/29
 */
public class MyPositionDataSource<T> extends PositionalDataSource<T> {
    private static final String TAG = MyPositionDataSource.class.getSimpleName();

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback callback) {
        Log.d(TAG, "params" + params);
        //callback.onResult();
        int start = params.requestedStartPosition;
        int count = params.requestedLoadSize;
        List<String> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add("item" + (start + i));
        }
        //if placeHolder is enabled, totalCOunt is mandatory
        //PositionalDataSource requires initial load size to be a multiple of page size
        //if real data count < pageSize, totalCount should = read data count or Exception will be thrown
        callback.onResult(list, start, 252);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback callback) {
        int start = params.startPosition;
        int count = params.loadSize;
        List<String> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add("item" + (start + i));
        }
        callback.onResult(list);



    }
}
