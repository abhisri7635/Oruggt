package cse.fet.gkv.oruggt;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class customadapter extends ArrayAdapter<String>{
    private final Activity context;
    private final String []settings;
    private final Integer []imagesid;
    public customadapter(Activity context, String []settings, Integer []imagesid) {
        super(context,R.layout.custom_row,settings);
        this.context = context;
        this.settings = settings;
        this.imagesid = imagesid;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myinflator=LayoutInflater.from(getContext());
        View customview=myinflator.inflate(R.layout.custom_row,parent,false);


        TextView mytext=(TextView) customview.findViewById(R.id.mytext);
        ImageView myimage=(ImageView)  customview.findViewById(R.id.myimage);
        mytext.setText(settings[position]);
        myimage.setImageResource(imagesid[position]);
        return customview;
    }
}
