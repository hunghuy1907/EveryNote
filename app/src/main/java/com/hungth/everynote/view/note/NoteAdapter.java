package com.hungth.everynote.view.note;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hungth.everynote.R;
import com.hungth.everynote.dto.NoteDto;
import com.hungth.everynote.dto.NoteDto;
import com.hungth.everynote.view.home.AcountAdapter;
import com.hungth.everynote.view.home.HomeFragment;
import com.hungth.everynote.view.home.OnItemClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 4/1/2018.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<NoteDto> notes;
    private OnLongItemCLickListener onLongItemCLickListener;
    private OnCLickItemListenerNote onCLickItemListenerNote;
    public boolean isInVissible = false;

    public NoteAdapter(Context context, List<NoteDto> notes) {
        this.context = context;
        this.notes = notes;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_note, parent, false);
        return new NoteAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final NoteAdapter.ViewHolder holder, final int position) {
        NoteDto noteDto = notes.get(position);

        holder.txtTitle.setText(noteDto.getTitle());
        holder.txtTitle.setTextColor(noteDto.getColorText());
        holder.txtContent.setText(noteDto.getContent());
        holder.txtContent.setTextColor(noteDto.getColorText());
        holder.relativeLayout.setBackgroundColor(noteDto.getColorBackground());

        holder.txtDateCreateAcount.setText(getDateCurrent());
//        holder.imgPin.setVisibility(isVisible());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongItemCLickListener.choose(holder.getAdapterPosition());
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLickItemListenerNote.edit(holder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return notes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtContent;
        private TextView txtDateCreateAcount;
        private RelativeLayout relativeLayout;
        private ImageView imgPin;

        public ViewHolder(View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.item_note_relative);
            txtTitle = itemView.findViewById(R.id.txt_title_note);
            txtContent = itemView.findViewById(R.id.txt_content_note);
            txtDateCreateAcount = itemView.findViewById(R.id.txt_date_create_acount_note);
            imgPin = itemView.findViewById(R.id.img_pin);
        }
    }

    private String getDateCurrent() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public int isVisible() {
        if (!isInVissible)
            return View.VISIBLE;
        return View.INVISIBLE;
    }

    public void setOnLongItemCLickListener(OnLongItemCLickListener onLongItemCLickListener) {
        this.onLongItemCLickListener = onLongItemCLickListener;
    }

    public void setOnCLickItemListenerNote(OnCLickItemListenerNote onCLickItemListenerNote) {
        this.onCLickItemListenerNote = onCLickItemListenerNote;
    }

}
