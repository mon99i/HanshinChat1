package com.example.hanshinchat1;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetIdealActivity extends MainActivity {

    ListView listView;
    TextView firstPriorityTxt;
    TextView firstChoiceTxt;
    TextView secondPriorityTxt;
    TextView secondChoiceTxt;
    TextView thirdPriorityTxt;
    TextView thirdChoiceTxt;

    RecyclerView recyclerView;

    private ArrayList<String> idealList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_ideal);

        initializeView();
        initializeListener();


    }


    private void initializeView(){



        firstPriorityTxt=findViewById(R.id.firstPriorityTxt);
        firstChoiceTxt=findViewById(R.id.firstChoiceTxt);
        secondPriorityTxt=findViewById(R.id.secondPriorityTxt);
        secondChoiceTxt=findViewById(R.id.secondChoiceTxt);
        thirdPriorityTxt=findViewById(R.id.thirdPrioirtyTxt);
        thirdChoiceTxt=findViewById(R.id.thirdChoiceTxt);


        firstChoiceTxt.setPaintFlags(firstChoiceTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        secondChoiceTxt.setPaintFlags(secondChoiceTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        thirdChoiceTxt.setPaintFlags(thirdChoiceTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        //글자아래에 밑줄
       /* Paint paint = firstChoiceTxt.getPaint();
        paint.setFlags(paint.getFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Paint paint1 = secondChoiceTxt.getPaint();
        paint.setFlags(paint1.getFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Paint paint2 = thirdChoiceTxt.getPaint();
        paint.setFlags(paint2.getFlags() | Paint.UNDERLINE_TEXT_FLAG);
*/


    }

    private void initializeListener(){

        //글자 누르면 이벤트발생
        firstChoiceTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();


                //items.
            }
        });

        secondChoiceTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
               /* secondPriorityTxt.setText(idealPriority);
                idealList.remove(idealPriority);*/
            }
        });







    }

    private void showDialog(){

        //먼저 dialog xml을 생성함
        LayoutInflater inflater = getLayoutInflater();
        View customLayout = inflater.inflate(R.layout.ideal_prioity_dialog, null);

        //가져온 dialog xml로 부터 리사이클러뷰 초기화
        recyclerView=customLayout.findViewById(R.id.recycler_idealPriority);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(customLayout);

        final AlertDialog dialog = builder.create();
        recyclerView.setAdapter(new RecyclerIdealAdapter(this,dialog));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        /*alertDialog.getWindow().setGravity(Gravity.TOP); //상단에 위치
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);  //밖에 배경 어둡지않게
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));  // 배경 투명하게
        //alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);*/

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        dialog.show();



   /*     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = items.get(position);
                //Toast.makeText(MainActivity.this, "선택한 항목: " + selectedItem, Toast.LENGTH_SHORT).show();
                dialog.dismiss(); // 항목을 선택하면 AlertDialog 닫기
            }
        });*/

        // AlertDialog 보이기



    }



   /* ArrayAdapter<CharSequence> adspin1, adspin2, adspin3, adspin4;
    String choice_first="";
    String choice_second="";
    String choice_third="";
    String choice_forth="";
    TextView idealFirst, idealSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_ideal);

        Button nextBtn = findViewById(R.id.set_idealtype_next);

        idealFirst = (TextView) findViewById(R.id.ideal_first);
        idealSecond = (TextView) findViewById(R.id.ideal_second);

        final Spinner spin1 = (Spinner)findViewById(R.id.first_idealtype_spinner1);
        final Spinner spin2 = (Spinner)findViewById(R.id.first_idealtype_spinner2);

        final Spinner spin3 = (Spinner)findViewById(R.id.second_idealtype_spinner1);
        final Spinner spin4 = (Spinner)findViewById(R.id.second_idealtype_spinner2);

        adspin1 = ArrayAdapter.createFromResource(this, R.array.이상형조건1, android.R.layout.simple_spinner_dropdown_item);
        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adspin1);

        adspin3 = ArrayAdapter.createFromResource(this, R.array.이상형조건1, android.R.layout.simple_spinner_dropdown_item);
        adspin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(adspin3);

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adspin1.getItem(i).equals("키")) {
                    choice_first = "키";
                    adspin2 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.키, android.R.layout.simple_spinner_dropdown_item);

                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            idealFirst.setText("1순위 : " + choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealFirst.setText("1순위 선택");
                        }
                    });
                } else if (adspin1.getItem(i).equals("나이")) {
                    choice_first = "나이";
                    adspin2 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.나이, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            idealFirst.setText("1순위 : " + choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealFirst.setText("1순위 선택");
                        }
                    });
                } else if (adspin1.getItem(i).equals("학년")) {
                    choice_first = "학년";
                    adspin2 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.학년, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            idealFirst.setText("1순위 : " + choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealFirst.setText("1순위 선택");
                        }
                    });
                } else if (adspin1.getItem(i).equals("학과")) {
                    choice_first = "학과";
                    adspin2 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.학과, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            idealFirst.setText("1순위 : " + choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealFirst.setText("1순위 선택");
                        }
                    });
                } else if (adspin1.getItem(i).equals("종교")) {
                    choice_first = "종교";
                    adspin2 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.종교, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            idealFirst.setText("1순위 : " + choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealFirst.setText("1순위 선택");
                        }
                    });
                } else if (adspin1.getItem(i).equals("거주지")) {
                    choice_first = "거주지";
                    adspin2 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.도, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            idealFirst.setText("1순위 : " + choice_first + " " + choice_second);
                        }
                        // 도 까지만 선택할 수 있게 해놓았는데, 도 + 시 선택할 수 있게 수정해야 함.

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealFirst.setText("1순위 선택");
                        }
                    });
                } else if (adspin1.getItem(i).equals("흡연여부")) {
                    choice_first = "흡연여부";
                    adspin2 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.YN, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            idealFirst.setText("1순위 : " + choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealFirst.setText("1순위 선택");
                        }
                    });
                } else if (adspin1.getItem(i).equals("음주여부")) {
                    choice_first = "음주여부";
                    adspin2 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.YN, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            idealFirst.setText("1순위 : " + choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealFirst.setText("1순위 선택");
                        }
                    });
                } else if (adspin1.getItem(i).equals("관심사")) {
                    choice_first = "관심사";
                    adspin2 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.관심사, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            idealFirst.setText("1순위 : " + choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealFirst.setText("1순위 선택");
                        }
                    });
                } else if (adspin1.getItem(i).equals("성격")) {
                    choice_first = "성격";
                    adspin2 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.성격, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            idealFirst.setText("1순위 : " + choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealFirst.setText("1순위 선택");
                        }
                    });
                } else if (adspin1.getItem(i).equals("MBTI")) {
                    choice_first = "MBTI";
                    adspin2 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.mbti, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            idealFirst.setText("1순위 : " + choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealFirst.setText("1순위 선택");
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adspin3.getItem(i).equals("키")) {
                    choice_third = "키";
                    adspin4 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.키, android.R.layout.simple_spinner_dropdown_item);

                    adspin4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_forth = adspin4.getItem(i).toString();
                            idealSecond.setText("2순위 : " + choice_third + " " + choice_forth);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealSecond.setText("2순위 선택");
                        }
                    });
                } else if (adspin3.getItem(i).equals("나이")) {
                    choice_third = "나이";
                    adspin4 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.나이, android.R.layout.simple_spinner_dropdown_item);
                    adspin4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_forth = adspin4.getItem(i).toString();
                            idealSecond.setText("2순위 : " + choice_third + " " + choice_forth);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealSecond.setText("2순위 선택");
                        }
                    });
                } else if (adspin3.getItem(i).equals("학년")) {
                    choice_third = "학년";
                    adspin4 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.학년, android.R.layout.simple_spinner_dropdown_item);
                    adspin4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_forth = adspin4.getItem(i).toString();
                            idealSecond.setText("2순위 : " + choice_third + " " + choice_forth);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealSecond.setText("2순위 선택");
                        }
                    });
                } else if (adspin3.getItem(i).equals("학과")) {
                    choice_third = "학과";
                    adspin4 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.학과, android.R.layout.simple_spinner_dropdown_item);
                    adspin4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_forth = adspin4.getItem(i).toString();
                            idealSecond.setText("2순위 : " + choice_third + " " + choice_forth);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealSecond.setText("2순위 선택");
                        }
                    });
                } else if (adspin3.getItem(i).equals("종교")) {
                    choice_third = "종교";
                    adspin4 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.종교, android.R.layout.simple_spinner_dropdown_item);
                    adspin4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_forth = adspin4.getItem(i).toString();
                            idealSecond.setText("2순위 : " + choice_third + " " + choice_forth);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealSecond.setText("2순위 선택");
                        }
                    });
                } else if (adspin3.getItem(i).equals("거주지")) {
                    choice_third = "거주지";
                    adspin4 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.도, android.R.layout.simple_spinner_dropdown_item);
                    adspin4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_forth = adspin4.getItem(i).toString();
                            idealSecond.setText("2순위 : " + choice_third + " " + choice_forth);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealSecond.setText("2순위 선택");
                        }
                    });
                } else if (adspin3.getItem(i).equals("흡연여부")) {
                    choice_third = "흡연여부";
                    adspin4 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.YN, android.R.layout.simple_spinner_dropdown_item);
                    adspin4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_forth = adspin4.getItem(i).toString();
                            idealSecond.setText("2순위 : " + choice_third + " " + choice_forth);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealSecond.setText("2순위 선택");
                        }
                    });
                } else if (adspin3.getItem(i).equals("음주여부")) {
                    choice_third = "음주여부";
                    adspin4 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.YN, android.R.layout.simple_spinner_dropdown_item);
                    adspin4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_forth = adspin4.getItem(i).toString();
                            idealSecond.setText("2순위 : " + choice_third + " " + choice_forth);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealSecond.setText("2순위 선택");
                        }
                    });
                } else if (adspin3.getItem(i).equals("관심사")) {
                    choice_third = "관심사";
                    adspin4 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.관심사, android.R.layout.simple_spinner_dropdown_item);
                    adspin4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_forth = adspin4.getItem(i).toString();
                            idealSecond.setText("2순위 : " + choice_third + " " + choice_forth);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealSecond.setText("2순위 선택");
                        }
                    });
                } else if (adspin3.getItem(i).equals("성격")) {
                    choice_third = "성격";
                    adspin4 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.성격, android.R.layout.simple_spinner_dropdown_item);
                    adspin4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_forth = adspin4.getItem(i).toString();
                            idealSecond.setText("2순위 : " + choice_third + " " + choice_forth);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealSecond.setText("2순위 선택");
                        }
                    });
                } else if (adspin3.getItem(i).equals("MBTI")) {
                    choice_third = "MBTI";
                    adspin4 = ArrayAdapter.createFromResource(SetIdealActivity.this, R.array.mbti, android.R.layout.simple_spinner_dropdown_item);
                    adspin4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin4.setAdapter(adspin4);
                    spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_forth = adspin4.getItem(i).toString();
                            idealSecond.setText("2순위 : " + choice_third + " " + choice_forth);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            idealSecond.setText("2순위 선택");
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference usersRef = myRef.child("users").child(user.getUid());
                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            UserInfo userInfo = snapshot.getValue(UserInfo.class);
                            String strFirIdealType = idealFirst.getText().toString();
                            String strSecIdealType = idealSecond.getText().toString();
                            if (!strFirIdealType.isEmpty() && !strSecIdealType.isEmpty()) {
                                try {
                                    userInfo.setIdealTypeFirst(strFirIdealType);
                                    userInfo.setIdealTypeSecond(strSecIdealType);
                                    usersRef.setValue(userInfo);

                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                } catch (NumberFormatException e) {
                                    Toast.makeText(getApplicationContext(), "올바른 이상형을 선택해주세요", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "이상형을 선택해주세요", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "오류 발생", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "프로필 저장 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }*/
}