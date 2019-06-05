package com.hufs.cdt;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;


public class Mypage extends AppCompatActivity{

    public static String strEmail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        Intent intent= getIntent();
        strEmail = intent.getStringExtra("email");
        final TextView logtx=(TextView)findViewById(R.id.login_tx);

        TextView textView=(TextView)findViewById(R.id.login_id);



        Button post_btn1 = (Button)findViewById(R.id.post_btn1);
        final Button login_btn = (Button)findViewById(R.id.login_btn);
        Button home = (Button)findViewById(R.id.action_btn);
        Button logout=(Button)findViewById(R.id.logout);
        Button signout=(Button)findViewById(R.id.signout);
        Button selectoption=(Button)findViewById(R.id.selectoption);
        if(strEmail!=null){
            textView.setText(getmyid());
            login_btn.setVisibility(View.GONE);
            logtx.setText("로그인 완료");
            ; //로그아웃 Toast 메세지
        }
        else{
            logout.setVisibility(View.GONE);
            signout.setVisibility(View.GONE);
            selectoption.setVisibility(View.GONE);
        }


        post_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (strEmail != null) {
                    Intent intent = new Intent(getApplicationContext(), search_result_list.class);
                    Log.e("inputaddress", strEmail);
                    intent.putExtra("inputaddress", strEmail);
                    startActivityForResult(intent, 3);
                } else {
                    Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show(); //로그아웃 Toast 메세지
                }
            }
        });

        selectoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),option.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(strEmail==null) {
                    Intent intent = new Intent(
                            getApplicationContext(),
                            Login.class);
                    startActivity(intent);
                }

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "정상적으로 로그아웃되었습니다.", Toast.LENGTH_SHORT).show(); //로그아웃 Toast 메세지

                //실제 로그아웃 처리
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        //로그아웃에 성공하면: LoginActivity로 이동
                        Intent intent = new Intent(Mypage.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
            }
        });

        //회원탈퇴 버튼
        signout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Mypage.this) //탈퇴 의사를 묻는 팝업창 생성
                        .setMessage("탈퇴하시겠습니까?") //팝업창 메세지
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { //"네" 버튼 클릭 시 -> 회원탈퇴 수행
                                //회원탈퇴 수행
                                UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                                    @Override
                                    public void onFailure(ErrorResult errorResult) { //회원탈퇴 실패 시
                                        int result = errorResult.getErrorCode(); //에러코드 받음

                                        if(result == ApiErrorCode.CLIENT_ERROR_CODE) { //클라이언트 에러인 경우 -> 네트워크 오류
                                            Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                        } else { //클라이언트 에러가 아닌 경우 -> 기타 오류
                                            Toast.makeText(getApplicationContext(), "회원탈퇴에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onSessionClosed(ErrorResult errorResult) { //처리 도중 세션이 닫힌 경우
                                        Toast.makeText(getApplicationContext(), "로그인 세션이 닫혔습니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Mypage.this, Login.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onNotSignedUp() { //가입된 적이 없는 계정에서 탈퇴를 요구하는 경우
                                        Toast.makeText(getApplicationContext(), "가입되지 않은 계정입니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Mypage.this, Login.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onSuccess(Long result) { //회원탈퇴에 성공한 경우
                                        Toast.makeText(getApplicationContext(), "회원탈퇴에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Mypage.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                                dialog.dismiss(); //팝업창 제거
                            }
                        })
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { //"아니요" 버튼 클릭 시 -> 팝업창 제거
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }


    public static final String getmyid(){
        return strEmail;
    }
}

