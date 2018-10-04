package com.swipe.shrinkcom.swipe.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.swipe.adapter.InviteAdaptor;
import com.swipe.shrinkcom.swipe.beans.Invite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InviteFriendActivity extends AppCompatActivity {

    ArrayList<Invite> selectUsers;
    List<Invite> temp;
    // Contact List
    ListView listView;
    // Cursor to load contacts list
    Cursor phones, email;

    ContentResolver resolver;
    SearchView search;
    Context context;
    InviteAdaptor adapter;
    LinearLayout invitelayout;

  public static List<Invite> selected;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


         ImageView imgback = (ImageView)toolbar.findViewById(R.id.idimageback);
         imgback.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 finish();
             }
         });
        context = this;
        listView = (ListView) findViewById(R.id.idrecycleview);
        invitelayout = (LinearLayout)findViewById(R.id.invitefriend);
        selected = new ArrayList<>();
        selectUsers = new ArrayList<Invite>();
        resolver = this.getContentResolver();


        phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        LoadContact loadContact = new LoadContact();
        loadContact.execute();
        search = (SearchView) findViewById(R.id.searchView);

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                adapter.filter(newText);
                return false;
            }
        });

        invitelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selected.size()>0){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + "123456"));
                    intent.putExtra("sms_body", "Meet milion of people from around the world!by using Swipe \n https://www.shrinkcom.com/");
                    startActivity(intent);
                }else {
                    Toast.makeText(context, "please Select Contact", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    // Load data on background
    class LoadContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... voids) {
            // Get Contact list from Phone

            if (phones != null) {
                Log.e("count", "" + phones.getCount());
                if (phones.getCount() == 0) {
                    Toast.makeText(context, "No contacts in your contact list.", Toast.LENGTH_LONG).show();
                }

                while (phones.moveToNext()) {
                    Bitmap bit_thumb = null;
                    String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String EmailAddr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA2));
                    String image_thumb = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
                    try {
                        if (image_thumb != null) {
                            bit_thumb = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(image_thumb));
                        } else {
                            Log.e("No Image Thumb", "--------------");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Invite selectUser = new Invite();
                    selectUser.setThumb(bit_thumb);
                    selectUser.setName(name);
                    selectUser.setPhone(phoneNumber);
                    //selectUser.setEmail(id);
                    selectUser.setCheckedBox(false);
                    selectUsers.add(selectUser);
                }
            } else {
                Log.e("Cursor close 1", "----------------");
            }
            //phones.close();
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new InviteAdaptor(selectUsers, context);
            listView.setAdapter(adapter);

            // Select item on listclick
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.e("search", "here---------------- listener");
                    Invite data = selectUsers.get(i);

                    Log.e("dadadaaaa","---->"+data);
                }
            });

            listView.setFastScrollEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        phones.close();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        selected.clear();
    }
}
