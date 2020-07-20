package com.example.simplenoteapps;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnNew;
    private Button btnSave;
    private Button btnOpen;

    private EditText edtTitle;
    private EditText edtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNew = findViewById(R.id.btn_new_file);
        btnOpen = findViewById(R.id.btn_open_file);
        btnSave = findViewById(R.id.btn_save_file);

        edtTitle = findViewById(R.id.edt_text_title);
        edtContent = findViewById(R.id.edt_text_content);

        btnNew.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_new_file:
                newFile();
                break;
            case R.id.btn_open_file:
                showList();
                break;
            case R.id.btn_save_file:
                saveFile();
                break;
        }
    }

    private void newFile(){
        edtTitle.setText("");
        edtContent.setText("");
        Toast.makeText(this, "Clearing File", Toast.LENGTH_SHORT).show();
    }

    private void showList(){
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, getFilesDir().list());
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose notes");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                loadData(items[i].toString());
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadData(String title){
        FileModel fileModel = FileHelper.readFromFile(this,title);
        edtTitle.setText(fileModel.getFileName());
        edtContent.setText(fileModel.getData());
        Toast.makeText(this, "Loading "+fileModel.getFileName()+" data", Toast.LENGTH_SHORT).show();
    }

    private void saveFile(){
        if (edtTitle.getText().toString().isEmpty()){
            Toast.makeText(this, "Title must be filled", Toast.LENGTH_SHORT).show();
        } else if (edtContent.getText().toString().isEmpty()){
            Toast.makeText(this, "Content must be filled", Toast.LENGTH_SHORT).show();
        } else {
            String title = edtTitle.getText().toString();
            String content = edtContent.getText().toString();
            FileModel fileModel = new FileModel();
            fileModel.setFileName(title);
            fileModel.setData(content);
            FileHelper.writeToFile(fileModel, this);
            Toast.makeText(this, "Saving "+fileModel.getFileName()+" data", Toast.LENGTH_SHORT).show();
        }
    }

}