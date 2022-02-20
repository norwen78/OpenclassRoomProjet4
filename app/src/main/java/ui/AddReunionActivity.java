package ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import di.DI;
import model.Participant;
import model.Reunion;
import model.Room;
import service.DummyReunionGenerator;
import service.ReunionApiService;

public class AddReunionActivity extends AppCompatActivity {
    public EditText mEditTextSubject;
    public Spinner mRoomSpinner;
    public Button mCancel;
    public Button mCreate;
    public EditText mEditTextDate;
    public EditText mEditTextTimeEnd;
    public EditText mEditTextTimeStart;
    public MultiAutoCompleteTextView mAutocompleteParticipants;
    public TextView mTextViewRoom;

    public int selectedYear;
    public int selectedMonth;
    public int selectedDay;
    public int selectedHourStart;
    public int selectedMinuteStart;
    public int selectedMinuteEnd;
    public int selectedHourEnd;

    RecyclerView mRecyclerView;
    private ReunionApiService mApiService;
    public DatePickerDialog mDatePickerDialog;
    public TimePickerDialog mTimeEndPickerDialog;
    public TimePickerDialog mTimeStartPickerDialog;
    Calendar calendar = Calendar.getInstance();
    Calendar calendarStart = Calendar.getInstance();
    Calendar calendarEnd = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reunion);

        mApiService = DI.getReunionApiService();
        mRecyclerView = findViewById(R.id.reunion_list);
        mRoomSpinner = findViewById(R.id.spinner_room_add_reunion);
        mEditTextSubject = findViewById(R.id.select_subject);
        mCancel = findViewById(R.id.button_cancel);
        mCreate = findViewById(R.id.button_validate);
        mEditTextDate = findViewById(R.id.select_date);
        mAutocompleteParticipants = findViewById(R.id.participant);
        mEditTextTimeStart = findViewById(R.id.select_hour_start);
        mEditTextTimeEnd = findViewById(R.id.select_hour_end);
        mTextViewRoom = findViewById(R.id.spinner_text_view);

        List<String> area = Room.getRoom();
        ArrayAdapter<String> roomArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, area);
        mRoomSpinner.setDropDownHorizontalOffset(android.R.layout.simple_dropdown_item_1line);
        mRoomSpinner.setAdapter(roomArrayAdapter);





        initParticipants();
        initListeners();
        initDatePicker();
        initTimePickerStart();
        initTimePickerEnd();


    }
    public void initParticipants() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, Participant.listParticipants);
        mAutocompleteParticipants.setAdapter(adapter);
        mAutocompleteParticipants.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    }

    public void initListeners() {
        mCancel.setOnClickListener(v -> AddReunionActivity.this.finish());
        mCreate.setOnClickListener(v -> createMeeting());
        mEditTextTimeStart.setOnClickListener(v -> mTimeStartPickerDialog.show());
        mEditTextTimeEnd.setOnClickListener(v -> mTimeEndPickerDialog.show());
        mEditTextDate.setOnClickListener(v -> mDatePickerDialog.show());

    }
    public Room getRoom() {
        return (Room) mRoomSpinner.getSelectedItem();
    }

    public void initTimePickerStart() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
            selectedHourStart = hourOfDay;
            selectedMinuteStart = minute;
            setStringTimeStart();


        };
        mTimeStartPickerDialog = new TimePickerDialog(this, android.R.style.Theme_Material_Dialog,
                timeSetListener, calendarStart.get(Calendar.HOUR_OF_DAY),
                calendarStart.get(Calendar.MINUTE), true);

    }

    public void initTimePickerEnd() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
            selectedHourEnd = hourOfDay;
            selectedMinuteEnd = minute;
                setStringTimeEnd();


        };

        mTimeEndPickerDialog = new TimePickerDialog(this, android.R.style.Theme_Material_Dialog,
                timeSetListener, calendarEnd.get(Calendar.HOUR_OF_DAY),
                calendarEnd.get(Calendar.MINUTE), true);
    }

    public void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            selectedYear = year;
            selectedMonth = month;
            selectedDay = dayOfMonth;
            setStringDate();

        };
        mDatePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Material_Dialog,
                dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void setStringDate() {
        calendar.set(selectedYear, selectedMonth, selectedDay);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        String date = formatter.format(calendar.getTime());
        mEditTextDate.setText(date);
    }

    public void setStringTimeStart() {
        calendarStart.set(Calendar.HOUR_OF_DAY, selectedHourStart);
        calendarStart.set(Calendar.MINUTE, selectedMinuteStart);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        String time = formatter.format(calendarStart.getTime());
        mEditTextTimeStart.setText(time);
    }
    public void setStringTimeEnd() {
        calendarEnd.set(Calendar.HOUR_OF_DAY, selectedHourEnd);
        calendarEnd.set(Calendar.MINUTE, selectedMinuteEnd);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        String time = formatter.format(calendarEnd.getTime());
        mEditTextTimeEnd.setText(time);
    }


    public void createMeeting() {
        String subject = mEditTextSubject.getText().toString();
        String date = mEditTextDate.getText().toString();
        String hourStart = mEditTextTimeStart.getText().toString();
        String hourEnd = mEditTextTimeEnd.getText().toString();
        String participants = mAutocompleteParticipants.getText().toString();

        if (subject.isEmpty()) {
            mEditTextSubject.setError("merci d'entrer un sujet");
            return;
        }
        if (date.isEmpty()) {
            mEditTextDate.setError("merci d'entrer un sujet");
            return;
        }
        if (hourStart.isEmpty()) {
            mEditTextTimeStart.setError("merci d'entrer un sujet");
            return;
        }
        if (hourEnd.isEmpty()) {
            mEditTextTimeEnd.setError("merci d'entrer un sujet");
            return;
        }
        if (participants.isEmpty()) {
            mAutocompleteParticipants.setError("merci d'entrer un sujet");
            return;
        }


        String[] participantsList = mAutocompleteParticipants.getText().toString().split("\n");
        List<String> participantListMeeting = new ArrayList<>(Arrays.asList(participantsList));
        calendarStart.set(selectedYear,selectedMonth,selectedDay,selectedHourStart,selectedMinuteStart);
        calendarEnd.set(selectedYear,selectedMonth,selectedDay,selectedHourEnd,selectedMinuteEnd);

        Reunion reunion = new Reunion(
                DummyReunionGenerator.getActualColor(),
                mRoomSpinner.getSelectedItem().toString(),
                calendarStart.getTime(),
                calendarEnd.getTime(),
                mEditTextSubject.getText().toString(),
                participantListMeeting
        );

        mApiService.createReunion(reunion);
        finish();

    }







}
