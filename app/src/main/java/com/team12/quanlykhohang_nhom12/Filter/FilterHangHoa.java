package com.team12.quanlykhohang_nhom12.Filter;

import android.widget.Filter;

import com.team12.quanlykhohang_nhom12.Adapter.HangHoaIDAdapter;
import com.team12.quanlykhohang_nhom12.Library.ModelHangHoa;

import java.util.ArrayList;

public class FilterHangHoa extends Filter{
    private HangHoaIDAdapter adapter;
    private ArrayList<ModelHangHoa> filterList;

    public FilterHangHoa(HangHoaIDAdapter adapter, ArrayList<ModelHangHoa> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected Filter.FilterResults performFiltering(CharSequence charSequence) {
        Filter.FilterResults results = new Filter.FilterResults();
        if(charSequence != null && charSequence.length()>0){
            charSequence = charSequence.toString().toUpperCase();
            ArrayList<ModelHangHoa> filterModels = new ArrayList<>();
            for (int i =0;i<filterList.size();i++){
                if(filterList.get(i).getLoaihang().toUpperCase().contains(charSequence)){
                    filterModels.add(filterList.get(i));
                }
            }
            results.count = filterModels.size();
            results.values = filterModels;
        }
        else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
        adapter.hanghoalist = (ArrayList<ModelHangHoa>) filterResults.values;
        adapter.notifyDataSetChanged();
    }
}

