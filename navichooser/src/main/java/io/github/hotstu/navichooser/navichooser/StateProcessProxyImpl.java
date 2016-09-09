package io.github.hotstu.navichooser.navichooser;


import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;


public class StateProcessProxyImpl implements StateProcessProxy {
    private static final String TAG = "ProcessorSimpleImpl";
    Object lock = new Object();
    LruCache<String, LoadResult> mCache = new LruCache<>(20);
    DictLoader mloader;
    State mState;
    Context mContext;
    View v;
    Callback callback = null;
    boolean isFirstTime = true;
    /**
     * 如果为false 这不使用
     * 如果为true, 则不使用, 仅对load方法有效, 对forceload无效
     */
    boolean useCacheGlobal = true;

    public StateProcessProxyImpl(Context ctx) {
        this.mContext = ctx;
    }

    @Override
    public void setState(State state) {
        synchronized (lock) {
            cancel();
            this.mState = state;
        }
    }

    @Override
    public void setView(View container) {
        synchronized (lock) {
            this.v = container;
        }
    }

    public ViewHolder getViewHolder() {
        return (ViewHolder) v.getTag();
    }

    @Override
    public State currentState() {
        return mState;
    }

    @Override
    public State rootState() {
        State ret = currentState();
        while (ret.getParent() != null) {
            ret = ret.getParent();
        }
        return ret;
    }

    @Override
    public void load() {
        cancel();
        mloader = new DictLoader(useCacheGlobal);
        mloader.execute();
        isFirstTime = false;
    }

    @Override
    public boolean isLoadFirstTime() {
        return isFirstTime;
    }

    public void forceload() {
        cancel();
        mloader = new DictLoader(false);
        mloader.execute();
    }

    @Override
    public void cancel() {
        Log.d(TAG, "cancel:" + mloader);
        if (mloader != null)
            Log.d(TAG, "cancel:" + mloader.isCancelled());
        if (mloader != null && !mloader.isCancelled()) {
            Log.d(TAG, "cancel:" + mloader.cancel(false));
            mloader = null;
        }
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        Log.d(TAG, "onDismiss");
        cancel();
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void forward() {
        if (mState.getChild() != null) {
            setState(mState.getChild());
        }
        load();
    }

    @Override
    public void back() {
        if (mState.getParent() != null) {
            setState(mState.getParent());
        }
        load();
    }

    @Override
    public void refresh() {
        load();
    }


    private class DictLoader extends AsyncTask<Void, Void, LoadResult> {
        boolean useCache = false;

        public DictLoader(boolean useCache) {
            this.useCache = useCache;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mState.onBeforeloading();
        }

        @Override
        protected LoadResult doInBackground(Void... params) {
            // 缓存
            try {
                LoadResult ret = null;
                if (useCache && (ret = mCache.get(mState.getPseudoUrl())) != null) {
                    Log.d(TAG, "cache Hit!");
                    return ret;
                }
                return mState.loadImpl();
            } catch (Exception e) {
                e.printStackTrace();
                LoadResult l = new LoadResult();
                l.errCode = -1;
                l.errMsg = "发生非预测异常:" + e.getMessage();
                return l;
            }
        }

        @Override
        protected void onPostExecute(LoadResult result) {
            super.onPostExecute(result);
            if (useCache && result != null && result.errCode == 0
                    && result.items != null && result.items.size() > 0) {
                mCache.put(mState.getPseudoUrl(), result);
            }
            mState.onloadingFinished(result);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            mState.onLoadingCanceled();
        }

    }


    @Override
    public void setSubmitAction(Callback action) {
        this.callback = action;
    }

    @Override
    public Callback getSubmitAction() {
        return callback;
    }


}
