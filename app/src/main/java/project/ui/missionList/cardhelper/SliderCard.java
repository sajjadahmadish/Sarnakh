package project.ui.missionList.cardhelper;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import ir.sinapp.sarnakh.R;
import project.utils.cardslider.DecodeBitmapTask;


public class SliderCard extends RecyclerView.ViewHolder implements DecodeBitmapTask.Listener {

    private static int viewWidth = 0;
    private static int viewHeight = 0;

    private final ImageView imageView;

    private DecodeBitmapTask task;

    public SliderCard(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }

    void setContent(@DrawableRes final int resId) {
        if (viewWidth == 0) {
            itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    viewWidth = itemView.getWidth();
                    viewHeight = itemView.getHeight();
//                    loadBitmap(resId);Glide.with(itemView.getContext()).load(resId).into(imageView);

                    Glide.with(itemView.getContext()).load(resId).override(viewWidth, viewHeight).into(imageView);
                }
            });
        } else {
//            loadBitmap(resId);
            Glide.with(itemView.getContext()).load(resId).override(viewWidth, viewHeight).into(imageView);
        }
    }

    void clearContent() {
        if (task != null) {
            task.cancel(true);
        }
    }

    private void loadBitmap(@DrawableRes int resId) {
        task = new DecodeBitmapTask(itemView.getResources(), resId, viewWidth, viewHeight, this);
        task.execute();
    }

    @Override
    public void onPostExecuted(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

}