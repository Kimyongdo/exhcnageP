package exchange.example.newopenapiexchangeproject3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.newopenapiexchangeproject3.R;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;


public class KaKaoLoginclass extends AppCompatActivity {



    private static final String TAG = "";
    public static final int KAKAOLOGOUT = 123;
    public static final int KAKAOLOGIN = 124;
    SessionCallback callback;
    Button kakao_custom_login;
    LoginButton com_kakao_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z_loginclass);

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        requestMe();
        kakao_custom_login = findViewById(R.id.kakao_custom_login);
        kakao_custom_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com_kakao_login.performClick();
            }
        });
        com_kakao_login = findViewById(R.id.com_kakao_login);
    }

    public void kakao_logout(View view) {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                Intent intent = getIntent();
                intent.putExtra("logout",KAKAOLOGOUT);
                intent.putExtra("logoutNickname","Anonymous");//Anonymous
                intent.putExtra("logoutImage",R.drawable.user);//유저그림
                //intent.putExtra("logoutNumber",5959);
                KaKaoLoginclass.this.setResult(RESULT_OK,intent); //RESULT_OK 전달
                Toast.makeText(KaKaoLoginclass.this, "로그아웃 완료", Toast.LENGTH_SHORT).show();
            }
        });
    }


    class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {

            UserManagement.requestMe(new MeResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    String message = "failed to get user info. msg=" + errorResult;

                    ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                    if (result == ErrorCode.CLIENT_ERROR_CODE) {
                        //에러로 인한 로그인 실패
                        // finish();
                    } else {
                        //redirectMainActivity();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                }

                @Override
                public void onNotSignedUp() {

                }

                @Override
                public void onSuccess(UserProfile userProfile) {
                    //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.
                    //    Log.e("UserProfile", userProfile.getNickname());
                    //    Log.e("UserProfile", userProfile.getThumbnailImagePath());
                    //    Log.e("UserProfile", userProfile.getId() + "");
                    long nicknumber = userProfile.getId(); //카톡 고유번호
                    String nickname = userProfile.getNickname(); //이름
                    String nickimage = userProfile.getProfileImagePath(); //이미지

                    String dologout = "Logout";

                    Intent intent = getIntent();
                    intent.putExtra("login",KAKAOLOGIN);
                    intent.putExtra("nicknumber",nicknumber);
                    intent.putExtra("nickname",nickname);
                    intent.putExtra("nicknameimage",nickimage);
                    intent.putExtra("dologout",dologout);
                    KaKaoLoginclass.this.setResult(RESULT_OK,intent);
                    Toast.makeText(KaKaoLoginclass.this, "로그인 완료", Toast.LENGTH_SHORT).show();
                    //바로 인텐트하고 finish 하면 스레드로 인해 적용이 바로 안됨.
                }
            });

        }
        // 세션 실패시
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
        }
    }

    public void requestMe() {
        //유저의 정보를 받아오는 함수
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e(TAG, "error message=" + errorResult);
            }
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d(TAG, "onSessionClosed1 =" + errorResult);
            }
            @Override
            public void onNotSignedUp() {
                //카카오톡 회원이 아닐시
                Log.d(TAG, "onNotSignedUp ");
            }
            @Override
            public void onSuccess(UserProfile result) {
                Log.e("UserProfile", result.toString());
                Log.e("UserProfile", result.getId() + "");
            }
        });
    }
}
