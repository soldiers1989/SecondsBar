package com.fx.secondbar.ui.date;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.btten.bttenlibrary.base.adapter.BtAdapter;

/**
 * function:地理位置列表适配器
 * author: frj
 * modify date: 2018/11/7
 */
public class AdLocation extends BtAdapter<SuggestionResult.SuggestionInfo> implements Filterable
{

    private ArrayFilter filter;

    public AdLocation(Context context)
    {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = mInflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(getItem(position).getKey());
        return convertView;
    }

    @Override
    public Filter getFilter()
    {
        if (filter == null)
        {
            filter = new ArrayFilter();
        }
        return filter;
    }

    /**
     * <p>An array filter constrains the content of the array adapter with
     * a prefix. Each item that does not start with the supplied prefix
     * is removed from the list.</p>
     */
    private class ArrayFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence prefix)
        {
            final FilterResults results = new FilterResults();


            results.values = list;
            results.count = list.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
        }
    }
}
