package com.hungth.everynote.view.note;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hungth.everynote.R;
import com.hungth.everynote.dto.AcountDto;
import com.hungth.everynote.dto.NoteDto;
import com.hungth.everynote.model.AcountDatabase;
import com.hungth.everynote.model.NoteDatabase;
import com.hungth.everynote.view.create_acount.CreateAcountFragment;
import com.hungth.everynote.view.create_acount.EditAcountFragment;
import com.hungth.everynote.view.home.AcountAdapter;
import com.hungth.everynote.view.home.HomeFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 4/1/2018.
 */

public class NoteFragment extends Fragment implements OnLongItemCLickListener, OnCLickItemListenerNote {
    private View rootView;
    public List<NoteDto> noteDtos;
    private RecyclerView rcvNote;
    private NoteAdapter noteAdapter;
    private TextView txtFirstText;
    private Button btnCreateNote;
    private CreateNoteFragment createNoteFragment;
    public int id;
    private int idLongClick;
    private String title;
    private Dialog dialog;
    private Dialog dialogReminder;
    private NoteDatabase noteDatabase;

    private DatePicker datePicker;
    private TimePicker timePicker;

    private String date;
    private String newDate;
    private String time;
    private String newTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_note, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeComponents();
        showAcounts();
    }

    private void showAcounts() {
        noteDtos = new ArrayList<>();
        Cursor cursor = null;
        NoteDatabase noteDatabase = new NoteDatabase(getActivity());
        noteDatabase.open();

        try {
            cursor = noteDatabase.getAllNoteFromDtb();
        } catch (Exception e) {

        }

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(0);
                String content = cursor.getString(1);
                int color = cursor.getInt(2);
                int colorText = cursor.getInt(3);
                int id = cursor.getInt(4);
//                String alarm = cursor.getString(5);
//                Toast.makeText(getActivity(), alarm, Toast.LENGTH_SHORT).show();
                NoteDto noteDto = new NoteDto(title, content, color, colorText, id);
                noteDtos.add(noteDto);
            } while (cursor.moveToNext());
        }

        noteDatabase.close();
        if (noteDtos.size() > 0) {
            txtFirstText.setText("");
        }
        noteAdapter = new NoteAdapter(getActivity(), noteDtos);
        noteAdapter.setOnLongItemCLickListener(this);
        noteAdapter.setOnCLickItemListenerNote(this);
        rcvNote.setAdapter(noteAdapter);
    }

    private void initializeComponents() {
        NoteDatabase noteDatabase = new NoteDatabase(getActivity());
        noteDatabase.open();
        Cursor cursor = noteDatabase.getAllNoteFromDtb();
        if (cursor.getCount() == 0) {
            id = 0;
        } else {
            cursor.moveToLast();
            id = cursor.getInt(4);
        }

        createNoteFragment = new CreateNoteFragment();
        rcvNote = getActivity().findViewById(R.id.rcv_note);
        rcvNote.setLayoutManager(new LinearLayoutManager(getActivity()));
        txtFirstText = getActivity().findViewById(R.id.txt_first_text);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        btnCreateNote = getActivity().findViewById(R.id.btn_create_note);
        btnCreateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendId();
                showScreenCreateNote();
            }
        });

        int orient = DividerItemDecoration.VERTICAL;
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), orient);
        rcvNote.addItemDecoration(decoration);


    }

    private void showScreenCreateNote() {
        if (createNoteFragment == null) {
            createNoteFragment = new CreateNoteFragment();
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_home, createNoteFragment).addToBackStack(null).commit();
    }

    private void sendId() {
        if (createNoteFragment == null) {
            createNoteFragment = new CreateNoteFragment();
        }

        id++;
        Bundle bundle = new Bundle();
        bundle.putInt("createId", id);
        createNoteFragment.setArguments(bundle);
    }

    @Override
    public void choose(int position) {
        title = noteDtos.get(position).getTitle();
        idLongClick = noteDtos.get(position).getId();
//        Toast.makeText(getActivity(), idLongClick, Toast.LENGTH_SHORT).show();
        showDialogNote(position);
    }

    public void makeCopy(int position) {
        if (noteDatabase == null) {
            noteDatabase = new NoteDatabase(getActivity());
        }

        String titleCopy = noteDtos.get(position).getTitle();
        String contentCopy = noteDtos.get(position).getContent();
        int colorBackgroundCopy = noteDtos.get(position).getColorBackground();
        int colorTextCopy = noteDtos.get(position).getColorText();

        noteDatabase = new NoteDatabase(getActivity());
        noteDatabase.open();
        Cursor cursor = noteDatabase.getAllNoteFromDtb();
        int idCopy;

        cursor.moveToLast();

        idCopy = cursor.getInt(4);
        Toast.makeText(getActivity(), "idCopy: " + id, Toast.LENGTH_SHORT).show();
        try {
            noteDatabase.insertNote(titleCopy, contentCopy, colorBackgroundCopy, colorTextCopy, idCopy + 1, "0");
            NoteFragment noteFragment = new NoteFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_home, noteFragment).commit();
        } catch (Exception e) {

        }
        noteDatabase.close();
    }


    @Override
    public void edit(int position) {
        id++;
        EditNoteFragment editNoteFragment = new EditNoteFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_home, editNoteFragment).addToBackStack(null).commit();

        NoteDto noteDto = noteDtos.get(position);
        String title = noteDto.getTitle();
        String content = noteDto.getContent();
        int colorBackground = noteDto.getColorBackground();
        int colorText = noteDto.getColorText();
        int id = noteDto.getId();

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("content", content);
        bundle.putInt("colorBackground", colorBackground);
        bundle.putInt("colorText", colorText);
        bundle.putInt("id", id);
        editNoteFragment.setArguments(bundle);
    }

    private void showDialogNote(final int position) {
        if (dialog == null) {
            dialog = new Dialog(getActivity());
        }
        dialog.setContentView(R.layout.elert_dialog);
        dialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = (int) (getWithScreen(getActivity()) * 0.8);
        lp.height = (int) (getHeightScreen(getActivity()) * 0.75);
        window.setAttributes(lp);

        TextView txtTitle = dialog.findViewById(R.id.txt_dialog_title);
        txtTitle.setText(title);

        setClickDialog(position);

    }

    private void setClickDialog(final int position) {
        dialog.findViewById(R.id.layout_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                edit(position);
            }
        });

        dialog.findViewById(R.id.layout_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                deleteNote();
            }
        });

        dialog.findViewById(R.id.layout_reminder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminder();
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.layout_coppy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCopy(position);
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.layout_pin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                pin(position);
                dialog.dismiss();
            }
        });
    }

    private void alarm() {
        final Dialog dialogAlarm = new Dialog(getActivity());
        dialogAlarm.setContentView(R.layout.dialog_alarm);
        dialogAlarm.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialogReminder.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = (int) (getWithScreen(getActivity()) * 0.8);
        lp.height = (int) (getHeightScreen(getActivity()) * 0.3);
        window.setAttributes(lp);

        TextView txtDateAlarm = dialogAlarm.findViewById(R.id.txt_alarm_date);
        TextView txtTimeAlarm = dialogAlarm.findViewById(R.id.txt_alarm_time);

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        if (date == null) {
            if (dayOfWeek == 1) {
                date = "chủ nhật " + " " + dayOfMonth + "/" + month + "/" + year;
            } else {
                date = "Thứ " + " " + dayOfWeek + dayOfMonth + "/" + month + "/" + year;
            }
        }

        if (time == null) {
            if (minute < 10) {
                time = hour + " : 0" + minute;
            } else {
                time = hour + " : " + minute;
            }
        }
        txtDateAlarm.setText(date);
        txtTimeAlarm.setText(time);

        txtDateAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDateAlarm();
                dialogAlarm.dismiss();
                ;
            }
        });

        txtTimeAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTimeAlarm();
                dialogAlarm.dismiss(); 
            }
        });

        setAlarm(dialogAlarm);

    }

    private void setAlarm(final Dialog dialog) {
        dialog.findViewById(R.id.btn_alarm_destroy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.btn_alarm_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteDatabase noteDatabase = new NoteDatabase(getActivity());
                noteDatabase.open();
                int idAlarm = noteDtos.get(idLongClick).getId();
//                noteDatabase.updateTimeAlarm(idLongClick, date + time);
                Toast.makeText(getActivity(), idAlarm, Toast.LENGTH_SHORT).show();
                noteDatabase.close();

                Intent intent = new Intent(getActivity(), ServiceNote.class);
                intent.putExtra("alarm", idLongClick);
                intent.setAction("intentAlarm");
                getActivity().startService(intent);
            }
        });
    }

    private void chooseTimeAlarm() {
        final Dialog dialogTime = new Dialog(getActivity());
        dialogTime.setContentView(R.layout.dialog_timepicker);

        timePicker = dialogTime.findViewById(R.id.tpk_alarm);
        dialogTime.show();

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (minute < 10) {
                    newTime = hourOfDay + " : 0" + minute;
                } else {
                    newTime = hourOfDay + ":" + minute;
                }
            }
        });

        dialogTime.findViewById(R.id.btn_alarm_tpk_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTime.dismiss();
                time = newTime;
                alarm();
            }
        });

        dialogTime.findViewById(R.id.btn_alarm_tpk_destroy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTime.dismiss();
                alarm();
            }
        });
    }

    private void chooseDateAlarm() {
        final Dialog dialogDate = new Dialog(getActivity());
        dialogDate.setContentView(R.layout.dialog_datepicker);

        datePicker = dialogDate.findViewById(R.id.dpk_alarm);
        dialogDate.show();

        Calendar calendar = Calendar.getInstance();

        int yearCa = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(yearCa, month, dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                Date dateWeek = new Date(year, monthOfYear, dayOfMonth - 1);
                String dayOfWeek = simpledateformat.format(dateWeek);
                newDate = dayOfWeek + " " + dayOfMonth + "/" + monthOfYear + "/" + year;
            }
        });

        dialogDate.findViewById(R.id.btn_alarm_dpk_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDate.dismiss();
                date = newDate;
                alarm();
            }
        });

        dialogDate.findViewById(R.id.btn_alarm_dpk_destroy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDate.dismiss();
                alarm();
            }
        });
    }

//    private void pin(int position) {
//        NoteDto noteDto = noteDtos.get(position);
//        if (noteDatabase == null) {
//            noteDatabase = new NoteDatabase(getActivity());
//        }
//
//        noteDatabase.open();
////        noteDatabase.deleteNote(noteDto.getId());
//        noteDatabase.insertNote(noteDto.getTitle(), noteDto.getContent(), noteDto.getColorBackground(),
//                noteDto.getColorText(), noteDto.getId(), "0");
//        noteAdapter.isInVissible = true;
//
//        NoteFragment noteFragment = new NoteFragment();
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frame_home, noteFragment).commit();
//
//        noteDatabase.close();
//    }

    private void reminder() {
        if (dialogReminder == null) {
            dialogReminder = new Dialog(getActivity());
        }
        dialogReminder.setContentView(R.layout.dialog_reminder);
        dialogReminder.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialogReminder.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = getWithScreen(getActivity()) / 7 * 6;
        lp.height = (int) (getHeightScreen(getActivity()) * 0.4);
        window.setAttributes(lp);

        dialogReminder.findViewById(R.id.layout_dialog_reminder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm();
                dialogReminder.dismiss();
            }
        });

    }

    private void deleteNote() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông báo").setMessage("Bạn có chắc chắn muốn xóa không?")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final NoteDatabase database = new NoteDatabase(getActivity());
                        database.open();
                        Toast.makeText(getActivity(), "Đã xóa", Toast.LENGTH_SHORT).show();
                        database.deleteNote(idLongClick);
                        database.close();
                        NoteFragment noteFragment = new NoteFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_home, noteFragment).commit();
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

    public int getWithScreen(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public int getHeightScreen(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }
}
