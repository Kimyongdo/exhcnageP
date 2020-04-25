package exchange.example.newopenapiexchangeproject3.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GetKaKaoTalkHashKey {

    Context context;
    //카카오톡 해시값
    private void getHashKey(){
        try {                                                        // 패키지이름을 입력해줍니다.  //여기 패키지명 제대로 바뀌었는지 확인 바람.
            PackageInfo info = context.getPackageManager().getPackageInfo("exchange.example.newopenapiexchangeproject3", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                    // Log.d("카카오톡 해쉬코드","key_hash="+ Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //카카오톡 키해시 구글 플레이 버전
        byte[] sha1 = {
                (byte)0xA0,(byte)0xD6,(byte)0x4F,(byte)0xDA,(byte)0x02,(byte)0xDF,(byte)0xAE,
                (byte)0xFD,(byte)0xE3,(byte)0x45,(byte)0xEE,(byte)0x6E,(byte)0x78,(byte)0xDA,
                (byte)0x24,(byte)0x27,(byte)0x1D,(byte)0x63,(byte)0xFC,(byte)0x1F
        };
        // Log.e("카카오톡 해쉬코드 구글플레이", Base64.encodeToString(sha1, Base64.NO_WRAP));
    }

}
