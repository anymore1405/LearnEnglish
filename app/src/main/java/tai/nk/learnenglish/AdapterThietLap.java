package tai.nk.learnenglish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;

import java.util.ArrayList;

public class AdapterThietLap extends BaseAdapter {
    private Context context;
    private ArrayList<TestChuDe> list;

    public AdapterThietLap(Context context, ArrayList<TestChuDe> list) {
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
    private  class ViewHolder{
        Switch swich;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_thietlaptest, null);
            holder = new ViewHolder();
            holder.swich = (Switch) convertView.findViewById(R.id.sw_ChuDe);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.swich.setText(list.get(position).TEN);
        return convertView;
    }
}
