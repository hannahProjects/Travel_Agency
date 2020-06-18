package com.example.user.android5777_3372_8622_01.Controller;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.user.android5777_3372_8622_01.Model.backend.SystemContract;
import com.example.user.android5777_3372_8622_01.R;

public class UserListActivity extends ListActivity {

    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user_list);

        final SimpleCursorAdapter adapter = new SimpleCursorAdapter
                (
                        this,
                        R.layout.item_row,
                        null,
                        new String[]{SystemContract.UserContract.USER_NAME, SystemContract.UserContract.User_ID},
                        new int[]{R.id.itemId, R.id.itemName}
                );
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    cursor = getContentResolver().query(SystemContract.UserContract.User_URI, null, null, null, null, null);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    adapter.changeCursor(cursor);
                }
            }.execute();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();}




        this.setListAdapter(adapter);


    }

}
