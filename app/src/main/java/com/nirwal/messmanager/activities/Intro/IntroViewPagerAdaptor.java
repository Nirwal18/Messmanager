package com.nirwal.messmanager.activities.Intro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.nirwal.messmanager.R;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class IntroViewPagerAdaptor extends PagerAdapter {
    private Context _context;
    private ArrayList<IntroItem> _items;

    public IntroViewPagerAdaptor(Context context, ArrayList<IntroItem> items) {
        this._context = context;
        this._items = items;
    }

    @Override
    public int getCount() {

        return _items==null? 0 : _items.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.holder_intro,null);
        // update view UI here

        v.findViewById(R.id.imageView_holder_intro_photo).setBackgroundResource(_items.get(position).getPhoto());
        ((TextView)v.findViewById(R.id.textview_holder_intro_title)).setText(_items.get(position).getTitle());
        ((TextView)v.findViewById(R.id.textView_holder_intro_description)).setText(_items.get(position).getDescription());

        container.addView(v);
        return v;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
        //super.destroyItem(container, position, object);
    }
}
