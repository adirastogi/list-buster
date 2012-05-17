package com.adi.ListBuster.InfoView;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.adi.ListBuster.R;


public class GalleryAdapter extends BaseAdapter{
    
    private Context context;
    private int imageBackground; 
    //an array of album art image bitmaps that is passed to this control.
    //The bitmaps have been created after loading the images from the server and processing
    //them to get a certain size.
    private ArrayList<Bitmap> albumImages;

    public GalleryAdapter(Context context,ArrayList<Bitmap> aI) {
        super();
        this.context = context;
        albumImages = aI;
        TypedArray a = context.obtainStyledAttributes(R.styleable.AlbumGallery);
        imageBackground = a.getResourceId(R.styleable.AlbumGallery_android_galleryItemBackground, 0);
        a.recycle();
    }

    
    /**
     * @param bmp
     * This adds the received item to the underlying dataset of the image
     * and notifies the gallery to update itself.
     */
    public void addImage(Bitmap bmp){
    	albumImages.add(bmp);
    	this.notifyDataSetChanged();
    }
    
    public int getCount() {
        // TODO Auto-generated method stub
        return albumImages.size();
    }

    
    public Bitmap getItem(int arg0) {
        // TODO Auto-generated method stub
        return albumImages.get(arg0);
    }

    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView==null) {
            LayoutInflater inflator = LayoutInflater.from(context);
            convertView = inflator.inflate(R.layout.gallery_view,parent,false);
        }
        ImageView imageView = (ImageView)convertView.findViewById(R.id.gallery_image);
        imageView.setImageBitmap(albumImages.get(position));
        imageView.setBackgroundResource(this.imageBackground);
        
        return imageView;
    }

}
