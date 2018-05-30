package com.hungth.everynote.view.note;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.ContextMenu;
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

import com.hungth.everynote.MainActivity;
import com.hungth.everynote.R;
import com.hungth.everynote.model.AcountDatabase;
import com.hungth.everynote.model.NoteDatabase;
import com.hungth.everynote.view.home.HomeActivity;
import com.hungth.everynote.view.home.HomeFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import yuku.ambilwarna.AmbilWarnaDialog;

import static com.hungth.everynote.R.id.cancel_action;
import static com.hungth.everynote.R.id.item_option;
import static com.hungth.everynote.R.id.item_reminder;
import static com.hungth.everynote.R.string.app_name;
import static com.hungth.everynote.R.string.navigation_drawer_close;

/**
 * Created by Admin on 4/9/2018.
 */

public class CreateNoteFragment extends Fragment {
    private View view;
    private EditText edtTitle;
    private EditText edtContent;
    private int colorBackground;
    private int colorText;
    private int id;

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
        colorBackground = Color.WHITE;
        colorText = Color.BLACK;

        edtTitle = getActivity().findViewById(R.id.edt_title);
        edtContent = getActivity().findViewById(R.id.edt_content);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        Bundle bundle = getArguments();
        id = bundle.getInt("createId");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        getActivity().setTitle("Ghi chú mới");
        inflater.inflate(R.menu.menu_create_note, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_tick:
                hideKeyboard(getActivity());
                addNote();
                id++;
                getActivity().onBackPressed();
                return true;

            case item_option:
                option();
                return true;

            case item_reminder:
                showReminder();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showReminder() {
        PopupMenu popupMenu = new PopupMenu(getActivity(), getActivity().findViewById(R.id.item_reminder));

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

    public void addNote() {
        String title = edtTitle.getText().toString();
        if (title.length() >= 30) {
            title = title.substring(0, 30) + "...";
        }

        String content = edtContent.getText().toString();

        if (content.length() >= 15) {
            content = content.substring(0, 15) + "...";
        }

        if (title.isEmpty() && content.isEmpty()) {
            Toast.makeText(getActivity(), "Chưa được lưu", Toast.LENGTH_LONG).show();
            return;
        } else {
            Toast.makeText(getActivity(), "Thêm ghi chú thành công", Toast.LENGTH_LONG).show();
        }
        NoteDatabase noteDatabase = new NoteDatabase(getActivity());
        noteDatabase.open();
        try {
            noteDatabase.insertNote(title, content, colorBackground, colorText, id, "0");
        } catch (Exception e) {

        }
        noteDatabase.close();
    }

    private void option() {
        PopupMenu popupMenu = new PopupMenu(getActivity(), getActivity().findViewById(R.id.item_option));
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
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
