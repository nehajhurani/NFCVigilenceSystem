package com.example.dell.vigilance;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by aparna on 8/12/2017.
 */
public class ListViewAdapter2 extends BaseAdapter

{
        Activity context;
        String rid[],date[],sttime[],name[],rank[];


public ListViewAdapter2(Activity context, String[] rid, String[] date, String[] sttime, String[] name,String[] rank) {
        super();
        this.context = context;
        this.rid = rid;
        this.date = date;
        this.sttime = sttime;
        this.name = name;
        this.rank=rank;

}

public int getCount() {
        // TODO Auto-generated method stub
        return rid.length;
        }

public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
        }

public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
        }

private class ViewHolder {
    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4,t5;
}

    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.mytext, null);
            holder = new ViewHolder();
            holder.t1 = (TextView) convertView.findViewById(R.id.textView1);
            holder.t2 = (TextView) convertView.findViewById(R.id.textView2);
            holder.t3 = (TextView) convertView.findViewById(R.id.textView3);
            holder.t4 = (TextView) convertView.findViewById(R.id.textView4);
            holder.t5 = (TextView) convertView.findViewById(R.id.textView5);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.t1.setText(rid[position]);
        holder.t2.setText(date[position]);
        holder.t3.setText(sttime[position]);
        holder.t4.setText(name[position]);
        holder.t5.setText(rank[position]);
        return convertView;
    }

}