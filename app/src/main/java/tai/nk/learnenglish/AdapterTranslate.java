package tai.nk.learnenglish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by TAI on 23/07/2017.
 */

public class AdapterTranslate extends BaseAdapter {
    private Context context;
    private ArrayList<ListTranslate> list;

    public AdapterTranslate(Context context, ArrayList<ListTranslate> list) {
        this.context = context;
        this.list = list;
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
        Button speak;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.translate,parent);
            holder = new ViewHolder();
            holder.TA = (TextView) convertView.findViewById(R.id.translate_ta);
            holder.TV = (TextView) convertView.findViewById(R.id.translate_tv);
            holder.speak = (Button) convertView.findViewById(R.id.btn_translateSpeak);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.TA.setText(list.get(position).TA);
        holder.TV.setText(list.get(position).TV);
        holder.speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //list.get(position).TA;SPEAK RA TA TRONG LISTVIEW
            }
        });

        return convertView;
    }
}
