package com.example.hella.proxym;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SlideAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater _inflater;

    //todo change avatars to owners -> list of images
    public int[] list_image={R.drawable.fighterman, R.drawable.fighterwoman, R.drawable.collectorman, R.drawable.collectorwoman, R.drawable.crafterman,R.drawable.craftwomantwo};
    //list of titles
    public String[] list_title={"Fighter M","Fighter F","Collector M","Collector F", "Craftier M","Craftier F"};

    //public ArrayList<View> focus=new ArrayList<>();

   // public ID[] list_tag=new ID[6];


    public SlideAdapter(Context context){
        this.context =context;
    }



    @Override
    public int getCount() {
        return list_title.length;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        _inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=_inflater.inflate(R.layout.slide, container,false);

        ImageView image_slide=view.findViewById(R.id.slide_image);
        TextView text_title=view.findViewById(R.id.text_title);
        LinearLayout layout_description=view.findViewById(R.id.slide_stats);


        image_slide.setImageResource(list_image[position]);
        text_title.setText(list_title[position]);

        int _position=1;
        switch(position){
            case 1:{
                _position=1;
                break;
            }
            case 2:{
                _position=2;
                break;
            }
            case 3:{
                _position=2;
                break;
            }
            case 4 :{
                _position=0;
                break;
            }case 5:{
                _position=0;
                break;
            }
        }
        layout_description.getChildAt(_position).setVisibility(View.VISIBLE);
        //setTag(view,position);
       // focus.add(position,view);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(LinearLayout)object);
    }

    //todo added this method though they would help but did not + the ID class + was an ID Array for views and indexes -> but didn'T resolve the problem either
     /*private void setTag(View view, int position){
        list_tag[position]=new ID(view,position);
    }

   public int getTag(View view){
        for(int i=0; i<6; i++){
            if(view.getId()==list_tag[i].getView().getId())
                return list_tag[i].getId();
        }
        return -1;
    }*/
}
