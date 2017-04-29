package com.notemon.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.color.CircleView;
import com.notemon.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by emil on 4/28/17.
 */

public class ColorListAdapter extends RecyclerView.Adapter<ColorListAdapter.ColorHolder> {

    private Map<String, Integer> colorMap;
    private List<String> colors;

    public ColorListAdapter(List<String> colors) {
        this.colorMap = new HashMap<>();
        this.colors = colors;

        colorMap.put("red", R.color.project_red);
        colorMap.put("blue", R.color.project_blue);
        colorMap.put("green", R.color.project_green);
        colorMap.put("yellow", R.color.project_yellow);
        colorMap.put("pink", R.color.project_pink);
        colorMap.put("purple", R.color.project_purple);
        colorMap.put("teal", R.color.project_teal);
        colorMap.put("orange", R.color.project_orange);
        colorMap.put("lime", R.color.project_lime);
    }

    @Override
    public ColorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_item, parent, false);
        return new ColorHolder(view);
    }

    @Override
    public void onBindViewHolder(final ColorHolder holder, final int position) {
//        Project project = projects.get(position);

        holder.name.setText(colors.get(position));
        holder.circleView.setImageResource(colorMap.get(colors.get(position)));

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.linearLayout.setSelected(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    public class ColorHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.colorItemLayout)
        LinearLayout linearLayout;

        @BindView(R.id.colorNameTextView)
        TextView name;

        @BindView(R.id.colorImageView)
        CircleImageView circleView;

        public ColorHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
