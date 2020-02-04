package project.adapter.listeners;

import android.view.View;

import androidx.annotation.Nullable;

public interface OnClickListener<Item> {
    /**
     * the onClick event of a specific item inside the RecyclerView
     *
     * @param v        the view we clicked
     * @param item     the IItem which was clicked
     * @param position the global position
     * @return true if the event was consumed, otherwise false
     */
    void onClick(@Nullable View v, Item item, int position);
}