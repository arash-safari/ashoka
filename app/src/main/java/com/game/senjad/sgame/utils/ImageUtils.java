package com.game.senjad.sgame.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ImageUtils {
    Context context;
    private ImageUtils(Context context){
        this.context = context;
    }

    private static ImageUtils instance = null;

    public static ImageUtils getInstance(Context context) {
        if(instance==null)
            instance = new ImageUtils(context);
        return instance;
    }

    public boolean saveImage(Bitmap bm,String fileName){
        OutputStream fOut = null;
        try {
            File root = new File(getRootPath());
            root.mkdirs();
            File sdImageMainDirectory = new File(root, fileName);
            fOut = new FileOutputStream(sdImageMainDirectory);

        } catch (Exception e) {
            return false;
        }
        try {
            bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public Bitmap getImage(String fileName){
        File imgFile = new  File(getRootPath()+ fileName);
        Bitmap myBitmap = null;
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
        return myBitmap;
    }
    public String getRootPath(){
        return  context.getApplicationContext().getCacheDir()
                + File.separator + "senjadGame" + File.separator;
    }
}
