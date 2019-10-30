package io.github.hotstu.archcomponent;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.example.slab.loader.http.RxFetch;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * @author hglf
 * @since 2018/1/15
 */
public class MainViewModel extends AndroidViewModel {
    private final MutableLiveData<String> repos;
    private final MutableLiveData<Boolean> loading;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repos = new MutableLiveData<>();
        loading = new MutableLiveData<>();
        loading.setValue(true);
    }

    public MutableLiveData<String> getRepos() {
        if (repos.getValue() == null) {
            load();
        }
        return repos;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    private void load() {
        RxFetch.<List<Repo>>get("https://api.github.com/users/hotstu/repos",
                null,
                new TypeToken<List<Repo>>() {
                }.getType())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<List<Repo>, String>() {
                    @Override
                    public String apply(List<Repo> repos) throws Exception {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < repos.size(); i++) {
                            sb.append(repos.get(i).name);
                            sb.append('\n');
                        }
                        return sb.toString();
                    }
                })
                .subscribe(new DisposableSubscriber<String>() {
                    @Override
                    protected void onStart() {
                        loading.setValue(true);
                        super.onStart();
                    }

                    @Override
                    public void onNext(String s) {
                        repos.setValue(s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        repos.setValue(t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        loading.setValue(false);
                    }
                });
    }



    public static class Repo {
        public String name;
    }

}
