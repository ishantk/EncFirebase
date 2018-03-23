package com.auribises.encfirebase.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.auribises.encfirebase.R;
import com.auribises.encfirebase.adapter.UserAdapter;
import com.auribises.encfirebase.listener.RecyclerAdapterClickListener;
import com.auribises.encfirebase.model.Address;
import com.auribises.encfirebase.model.Song;
import com.auribises.encfirebase.model.User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import butterknife.BindView;

public class HomeActivity extends AppCompatActivity implements RecyclerAdapterClickListener{

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    String uid;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    UserAdapter userAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        uid = auth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.recyclerView);

        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        /*firestore.collection("users").document(uid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });*/


        /*CollectionReference collectionReference1 = firestore.collection("users").document(uid).collection("addresses");
        CollectionReference collectionReference2 = firestore.collection("users").document(uid).collection("songs");


        Address a1 = new Address("2144","B20 KN","Ludhiana","Punjab",141002);
        //Address a2 = new Address("2144","B20 KN","Ludhiana","Punjab",141002);

        Song s1 = new Song("Shape of You","Ed Shreen",5);

        collectionReference1.add(a1).addOnSuccessListener(this, new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String aid = documentReference.getId();
                Log.i("User","Address Created with ID "+aid);
            }
        });

        collectionReference2.add(s1).addOnSuccessListener(this, new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String sid = documentReference.getId();
                Log.i("User","Song Created with ID "+sid);
            }
        });*/

        final ArrayList<User> userList = new ArrayList<>();

        Query query = firestore.collection("users").orderBy("name", Query.Direction.ASCENDING);

        /*query.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for(DocumentSnapshot documentSnapshot : documentSnapshots){
                    User user = documentSnapshot.toObject(User.class);
                    userList.add(user);

                    Log.i("User","Name: "+user.name+" - Email: "+user.email);
                }
            }
        });*/

        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                                                    .setQuery(query, User.class)
                                                    .build();

        userAdapter = new UserAdapter(options);
        userAdapter.setRecyclerAdapterClickListener(this);

        userAdapter.startListening();

        recyclerView.setAdapter(userAdapter);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_home,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.signOut){


            auth.signOut();

            Intent intent = new Intent(HomeActivity.this,RegisterUserActivity.class);
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRecyclerAdapterClicked(int position) {

    }
}
