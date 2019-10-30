package io.github.hotstu.archcomponent.paging;

import androidx.paging.PagedListAdapter;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author hglf
 * @since 2018/9/29
 */
public class MyPagedAdapter extends PagedListAdapter<String, MyPagedAdapter.VH> {
    public static class VH extends RecyclerView.ViewHolder {
        public VH(TextView itemView) {
            super(itemView);
        }

        void setText(String text) {
            ((TextView) itemView).setText(text);
        }
    }
    private static  DiffUtil.ItemCallback<String> diffCallback = new DiffUtil.ItemCallback<String>() {
        @Override
        public boolean areItemsTheSame(String oldItem, String newItem) {
            //noinspection StringEquality
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(String oldItem, String newItem) {
            if (oldItem == null) {
                return newItem == null;
            }
            return oldItem.equals(newItem);
        }
    };
    protected MyPagedAdapter() {
        super(diffCallback);
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(new TextView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        String item = getItem(position);
        if (item == null) {
            holder.setText("loading...");
        } else {
            holder.setText(item);
        }
    }
}
