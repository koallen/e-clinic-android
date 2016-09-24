package sg.edu.ntu.cz3002.enigma.eclinic.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.model.SettingItem;

/**
 * Setting array adapter
 */
public class SettingArrayAdapter extends ArrayAdapter<SettingItem> {
    private Context context;
    private int layoutResourceId;
    private SettingItem[] settingItems = null;

    public SettingArrayAdapter(Context context, int layoutResourceId, SettingItem[] data){
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.settingItems = data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        SettingItemHolder holder;

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
    private static class SettingItemHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }

}
