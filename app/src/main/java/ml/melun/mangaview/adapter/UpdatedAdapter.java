package ml.melun.mangaview.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ml.melun.mangaview.R;
import ml.melun.mangaview.mangaview.Manga;
import ml.melun.mangaview.mangaview.Title;

import static ml.melun.mangaview.MainApplication.p;

public class UpdatedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<Manga> mData;
    onclickListener olisten;
    boolean save;
    boolean dark;
    private LayoutInflater mInflater;

    public UpdatedAdapter(Context main) {
        super();
        context = main;
        mData = new ArrayList<>();
        save = p.getDataSave();
        dark = p.getDarkTheme();
        this.mInflater = LayoutInflater.from(main);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addData(ArrayList<Manga> data){
        int oSize = mData.size();
        mData.addAll(data);
        notifyItemRangeInserted(oSize,data.size());
    }

    public void setOnClickListener(onclickListener click){
        olisten = click;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_updated_list, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        viewHolder h = (viewHolder) holder;
        Manga m = mData.get(position);
        h.text.setText(m.getName());
        h.date.setText(m.getDate());
        if(m.getThumb().length()>1 && !save) Glide.with(context).load(m.getThumb()).into(h.thumb);
        else h.thumb.setImageBitmap(null);
        if(save) h.thumb.setVisibility(View.GONE);
        if(p.getBookmark(m.getTitle())>0)
            h.seen.setVisibility(View.VISIBLE);
        else
            h.seen.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        TextView text, date;
        ImageView thumb;
        CardView card;
        ImageView seen;
        Button viewEps;
        public viewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.Title);
            date = itemView.findViewById(R.id.date);
            card = itemView.findViewById(R.id.updatedCard);
            thumb = itemView.findViewById(R.id.Thumb);
            viewEps = itemView.findViewById(R.id.epsButton);
            seen = itemView.findViewById(R.id.seenIcon);
            if(dark){
                card.setBackgroundColor(ContextCompat.getColor(context, R.color.colorDarkBackground));
                viewEps.setBackgroundColor(ContextCompat.getColor(context, R.color.resumeDark));
            }
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    olisten.onClick(mData.get(getAdapterPosition()));
                }
            });
            viewEps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    olisten.onEpsClick(mData.get(getAdapterPosition()).getTitle());
                }
            });
        }
    }

    public interface onclickListener {
        void onClick(Manga m);
        void onEpsClick(Title t);
    }
}