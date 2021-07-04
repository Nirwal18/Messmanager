package com.nirwal.messmanager.adaptor;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nirwal.messmanager.MyApp;
import com.nirwal.messmanager.R;

import java.util.ArrayList;






public class UserListAdaptor extends BaseAdapter  {
    private Context _context;
    private MyApp _app;
    private ArrayList<String> _list;
    private View.OnClickListener _listner;


    public UserListAdaptor(Context context, MyApp app, /*LoginFragment fragment,*/ ArrayList<String> list) {
        this._context = context;
        this._app=app;
        this._list = list;
    }


    @Override
    public int getCount() {
        return _list==null? 0: _list.size();
    }

    @Override
    public Object getItem(int position) {
        return this._list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        if(view == null) view = LayoutInflater.from(_context).inflate(R.layout.list_user, parent, false);

        final TextView user;

        user = view.findViewById(R.id.textView_user);
        user.setText(String.valueOf(_list.get(position)));

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _listner.onClick(view);

//                Toast. makeText(_context, "Clicked on " + user.getText().toString(),Toast.LENGTH_SHORT)
//                .show();
            }
        });


        return view;
    }

    public void updateDataSet(ArrayList<String> list){
        this._list = list;
        notifyDataSetChanged();
    }

    public void setOnClickListener(View.OnClickListener listener){
        _listner =listener;
    }














}

