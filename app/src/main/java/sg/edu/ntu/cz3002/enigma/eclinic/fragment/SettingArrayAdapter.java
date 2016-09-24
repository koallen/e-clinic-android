package sg.edu.ntu.cz3002.enigma.eclinic.fragment;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.model.SettingItem;

/**
 * Created by HuaBa on 15/09/16.
 */
public class SettingArrayAdapter extends ArrayAdapter<SettingItem> {
    Context context;
    int layoutResourceId;
    SettingItem[] settingItems = null;

    public SettingArrayAdapter(Context context, int layoutResourceId, SettingItem[] data){
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.settingItems = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        SettingItemHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new SettingItemHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);

            row.setTag(holder);
        }
        else
        {
            holder = (SettingItemHolder)row.getTag();
        }

        SettingItem settingItem = settingItems[position];
        holder.txtTitle.setText(settingItem.title);
        holder.imgIcon.setImageResource(settingItem.icon);

        return row;
    }
    static class SettingItemHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }

}
