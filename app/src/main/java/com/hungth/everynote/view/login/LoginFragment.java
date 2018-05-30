package com.hungth.everynote.view.login;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hungth.everynote.R;
import com.hungth.everynote.model.AcountDatabase;
import com.hungth.everynote.view.home.HomeActivity;


/**
 * Created by Admin on 2/28/2018.
 */

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class LoginFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private TextView btnChangePassword;
    private Button btnLogin;
    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnDelete;
    private Button btnClear;

    private TextView txtPassword;
    private String password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initializeComponents();



        Bundle bundle = getArguments();
        if (bundle != null) {
            txtPassword.setText(bundle.getString("new_password"));
        }
        password = txtPassword.getText().toString();
    }

    private void initializeComponents() {
        txtPassword = getActivity().findViewById(R.id.txt_password);

        btnChangePassword = getActivity().findViewById(R.id.btn_change_password);
        btn0 = getActivity().findViewById(R.id.btn_0);
        btn1 = getActivity().findViewById(R.id.btn_1);
        btn2 = getActivity().findViewById(R.id.btn_2);
        btn3 = getActivity().findViewById(R.id.btn_3);
        btn4 = getActivity().findViewById(R.id.btn_4);
        btn5 = getActivity().findViewById(R.id.btn_5);
        btn6 = getActivity().findViewById(R.id.btn_6);
        btn7 = getActivity().findViewById(R.id.btn_7);
        btn8 = getActivity().findViewById(R.id.btn_8);
        btn9 = getActivity().findViewById(R.id.btn_9);
        btnDelete = getActivity().findViewById(R.id.btn_delete);
        btnClear = getActivity().findViewById(R.id.btn_clear);
        btnLogin = getActivity().findViewById(R.id.btn_log_in);


        btnDelete.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        firstLogin();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_0:
                countOfPassword();
                password += 0;
                txtPassword.setText(password);
                break;

            case R.id.btn_1:
                countOfPassword();
                password += 1;
                txtPassword.setText(password);
                break;

            case R.id.btn_2:
                countOfPassword();
                password += 2;
                txtPassword.setText(password);
                break;

            case R.id.btn_3:
                countOfPassword();
                password += 3;
                txtPassword.setText(password);
                break;

            case R.id.btn_4:
                countOfPassword();
                password += 4;
                txtPassword.setText(password);
                break;

            case R.id.btn_5:
                countOfPassword();
                password += 5;
                txtPassword.setText(password);
                break;

            case R.id.btn_6:
                countOfPassword();
                password += 6;
                txtPassword.setText(password);
                break;

            case R.id.btn_7:
                countOfPassword();
                password += 7;
                txtPassword.setText(password);
                break;

            case R.id.btn_8:
                countOfPassword();
                password += 8;
                txtPassword.setText(password);
                break;

            case R.id.btn_9:
                countOfPassword();
                password += 9;
                txtPassword.setText(password);
                break;

            case R.id.btn_delete:
                countOfPassword();
                password = password.substring(0, password.length() - 1);
                txtPassword.setText(password);
                break;

            case R.id.btn_clear:
                countOfPassword();
                password = "";
                txtPassword.setText(password);
                break;

            case R.id.btn_log_in:
                if (password.length() == 0 ) {
                    Toast.makeText(getActivity(), "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT ).show();
                    return;
                }

                if (password.length() < 4) {
                    Toast.makeText(getActivity(), "Mật khẩu phải có ít nhất 4 kí tự", Toast.LENGTH_SHORT).show();
                    return;
                }
                AcountDatabase acountDatabase = new AcountDatabase(getActivity());

                acountDatabase.open();
                if (acountDatabase.isFirst()) {
                   acountDatabase.changePassword(password);
                    Toast.makeText(getActivity(), "Mật khẩu là: " + password, Toast.LENGTH_SHORT).show();
                    showScreenHome();
                } else {
                    if (acountDatabase.checkPassword(password)) {
                        showScreenHome();
                    } else {
                        Toast.makeText(getActivity(), "Bạn đã nhập sai mật khẩu", Toast.LENGTH_SHORT).show();
                        password = "";
                        txtPassword.setText(password);
                    }
                }
                acountDatabase.close();

                break;

            case R.id.btn_change_password:

                changePassword();
                break;

            default:
                break;
        }
    }

    private void countOfPassword() {
        if (password.length() >= 6)
            return;
    }


    private void changePassword() {
        ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
        final Bundle bundle = new Bundle();
        bundle.putString("old_password", password);
        changePasswordFragment.setArguments(bundle);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, changePasswordFragment).addToBackStack(null).commit();
    }

    private void firstLogin() {
        AcountDatabase acountDatabase = new AcountDatabase(getActivity());

        acountDatabase.open();
        if (acountDatabase.isFirst()) {
            Toast.makeText(getActivity(), "Đây là lần đầu", Toast.LENGTH_LONG).show();

        }
        acountDatabase.close();
    }

    private void showScreenHome() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
    }

}
