package tai.nk.learnenglish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TAI on 15/08/2017.
 */

public class CustomExpandableListView extends BaseExpandableListAdapter {
    Context context;
    ArrayList<ChuDe> list;
    HashMap<String,List<ChonNhanh>> hashMap;

    public CustomExpandableListView(Context context, ArrayList<ChuDe> list, HashMap<String, List<ChonNhanh>> hashMap) {
        this.context = context;
        this.list = list;
        this.hashMap = hashMap;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return hashMap.get(list.get(groupPosition).Ten).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return hashMap.get(list.get(groupPosition).Ten).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.layout_group,null);
        TextView txt_Group = (TextView) convertView.findViewById(R.id.txt_Group);
        txt_Group.setText(list.get(groupPosition).Ten);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.layout_child,null);
        TextView txt_Child = (TextView) convertView.findViewById(R.id.txt_Child);
        txt_Child.setText(hashMap.get(list.get(groupPosition).Ten).get(childPosition).Ten);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
