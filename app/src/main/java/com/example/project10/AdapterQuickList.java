package com.example.project10;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

class AdapterQuickList extends ArrayAdapter<String> {

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
    public final String EmptyID = "Empty101010011010";
    public static final String ActivityID= "theactivityId01320434";
    public static final String ScoreID = "theScore0110010101";
    public static final String ButtID = "butt01010110";

   private int Position;
    private List<String> items;
    private String hopefulupdater;
    private String[] hopefullList;
    private AdapterQuickList adapter;
    public String listId;
    public int Score;
    public String[] toDoList = {"hey"};
    // delete the zero after debugging most likely
    public int lineNumber = 0;
    public String listIdFull = "def";
    public String[] timeList = {EmptyID};
    public String[] dateList = {EmptyID};

//    public  Context context;
    public AdapterQuickList(Context context, String[] quickToDos, String listID, String[] TList, String[] DList, int sscore)
    {
        super(context, R.layout.custom_row, quickToDos);
        listId = listID;
        this.hopefullList = quickToDos;
//        Log.i("variableName", new Context(context));
//        this.context = context;
        int i = TList.length;
        timeList = Arrays.copyOf(TList,i);
        dateList = Arrays.copyOf(DList, i);

        this.adapter = this;
        Score = sscore;
    }

    @Override
    //hope it doesn't hurt anything but position had to turn final for the interfacing
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Position = position;
            //prepares for rendering
            LayoutInflater quickInflater = LayoutInflater.from(getContext());
            // this is a single row
            final View quickView = quickInflater.inflate(R.layout.custom_row, parent, false);
            // position is the position of each item in your list
            String singleQuickListItem = getItem(position);


        hopefulupdater = hopefullList[position];
         ImageButton completeButton = (ImageButton) quickView.findViewById(R.id.buttonComplete);
         ImageButton deleteButton = (ImageButton) quickView.findViewById(R.id.buttonDelete);
         TextView taskShower = (TextView) quickView.findViewById(R.id.taskShower);
         TextView timeShower = (TextView) quickView.findViewById(R.id.timeShower);
         TextView dateShower = (TextView) quickView.findViewById(R.id.dateShower);

         if (!(timeList[position].equals(EmptyID)))
         {
             timeShower.setText(timeList[position]);
         }

        if (!(dateList[position].equals(EmptyID)))
        {
            dateShower.setText(dateList[position]);
        }

// final LinearLayout layout = (LinearLayout) quickView.findViewById(R.id.layout);
        //make and and if statement to see if there is nothing going to both time and date
        // then do an else if for date
        //then do else if for time
        // then do else
        // might make a variable in the case there isn't a date or time or a var for each case in particular
       // LinearLayout invis = (LinearLayout) quickView.findViewById(R.id.invisLayout);
       // invis.setVisibility(View.GONE);
        taskShower.setText(singleQuickListItem);
        completeButton.setImageResource(R.drawable.ic_input_add);
        deleteButton.setImageResource(R.drawable.ic_delete);


//from stack overflow interfacing for a button
       // completeButton.setTag(position);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        completeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
               Position= position;
               String strr = Integer.toString(position);

                int sign ;
                if (listId.equals(trash))
                {
                    trashRecover();
                    sign = 2;
                }
                else
                    {
                    fileOverwrite();
                    Score++;
                        sign = 1;
                    }

//                ListView listview = (ListView) quickView.findViewById(R.id.mainList);
//                listview.removeViewAt(Position);
                try {
                    writeToFile("update ready", updateFile,getContext() );
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }

                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra(ActivityID, listId);
                intent.putExtra(ScoreID, Score);
                intent.putExtra(ButtID, sign);
                v.getContext().startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Position= position;
                String strr = Integer.toString(position);

                int sign;
                if (listId.equals(trash))
                {
                    completeDelete();
                    sign = 2;
                }
                else
                {
                    fileOverwrite();
                    Score--;
                    sign = 0;
                }
//                completeButton.setVisibility(View.INVISIBLE);
//                deleteButton.setVisibility(View.INVISIBLE);
//                taskShower.setVisibility(View.INVISIBLE);
//                timeShower.setVisibility(View.INVISIBLE);
//                dateShower.setVisibility(View.INVISIBLE);
//                layout.setVisibility(View.INVISIBLE);
                try {
                    writeToFile("update ready", updateFile,getContext() );
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra(ActivityID, listId);
                intent.putExtra(ScoreID, Score);
                intent.putExtra(ButtID, sign);

                v.getContext().startActivity(intent);
            }
        });
        return quickView;
    }




public static void finApp(AppCompatActivity appCompatActivity)
{
    appCompatActivity.finish();
}

public static void refreshApp(AppCompatActivity appCompatActivity)
{
    appCompatActivity.recreate();
}

//    @Override
//    protected void onSaveInstanceState(Bundle outState)
//    {
//        super.onSaveInstanceState(outState);
//    }

public void fileOverwrite()
{
    String strr = Integer.toString(Position);
    //Toast.makeText(this.getContext(), "Position Number is "+strr, Toast.LENGTH_LONG).show();

    int i = 0;
    FileInputStream fis = null;
    getLineNumberz(todoFile);
    String str = Integer.toString(lineNumber);

    readFile(todoFile);
    int j = getFileSize(todoFile);
    try
    {
//this reads the file before we overwrite it

        String text = Integer.toString(j);


        while (i<j)
        {

            text = toDoList[i];

                  if (listIdFull.equals(quick) || listIdFull.equals(sun) || listIdFull.equals(mon) || listIdFull.equals(tues) || listIdFull.equals(wed) || listIdFull.equals(thur) || listIdFull.equals(fri) || listIdFull.equals(sat))
                  {
                    if (i == 0)
                    {
                        newwriteToFile(text,"todolist.txt",this.getContext());
                        i++;
                    }
                    else if (i == lineNumber|| i == lineNumber-1)
                    {

                        // still need to add a time stamp above the below line
                        writeToFile(text,"trash.txt", this.getContext());
                        i++;
                    }
                    else
                    {

                        writeToFile(text, "todolist.txt",this.getContext());
                        i++;
                    }


                }
                else if (listIdFull.equals(quick+TIME) || listIdFull.equals(sun+TIME) || listIdFull.equals(mon+TIME) || listIdFull.equals(tues+TIME) || listIdFull.equals(wed+TIME) || listIdFull.equals(thur+TIME) || listIdFull.equals(fri+TIME)
                          || listIdFull.equals(sat+TIME) || listIdFull.equals(quick+DATE) || listIdFull.equals(sun+DATE) || listIdFull.equals(mon+DATE) || listIdFull.equals(tues+DATE) || listIdFull.equals(wed+DATE) || listIdFull.equals(thur+DATE)
                          || listIdFull.equals(fri+DATE) || listIdFull.equals(sat+DATE))
                  {
                      if (i == 0)
                      {
                          newwriteToFile(text,"todolist.txt",this.getContext());
                          i++;
                      }
                      else if (i == lineNumber|| i == lineNumber-1 || i == lineNumber -2)
                      {

                          // still need to add a time stamp above the below line
                          writeToFile(text,"trash.txt", this.getContext());
                          i++;
                      }
                      else
                      {

                          writeToFile(text, "todolist.txt",this.getContext());
                          i++;
                      }
                  }
                  else if (listIdFull.equals(quick+TIME+DATE) || listIdFull.equals(sun+TIME+DATE) || listIdFull.equals(mon+TIME+DATE) || listIdFull.equals(tues+TIME+DATE) || listIdFull.equals(wed+TIME+DATE) || listIdFull.equals(thur+TIME+DATE)
                          || listIdFull.equals(fri+TIME+DATE) || listIdFull.equals(sat+TIME+DATE))
                  {
                      if (i == 0)
                      {
                          newwriteToFile(text,"todolist.txt",this.getContext());
                          i++;
                      }
                      else if (i == lineNumber|| i == lineNumber-1 || i == lineNumber -2 || i== lineNumber -3)
                      {

                          // still need to add a time stamp above the below line
                          writeToFile(text,"trash.txt", this.getContext());
                          i++;
                      }
                      else
                      {

                          writeToFile(text, "todolist.txt",this.getContext());
                          i++;
                      }
                  }
        }
    }


    catch (IOException e)
    {
        Log.e("Exception", "File write failed: " + e.toString());
    }
    finally
    {
        if (fis != null)
        {
            try
            {
                fis.close();
            }
            catch (IOException e)
            {
                Log.e("Exception", "File write failed: " + e.toString());
            }
        }
    }

}







public void trashRecover()
{

    int i = 0;
    FileInputStream fis = null;
    getLineNumber(trashFile);
    String str = Integer.toString(lineNumber);
    //Toast.makeText(this.getContext(), "Line Number is "+str, Toast.LENGTH_LONG).show();

    readFile(trashFile);
    int j = getFileSize(trashFile);
    try
    {
//this reads the file before we overwrite it

        String text = Integer.toString(j);


        while (i<j)
        {

            text = toDoList[i];


            if (listIdFull.equals(quick) || listIdFull.equals(sun) || listIdFull.equals(mon) || listIdFull.equals(tues) || listIdFull.equals(wed) || listIdFull.equals(thur) || listIdFull.equals(fri) || listIdFull.equals(sat))
            {

                if (i == 0)
                {
                    newwriteToFile("empty",trashFile,this.getContext());
                    writeToFile("empty", trashFile,this.getContext());
                    writeToFile("empty", trashFile,this.getContext());
                    writeToFile(text, trashFile,this.getContext());
                }
                else if (i == lineNumber|| i == lineNumber-1)
                {

                    // still need to add a time stamp above the below line
                    writeToFile(text,todoFile, this.getContext());
                }
                else
                {

                    writeToFile(text, trashFile,this.getContext());
                }


            }
            else if (listIdFull.equals(quick+TIME) || listIdFull.equals(sun+TIME) || listIdFull.equals(mon+TIME) || listIdFull.equals(tues+TIME) || listIdFull.equals(wed+TIME) || listIdFull.equals(thur+TIME) || listIdFull.equals(fri+TIME)
                    || listIdFull.equals(sat+TIME) || listIdFull.equals(quick+DATE) || listIdFull.equals(sun+DATE) || listIdFull.equals(mon+DATE) || listIdFull.equals(tues+DATE) || listIdFull.equals(wed+DATE) || listIdFull.equals(thur+DATE)
                    || listIdFull.equals(fri+DATE) || listIdFull.equals(sat+DATE))
            {
                if (i == 0)
                {
                    newwriteToFile("empty",trashFile,this.getContext());
                    writeToFile("empty", trashFile,this.getContext());
                    writeToFile("empty", trashFile,this.getContext());
                    writeToFile(text, trashFile,this.getContext());
                }
                else if (i == lineNumber|| i == lineNumber-1 || i == lineNumber -2)
                {

                    // still need to add a time stamp above the below line
                    writeToFile(text,todoFile, this.getContext());
                }
                else
                {

                    writeToFile(text, trashFile,this.getContext());
                }
            }
            else if (listIdFull.equals(quick+TIME+DATE) || listIdFull.equals(sun+TIME+DATE) || listIdFull.equals(mon+TIME+DATE) || listIdFull.equals(tues+TIME+DATE) || listIdFull.equals(wed+TIME+DATE) || listIdFull.equals(thur+TIME+DATE)
                    || listIdFull.equals(fri+TIME+DATE) || listIdFull.equals(sat+TIME+DATE))
            {
                if (i == 0)
                {
                    newwriteToFile("empty",trashFile,this.getContext());
                    writeToFile("empty", trashFile,this.getContext());
                    writeToFile("empty", trashFile,this.getContext());
                    writeToFile(text, trashFile,this.getContext());
                }
                else if (i == lineNumber|| i == lineNumber-1 || i == lineNumber -2 || i== lineNumber -3)
                {

                    // still need to add a time stamp above the below line
                    writeToFile(text,todoFile, this.getContext());
                }
                else
                {

                    writeToFile(text, trashFile,this.getContext());
                }
            }
        i++;
        }
    }


    catch (IOException e)
    {
        Log.e("Exception", "File write failed: " + e.toString());
    }
    finally
    {
        if (fis != null)
        {
            try
            {
                fis.close();
            }
            catch (IOException e)
            {
                Log.e("Exception", "File write failed: " + e.toString());
            }
        }
    }
}


    public void completeDelete()
    {

        int i = 0;
        FileInputStream fis = null;
        getLineNumber(trashFile);
        String str = Integer.toString(lineNumber);
        //Toast.makeText(this.getContext(), "Line Number is "+str, Toast.LENGTH_LONG).show();
        str = Integer.toString(Position);
        //Toast.makeText(this.getContext(), "Pos Number is "+str, Toast.LENGTH_LONG).show();

        readFile(trashFile);
        int j = getFileSize(trashFile);
        try
        {
//this reads the file before we overwrite it

            String text = Integer.toString(j);
            //Toast.makeText(this.getContext(), listIdFull, Toast.LENGTH_LONG).show();


            while (i<j)
            {

                text = toDoList[i];


                if (listIdFull.equals(quick) || listIdFull.equals(sun) || listIdFull.equals(mon) || listIdFull.equals(tues) || listIdFull.equals(wed) || listIdFull.equals(thur) || listIdFull.equals(fri) || listIdFull.equals(sat))
                {

                    if (i == 0)
                    {
                        newwriteToFile("empty",trashFile,this.getContext());
                        writeToFile("empty", trashFile,this.getContext());
                        writeToFile("empty", trashFile,this.getContext());
                        writeToFile(text, trashFile,this.getContext());
                    }
                    else if (i == lineNumber|| i == lineNumber-1)
                    {

                        // still need to add a time stamp above the below line

                    }
                    else
                    {

                        writeToFile(text, trashFile,this.getContext());
                    }


                }
                else if (listIdFull.equals(quick+TIME) || listIdFull.equals(sun+TIME) || listIdFull.equals(mon+TIME) || listIdFull.equals(tues+TIME) || listIdFull.equals(wed+TIME) || listIdFull.equals(thur+TIME) || listIdFull.equals(fri+TIME)
                        || listIdFull.equals(sat+TIME) || listIdFull.equals(quick+DATE) || listIdFull.equals(sun+DATE) || listIdFull.equals(mon+DATE) || listIdFull.equals(tues+DATE) || listIdFull.equals(wed+DATE) || listIdFull.equals(thur+DATE)
                        || listIdFull.equals(fri+DATE) || listIdFull.equals(sat+DATE))
                {
                    if (i == 0)
                    {
                        newwriteToFile("empty",trashFile,this.getContext());
                        writeToFile("empty", trashFile,this.getContext());
                        writeToFile("empty", trashFile,this.getContext());
                        writeToFile(text, trashFile,this.getContext());                    }
                    else if (i == lineNumber|| i == lineNumber-1 || i == lineNumber -2)
                    {

                        // still need to add a time stamp above the below line

                    }
                    else
                    {

                        writeToFile(text, trashFile,this.getContext());
                    }
                }
                else if (listIdFull.equals(quick+TIME+DATE) || listIdFull.equals(sun+TIME+DATE) || listIdFull.equals(mon+TIME+DATE) || listIdFull.equals(tues+TIME+DATE) || listIdFull.equals(wed+TIME+DATE) || listIdFull.equals(thur+TIME+DATE)
                        || listIdFull.equals(fri+TIME+DATE) || listIdFull.equals(sat+TIME+DATE))
                {
                    if (i == 0)
                    {
                        newwriteToFile("empty",trashFile,this.getContext());
                        writeToFile("empty", trashFile,this.getContext());
                        writeToFile("empty", trashFile,this.getContext());
                        writeToFile(text, trashFile,this.getContext());
                    }
                    else if (i == lineNumber|| i == lineNumber-1 || i == lineNumber -2 || i== lineNumber -3)
                    {

                        // still need to add a time stamp above the below line

                    }
                    else
                    {

                        writeToFile(text, trashFile,this.getContext());
                    }
                }
                i++;
            }
        }


        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e)
                {
                    Log.e("Exception", "File write failed: " + e.toString());
                }
            }
        }
    }
// gets the line number in the todolist

public void getLineNumberz(String fileName)
{ if (fileExists(this.getContext(),fileName))
    {
    int i = getFileSize(fileName) - 1;
    int j = -1;
    readFile(fileName);
    while (i >= 0)
     {
         String text = toDoList[i];
        if (text.equals(listId) || text.equals(listId+TIME) || text.equals(listId+DATE) || text.equals(listId+TIME+DATE)) {
            j++;
        } else {
            j = j;
        }

        if (j == Position) {
            listIdFull = text;
            lineNumber = i;
            break;
        } else {
            i--;
        }
     }
    }
}

    public void getLineNumber(String fileName)
    { if (fileExists(this.getContext(),fileName))
    {
        int i = getFileSize(fileName) - 1;
        int j = -1;
        readFile(fileName);
        while (i >= 0)
        {
            String text = toDoList[i];
            if (toDoList[i].equals(quick) || toDoList[i].equals(sun) || toDoList[i].equals(mon) || toDoList[i].equals(tues) || toDoList[i].equals(wed) || toDoList[i].equals(thur) || toDoList[i].equals(fri) || toDoList[i].equals(sat)
                    || toDoList[i].equals(quick+TIME)|| toDoList[i].equals(sun+TIME)|| toDoList[i].equals(mon+TIME)|| toDoList[i].equals(tues+TIME)|| toDoList[i].equals(wed+TIME)|| toDoList[i].equals(thur+TIME)|| toDoList[i].equals(fri+TIME)|| toDoList[i].equals(sat+TIME)
                    || toDoList[i].equals(quick+DATE)|| toDoList[i].equals(sun+DATE)|| toDoList[i].equals(mon+DATE)|| toDoList[i].equals(tues+DATE)|| toDoList[i].equals(wed+DATE)|| toDoList[i].equals(thur+DATE)|| toDoList[i].equals(fri+DATE)|| toDoList[i].equals(sat+DATE)
                    || toDoList[i].equals(quick+TIME+DATE)|| toDoList[i].equals(sun+TIME+DATE)|| toDoList[i].equals(mon+TIME+DATE)|| toDoList[i].equals(tues+TIME+DATE)|| toDoList[i].equals(wed+TIME+DATE)|| toDoList[i].equals(thur+TIME+DATE)|| toDoList[i].equals(fri+TIME+DATE)|| toDoList[i].equals(sat+TIME+DATE)
            ) {
                j++;
            } else {
                j = j;
            }

            if (j == Position) {
                listIdFull = text;
                lineNumber = i;
                break;
            } else {
                i--;
            }
        }
    }
    }





    public void readFile(String fileName)
    {

        if (fileExists(this.getContext(),fileName))
        {
            int j = getFileSize(fileName);
            setArraySize(j);
            FileInputStream fis = null;

            try {
                fis = getContext().openFileInput(fileName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                int I = 0;
                String text;
                while ((text = br.readLine()) != null)
                {
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
        if (fileExists(this.getContext(), fileName))
    {


        FileInputStream fis = null;

        try {
            fis = getContext().openFileInput(fileName);
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






    private void writeToFile(String data, String fileName, Context context) throws FileNotFoundException
    {
// if the file already exists just add to it
        if (fileExists(this.getContext(), fileName))
        {

            FileOutputStream fOut = getContext().openFileOutput(fileName, Context.MODE_APPEND);
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

            fos = getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            try {
                fos.write(data.getBytes());
               //Toast.makeText(this.getContext(), getContext().getFilesDir() + "/" + fileName, Toast.LENGTH_LONG).show();
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

    public boolean fileExists(Context context, String filename)
    {
        File file = context.getFileStreamPath(filename);
        if(file == null || !file.exists())
        {
            return false;
        }
        return true;
    }


    private void newwriteToFile(String data, String fileName, Context context) throws FileNotFoundException
    {
        FileOutputStream fos = null;

        fos = getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
        try
        {
            fos.write(data.getBytes());
           // Toast.makeText(this.getContext(), getContext().getFilesDir() + "/" + fileName, Toast.LENGTH_LONG).show();
        }
        catch (IOException e)
        {
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

//maybe give every single it

// to find what needs to be changed
// to keep track of line number we need to find the size of the list using the function I already created
// then we start counting backwards from that or one minus that.

// remake the list while keeping track of line number and keep track of position number

// to track position number start a number at negative one.
// once a compatible id is found increment the number.
// if that incremented number is the position number we are looking for then return the line number
// do similar to read file and get the buffered reader
// setup a while loop that translates buffered reader to string while itterating a number
// if the loop number is equal to desired line number then write trash information



// for permanantly deleting an item make an array of integers that holds the line numbers that should be deleted
// while it is rewriting the entire file in a loop that is counting line numbers
// if its line number matches a line number within the array then go to next line, but do not write it in the file assign it to a trash string if necessary
// else write it in the file