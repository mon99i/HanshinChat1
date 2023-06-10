package com.example.hanshinchat1;

import android.os.Bundle;

public class ProfileActivity extends MainActivity{

    /*FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseStorage storage;
    StorageReference storageRef;
    ImageView profile;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        checkCurrentUser();
        checkProfileExist();

        clickBoard();
        clickChat();
        clickRoom();
        clickHome();

        /*database=FirebaseDatabase.getInstance();
        myRef=database.getReference();
        storage=FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        profile=(ImageView)findViewById(R.id.profile);

        myRef.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserInfo userInfo=snapshot.getValue(UserInfo.class);
                    String Url=userInfo.getPhotoUrl();
                    Uri imageUri=Uri.parse(Url);

                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        // 비트맵 사용
                        profile.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                   // profile.setImageURI(imageUri);
                }
                else  Toast.makeText(getApplicationContext(), "개같은오류!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

       /* ImageButton homeBtn = findViewById(R.id.home);
        ImageButton roomBtn = findViewById(R.id.room);
        ImageButton chatBtn = findViewById(R.id.chat);
        ImageButton postBtn = findViewById(R.id.post);
        ImageButton infoBtn = findViewById(R.id.info);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(chatIntent);
                finish();
            }
        });
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postIntent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(postIntent);
                finish();
            }
        });
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent infoIntent = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(infoIntent);
                finish();
            }
        });*/


        // 하단바 Fragment 상속해서 사용할때 쓸 코드

//        //기본화면 설정, MainActivity xml 만든 후 넣기
//         //getSupportFragmentManager().beginTransaction().replace(R.id.top_view, mainActivity).commit;
//
//        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigationview);
//        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId()){
////                    case R.id.home:
////                        getSupportFragmentManager().beginTransaction().replace(R.id.top_view,mainActivity).commit();
////                    case R.id.room:
////                        getSupportFragmentManager().beginTransaction().replace(R.id.top_view,mainActivity).commit();
//                    case R.id.post:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.top_view,listActivity).commit();
//                    case R.id.chat:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.top_view,chatActivity).commit();
////                    case R.id.info:
////                        getSupportFragmentManager().beginTransaction().replace(R.id.top_view,mainActivity).commit();
//                        return true;
//                }
//                return false;
//            }
//        });

    }
}
