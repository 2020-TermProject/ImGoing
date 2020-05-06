package com.example.a2020project;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

public class SignUPActivity extends AppCompatActivity {
    SessionCallback callback;
    public static final String NICKNAME = "nick";
    public static final String USER_ID = "id";
    public static final String PROFILE_IMG = "img";
    String kid, email, name, userowner, restaurantName, businessNo, category, restaurantLongitude, restaurantLatitude;
    public Button backButton;
    public EditText restaurantNameTextBox;
    public EditText businessNoTextBox;
    public Spinner spinnerCategory;
    public Button findButton;


    public static final int sub = 1001;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //hide the options for user
        restaurantNameTextBox = (EditText) findViewById(R.id.restaurantName);
        businessNoTextBox = (EditText) findViewById(R.id.businessNo);
        spinnerCategory = (Spinner) findViewById(R.id.txt_question_type);
        findButton = (Button) findViewById(R.id.locationfind);
        ((TextView) findViewById(R.id.textview_restaurantName)).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.textview_businessNo)).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.textview_category)).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.restaurantLocation)).setVisibility(View.GONE);
        restaurantNameTextBox.setVisibility(View.GONE);
        businessNoTextBox.setVisibility(View.GONE);
        spinnerCategory.setVisibility(View.GONE);
        findButton.setVisibility(View.GONE);

        backButton =(Button)findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();

            }
        });


        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                //로그아웃 성공 후 하고싶은 내용 코딩 ~

            }
        });

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
    }


//
    public void hideOptions(View view){
        AppCompatCheckBox appCompatCheckBox = (AppCompatCheckBox)view;
        if(appCompatCheckBox.isChecked()) {
            ((TextView) findViewById(R.id.textview_restaurantName)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.textview_businessNo)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.textview_category)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.restaurantLocation)).setVisibility(View.VISIBLE);
            restaurantNameTextBox.setVisibility(View.VISIBLE);
            businessNoTextBox.setVisibility(View.VISIBLE);
            spinnerCategory.setVisibility(View.VISIBLE);
            findButton.setVisibility(View.VISIBLE);
        }
        else{
            ((TextView) findViewById(R.id.textview_restaurantName)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.textview_businessNo)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.textview_category)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.restaurantLocation)).setVisibility(View.GONE);
            restaurantNameTextBox.setVisibility(View.GONE);
            businessNoTextBox.setVisibility(View.GONE);
            spinnerCategory.setVisibility(View.GONE);
            findButton.setVisibility(View.GONE);
        }
    }

    public void startSignup(View v){
        AppCompatCheckBox appCompatCheckBox = (AppCompatCheckBox) findViewById(R.id.UserOwnerCheck);


        String owneruser;
        if(appCompatCheckBox.isChecked()) {
            restaurantName = ((EditText) findViewById(R.id.restaurantName)).getText().toString();
            businessNo = ((EditText) findViewById(R.id.businessNo)).getText().toString();
            category = ((Spinner) findViewById(R.id.txt_question_type)).getSelectedItem().toString();
            owneruser = "o";
        }
        else{
            restaurantName = "";
            businessNo = "";
            category = "";
            owneruser = "u";
        }


        try {
            SignUp signupTask = new SignUp();
            String msg = String.valueOf(signupTask.execute("http://khprince.com/restaurantApp/login.php", "abc","kakaak", "hi11","o","1212","kdkd","123123","111","1222"/*name, email, kid, owneruser , restaurantName, businessNo,  category ,"restaurantLatitude", "restaurantLongitude"*/));

        }catch (Exception e){
            e.printStackTrace();
            Log.e("tag","In cat");
        }

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //간편로그인시 호출 ,없으면 간편로그인시 로그인 성공화면으로 넘어가지 않음
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    //SessionCallback 클래스 구현
    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {


            UserManagement.getInstance()
                    .me(new MeV2ResponseCallback() {
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                            Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                        }

                        @Override
                        public void onFailure(ErrorResult errorResult) {
                            Log.e("KAKAO_API", "사용자 정보 요청 실패: " + errorResult);
                        }

                        @Override
                        public void onSuccess(MeV2Response result) {
                            Log.i("KAKAO_API", "사용자 아이디: " + result.getId());

                            UserAccount kakaoAccount = result.getKakaoAccount();
                            if (kakaoAccount != null) {

                                // 이메일
                                String mail = kakaoAccount.getEmail();

                                if (mail != null) {
                                    Log.i("KAKAO_API", "email: " + mail);

                                } else if (kakaoAccount.emailNeedsAgreement() == OptionalBoolean.TRUE) {
                                    // 동의 요청 후 이메일 획득 가능
                                    // 단, 선택 동의로 설정되어 있다면 서비스 이용 시나리오 상에서 반드시 필요한 경우에만 요청해야 합니다.

                                } else {
                                    // 이메일 획득 불가
                                }

                                // 프로필
                                Profile profile = kakaoAccount.getProfile();

                                if (profile != null) {
                                    Log.d("KAKAO_API", "nickname: " + profile.getNickname());
                                    Log.d("KAKAO_API", "profile image: " + profile.getProfileImageUrl());
                                    Log.d("KAKAO_API", "thumbnail image: " + profile.getThumbnailImageUrl());

                                    Intent intent = new Intent(SignUPActivity.this, SignUPActivity.class);
                                    final String nickName = profile.getNickname();
                                    intent.putExtra("NICKNAME",nickName);
                                    Log.d("check","nickName check" + nickName);
                                    intent.putExtra("USER_ID",String.valueOf(result.getId()));
                                    intent.putExtra("PROFILE_IMG",profile.getProfileImageUrl());
                                    intent.putExtra("E-MAIL", mail);
                                    name = nickName;
                                    kid = String.valueOf(result.getId());
                                    Log.e("name is ", name);

                                    //finish();
                                    startActivity(intent);
                                } else if (kakaoAccount.profileNeedsAgreement() == OptionalBoolean.TRUE) {
                                    // 동의 요청 후 프로필 정보 획득 가능

                                } else {
                                    // 프로필 획득 불가
                                }
                            }
                        }
                    });

        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            // 세션 연결이 실패했을때
            if(exception != null) {
                Logger.e(exception);
            }
        }
    }
}
