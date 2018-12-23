package cse.fet.gkv.oruggt;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/**
 * Created by JAPNEET SINGH on 17-Dec-17.
 */

class customAdaptor extends ArrayAdapter<String>{

    public customAdaptor(@NonNull Context context, String [] res) {
        super(context,R.layout.disaster_row,res);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inf=LayoutInflater.from(getContext());
        View vr=inf.inflate(R.layout.disaster_row,parent,false);
        String imgres=getItem(position);
        int i=Integer.parseInt(imgres);
        ImageView img=vr.findViewById(R.id.img);
        img.setImageResource(i);
        return vr;
    }
}
