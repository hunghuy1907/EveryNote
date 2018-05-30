package com.hungth.everynote.view.home;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hungth.everynote.R;
import com.hungth.everynote.dto.AcountDto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 3/2/2018.
 */

public class AcountAdapter extends RecyclerView.Adapter<AcountAdapter.ViewHolder>{
    private static final String TAG = "tag";
    private LayoutInflater inflater;
    private Context context;
    private List<AcountDto> acountDtos;
    private OnItemClickListener onItemClickListener;
    private PopupMenu popupMenu;
    private HomeFragment homeFragment;

    public AcountAdapter(Context context, List<AcountDto> acountDtos) {
        this.context = context;
        this.acountDtos = acountDtos;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_account, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        homeFragment = new HomeFragment();
        AcountDto acountDto = acountDtos.get(position);

        holder.imgIconAcount.setImageResource(Integer.parseInt(acountDto.getImgIconAcount()));
        holder.txtNameAcount.setText(acountDto.getNameAcount());
        holder.txtDateCreateAcount.setText(getDateCurrent());
        holder.btnMenuAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu = new PopupMenu(context, holder.btnMenuAcount);
                popupMenu.getMenuInflater().inflate(R.menu.custom_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.item_delete_acount:
                                onItemClickListener.onRemoving(holder.getAdapterPosition());
                                return true;

                            case R.id.item_edit_acount:
                                onItemClickListener.onEditting(holder.getAdapterPosition());
                                return true;

                                default:
                                    return false;
                        }
                    }
                });

                popupMenu.show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return acountDtos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgIconAcount;
        private TextView txtNameAcount;
        private TextView txtDateCreateAcount;
        private Button btnMenuAcount;

        public ViewHolder(View itemView) {
            super(itemView);

            imgIconAcount = itemView.findViewById(R.id.img_icon_acount);
            txtNameAcount = itemView.findViewById(R.id.txt_name_acount);
            txtDateCreateAcount = itemView.findViewById(R.id.txt_date_create_acount);
            btnMenuAcount = itemView.findViewById(R.id.btn_menu_acount);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private String getDateCurrent() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
