package project.adapter;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class DetailSliderAdapter extends SliderAdapter {

    public DetailSliderAdapter(List<String> list) {
        this.list = list;
    }

    private List<String> list;

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        if (list != null)
            imageSlideViewHolder.bindImageSlide(list.get(position));
    }

}
