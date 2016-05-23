package com.app.ks.myapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ks on 02.04.2016.
 */
public class SimpleAdapter extends BaseAdapter {

    List<Client> mClientList;

    public SimpleAdapter(List<Client> clientList) {
        this.mClientList = clientList;
    }

    @Override
    public int getCount() {
        return mClientList.size();
    }

    @Override
    public Object getItem(int position) {
        return mClientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        Client client = mClientList.get(position);
        return  client.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_layout,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Client client = mClientList.get(position);
        holder.mClientName.setText(client.getName());
        holder.mClientAddress.setText(client.getAddress());

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.text_view_client_name)
        TextView mClientName;
        @Bind(R.id.text_view_client_address)
        TextView mClientAddress;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }

}
