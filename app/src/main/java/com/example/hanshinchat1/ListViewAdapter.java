package com.example.hanshinchat1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hanshinchat1.R.layout;

import java.util.ArrayList;

//@Metadata(
//        mv = {1, 8, 0},
//        k = 1,
//        d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\tH\u0016J$\u0010\u000f\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\t2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"},
//        d2 = {"Lcom/example/myapplication/ListViewAdapter;", "Landroid/widget/BaseAdapter;", "List", "", "Lcom/example/myapplication/Model;", "(Ljava/util/List;)V", "getList", "()Ljava/util/List;", "getCount", "", "getItem", "", "position", "getItemId", "", "getView", "Landroid/view/View;", "convertView", "parent", "Landroid/view/ViewGroup;", "app_debug"}
//)
public final class ListViewAdapter extends BaseAdapter {
//    @NotNull
//    private final List List;
    private TextView title;
    private TextView content;
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    public ListViewAdapter() {

    }

    @Override
    public int getCount() {
//        Collection var1 = (Collection)this.List;
//        return var1.size();
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
//        return this.List.get(position);
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
//        return (long)position;
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = convertView;
//        if (convertView == null) {
//            view = LayoutInflater.from(parent != null ? parent.getContext() : null).inflate(layout.listview_item, parent, false);
//        }
//
//        Intrinsics.checkNotNull(view);
//        return view;
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout.listview_item, parent, false);
        }

        title = (TextView) convertView.findViewById(R.id.itemTextId);
        content = (TextView) convertView.findViewById(R.id.itemText);

        ListViewItem listViewItem = listViewItemList.get(position);

        title.setText(listViewItem.getTitle());
        content.setText(listViewItem.getContent());

        return convertView;
    }

    public void addItem(String title, String content) {
        ListViewItem item = new ListViewItem(title, content);
        item.setTitle(title);
        item.setContent(content);

        listViewItemList.add(item);
    }

//    @NotNull
//    public final List getList() {
//        return this.List;
//    }
//
//    public ListViewAdapter(@NotNull List List) {
//        super();
//        Intrinsics.checkNotNullParameter(List, "List");
//        this.List = List;
//    }
}
