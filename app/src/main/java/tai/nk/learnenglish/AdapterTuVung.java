package tai.nk.learnenglish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by TAI on 03/08/2017.
 */

public class AdapterTuVung extends BaseAdapter {
    private ArrayList<TuVung> list;
    private Context context;

    public AdapterTuVung(ArrayList<TuVung> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        TextView TA;
        TextView TV;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tuvung,null);
            holder = new ViewHolder();
            holder.TA = (TextView) convertView.findViewById(R.id.txt_TA);
            holder.TV = (TextView) convertView.findViewById(R.id.txt_TV);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.TA.setText("Tiếng Anh: "+list.get(position).TA);
        holder.TV.setText("Tiếng Việt: "+list.get(position).TV);
        return convertView;
    }
}
