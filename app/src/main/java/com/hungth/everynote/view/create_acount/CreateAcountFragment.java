package com.hungth.everynote.view.create_acount;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hungth.everynote.R;
import com.hungth.everynote.dto.AcountDto;
import com.hungth.everynote.model.AcountDatabase;
import com.hungth.everynote.view.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Admin on 3/3/2018.
 */

public class CreateAcountFragment extends Fragment implements View.OnClickListener {
    private static final int[] icons = new int[]{R.drawable.ic_add, R.drawable.ic_avatar, R.drawable.ic_blue,
            R.drawable.ic_three_dots, R.drawable.ic_blue};
    private Gallery gallery;
    private GallaryAdapter gallaryAdapter;

    public ImageView imgIconAcount;
    public EditText edtNameAcount;
    public EditText edtPassword;
    private EditText edtConfirmPassword;
    private Button btnAddAcount;
    private Button btnAddIcon;
    private Button btnShowGallary;
    public static List<AcountDto> acountDtos = new ArrayList<>();
    private int imgId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_account, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeComponents();
    }

    private void goneGallary() {
        LinearLayout layoutGallary = getActivity().findViewById(R.id.linearLayout_gallary);
        layoutGallary.setVisibility(View.GONE);
    }

    private void initializeComponents() {

        gallery = getActivity().findViewById(R.id.gallery_icon);
        gallery.setSpacing(2);
        gallaryAdapter = new GallaryAdapter(getActivity(), icons);
        gallery.setAdapter(gallaryAdapter);
        goneGallary();

        imgIconAcount = getActivity().findViewById(R.id.img_choose_icon);
        edtNameAcount = getActivity().findViewById(R.id.edt_name_acount);
        edtPassword = getActivity().findViewById(R.id.edt_password);
        edtConfirmPassword = getActivity().findViewById(R.id.edt_confirm_password);
        btnAddAcount = getActivity().findViewById(R.id.btn_add_acount);
        btnAddIcon = getActivity().findViewById(R.id.btn_add_icon);
        btnShowGallary = getActivity().findViewById(R.id.btn_show_gallery);

        chooseIconFromGalery();

        btnAddAcount.setOnClickListener(this);
        btnAddIcon.setOnClickListener(this);
        btnShowGallary.setOnClickListener(this);
    }

    public void addAcount() {
        String nameAcount = edtNameAcount.getText().toString();
        String password = edtPassword.getText().toString();

        AcountDatabase acountDatabase = new AcountDatabase(getActivity());
        acountDatabase.open();
        long result = 0;
        try {
            result = acountDatabase.insertAcount(imgId + "", nameAcount, password);
        } catch (Exception e) {

        }


        acountDatabase.close();
        HomeFragment homeFragment = new HomeFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_home, homeFragment).commit();
    }


    private void check() {
        if (edtNameAcount.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Vui lòng nhập tên mật khẩu.", Toast.LENGTH_LONG).show();
            return;
        }

        if (edtPassword.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Vui lòng nhập mật khảu .", Toast.LENGTH_LONG).show();
            return;
        }


        if (edtConfirmPassword.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Vui lòng nhập lại mật khảu.", Toast.LENGTH_LONG).show();
            return;
        }

        if (!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
            Toast.makeText(getActivity(), "Vui lòng nhập lại đúng mật khẩu", Toast.LENGTH_LONG).show();
            return;
        }


        addAcount();

    }

    public static List<AcountDto> getAcountDtos() {
        return acountDtos;
    }

    private void showGallery() {
        LinearLayout layoutGallary = getActivity().findViewById(R.id.linearLayout_gallary);

        if (layoutGallary.getVisibility() == View.GONE) {
            layoutGallary.setVisibility(View.VISIBLE);
        } else {
            layoutGallary.setVisibility(View.GONE);
        }
    }

    private void chooseIconFromGalery() {
        imgIconAcount.setImageResource(icons[0]);
        imgId = icons[0];
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imgIconAcount.setImageResource(icons[position]);
                goneGallary();
                imgId = icons[position];
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_acount:
                check();
                break;

            case R.id.btn_show_gallery:
                showGallery();
                break;

            case R.id.img_choose_icon:
                showGallery();
                break;

            case R.id.btn_add_icon:
                addIcon();
                break;

            default:
                break;
        }

    }

    private void addIcon() {

    }
}
