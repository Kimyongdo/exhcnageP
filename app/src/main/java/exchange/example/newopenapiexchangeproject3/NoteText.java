package exchange.example.newopenapiexchangeproject3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.newopenapiexchangeproject3.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static exchange.example.newopenapiexchangeproject3.MainActivity.nicknumber;

public class NoteText extends AppCompatActivity {

    ImageView iv_done;
    ImageView iv_before;

    EditText et_title;
    EditText et_content;

    String et_title_reader;
    String et_content_reader;

    static ArrayList<NoteVO> notelist = new ArrayList<>();
    static ArrayList<NoteVO> testlist = new ArrayList<>();
    static ArrayList<NoteVO> searchlist = new ArrayList<>(); //멍청한짓인지 나도 안다.


    InputMethodManager imm;
    int save;

    ImageView ivCamera;
    ImageView ivCemeraImage;
    Uri imgUri=null; //캡쳐이미지 저장경로

    int beforeEtTitle;
    int beforeEtContent;
    int afterEtTitle;
    int afterEtContent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_text);

        ///////////////////////////카메라를 사용하는 경우/////////////////////////////////////////////
        //ivCamera = findViewById(R.id.iv_updateNote_first_camera);
        //ivCemeraImage = findViewById(R.id.iv_camareaImage);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){ //sdk version이 마시멜로우보다 높은 겨우
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){ //처음에 거부되어있다면
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10); //허가여부 다이얼로그 확인.
            }
        }


        //카메라 이미지 선택시.
//        ivCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                setImageUri();
//                if(imgUri!=null) intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
//                startActivityForResult(intent, 1000);
//            }
//        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////


        //연결
        iv_before = findViewById(R.id.iv_updateNote_first_done2);
        iv_done = findViewById(R.id.iv_updateNote_first_done);
        et_title = findViewById(R.id.et_note_title);
        et_content = findViewById(R.id.et_note_content);

        //et_content 현재위치기 가져오기 - > 이미지뷰 커서 뒤에 놓기 위해서
        int endOfCurosr = et_content.getSelectionEnd();

        //키보드가 올라오도록
        et_title.requestFocus();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        //시작전 키 값
        beforeEtTitle = et_title.length();
        beforeEtContent = et_content.length();


        et_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(et_title.getText().toString()!=null){
                        iv_before.setVisibility(View.GONE);
                        iv_done.setVisibility(View.VISIBLE);
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_content.getText().toString()!=null){
                    iv_before.setVisibility(View.GONE);
                    iv_done.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        iv_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(et_title.getWindowToken(),0);
                imm.hideSoftInputFromWindow(et_content.getWindowToken(),0);
                finish();
            }
        });

        //확인 누르면 글 내용 읽고 notelist에 추가 및 저장 후 닫가.
        iv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_title_reader = et_title.getText().toString();
                et_content_reader= et_content.getText().toString();

                afterEtTitle = et_title.length();
                afterEtContent = et_content.length();

                //edittext비어있는 경우를 아기 위해 mathces를 이용
                //모두 비어있는 경우
                if(afterEtTitle==0 && afterEtContent==0){
                   Toast.makeText(NoteText.this, "빈 노트는 저장할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
                //제목은 없고 내용만 있는 경우
                else if(afterEtTitle==0 && afterEtContent!=0) {
                    et_title_reader=et_content_reader;
                    noelistGroup();
                }else{
                    //제목, 내용을 모두 적은 경우
                    noelistGroup();
                }

//                    finish(); 너무 빠르게 넘어가서 적용이 안됨 -> Thread 맨 끝에 해도 마찬가지임.

            }
        });
    }/////////////////////////////////////////////////////////////onCreate////////////////////////////////////////////////////////////////////////////

    //확인 눌렀을 경우 반복되는 메소드 압축함.
    public void noelistGroup(){
        //여기서는 이거 add하나만으로도 변경가능했음.
        notelist.add(0,new NoteVO(GlobalTime.noetime,et_title_reader, et_content_reader)); //가장 먼저. 했는데도 흠.
        
        save=911; //저장토큰 토큰 기본값을 0으로 주면 안됨. int 초기값 자체가 0
        imm.hideSoftInputFromWindow(et_title.getWindowToken(),0);
        imm.hideSoftInputFromWindow(et_content.getWindowToken(),0);
        Toast.makeText(NoteText.this, "저장 되었습니다.", Toast.LENGTH_SHORT).show();

        //로그인 여부
        if(nicknumber!=0.0){ //0인지 0.0인지..
            //db에 저장
            Noteupdate(); //서버저장-휴대폰 내부저장 불필요
        }else{
            //비로그인
            DataSaveNote();
        }

    }

    public void Noteupdate(){

        final String noetime = GlobalTime.noetime;
        final String title = et_title.getText().toString();
        final String content = et_content.getText().toString();
        String serverurl = "http://chocojoa123.dothome.co.kr/Exchange/Noteupdate.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> datas = new HashMap<>();

                datas.put("number",nicknumber+"");
                datas.put("time",noetime);
                datas.put("title",title);
                datas.put("content",content); //서버에 저장.
                return datas;
            }
        };//stringRequest

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void DataSaveNote(){
        try {
            File file = new File(getFilesDir(),"notelist");
            FileOutputStream fos  = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(notelist); //현재의 datas를 저장.
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //뒤로가기 눌렀을 때 변경이 되도록.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){

            //수정 후 글씨 개수
            afterEtTitle = et_title.length();
            afterEtContent = et_content.length();

            if(save==911){
                Intent intent = new Intent(this,NoteMain.class);
                startActivity(intent);
                finish();
            }

            else if(beforeEtTitle!=afterEtTitle || beforeEtContent!=afterEtContent){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("삭제하시겠습니까?");//창 이름
                builder.setMessage("변경 내용이 저장되지 않았습니다. 변경 내용을 삭제하시겠습니까?");

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(NoteText.this, "변경되지 않았습니다..", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NoteText.this, NoteMain.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //아무일도 안일어남.
                    }
                });

                AlertDialog dialog = builder.create(); //create 생성
                dialog.show(); //.show()를 표시할것
                return  true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    //사진촬영 reqeustPermission 선택의 결과여부를 알려주는 곳
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 10:
                if(grantResults[0]==PackageManager.PERMISSION_DENIED){
                    ivCamera=null;
                    Toast.makeText(this, "카메라 기능 사용 제한", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "카메라 사용 가능", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    void setImageUri(){

        File path= getExternalFilesDir("photo");
        //path 경로에 없다면 폴더 만들기.
        if(!path.exists()) path.mkdirs();
        path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); //Picture폴더

        //1) 날짜를 이용하는 방법
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddhhmmss");
        String fileName= "IMG_"+sdf.format(new Date()) + ".jpg";
        File imgFile= new File(path, fileName);

        //2) 자동으로 임시파일명으로 만들어주는 메소드 이용하는 방법
        try {
            imgFile= File.createTempFile("IMG_",".jpg", path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //File클래스 -> Uri클래스로 변경 (누가버전 이후 변경)
        if( Build.VERSION.SDK_INT<Build.VERSION_CODES.N){ //누가버전 이전이라면
            imgUri= Uri.fromFile(imgFile);
        }else{
            imgUri= FileProvider.getUriForFile(this,  "com.example.newopenapiexchangeproject3", imgFile);
        }
        //manifest 및 xml 폴더 만들기.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1000:
                if(resultCode==RESULT_OK){
                    if(data!=null){
                        Uri uri= data.getData();
                        if(uri!=null) {//파일로 자동 저장되어 있는 경우 (작업이 수월함)]
                            Glide.with(this).load(uri).into(ivCemeraImage);
                        }
                        else{
                            if(imgUri!=null) Glide.with(this).load(imgUri).into(ivCemeraImage);

                            //실력부족으로 커서에 맞는 사진이미지를 넣는 것이 불가능함. ㅎ...ㅎㅎ;;;

                            Intent intent= new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            intent.setData(imgUri);
                            sendBroadcast(intent);
                        }
                    }else{
                        if(imgUri!=null) Glide.with(this).load(imgUri).into(ivCemeraImage);
                        Intent intent= new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        intent.setData(imgUri);
                        sendBroadcast(intent);
                    }

                }

                break;
        }
    }


}
