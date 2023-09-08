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

public class SetProfile10addressActivity extends MainActivity {

    ArrayAdapter<CharSequence> adspin1, adspin2;
    String choice_first="";
    String choice_second="";

    TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.set_profile_10_address);

        Button nextBtn = findViewById(R.id.set_address_next);

        address = (TextView) findViewById(R.id.address);

        final Spinner spin1 = (Spinner)findViewById(R.id.first_address_spinner);
        final Spinner spin2 = (Spinner)findViewById(R.id.second_address_spinner);


        adspin1 = ArrayAdapter.createFromResource(this, R.array.도, android.R.layout.simple_spinner_dropdown_item);
        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adspin1);

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adspin1.getItem(i).equals("서울특별시")) {
                    choice_first = "서울특별시";
                    adspin2 = ArrayAdapter.createFromResource(SetProfile10addressActivity.this, R.array.서울특별시, android.R.layout.simple_spinner_dropdown_item);

                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            address.setText(choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            address.setText("지역 선택");
                        }
                    });
                } else if (adspin1.getItem(i).equals("부산광역시")) {
                    choice_first = "부산광역시";
                    adspin2 = ArrayAdapter.createFromResource(SetProfile10addressActivity.this, R.array.부산광역시, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            address.setText(choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            address.setText("지역 선택");
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("대구광역시")) {
                    choice_first = "대구광역시";
                    adspin2 = ArrayAdapter.createFromResource(SetProfile10addressActivity.this, R.array.대구광역시, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            address.setText(choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            address.setText("지역 선택");
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("인천광역시")) {
                    choice_first = "인천광역시";
                    adspin2 = ArrayAdapter.createFromResource(SetProfile10addressActivity.this, R.array.인천광역시, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            address.setText(choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            address.setText("지역 선택");
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("대전광역시")) {
                    choice_first = "대전광역시";
                    adspin2 = ArrayAdapter.createFromResource(SetProfile10addressActivity.this, R.array.대전광역시, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            address.setText(choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            address.setText("지역 선택");
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("광주광역시")) {
                    choice_first = "광주광역시";
                    adspin2 = ArrayAdapter.createFromResource(SetProfile10addressActivity.this, R.array.광주광역시, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            address.setText(choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            address.setText("지역 선택");
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("울산광역시")) {
                    choice_first = "울산광역시";
                    adspin2 = ArrayAdapter.createFromResource(SetProfile10addressActivity.this, R.array.울산광역시, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            address.setText(choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            address.setText("지역 선택");
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("경기도")) {
                    choice_first = "경기도";
                    adspin2 = ArrayAdapter.createFromResource(SetProfile10addressActivity.this, R.array.경기도, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            address.setText(choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            address.setText("지역 선택");
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("충청남도")) {
                    choice_first = "충청남도";
                    adspin2 = ArrayAdapter.createFromResource(SetProfile10addressActivity.this, R.array.충청남도, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            address.setText(choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            address.setText("지역 선택");
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("충청북도")) {
                    choice_first = "충청북도";
                    adspin2 = ArrayAdapter.createFromResource(SetProfile10addressActivity.this, R.array.충청북도, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            address.setText(choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            address.setText("지역 선택");
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("강원도")) {
                    choice_first = "강원도";
                    adspin2 = ArrayAdapter.createFromResource(SetProfile10addressActivity.this, R.array.강원도, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            address.setText(choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            address.setText("지역 선택");
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("경상북도")) {
                    choice_first = "경상북도";
                    adspin2 = ArrayAdapter.createFromResource(SetProfile10addressActivity.this, R.array.경상북도, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            address.setText(choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            address.setText("지역 선택");
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("경상남도")) {
                    choice_first = "경상남도";
                    adspin2 = ArrayAdapter.createFromResource(SetProfile10addressActivity.this, R.array.경상남도, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            address.setText(choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            address.setText("지역 선택");
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("전라북도")) {
                    choice_first = "전라북도";
                    adspin2 = ArrayAdapter.createFromResource(SetProfile10addressActivity.this, R.array.전라북도, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            address.setText(choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            address.setText("지역 선택");
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("전라남도")) {
                    choice_first = "전라남도";
                    adspin2 = ArrayAdapter.createFromResource(SetProfile10addressActivity.this, R.array.전라남도, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_second = adspin2.getItem(i).toString();
                            address.setText(choice_first + " " + choice_second);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            address.setText("지역 선택");
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
                            String strAddress = address.getText().toString();
                            if (!strAddress.isEmpty()) {
                                try {
                                    userInfo.setAddress(strAddress);
                                    usersRef.setValue(userInfo);

                                    Intent intent = new Intent(getApplicationContext(), SetProfile11ReligionActivity.class);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                } catch (NumberFormatException e) {
                                    Toast.makeText(getApplicationContext(), "올바른 주소를 선택해주세요", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "주소를 선택해주세요", Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), SetProfile9FormActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
