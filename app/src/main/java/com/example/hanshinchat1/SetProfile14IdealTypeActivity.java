package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SetProfile14IdealTypeActivity extends MainActivity {

    ArrayAdapter<CharSequence> adspin1, adspin2, adspin3, adspin4;
    String choice_first="";
    String choice_second="";
    String choice_third="";
    String choice_forth="";
    TextView idealFirst, idealSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.set_profile_14_idealtype);

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
                    adspin2 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.키, android.R.layout.simple_spinner_dropdown_item);

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
                    adspin2 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.나이, android.R.layout.simple_spinner_dropdown_item);
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
                    adspin2 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.학년, android.R.layout.simple_spinner_dropdown_item);
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
                    adspin2 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.학과, android.R.layout.simple_spinner_dropdown_item);
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
                    adspin2 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.종교, android.R.layout.simple_spinner_dropdown_item);
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
                    adspin2 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.도, android.R.layout.simple_spinner_dropdown_item);
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
                    adspin2 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.YN, android.R.layout.simple_spinner_dropdown_item);
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
                    adspin2 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.YN, android.R.layout.simple_spinner_dropdown_item);
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
                    adspin2 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.관심사, android.R.layout.simple_spinner_dropdown_item);
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
                    adspin2 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.성격, android.R.layout.simple_spinner_dropdown_item);
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
                    adspin2 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.mbti, android.R.layout.simple_spinner_dropdown_item);
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
                    adspin4 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.키, android.R.layout.simple_spinner_dropdown_item);

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
                    adspin4 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.나이, android.R.layout.simple_spinner_dropdown_item);
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
                    adspin4 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.학년, android.R.layout.simple_spinner_dropdown_item);
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
                    adspin4 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.학과, android.R.layout.simple_spinner_dropdown_item);
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
                    adspin4 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.종교, android.R.layout.simple_spinner_dropdown_item);
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
                    adspin4 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.도, android.R.layout.simple_spinner_dropdown_item);
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
                    adspin4 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.YN, android.R.layout.simple_spinner_dropdown_item);
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
                    adspin4 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.YN, android.R.layout.simple_spinner_dropdown_item);
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
                    adspin4 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.관심사, android.R.layout.simple_spinner_dropdown_item);
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
                    adspin4 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.성격, android.R.layout.simple_spinner_dropdown_item);
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
                    adspin4 = ArrayAdapter.createFromResource(SetProfile14IdealTypeActivity.this, R.array.mbti, android.R.layout.simple_spinner_dropdown_item);
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

                                    Intent intent = new Intent(getApplicationContext(), SetProfile15MbtiActivity.class);
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

    }
}
