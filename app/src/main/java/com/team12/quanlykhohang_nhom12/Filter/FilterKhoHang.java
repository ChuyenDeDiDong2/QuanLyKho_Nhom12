package com.team12.quanlykhohang_nhom12.Filter;

import android.widget.Filter;

import com.team12.quanlykhohang_nhom12.Adapter.KhoHangIDAdapter;
import com.team12.quanlykhohang_nhom12.Library.ModelKhoHang;

import java.util.ArrayList;

public class FilterKhoHang extends Filter {
    private KhoHangIDAdapter adapter;
    private ArrayList<ModelKhoHang> filterList;

    public FilterKhoHang(KhoHangIDAdapter adapter, ArrayList<ModelKhoHang> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();
        if(charSequence != null && charSequence.length()>0){
            charSequence = charSequence.toString().toUpperCase();
            ArrayList<ModelKhoHang> filterModels = new ArrayList<>();
            for (int i =0;i<filterList.size();i++){
                if(filterList.get(i).getTenkho().toUpperCase().contains(charSequence) || filterList.get(i).getTinhtrangkho().toUpperCase().contains(charSequence)){
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
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.khohanglist = (ArrayList<ModelKhoHang>) filterResults.values;
        adapter.notifyDataSetChanged();
    }
}
