package com.hungth.everynote.view.login;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.hungth.everynote.R;
import com.hungth.everynote.model.AcountDatabase;

/**
 * Created by Admin on 3/1/2018.
 */

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class ChangePasswordFragment extends Fragment {
    private View rootView;
    private EditText edtOldPassword;
    private EditText edtNewPassword;
    private EditText edtConfirmPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_register, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeComponents();

        Bundle bundle = getArguments();
        edtOldPassword.setText(bundle.getString("old_password"));
    }

    private void initializeComponents() {
        edtOldPassword = getActivity().findViewById(R.id.edt_old_password);
        edtNewPassword = getActivity().findViewById(R.id.edt_new_password);
        edtConfirmPassword = getActivity().findViewById(R.id.edt_confirm_password);

        getActivity().findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private void changePassword() {

        if (edtOldPassword.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Vui lòng nhập mật khảu cũ.", Toast.LENGTH_LONG).show();
            return;
        }


        if (edtNewPassword.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Vui lòng nhập mật khảu mới.", Toast.LENGTH_LONG).show();
            return;
        }


        if (edtConfirmPassword.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Vui lòng nhập lại mật khảu mới.", Toast.LENGTH_LONG).show();
            return;
        }

        AcountDatabase acountDatabase = new AcountDatabase(getActivity());
        acountDatabase.open();
        if (acountDatabase.isFirst()) {
            Toast.makeText(getActivity(), "Bạn chưa đặt mật khẩu", Toast.LENGTH_LONG).show();
            registerPassword();
            return;
        }

        if (acountDatabase.checkPassword(edtOldPassword.getText().toString())) {
            if (!edtNewPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
                Toast.makeText(getActivity(), "Vui lòng nhập lại đúng mật khẩu mới", Toast.LENGTH_LONG).show();
                return;
            } else {
                acountDatabase.changePassword(edtNewPassword.getText().toString());
                Toast.makeText(getActivity(), "Thau đổi mật khẩu thành công", Toast.LENGTH_LONG).show();
                registerPassword();
            }
        } else {
            Toast.makeText(getActivity(), "Bạn nhập sai mật khẩu cũ", Toast.LENGTH_LONG).show();
        }

        acountDatabase.close();



    }

    private void registerPassword() {
        LoginFragment loginFragment = new LoginFragment();
        final Bundle bundle = new Bundle();
        bundle.putString("new_password", edtNewPassword.getText().toString());
        loginFragment.setArguments(bundle);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, loginFragment).commit();
    }

}
