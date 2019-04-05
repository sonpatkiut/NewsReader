package com.example.icsssd358.newsviewer.Utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.icsssd358.newsviewer.R;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class ImageBindingUtils {

    private static int MAX_AGE = 120;

    private static int MAX_STALE = 300;

    private ImageBindingUtils() {
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String imgUrl){
        /*try{

            Context context = imageView.getContext();
            Glide.with(context.getApplicationContext()).load(R.drawable.ic_launcher_background).override(150, 150).into(imageView);

            String imageUrl = imgUrl;
            if(!TextUtils.isEmpty(imageUrl)){
                Rx2AndroidNetworking.get(imageUrl)
                        .setMaxAgeCacheControl(MAX_AGE, TimeUnit.SECONDS)
                        .setMaxStaleCacheControl(MAX_STALE, TimeUnit.SECONDS)
                        .build()
                        .getStringObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                                    String imageString[] = response.split(",");
                                    byte[] imageByteArray = Base64.decode(imageString[1], Base64.URL_SAFE);
                                    Glide.with(context.getApplicationContext()).load(imageByteArray).diskCacheStrategy(DiskCacheStrategy.RESULT).override(150, 150).error(R.drawable.ic_launcher_background).into(imageView);
                                },
                                throwable -> {
                                    Glide.with(context.getApplicationContext()).load(R.drawable.ic_launcher_background).into(imageView);

                                });
            }
        }catch (Throwable throwable){
        }*/
        Picasso.with(imageView.getContext().getApplicationContext()).load(imgUrl)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(imageView.getContext().getApplicationContext())
                                .load(imgUrl)
                                .error(R.drawable.ic_launcher_background)
                                .into(imageView, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {
                                        Log.v("Picasso","Could not fetch image");
                                    }
                                });
                    }
                });
    }

    //For downloading and showing image ex: PNG, JPG,etc..
    /*@BindingAdapter("imgUrl")
    public static void setImgUrl(ImageView imageView, String imageUrl) {
        try {
            Context context = imageView.getContext();

            if (!TextUtils.isEmpty(imageUrl)) {
                String url = imageUrl;
                if (findUrlInString(imageUrl)) {
                    url = imageUrl;
                }

                Glide.with(context.getApplicationContext()).load(url).diskCacheStrategy(DiskCacheStrategy.RESULT).override(100, 100).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageView);


            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }
*/
    /*@BindingAdapter("imageBmp")
    public static void setImageBmp(ImageView imageView, String imageBytes) {
        try {
            if (!TextUtils.isEmpty(imageBytes)) {
                Bitmap bmp = getBitmapFromByteArray(imageBytes);
                imageView.setImageBitmap(bmp);
            }
        } catch (Throwable e) {
            e.printStackTrace();

        }
    }*/


    /*private static Bitmap getBitmapFromByteArray(String imageBytes) {
        Bitmap bmp = null;
        try {
            byte[] data = Base64.decode(imageBytes, Base64.URL_SAFE);
            ;
            bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bmp;
    }

    public static boolean findUrlInString(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Pattern urlPattern = Pattern.compile(
                "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)" + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                        + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        Matcher matcher = urlPattern.matcher(str);
        return matcher.find();

    }*/
}
