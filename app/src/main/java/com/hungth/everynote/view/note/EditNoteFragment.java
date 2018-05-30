package com.hungth.everynote.view.note;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.hungth.everynote.R;
import com.hungth.everynote.model.AcountDatabase;
import com.hungth.everynote.model.NoteDatabase;
import com.hungth.everynote.view.home.HomeActivity;
import com.hungth.everynote.view.home.HomeFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import yuku.ambilwarna.AmbilWarnaDialog;

import static com.hungth.everynote.R.id.item_option;
import static com.hungth.everynote.R.id.item_reminder;

/**
 * Created by Admin on 4/12/2018.
 */

public class EditNoteFragment extends Fragment {
    private View view;
    private EditText edtTitle;
    private EditText edtContent;
    private int colorBackground;
    private int colorText;
    private int oldColorText;
    private int oldColorBackground;
    private int id;
    private String oldTitle;
    private String oldContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_create_note, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeComponents();
    }

    private void initializeComponents() {
        android.support.v7.app.ActionBar actionBar = ((HomeActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        edtTitle = getActivity().findViewById(R.id.edt_title);
        edtContent = getActivity().findViewById(R.id.edt_content);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        Bundle bundle = getArguments();
        colorText = bundle.getInt("colorText");
        oldColorText = colorText;
        oldColorBackground = colorBackground;
        colorBackground = bundle.getInt("colorBackground");
        id = bundle.getInt("id");
        oldTitle = bundle.getString("title");
        oldContent = bundle.getString("content");
        edtTitle.setText(oldTitle);
        edtContent.setText(oldContent);
        edtTitle.setTextColor(colorText);
        edtContent.setTextColor(colorText);
        getActivity().findViewById(R.id.layout_title).setBackgroundColor(colorBackground);
        getActivity().findViewById(R.id.layout_content).setBackgroundColor(colorBackground);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        getActivity().setTitle("Chỉnh sửa ghi chú");
        inflater.inflate(R.menu.edit_note, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_tick_edit:
                hideKeyboard(getActivity());
                updateNote();
                getActivity().onBackPressed();
                return true;

            case R.id.item_option_edit:
                option();
                return true;

            case R.id.item_reminder_edit:
                showReminder();
                return true;

            case R.id.item_delete_edit:
                deleteNote();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteNote() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông báo").setMessage("Bạn có chắc chắn muốn xóa không?")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final NoteDatabase noteDatabase = new NoteDatabase(getActivity());
                        noteDatabase.open();
                        Toast.makeText(getActivity(), "Đã xóa", Toast.LENGTH_SHORT).show();
                        noteDatabase.deleteNote(id);
                        noteDatabase.close();
                        getActivity().onBackPressed();
                        dialog.dismiss();
                    }
                });
        builder.setPositiveButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void updateNote() {
        NoteDatabase noteDatabase = new NoteDatabase(getActivity());
        noteDatabase.open();
        String newTitle = edtTitle.getText().toString();
        String newContent = edtContent.getText().toString();
        noteDatabase.updateNote(id, newTitle, newContent, colorBackground, colorText);
        noteDatabase.close();

        if (!newTitle.equals(oldTitle) || !newContent.equals(oldContent) || oldColorText != colorText
                || oldColorBackground != colorBackground) {

            Toast.makeText(getActivity(), "đã thay đổi", Toast.LENGTH_SHORT).show();
        } else {

        }
    }

    private void showReminder() {
        PopupMenu popupMenu = new PopupMenu(getActivity(), getActivity().findViewById(R.id.item_reminder_edit));

        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popupMenu.getMenuInflater().inflate(R.menu.reminder, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_arlarm:

                        return true;

                    case R.id.item_gmail:

                        return true;


                    case R.id.item_screen:

                        return true;

                }
                return false;
            }
        });
        popupMenu.show();
    }


    private void option() {
        PopupMenu popupMenu = new PopupMenu(getActivity(), getActivity().findViewById(R.id.item_option_edit));
        popupMenu.getMenuInflater().inflate(R.menu.menu_option, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_color_background:
                        changeColorBackground();
                        return true;

                    case R.id.item_text_color:
                        changeTextColor();
                        return true;

                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void changeColorBackground() {
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(getActivity(), colorBackground,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        colorBackground = color;
                        getActivity().findViewById(R.id.layout_title).setBackgroundColor(colorBackground);
                        getActivity().findViewById(R.id.layout_content).setBackgroundColor(colorBackground);
                    }
                });
        ambilWarnaDialog.show();

    }

    private void changeTextColor() {
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(getActivity(), colorBackground,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        colorText = color;
                        EditText edtTitle = getActivity().findViewById(R.id.edt_title);
                        edtTitle.setTextColor(colorText);

                        EditText edtContent = getActivity().findViewById(R.id.edt_content);
                        edtContent.setTextColor(colorText);

                    }
                });
        ambilWarnaDialog.show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
