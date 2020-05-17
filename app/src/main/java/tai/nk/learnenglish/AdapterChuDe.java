package tai.nk.learnenglish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by TAI on 15/07/2017.
 */

public class AdapterChuDe extends BaseAdapter{
    private Context context;
    private ArrayList<ChuDe> list;
    private ArrayList<Integer> listSTT;

    public AdapterChuDe(Context context, ArrayList<ChuDe> list, ArrayList<Integer> listSTT) {
        this.context = context;
        this.list = list;
        this.listSTT = listSTT;
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
        TextView txt_TenChuDe;
        TextView txt_STT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_lv_chude,null);
            viewHolder = new ViewHolder();
            viewHolder.txt_TenChuDe = (TextView) convertView.findViewById(R.id.txt_itemChuDe_TenChuDe);
            viewHolder.txt_STT = (TextView) convertView.findViewById(R.id.txt_itemChuDe_STT);
        convertView.setTag(viewHolder);}
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txt_STT.setText(""+listSTT.get(position));
        viewHolder.txt_TenChuDe.setText(list.get(position).Ten);

        return convertView;
    }
}
