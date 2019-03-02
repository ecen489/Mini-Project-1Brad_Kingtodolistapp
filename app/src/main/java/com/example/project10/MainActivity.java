package com.example.project10;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.*;

public class MainActivity extends AppCompatActivity {
public Scanner fileReader;
public String todoItem;
public String[] toDoList= {"hi", "yo", " sfds", " fdsf", " sfd"};
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
public final String EmptyID = "Empty101010011010";
public static final String ActivityID= "theactivityId01320434";
public static final String ScoreID = "theScore0110010101";
public static final String ButtID = "butt01010110";


public String[] Empty = {EmptyID, EmptyID};
public final String todoFile = "todolist.txt";
public final String trashFile = "trash.txt";
public final String updateFile = "updateFile.txt";
public final String updateFile2 = "updateFile2.txt";

public String displayID = "def";
public int Score = 0;
public int buttnum;
//public MediaPlayer player;
private Formatter x; // following Bucky

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonDisp = (Button) findViewById(R.id.buttonDisp);
        EditText editTextToDo = (EditText) findViewById(R.id.editTextQuickTodo);


        onff();
        onfff();
        Intent intent = getIntent();

        if(savedInstanceState != null)
        {
            Score = savedInstanceState.getInt("score");
            displayID = savedInstanceState.getString("listID");
        }
//when plus or minus is pressed
        if (fileExists(this , updateFile)) {

            displayID = intent.getStringExtra(MainActivity.ActivityID);
            Score = intent.getIntExtra(MainActivity.ScoreID,0);
            buttnum = intent.getIntExtra(MainActivity.ButtID, 0);
            deleteFile(updateFile);
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (buttnum == 1) {
                onUP();
                playPlus(this.findViewById(R.id.fragment2));

            }
            else if( buttnum == 0)
            {
                onUPP();
                playMinus(this.findViewById(R.id.fragmentSad));
            }
        }

        if (fileExists(this , updateFile2)) {
            displayID = intent.getStringExtra(MainActivity.ActivityID);
            Score = intent.getIntExtra(MainActivity.ScoreID,0);
            deleteFile(updateFile2);
            FragmentManager fragmentManager = getSupportFragmentManager();
        }

        TextView score = (TextView) findViewById(R.id.textScore);

        score.setText(Integer.toString(Score));

        switch(displayID)
        {
            case sun:
                this.sunButton(findViewById(R.id.buttonSun));
                break;

            case mon:
                this.monButton(findViewById(R.id.buttonMon));
                break;

            case tues:
                this.tuesButton(findViewById(R.id.buttonTues));
                break;

            case wed:
                this.wedButton(findViewById(R.id.buttonWed));
                break;

            case thur:
                this.thurButton(findViewById(R.id.buttonThur));
                break;

            case fri:
                this.friButton(findViewById(R.id.buttonFri));
                break;

            case sat:
                this.satButton(findViewById(R.id.buttonSat));
                break;

            case trash:
                this.trashButton(findViewById(R.id.buttonTrash));
                break;

            case quick:
                this.quickButton(findViewById(R.id.buttonQuick));
                break;
            default:
                this.quickButton(findViewById(R.id.buttonQuick));
                break;

        }
// this part is for dealing with the second activity
        Button detialButton = (Button) findViewById(R.id.buttonDetailDisp);
        detialButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                opendetailedActivity();
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt("score", Score);
        outState.putString("listID", displayID);
    }


    public void opendetailedActivity()
{
    Intent intent = new Intent(this, detailedActivity.class);
    intent.putExtra(ActivityID, displayID);
    intent.putExtra(ScoreID, Score);
    startActivity(intent);
}


public void playPlus(View v)
{
    MediaPlayer player = null;
    if(player == null)
    {
        player= MediaPlayer.create(this,R.raw.succeed);
        final MediaPlayer finalPlayer = player;
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlayer(finalPlayer);
                onff();
            }
        });
    }
player.start();
}

    public void playMinus(View v)
    {    MediaPlayer player = null;
        if(player == null)
        {
            player= MediaPlayer.create(this,R.raw.fail);
            final MediaPlayer finalPlayer = player;
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                   stopPlayer(finalPlayer);
                    onfff();
                }
            });
        }
        player.start();
    }

    private void stopPlayer(MediaPlayer player) {
        if (player!=null)
        {
            player.release();
            player = null;
        }
    }

public void onff() {
    Fragment frag = getSupportFragmentManager().findFragmentById(R.id.fragment2);
//    //android.app.FragmentManager fm = getFragmentManager();
//    FragmentTransaction ft = getFragmentManager().beginTransaction();
//    ft.remove(frag);
//    ft.commit();
//}
    if(!(frag.isHidden())) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .hide(frag)
                .commit();
    }
}

public void onUP()
{
    Fragment frag = getSupportFragmentManager().findFragmentById(R.id.fragment2);
//    //android.app.FragmentManager fm = getFragmentManager();
//    FragmentTransaction ft = getFragmentManager().beginTransaction();
//    ft.remove(frag);
//    ft.commit();
//}


        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .show(frag)
                .commit();

}




//this isfor sad mario turn off
    public void onfff() {

        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.fragmentSad);
        if(!(frag.isHidden()))
        {
//    //android.app.FragmentManager fm = getFragmentManager();
//    FragmentTransaction ft = getFragmentManager().beginTransaction();
//    ft.remove(frag);
//    ft.commit();
//}

            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .hide(frag)
                    .commit();
        }
    }


//this is for sad mario turn on
    public void onUPP()
    {
        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.fragmentSad);
//    //android.app.FragmentManager fm = getFragmentManager();
//    FragmentTransaction ft = getFragmentManager().beginTransaction();
//    ft.remove(frag);
//    ft.commit();
//}

            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .show(frag)
                    .commit();

    }

    public void quickAdd(View v)
{
    Button buttonDisp = (Button) findViewById(R.id.buttonDisp);
    EditText editTextToDo = (EditText) findViewById(R.id.editTextQuickTodo);


    if (editTextToDo.getText() != null || !(editTextToDo.getText().toString().equals("")))
    {
        todoItem = editTextToDo.getText().toString();
        try
        {

//            writeToFile("mop", this);
            writeToFile(todoItem, this);
            switch(displayID)
            {
                case sun:
                    writeToFile(sun,this);
                    sunButton(v);
                    break;

                case mon:
                    writeToFile(mon,this);
                    monButton(v);
                    break;

                case tues:
                    writeToFile(tues,this);
                    tuesButton(v);
                    break;

                case wed:
                    writeToFile(wed,this);
                    wedButton(v);
                    break;

                case thur:
                    writeToFile(thur, this);
                    thurButton(v);
                    break;

                case fri:
                    writeToFile(fri,this);
                    friButton(v);
                    break;

                case sat:
                    writeToFile(sat,this);
                    satButton(v);
                    break;

                case trash:
//                    writeToFile(quick+trash,this);
//                    trashButton(v);
                    break;

                case quick:
                    writeToFile(quick,this);
                    quickButton(v);
                    break;
                default:
                    writeToFile(quick,this);
                    break;

            }
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
editTextToDo.getText().clear();
}



public void generalButton(View v, String day)
{
    displayID = day;
    String quickItem;
    readFile(todoFile);
    int j = 0;
    int k = 0;
    // reads the list backwards
    for (int i = getFileSize(todoFile)-1; i>=0; i--)
    {
        //checks how many quick items there are
        if (toDoList[i].equals(day)|| toDoList[i].equals(day+TIME) || toDoList[i].equals(day+DATE) || toDoList[i].equals(day+TIME+DATE))
        {
            j++;
        }
        else
        {
            j=j;
        }

    }
    // makes a new array with size equal to quick items
    String[] quickList = new String[j];
    String[] timeList = new String[j];
    String[] dateList = new String[j];

    j=0;
    // reads list backwards again
    for (int i = getFileSize(todoFile)-1; i>=0; i--)
    {
        //adds items to quicklist
        if (toDoList[i].equals(day))
        {
            k=i;
            k--;
            quickList[j]= toDoList[k];
            timeList[j] = EmptyID;
            dateList[j] = EmptyID;
            j++;
        }
        // when time is present
        else if (toDoList[i].equals(day+TIME))
        {
            k= i-2;
            quickList[j]= toDoList[k];
            k= i -1;
            timeList[j] = toDoList[k];
            dateList[j] = EmptyID;
            j++;
        }
        else if (toDoList[i].equals(day+DATE))
        {
            k= i-2;
            quickList[j]= toDoList[k];
            timeList[j] = EmptyID;
            k= i -1;
            dateList[j] = toDoList[k];
            j++;
        }
        else if (toDoList[i].equals(day+TIME+DATE))
        {
            k= i-3;
            quickList[j]= toDoList[k];
            k = i-2;
            timeList[j] = toDoList[k];
            k= i -1;
            dateList[j] = toDoList[k];
            j++;
        }
    }
    //changes list view to show quicklist in custom format
    //used to have quick list
    //there is something wrong with the code below maybe Items are not in to do list
    ListAdapter quickTodo = new AdapterQuickList(this, quickList, displayID, timeList, dateList , Score);
    ListView mainList = (ListView) findViewById(R.id.mainList);
    mainList.setAdapter(quickTodo);
}

public void quickButton(View v)
{
    generalButton(v, quick);
}

public void sunButton(View v)
    {
        generalButton(v, sun);
    }

    public  void monButton (View v) {
        generalButton(v, mon);
    }

    public void tuesButton(View v) {
        generalButton(v, tues);
    }
    public void wedButton(View v) {
        generalButton(v, wed);
    }
    public void thurButton(View v) {
        generalButton(v, thur);
    }
    public void friButton(View v) {
        generalButton(v, fri);
    }
    public void satButton(View v) {
        generalButton(v, sat);
    }
//public void quickButton (View v)
//    {
//        displayID = quick;
//        String quickItem;
//        readFile(todoFile);
//        int j = 0;
//        int k = 0;
//        // reads the list backwards
//        for (int i = getFileSize(todoFile)-1; i>=0; i--)
//        {
//            //checks how many quick items there are
//            if (toDoList[i].equals(quick)|| toDoList[i].equals(quick+TIME) || toDoList[i].equals(quick+DATE) || toDoList[i].equals(quick+TIME+DATE))
//            {
//                j++;
//            }
//            else
//            {
//                j=j;
//            }
//        }
//        // makes a new array with size equal to quick items
//        String[] quickList = new String[j];
//        String[] timeList = new String[j];
//        String[] dateList = new String[j];
//        j=0;
//        // reads list backwards again
//        for (int i = getFileSize(todoFile)-1; i>=0; i--)
//        {
//            //adds items to quicklist
//            if (toDoList[i].equals(quick))
//            {
//                k=i;
//                k--;
//                quickList[j]= toDoList[k];
//                timeList[j] = EmptyID;
//                dateList[j] = EmptyID;
//                j++;
//            }
//            else if (toDoList[i].equals(quick+TIME))
//            {
//                k= i-2;
//                quickList[j]= toDoList[k];
//                k= i -1;
//                timeList[j] = toDoList[k];
//                dateList[j] = EmptyID;
//                j++;
//            }
//            else if (toDoList[i].equals(quick+DATE))
//            {
//                k= i-2;
//                quickList[j]= toDoList[k];
//                timeList[j] = EmptyID;
//                k= i -1;
//                dateList[j] = toDoList[k];
//                j++;
//            }
//            else if (toDoList[i].equals(quick+TIME+DATE))
//            {
//                k= i-3;
//                quickList[j]= toDoList[k];
//                k = i-2;
//                timeList[j] = toDoList[k];
//                k= i -1;
//                dateList[j] = toDoList[k];
//                j++;
//            }
//        }
//        //changes list view to show quicklist in custom format
//        //used to have quick list
//        //there is something wrong with the code below maybe Items are not in to do list
//        ListAdapter quickTodo = new AdapterQuickList(this, quickList, displayID, timeList, dateList );
//        ListView mainList = (ListView) findViewById(R.id.mainList);
//        mainList.setAdapter(quickTodo);
//    }




//}
//    public void sunButton (View v)
//    {
//        displayID = sun;
//        String quickItem;
//        readFile(todoFile);
//        int j = 0;
//        int k = 0;
//        // reads the list backwards
//        for (int i = getFileSize(todoFile)-1; i>=0; i--)
//        {
//            //checks how many quick items there are
//            if (toDoList[i].equals(sun)|| toDoList[i].equals(sun+TIME) || toDoList[i].equals(sun+DATE) || toDoList[i].equals(sun+TIME+DATE))
//            {
//                j++;
//            }
//            else
//            {
//                j=j;
//            }
//
//        }
//        // makes a new array with size equal to quick items
//        String[] quickList = new String[j];
//        String[] timeList = new String[j];
//        String[] dateList = new String[j];
//
//        j=0;
//        // reads list backwards again
//        for (int i = getFileSize(todoFile)-1; i>=0; i--)
//        {
//            //adds items to quicklist
//            if (toDoList[i].equals(sun))
//            {
//                k=i;
//                k--;
//                quickList[j]= toDoList[k];
//                timeList[j] = EmptyID;
//                dateList[j] = EmptyID;
//                j++;
//            }
//            // when time is present
//            else if (toDoList[i].equals(sun+TIME))
//            {
//                k= i-2;
//                quickList[j]= toDoList[k];
//                k= i -1;
//                timeList[j] = toDoList[k];
//                dateList[j] = EmptyID;
//                j++;
//            }
//            else if (toDoList[i].equals(sun+DATE))
//            {
//                k= i-2;
//                quickList[j]= toDoList[k];
//                timeList[j] = EmptyID;
//                k= i -1;
//                dateList[j] = toDoList[k];
//                j++;
//            }
//            else if (toDoList[i].equals(sun+TIME+DATE))
//            {
//                k= i-3;
//                quickList[j]= toDoList[k];
//                k = i-2;
//                timeList[j] = toDoList[k];
//                k= i -1;
//                dateList[j] = toDoList[k];
//                j++;
//            }
//        }
//        //changes list view to show quicklist in custom format
//        //used to have quick list
//        //there is something wrong with the code below maybe Items are not in to do list
//        ListAdapter quickTodo = new AdapterQuickList(this, quickList, displayID, timeList, dateList );
//        ListView mainList = (ListView) findViewById(R.id.mainList);
//        mainList.setAdapter(quickTodo);
//    }







    public void trashButton (View v)
    {
        if (fileExists(this, trashFile))
        {

            displayID = trash;
            String quickItem;
            readFile(trashFile);
            int j = 0;
            int k = 0;
            // reads the list backwards
            for (int i = getFileSize(trashFile)-1; i>=0; i--)
            {
                //checks how many quick items there are
                if (toDoList[i].equals(quick) || toDoList[i].equals(sun) || toDoList[i].equals(mon) || toDoList[i].equals(tues) || toDoList[i].equals(wed) || toDoList[i].equals(thur) || toDoList[i].equals(fri) || toDoList[i].equals(sat)
                        || toDoList[i].equals(quick+TIME)|| toDoList[i].equals(sun+TIME)|| toDoList[i].equals(mon+TIME)|| toDoList[i].equals(tues+TIME)|| toDoList[i].equals(wed+TIME)|| toDoList[i].equals(thur+TIME)|| toDoList[i].equals(fri+TIME)|| toDoList[i].equals(sat+TIME)
                        || toDoList[i].equals(quick+DATE)|| toDoList[i].equals(sun+DATE)|| toDoList[i].equals(mon+DATE)|| toDoList[i].equals(tues+DATE)|| toDoList[i].equals(wed+DATE)|| toDoList[i].equals(thur+DATE)|| toDoList[i].equals(fri+DATE)|| toDoList[i].equals(sat+DATE)
                        || toDoList[i].equals(quick+TIME+DATE)|| toDoList[i].equals(sun+TIME+DATE)|| toDoList[i].equals(mon+TIME+DATE)|| toDoList[i].equals(tues+TIME+DATE)|| toDoList[i].equals(wed+TIME+DATE)|| toDoList[i].equals(thur+TIME+DATE)|| toDoList[i].equals(fri+TIME+DATE)|| toDoList[i].equals(sat+TIME+DATE))

                {
                    j++;
                }
                else
                {
                    j=j;
                }

            }
            // makes a new array with size equal to quick items
            String[] quickList = new String[j];
            String[] timeList = new String[j];
            String[] dateList = new String[j];

            j=0;
            // reads list backwards again
            for (int i = getFileSize(trashFile)-1; i>=0; i--)
            {
                //adds items to quicklist
                if (toDoList[i].equals(quick) || toDoList[i].equals(sun) || toDoList[i].equals(mon) || toDoList[i].equals(tues) || toDoList[i].equals(wed) || toDoList[i].equals(thur) || toDoList[i].equals(fri) || toDoList[i].equals(sat))
                {
                    k=i;
                    k--;
                    quickList[j]= toDoList[k];
                    timeList[j] = EmptyID;
                    dateList[j] = EmptyID;
                    j++;
                }
                // when time is present
                else if (toDoList[i].equals(quick+TIME)|| toDoList[i].equals(sun+TIME)|| toDoList[i].equals(mon+TIME)|| toDoList[i].equals(tues+TIME)|| toDoList[i].equals(wed+TIME)|| toDoList[i].equals(thur+TIME)|| toDoList[i].equals(fri+TIME)|| toDoList[i].equals(sat+TIME))
                {
                    k= i-2;
                    quickList[j]= toDoList[k];
                    k= i -1;
                    timeList[j] = toDoList[k];
                    dateList[j] = EmptyID;
                    j++;
                }
                else if (toDoList[i].equals(quick+DATE)|| toDoList[i].equals(sun+DATE)|| toDoList[i].equals(mon+DATE)|| toDoList[i].equals(tues+DATE)|| toDoList[i].equals(wed+DATE)|| toDoList[i].equals(thur+DATE)|| toDoList[i].equals(fri+DATE)|| toDoList[i].equals(sat+DATE))
                {
                    k= i-2;
                    quickList[j]= toDoList[k];
                    timeList[j] = EmptyID;
                    k= i -1;
                    dateList[j] = toDoList[k];
                    j++;
                }
                else if (toDoList[i].equals(quick+TIME+DATE)|| toDoList[i].equals(sun+TIME+DATE)|| toDoList[i].equals(mon+TIME+DATE)|| toDoList[i].equals(tues+TIME+DATE)|| toDoList[i].equals(wed+TIME+DATE)|| toDoList[i].equals(thur+TIME+DATE)|| toDoList[i].equals(fri+TIME+DATE)|| toDoList[i].equals(sat+TIME+DATE))
                {
                    k= i-3;
                    quickList[j]= toDoList[k];
                    k = i-2;
                    timeList[j] = toDoList[k];
                    k= i -1;
                    dateList[j] = toDoList[k];
                    j++;
                }
            }
            //changes list view to show quicklist in custom format
            //used to have quick list
            //there is something wrong with the code below maybe Items are not in to do list
            ListAdapter quickTodo = new AdapterQuickList(this, quickList, displayID, timeList, dateList , Score);
            ListView mainList = (ListView) findViewById(R.id.mainList);
            mainList.setAdapter(quickTodo);
        }
        else
        {}
    }



    public void readFile(String fileName)
    {
        if (fileExists(this, fileName))
        {
            int j = getFileSize(fileName);
            setArraySize(j);
            FileInputStream fis = null;

            try {
                fis = openFileInput(fileName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                int I = 0;
                String text;
                while ((text = br.readLine()) != null) {
                    toDoList[I] = text;
                    I++;
                }
            } catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        Log.e("Exception", "File write failed: " + e.toString());
                    }
                }
            }
        }
    }

public void setArraySize(int i)
{
    toDoList = Arrays.copyOf(toDoList, i);
}


public int getFileSize(String fileName)
{
    int i = 0;
    if (fileExists(this, fileName)) {

        FileInputStream fis = null;

        try {
            fis = openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;
            while ((text = br.readLine()) != null) {
                i++;
            }
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    Log.e("Exception", "File write failed: " + e.toString());
                }
            }
        }

    }
    return i;
}



private void writeToFile(String data, Context context) throws FileNotFoundException
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



public void deletLater( int j)
{
    Empty = Arrays.copyOf(Empty, j);
    int i = 0;
    while(i < j)
    {
        Empty[i] = EmptyID;
        i++;
    }
}
}
//raw files are not editable
// you need to be able to create this file and then read from it
// look into checking if file is already created

// make a display variable
// make the disp button check the display variable and press the coressponding button


//to make stuff delete automatically we will do the following code once the trash folder is hit

// find a date and compare it to current date and time
// start with dates
// increment the date that was pulled from the file
// if the incremented date is before current date delete the file
// if they are the same do a time check
    // time check (it is likely you will reuse simple date format for this)
// else don't delete