package com.example.hanshinchat1.ProfileFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hanshinchat1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ProfileNameFragment extends Fragment {
    private EditText name;
    DatabaseReference myRef;
    FirebaseUser user;
    public ProfileNameFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_name_fragment, container, false);
        name = (EditText) view.findViewById(R.id.name_fragment);
        myRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        return view;
    }

    public void updateDB() {
        String strName = name.getText().toString();

        if (strName.isEmpty()||name==null) {
            Toast.makeText(getContext(), "이름을 입력하세요", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference userRef = myRef.child("users").child(user.getUid());
            userRef.child("name").setValue(strName);
        }
    }

    public String editDB() {
        String newName = name.getText().toString();
        if (newName.isEmpty() || name == null) {
            Toast.makeText(getContext(), "이름을 입력하세요", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            return newName;
        }
    }
}
