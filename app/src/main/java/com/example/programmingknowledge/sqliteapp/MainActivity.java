package com.example.programmingknowledge.sqliteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editSurname, editMarks ,editTextId;
    Button btnAddData, btnviewAll;
    Button btnviewUpdate;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.editText_name);
        editSurname = (EditText) findViewById(R.id.editText_surname);
        editMarks = (EditText) findViewById(R.id.editText_Marks);
        editTextId = (EditText)findViewById(R.id.editText_id);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnviewAll = (Button) findViewById(R.id.button_viewAll);
        btnviewUpdate = (Button) findViewById(R.id.button_update);
        btnDelete = (Button)findViewById(R.id.button_delete);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }

    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deleteRows = myDb.deleteData(editTextId.getText().toString());
                        if(deleteRows >0)
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void UpdateData() {
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editTextId.getText().toString(),editName.getText().toString(),editSurname.getText().toString(),editMarks.getText().toString());//.toString() in all
                        if(isUpdate == true){
                            Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();}
                            else
                            Toast.makeText(MainActivity.this, "Data not Upated", Toast.LENGTH_SHORT).show();

                    }
                }
        );
    }

    public void AddData() {
        btnAddData.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              boolean isInserted = myDb.insertData(editName.getText().toString(),
                                                      editSurname.getText().toString(),
                                                      editMarks.getText().toString());
                                              if (isInserted = true)
                                                  Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                                              else
                                                  Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                                          }
                                      }
        );
    }

    public void viewAll() {
        btnviewAll.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Cursor res = myDb.getAllData();
                                              if (res.getCount() == 0) {
                                                  //Show Message
                                                  showMessage("Error", "Nothing found(Abe Kuch Nahi Hai re)");
                                                  return;
                                              }

                                              StringBuffer buffer = new StringBuffer();
                                              while (res.moveToNext()) {
                                                  buffer.append("Id : " + res.getString(0) + "\n");
                                                  buffer.append("Name : " + res.getString(1) + "\n");
                                                  buffer.append("SurName : " + res.getString(2) + "\n");
                                                  buffer.append("Marks : " + res.getString(3) + "\n\n");
                                              }
                                              ///Show All Data
                                              showMessage("Data", buffer.toString());
                                          }
                                      }
        );
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }
}
