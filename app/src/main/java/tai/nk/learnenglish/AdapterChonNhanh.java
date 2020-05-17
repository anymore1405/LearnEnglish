package tai.nk.learnenglish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by TAI on 22/07/2017.
 */

public class AdapterChonNhanh extends BaseAdapter {
    private ArrayList<ChonNhanh> list;
    private Context context;

    public AdapterChonNhanh(ArrayList<ChonNhanh> list, Context context) {
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
        TextView txt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_lv_chude,null);
            viewHolder = new ViewHolder();
            viewHolder.txt = (TextView) convertView.findViewById(R.id.txt_itemChuDe_TenChuDe);
            convertView.setTag(viewHolder);}
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txt.setText(list.get(position).Ten);

        return convertView;
    }
}
