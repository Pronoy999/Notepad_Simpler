package com.pronoymukherjee.notepadsimpler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private final String fileName = "Note.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.text);
    }

    private void setData() {
        try {
            FileInputStream fileInputStream = openFileInput(fileName);
            Scanner obj = new Scanner(fileInputStream);
            String t = "";
            while (obj.hasNext())
                t += obj.nextLine();
            t = setFormat(t);
            editText.setText(t);
            fileInputStream.close();
            obj.close();
        } catch (Exception e) {
            Toast.makeText(this, "No previous file found.", Toast.LENGTH_LONG).show();
        }
    }

    private void saveData() {
        String text = editText.getText().toString();
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            text = text.trim();
            text = editData(text);
            fileOutputStream.write(text.getBytes());
        } catch (Exception e) {
            Toast.makeText(this, " ERROR saving file. ", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!editText.getText().toString().equals("")) {
            saveData();
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
        } else {
            saveData();
            Toast.makeText(this, "Nothing to Save.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    private String editData(String text) {
        int l = text.length();
        char ch;
        String temp = "";
        for (int i = 0; i < l ; i++) {
            ch = text.charAt(i);
            if (ch == '\n') {
                temp += "''n";
            } else temp += ch;
        }
        return temp;
    }
    private String setFormat(String text){
        int l=text.length();
        int i;char ch;String temp="";
        for(i=0;i<l;i++){
            ch=text.charAt(i);
            if(ch=='\'' && text.charAt(i+1)=='\'' && text.charAt(i+2)=='n'){
                temp+="\n";
                i+=2;
            }
            else temp+=ch;
        }
        return temp;
    }
}

