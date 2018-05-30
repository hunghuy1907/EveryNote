package com.hungth.everynote.view.home;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hungth.everynote.R;
import com.hungth.everynote.dto.AcountDto;
import com.hungth.everynote.model.AcountDatabase;
import com.hungth.everynote.view.create_acount.CreateAcountFragment;
import com.hungth.everynote.view.create_acount.EditAcountFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 3/2/2018.
 */

public class HomeFragment extends Fragment implements OnItemClickListener {
    public boolean isClickEditBtn = false;
    private View rootView;
    public List<AcountDto> acountDtos;
    private RecyclerView rcvAcount;
    private AcountAdapter acountAdapter;
    private TextView txtFirstText;
    private CreateAcountFragment createAcountFragment;
    private ImageView imgIcon;
    public int temp = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeComponents();
        showAcounts();
    }

    private void showAcounts() {
        acountDtos = new ArrayList<>();
        Cursor cursor = null;
        AcountDatabase acountDatabase = new AcountDatabase(getActivity());
        acountDatabase.open();

        try {
            cursor = acountDatabase.getAllAcountFromDtb();
        } catch (Exception e) {

        }

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                String password = cursor.getString(1);
                String image = cursor.getString(2);
                AcountDto acountDto = new AcountDto(image, name, password);
                acountDtos.add(acountDto);
            } while (cursor.moveToNext());
        }

        acountDatabase.close();
        if (acountDtos.size() > 0) {
            txtFirstText.setText("");
        }
        acountAdapter = new AcountAdapter(getActivity(), acountDtos);
        acountAdapter.setOnItemClickListener(this);
        rcvAcount.setAdapter(acountAdapter);
    }

    private void initializeComponents() {
        rcvAcount = getActivity().findViewById(R.id.rcv_account);
        rcvAcount.setLayoutManager(new LinearLayoutManager(getActivity()));
        txtFirstText = getActivity().findViewById(R.id.txt_first_text);

        int orient = DividerItemDecoration.VERTICAL;
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), orient);
        rcvAcount.addItemDecoration(decoration);



        getActivity().findViewById(R.id.btn_create_acount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showScreenCreateAcount();
            }
        });

    }

    public void showScreenCreateAcount() {
        CreateAcountFragment createAcountFragment = new CreateAcountFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_home, createAcountFragment).addToBackStack(null).commit();
    }


    @Override
    public void onItemClicked(View itemView, int position) {
    }

    @Override
    public void onRemoving(int position) {
        AcountDatabase acountDatabase = new AcountDatabase(getActivity());
        acountDatabase.open();
        String nameAcount = acountDtos.get(position).getNameAcount().toString();
        acountDatabase.deleteAcount(nameAcount);
        showAcounts();
        acountDatabase.close();
    }

    @Override
    public void onEditting(int position) {
        EditAcountFragment editAcountFragment = new EditAcountFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_home, editAcountFragment).addToBackStack(null).commit();

        AcountDto acountDto = acountDtos.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("state", temp);
        bundle.putString("name", acountDto.getNameAcount().toString());
        bundle.putString("password", acountDto.getPassWord().toString());
        bundle.putString("image", acountDto.getImgIconAcount().toString());

        editAcountFragment.setArguments(bundle);
    }

}
