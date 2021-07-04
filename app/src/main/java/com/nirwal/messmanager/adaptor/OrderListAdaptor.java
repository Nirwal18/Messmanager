package com.nirwal.messmanager.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nirwal.messmanager.R;
import com.nirwal.messmanager.models.Meal;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class OrderListAdaptor extends RecyclerView.Adapter<OrderListAdaptor.OrderListHolder> {

    private ArrayList<Meal> _list;
    private Meal _item;
    private Context _context;

    public OrderListAdaptor(ArrayList<Meal> list , Context context) {
        this._list = list;
        this._context = context;

    }


    @NonNull
    @Override
    public OrderListAdaptor.OrderListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_orders,parent,false);
        return new OrderListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListHolder holder, int position) {
        this._item = _list.get(position);
        holder.requestorNameTxt.setText(_item.requestorName);
        holder.riceOnLunchCheckbox.setChecked(_item.lunchRiceRequired);
        holder.riceOnDinnerCheckbox.setChecked(_item.dinnerRiceRequired);
        holder.lunchRotiCountTxt.setText(String.valueOf(_item.lunchRotiCount));
        holder.dinnerRotiCountTxt.setText(String.valueOf(_item.dinnerRotiCount));
    }

    @Override
    public int getItemCount() {

        if(_list==null){
            return 0;
        }
        return _list.size();
    }

    public void removeItem(Meal item){
        this._list.remove(item);
        notifyDataSetChanged();
    }

    public void updateDataSet(ArrayList<Meal> list){
        this._list = list;
        notifyDataSetChanged();
    }



    class OrderListHolder extends RecyclerView.ViewHolder{

        TextView requestorNameTxt, lunchRotiCountTxt, dinnerRotiCountTxt;
        CheckBox brakefastCheckBox, riceOnLunchCheckbox, riceOnDinnerCheckbox;


        public OrderListHolder(View itemView) {
            super(itemView);
            requestorNameTxt = itemView.findViewById(R.id.text_order_list_requestor_name);
            lunchRotiCountTxt = itemView.findViewById(R.id.text_order_list_lunch_roti_count);
            dinnerRotiCountTxt = itemView.findViewById(R.id.text_order_list_dinner_roti_count);

             brakefastCheckBox = itemView.findViewById(R.id.checkBox_order_list_BreakFastReq);
             riceOnLunchCheckbox = itemView.findViewById(R.id.checkBox_order_list_rice_on_lunch);
             riceOnDinnerCheckbox = itemView.findViewById(R.id.checkBox_order_list_rice_on_dinner);

        }



    }
}


