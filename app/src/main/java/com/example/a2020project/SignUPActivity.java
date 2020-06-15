package com.example.a2020project;

import android.annotation.SuppressLint;
import android.app.AppComponentFactory;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.webkit.WebSettings;

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

import java.io.IOException;

public class SignUPActivity extends AppCompatActivity {
    private WebSettings webSettings;
    SessionCallback callback;
    public static final String NICKNAME = "nick";
    public static final String USER_ID = "id";
    public static final String PROFILE_IMG = "img";
    String kid, email, name, userowner, restaurantName, businessNo, category, restaurantLongitude, restaurantLatitude;
    public Button backButton, convertTest;
    public EditText restaurantNameTextBox;
    public EditText businessNoTextBox;
    public Spinner spinnerCategory;

    //주소 찾기
    private WebView webView;
    private TextView txt_address;
    private Handler handler;


    public static final int sub = 1001;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //WebView , 주소찾기용
        txt_address = findViewById(R.id.sign_txt_address);
        // WebView 초기화
        init_webView();
        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();

        //hide the options for user
        restaurantNameTextBox = (EditText) findViewById(R.id.restaurantName);
        businessNoTextBox = (EditText) findViewById(R.id.businessNo);
        spinnerCategory = (Spinner) findViewById(R.id.txt_question_type);
        ((TextView) findViewById(R.id.textview_restaurantName)).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.textview_businessNo)).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.textview_category)).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.restaurantLocation)).setVisibility(View.GONE);
        restaurantNameTextBox.setVisibility(View.GONE);
        businessNoTextBox.setVisibility(View.GONE);
        spinnerCategory.setVisibility(View.GONE);
        webView.setVisibility(View.GONE);

        convertTest = findViewById(R.id.test_address_1);

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

        //onClick listener 정리
        onClick();
    }


    public void onButton26Clicked(View view){
        //Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://khprince.com/restaurantApp/addressInquiry.php"));
        Intent intent = new Intent(getApplicationContext(), SearchAddress.class);
        startActivityForResult(intent,1001);
    }
    //버튼 클릭했을때 나오는것 여기 정리
    public void onClick(){
        convertTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {

                    ConvertAddress convert = new ConvertAddress("부산대학로64번길6",getApplicationContext());
                    double lon = convert.getlon();
                    double lat = convert.getlat();
                    Toast.makeText(getApplicationContext(),"부산대학로64번길6의 위치 - \n위도: " + lon + "\n경도: " + lat,Toast.LENGTH_LONG).show();
                    Log.d("point is"," lon: " + lat + " lon: " + lon);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
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
            webView.setVisibility(View.VISIBLE);
        }
        else{
            ((TextView) findViewById(R.id.textview_restaurantName)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.textview_businessNo)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.textview_category)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.restaurantLocation)).setVisibility(View.GONE);
            restaurantNameTextBox.setVisibility(View.GONE);
            businessNoTextBox.setVisibility(View.GONE);
            spinnerCategory.setVisibility(View.GONE);
            webView.setVisibility(View.GONE);
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
            String msg = String.valueOf(signupTask.execute("http://khprince.com/restaurantApp/signup.php",name, email, kid, owneruser , restaurantName, businessNo,  category ,"restaurantLatitude", "restaurantLongitude"));

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
        if(requestCode == 1001){
            Log.e("주소 받아짐?", "yes");
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
    @SuppressLint("SetJavaScriptEnabled")
    public void init_webView() {

        // WebView 설정
        webView = (WebView) findViewById(R.id.webView_address);

        // JavaScript 허용
        webView.getSettings().setJavaScriptEnabled(true);
        // JavaScript의 window.open 허용
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        webView.addJavascriptInterface(new AndroidBridge(), "TestApp");
        // web client 를 chrome 으로 설정
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        // webview url load. php 파일 주소
        webView.loadUrl("http://khprince.com/restaurantApp/addressInquiry.php");
        webView.getSettings().setDomStorageEnabled(true);
        webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    txt_address.setText(String.format("(%s) %s %s", arg1, arg2, arg3));

                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    init_webView();
                }
            });
        }
    }
}
