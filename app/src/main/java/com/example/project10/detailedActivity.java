package com.example.project10;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.sax.TextElementListener;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class detailedActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener{
    public final String quick = "Quick10101";
    public final String sun = "Sun1010101";
    public final String mon = "Mon0101010";
    public final String tues = "Tues101010";
    public final String wed = "Wed1010010";
    public final String thur = "Thur101010";
    public final String fri = "Fri1010101";
    public final String sat = "Sat1010101";
    public final String trash = "Trash10101";
    public final String TIME = "time";
    public final String DATE = "date";
    public final String todoFile = "todolist.txt";
    public final String trashFile = "trash.txt";
    public final String updateFile = "updateFile.txt";
    public final String updateFile2 = "updateFile2.txt";
    public final String Empty = "Empty101010011010";
    private static final String TAG = "detailedActivity";
    public static final String ActivityID= "theactivityId01320434";
    public static final String ScoreID = "theScore0110010101";

    public int Score;
    public String displayID;

    private TextView DisplayDate;
    private DatePickerDialog.OnDateSetListener DateListener;
    public String toDoItem = Empty;
    public String DATESelected = "none";
    public String TIMESelected = "none";
    public String DAY = quick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        //data collection for storage
        Intent intent = getIntent();
        displayID = intent.getStringExtra(MainActivity.ActivityID);
        Score = intent.getIntExtra(MainActivity.ScoreID, 0);



// this is for the day spinner
        Spinner daySpinner = findViewById(R.id.spinnerDay);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);
        daySpinner.setOnItemSelectedListener(this);

// this is for the Date selector
        DisplayDate = (TextView) findViewById(R.id.textViewDate);
        DisplayDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        detailedActivity.this, android.R.style.Theme_Black, DateListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        DateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
            //Log.d(TAG, "onDateSet: date: ", month+"/"+ year +"/" + dayOfMonth);

                String mm = Integer.toString(month);
                String dd = Integer.toString(dayOfMonth);
                String yyyy = Integer.toString(year);

                String Date = mm + "/" + dd + "/" + yyyy;
                // this sets the global date
                DATESelected = Date;
                DisplayDate.setText(Date);
            }
        };

//////////this is for the time
        EditText taskText =  (EditText) findViewById(R.id.editTextTask);
        Button timeButton = (Button) findViewById(R.id.buttonTimeSelect);
        timeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker" );
            }
        });

// this is for the submit button
        EditText textTask = (EditText) findViewById(R.id.editTextTask);
        Button submitButton = (Button) findViewById(R.id.buttonSubmit);
        toDoItem = textTask.getText().toString();
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            { DayIDChooser();

                EditText textTask = (EditText) findViewById(R.id.editTextTask);
                toDoItem = textTask.getText().toString();
                if (!(toDoItem.equals(null)))
                {

                    if (DATESelected.equals("none") && TIMESelected.equals("none"))
                    {
                        try
                        {
                            writeToFile(toDoItem, this );
                            writeToFile(DAY,this);
                        }
                        catch (FileNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    // if no date is selected and a time is selected
                    else if (DATESelected.equals("none") && !(TIMESelected.equals("none")))
                    {
                        try
                        {
                            writeToFile(toDoItem, this);
                            writeToFile(TIMESelected,this);
                            writeToFile(DAY+TIME, this);
                        }
                        catch (FileNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    //if a date is selected and time is not
                    else if (!(DATESelected.equals("none")) && TIMESelected.equals("none"))
                    {
                        try {
                            writeToFile(toDoItem, this);
                            writeToFile(DATESelected, this);
                            writeToFile(DAY+DATE,this);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                    // when both time and date are selsected we should be good to write all of them in
                    else
                    {
                        try {
                            writeToFile(toDoItem, this);
                            writeToFile(TIMESelected,this);
                            writeToFile(DATESelected, this);
                            writeToFile(DAY+TIME+DATE, this);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }
                //open the main activity
                try {
                    writeToFileup("update2",this);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                openMainActivity();
            }
        });

        if(savedInstanceState != null)
        {
            DATESelected = savedInstanceState.getString("ddate");
            TIMESelected = savedInstanceState.getString("ttime");
            TextView date = (TextView) findViewById(R.id.textViewDate);
            TextView time = (TextView) findViewById(R.id.textTime);
            if (!(TIMESelected.equals("none")))
            {
            time.setText(TIMESelected);
            }
            if(!(DATESelected.equals("none")))
            {
                date.setText(DATESelected);
            }
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("ddate", DATESelected);
        outState.putString("ttime",TIMESelected);
    }

    public void openMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(ActivityID, displayID);
        intent.putExtra(ScoreID, Score);
        intent.putExtra(ScoreID, Score);
        startActivity(intent);
    }
// this is for the day spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        // probably just set a global variable equal to this.
        DAY=text;
    }
// this is also for the day spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView timeText = (TextView) findViewById(R.id.textTime);
        String AmPm = "am";


         if ( hourOfDay > 11)
        {
            hourOfDay = hourOfDay-12;
            AmPm = "pm";
        }
        if (hourOfDay == 0)
         {
             hourOfDay = 12;
         }

        String hh = Integer.toString(hourOfDay);
        String mm = Integer.toString(minute);
        if(minute<10) {mm="0"+mm;}

        TIMESelected = hh + ":" + mm + " " + AmPm;
        timeText.setText(TIMESelected);
    }


// this is used to select the correct Id based on the day
    public void DayIDChooser()
    {
        switch (DAY)
        {
            case "Quick":
                DAY = quick;
                break;
            case "Sunday":
                DAY = sun;
                break;
            case "Monday":
                DAY = mon;
                break;
            case "Tuesday":
                DAY = tues;
                break;
            case "Wednesday":
                DAY = wed;
            case "Thursday":
                DAY = thur;
                break;
            case "Friday":
                DAY = fri;
                break;
            case "Saturday":
                DAY = sat;
                default:
                    DAY = quick;
                    break;
        }

    }

    private void writeToFile(String data, View.OnClickListener context) throws FileNotFoundException
    {
// if the file already exists just add to it
        if (fileExists(this, "todolist.txt"))
        {

            FileOutputStream fOut = openFileOutput("todolist.txt", MODE_APPEND);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            try {
                osw.write("\n");
                osw.write(data);
                osw.flush();
                osw.close();
            } catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }
        }
        // otherwise make the file
        else
        {
            FileOutputStream fos = null;

            fos = openFileOutput("todolist.txt", MODE_PRIVATE);
            try {
                fos.write(data.getBytes());
               // Toast.makeText(this, getFilesDir() + "/" + "todolist.txt", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    private void writeToFileup(String data, View.OnClickListener context) throws FileNotFoundException
    {
// if the file already exists just add to it
        if (fileExists(this, updateFile2))
        {

            FileOutputStream fOut = openFileOutput(updateFile2, MODE_APPEND);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            try {
                osw.write("\n");
                osw.write(data);
                osw.flush();
                osw.close();
            } catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }
        }
        // otherwise make the file
        else
        {
            FileOutputStream fos = null;

            fos = openFileOutput(updateFile2, MODE_PRIVATE);
            try {
                fos.write(data.getBytes());
//                Toast.makeText(this, getFilesDir() + "/" + "todolist.txt", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    // checks if file exists
    public boolean fileExists(Context context, String filename)
    {
        File file = context.getFileStreamPath(filename);
        if(file == null || !file.exists())
        {
            return false;
        }
        return true;
    }
}
