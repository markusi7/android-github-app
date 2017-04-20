package markusi.githubapp.utils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ImageUtils {

    private ImageUtils() {
        throw new AssertionError();
    }

    public static void displayCircleCroppedImage(ImageView imageView, String imageUrl) {
        displayCircleCroppedImage(imageView, imageUrl, 0);
    }

    //TODO check bitmap transformation
    // Android sometimes logs "W/Bitmap Called reconfigure on a bitmap that is in use! This may cause graphical corruption!" This is
    // due to the CropCircleTransformation. Further inspection needed.
    public static void displayCircleCroppedImage(ImageView imageView, String imageUrl, @DrawableRes int placeholderResId) {
        if (imageUrl == null) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(placeholderResId)
                .bitmapTransform(new CropCircleTransformation(imageView.getContext()))
                .into(imageView);
    }
}
