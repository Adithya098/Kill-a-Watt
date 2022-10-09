package com.nicktrick.usage;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nicktrick.R;


import java.util.List;


public class UsageAdapter extends BaseAdapter {

    private Activity activity;
    private List<Usagebean> listPerson;

    public UsageAdapter(Activity activity, List<Usagebean> listPerson) {
        this.activity = activity;
        this.listPerson = listPerson;
    }

    @Override
    public int getCount() {
        return listPerson.size();
    }

    @Override
    public Object getItem(int position) {
        return listPerson.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.usagexmllayout, null);

            holder = new ViewHolder();
            holder.textViewFirstName = convertView.findViewById(R.id.put_unicode);
            holder.textViewLastName = convertView.findViewById(R.id.put_usage);
            holder.textViewAge = convertView.findViewById(R.id.put_date);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textViewFirstName.setText(listPerson.get(position).getUniqid());
        holder.textViewLastName.setText(listPerson.get(position).getUsage());
        holder.textViewAge.setText(listPerson.get(position).getDater());

        return convertView;
    }

    class ViewHolder {
        TextView textViewFirstName;
        TextView textViewLastName;
        TextView textViewAge;
    }
}