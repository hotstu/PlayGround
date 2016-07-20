package com.example.slab.customviewplayground;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

/**
 * Created by hotstuNg on 2016/7/13.
 */
public class ButterKnifeFragment extends Fragment {
    private static final String TAG = "ButterKnifeFragment";
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.list)
    ListView list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.butters2, container, false);

        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] arrays = new String[3];
        arrays[0] = "selection1";
        arrays[1] = "selection2";
        arrays[2] = "selection3";
        spinner.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, arrays));
        list.setAdapter(new MyAdapter(getActivity(), arrays));
    }

    @OnItemSelected(R.id.spinner)
    void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemSelected() called with: " + "parent = [" + parent + "], view = [" + view + "], position = [" + position + "], id = [" + id + "]");
    }

    static class MyAdapter extends ArrayAdapter<String> {
        LayoutInflater inflater;

        public MyAdapter(Context context,  String[] objects) {
            super(context, 0, objects);
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            String s = getItem(position);
            ViewHolder holder;

            if (view != null) {
                holder = (ViewHolder) view.getTag();
            } else {
                view = inflater.inflate(R.layout.list_item_butters, parent, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }

            holder.textView4.setText(s);
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
            holder.textView5.setText("cool");

            return view;
        }
    }


    static class ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.textView5)
        TextView textView5;
        @BindView(R.id.textView4)
        TextView textView4;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
