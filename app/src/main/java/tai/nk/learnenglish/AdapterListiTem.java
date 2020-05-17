package tai.nk.learnenglish;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by TAI on 15/07/2017.
 */

public class AdapterListiTem extends BaseAdapter{
    private Context context;
    private ArrayList<ListiTem> listiTems;

    public AdapterListiTem(Context context, ArrayList<ListiTem> listiTems) {
        this.context = context;
        this.listiTems = listiTems;
    }

    @Override
    public int getCount() {
        return listiTems.size();
    }

    @Override
    public Object getItem(int position) {
        return listiTems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        TextView txt;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.itemlist,null);
            holder = new ViewHolder();
            holder.txt = (TextView) convertView.findViewById(R.id.txt_ListItem);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
            holder.txt.setText(listiTems.get(position).TV);

        return convertView;
    }
}
