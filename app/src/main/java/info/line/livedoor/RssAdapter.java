package info.line.livedoor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class RssAdapter extends BaseAdapter implements Serializable {

	private List<RssItem> items;
	private transient Context context;
    private static final long serialVersionUID = 1L;
    public RssAdapter(){}

    public RssAdapter(Context context, List<RssItem> items) {
		this.items = items;
		this.context = context;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.rss_item, null);
			holder = new ViewHolder();
			holder.itemTitle = (TextView) convertView.findViewById(R.id.itemTitle);
            holder.itemPubDate = (TextView) convertView.findViewById(R.id.date);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.itemTitle.setText(items.get(position).getTitle());
        holder.itemPubDate.setText(items.get(position).getPubDate());
		return convertView;
	}

	static class ViewHolder implements Serializable{
        ViewHolder(){}
        private static final long serialVersionUID = 1L;
        TextView itemTitle;
        TextView itemPubDate;
	}
}
