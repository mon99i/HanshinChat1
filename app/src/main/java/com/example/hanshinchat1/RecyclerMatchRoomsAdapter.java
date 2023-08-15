package com.example.hanshinchat1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerMatchRoomsAdapter extends RecyclerView.Adapter<RecyclerMatchRoomsAdapter.ViewHolder> {

    private Context context;
    private final static String TAG="매칭요청실패";

    private UserInfo userInfo=new UserInfo();
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    private List<MatchRoom> matchRoomsList;
    private ArrayList<String> matchKeyList;

    public RecyclerMatchRoomsAdapter(Context context) {
        this.context = context;
        setupAllMatchRoomList();
        matchRoomsList=new ArrayList<>();
        matchKeyList=new ArrayList<>();
    }

    private void setupAllMatchRoomList() {
        FirebaseDatabase.getInstance().getReference().child("matchRooms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                matchRoomsList.clear();                  //채팅룸 ArrayList 초기화
                matchKeyList.clear();
                for(DataSnapshot item:snapshot.getChildren()){
                    if(item!=null){
                        matchRoomsList.add(item.getValue(MatchRoom.class));
                        matchKeyList.add(item.getKey());
                    }

                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @NonNull
    @Override
    public RecyclerMatchRoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_matchroom, parent, false);

        return new RecyclerMatchRoomsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerMatchRoomsAdapter.ViewHolder holder, int position) {

        final int currentPosition = holder.getAdapterPosition();
        MatchRoom matchRoom=matchRoomsList.get(currentPosition);
        holder.txt_roomTitle.setText(matchRoom.getMatchInfo().getTitle());
        holder.txt_roomCategory.setText(matchRoom.getMatchInfo().getCategory());
        holder.btn_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMatch(currentPosition);
            }
        });
     /*   FirebaseDatabase.getInstance().getReference().child("matchRooms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()){
                    holder.txt_roomCategory.setText(item.getValue(MatchRoom.class).getMatchInfo().getCategory());
                    holder.txt_roomTitle.setText(item.getValue(MatchRoom.class).getMatchInfo().getTitle());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }

   /* private void putMessage() {          //데이터베이스에 메세지 넣고, 저장된 데이터를 다시 리사이클러뷰에 보이게하는거임
        try {
            Message message = new Message(myUid, getDateTimeString(), edt_message.getText().toString());
            FirebaseDatabase.getInstance().getReference().child("chatRooms")
                    .child(chatRoomKey).child("messages")
                    .push().setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            edt_message.getText().clear();
                        }
                    }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    private void requestMatch(int position) {
        MatchRoom matchRoom=matchRoomsList.get(position);
        String matchKey=matchKeyList.get(position);

        if(!matchRoom.getHost().equals(user.getUid())&&matchRoom!=null) {   //방만든애가 지가 만든 방에 요청 안되게
            Guest guest=new Guest();
            DatabaseReference reguestRef=FirebaseDatabase.getInstance().getReference().child("matchRooms").child(matchKey).child("guest").child(user.getUid());
            reguestRef.child("request").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    Toast.makeText(context,"매칭 요청 성공",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onComplete: 요청성공");
                }
            });
        }else  Toast.makeText(context,"내가 만든 방!",Toast.LENGTH_SHORT).show();
    }



    @Override
    public int getItemCount() {
        return matchRoomsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View background;
        Button btn_match;
        TextView txt_roomTitle;
        TextView txt_roomCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            background=itemView.findViewById(R.id.matchRoomBackground);
            btn_match=itemView.findViewById(R.id.btn_match);
            txt_roomTitle=itemView.findViewById(R.id.txt_roomTitle);
            txt_roomCategory=itemView.findViewById(R.id.txt_roomCategory);
        }
    }
}
