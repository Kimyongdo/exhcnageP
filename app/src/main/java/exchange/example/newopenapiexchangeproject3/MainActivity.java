package exchange.example.newopenapiexchangeproject3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.newopenapiexchangeproject3.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.navigation.NavigationView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static exchange.example.newopenapiexchangeproject3.JsonExchangeRate.exchangeMonies;
import static exchange.example.newopenapiexchangeproject3.KaKaoLoginclass.KAKAOLOGIN;
import static exchange.example.newopenapiexchangeproject3.KaKaoLoginclass.KAKAOLOGOUT;


public class MainActivity extends AppCompatActivity {

    //https://openweathermap.org/api/hourly-forecast 날씨 - json뷰로 도시 id추가 가능

    //날짜
    static ArrayList<String> CanlenderTime=new ArrayList<>();
    //퍼미션
    public static final int PHONEPERMISSION = 431;
    public static final int KAKAOREUSLT = 333;

    //국가선택
    Context context;
    EditText et_nation_title;
    natioDialongSelctionAdapter nation;
    ArrayList<nationVO> nationSelect = new ArrayList<>();
    ArrayList<nationVO> nationSelectCopy = new ArrayList<>();


    //카카오톡 이미지 및 닉네임
    static long nicknumber; //카카오톡 고유넘버
    String kNickname;
    String kNickimage;
    String logoutNickname;
    String doLogout;
    int logoutImage;
    int logoutcheckout;
    int logincheckin;

    View headerView;
    TextView navUsername;
    CircleImageView navUserimage;

    //플로팅버튼
    private FloatingActionMenu fab;
    //toolbar, draw, navigation
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    public static ArrayList<Itemlist> datas =new ArrayList<>();
    ArrayList<Itemlist> datasCopy =new ArrayList<>();

    static ArrayList<kakaoVO> kakaodatas  = new ArrayList<>();
    static MainRecylcerAdapter recyclerAdapter;

    //새로고침
    public static SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true); //벡터이미지 관련코드
        setContentView(R.layout.activity_main);
        context=this;

        //카카오톡 번호
        if(nicknumber!=0){
            nicknumber=kakaodatas.get(0).getNicknumber();
        }

        /////////////////////volley 라이브러리 생성//////////////////////////////
        if(AddHelper.requestQueue == null) {
            AddHelper.requestQueue = Volley.newRequestQueue(this);
        }

        //환율, 시간, 날씨 api thread - MainThread와 같이 처리.
        Thread t = new Thread(){
            @Override
            public void run() {
                super.run();
                MyThread();
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //플로팅버튼
        fab = findViewById(R.id.flaotingActionButton);
        fab.setClosedOnTouchOutside(true);//외부터치가능
        FloatingActionButton fabBtnWorld = findViewById(R.id.menu_item_world);
        FloatingActionButton fabBtnCal = findViewById(R.id.menu_item_calculator);
        FloatingActionButton fabBtnText = findViewById(R.id.menu_item_text);
        Glide.with(this).load(R.drawable.worldwide1).into(fabBtnWorld);
        Glide.with(this).load(R.drawable.calculator1).into(fabBtnCal);
        Glide.with(this).load(R.drawable.text).into(fabBtnText);

        //toolbar, Drawlayout
        drawerLayout = findViewById(R.id.layout_drawer);
        navigationView = findViewById(R.id.navi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        navigationView.setItemIconTintList(null); //네비게이션 아이콘 보임.
        toolbar.setTitle("환율");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle); //화살표모양 누르면 navi소환
        actionBarDrawerToggle.syncState(); //화살표모양->삼선모양


        //주소록 퍼미션 만들기.
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){ //sdk version이 마시멜로우보다 높은 겨우
            int chekcedPersmission =checkSelfPermission(Manifest.permission.READ_CONTACTS);
            if(chekcedPersmission== PackageManager.PERMISSION_DENIED){ //처음에 거부되어있다면
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},PHONEPERMISSION); //허가여부 다이얼로그 확인.
            }
        }

        //데이터로드하기.
        Dataload();

        //리싸이클러 연결하기
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerAdapter = new MainRecylcerAdapter(datasCopy,this);
        recyclerView.setAdapter(recyclerAdapter);

        /////////////////////////////////////////네비게이션//////////////////////////////////////////////////////////////////
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.menu_note :
                        Intent intent = new Intent(MainActivity.this,NoteMain.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(navigationView); //클 릭 후 네비뷰 닫힘
                        break;
                    case R.id.updateIcon:
                        Intent intent0 = new Intent(MainActivity.this, UpdateMain.class);
                        startActivity(intent0);
                        drawerLayout.closeDrawer(navigationView);
                        break;
                    case R.id.address:
                        Intent intent1 = new Intent(MainActivity.this,Address.class);
                        startActivity(intent1);
                        drawerLayout.closeDrawer(navigationView);
                        break;
                    case R.id.rubbish:
                        Intent intent2 = new Intent(MainActivity.this,NoteRubbish.class);
                        startActivity(intent2);
                        drawerLayout.closeDrawer(navigationView);
                        break;
                    case R.id.newsSearch:
                        Intent intent3 = new Intent(MainActivity.this,NewsPaper.class);
                        startActivity(intent3);
                        drawerLayout.closeDrawer(navigationView);
                        break;
                }
                return false;
            }
        });

        ///////////////////////////////////////새로고침 기능////////////////////////////////////////////////////////
        swipeRefreshLayout = findViewById(R.id.layout_refresh);
        swiperefresh();//드래그시 이 함수 호출

        ////////////////////////////////////////////카카오 기능//////////////////////////////////////////////////
        //getHashKey();오프라인 카카오톡 로그인시 필요한 해시키.

        //카카오 데이터 함수 호출
        DataloadKakao();

        if(kakaodatas.size()==0) return;
            //로그인
        //네비게이션의 헤더뷰를 제어하는 코드
        else if(kakaodatas.get(0).getLogintnumber()==KAKAOLOGIN){
            headerView = navigationView.getHeaderView(0);
            navUsername = headerView.findViewById(R.id.tv_navi_header_name);
            navUserimage = headerView.findViewById(R.id.iv_header);
            navUsername.setText(kakaodatas.get(0).getLoginNickname()+"님"); //유저이름+"님"
            Glide.with(this).load(kakaodatas.get(0).getLoginNickimage()).into(navUserimage);

        }

        //로그아웃
        else if(kakaodatas.get(0).getLogoutnumber()==KAKAOLOGOUT){
            headerView = navigationView.getHeaderView(0);
            navUsername = headerView.findViewById(R.id.tv_navi_header_name);
            navUserimage = headerView.findViewById(R.id.iv_header);
            navUsername.setText("Anonymous"+"님"); //유저이름+"님
            Glide.with(this).load(R.drawable.user).into(navUserimage);
        }


    }///////////////////////////////////////////////////onCreate//////////////////////////////////////////////////////////////////


    //주소록 퍼미션 결과
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PHONEPERMISSION://PUBLIC STATIC INT
                if(grantResults.length>0){ //0보다 커야 main에서 오류뜨지 않고 잘 크게 됨. 여기서 다 쓸어넣자.
                    if(grantResults[0]==PackageManager.PERMISSION_DENIED){
                        Toast.makeText(this, "주소록 기능 사용 제한", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "주소록 사용 가능", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
        }
    }

    //Thread 및 자바함수(시간) 모음
    public void MyThread(){
        JsonExchangeRate jsonExchangeRate = new JsonExchangeRate();
        jsonExchangeRate.sendRequest();



        int time=0;
        //Date date2 = new Date();//오늘날짜
        CanlenderTime.clear();
        for(int k=0; k<3; k++){
            Date date2 = new Date(); //오늘날짜
            date2.setTime(date2.getTime()-time);//time 전날+전날..반복
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dateStr2 = sdf.format(date2);
            CanlenderTime.add(dateStr2); //달력 날짜를 가져옴
            time = time+(1000*60*60*24);
            Log.d("TAG2223",CanlenderTime.get(k));//15 14

            //여기다가 조인써보도록 해보쟈 13을 넣으면 13 13 13이 나옴 ㅠㅠ;;
        }

        WeahterCallMethod  weahterCallMethod= new WeahterCallMethod();
        weahterCallMethod.WeahterCallMethod();
        GlobalTime globalTime = new GlobalTime();
        globalTime.globaltime();
        globalTime.koreantime();
        globalTime.Notetime();
    }



    //oncreatemenu를 지우고 onpreapre+invaild 쓰면 바뀔때마다 적용됨.(로그인->로그아웃)
    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    //Menu 값에 따라 달라지게하기
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.loginicon, menu);
        if(nicknumber!=0.0){
            menu.getItem(0).setTitle("LOGOUT");//로그인번호 등록이 들어왔다면 Logout표시
        }
        return super.onPrepareOptionsMenu(menu);
    }

    //Menu 선택 시 이동하게 만들기.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int n = item.getItemId();
        switch (n){
            case R.id.menu_lock:
                Intent intent = new Intent(this, KaKaoLoginclass.class);
                startActivityForResult(intent,KAKAOREUSLT);
        }
        return super.onOptionsItemSelected(item);
    }


    //startActivityForResult(intent,333);에서 보낸 카카오톡 정보 받아오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case KAKAOREUSLT:
                if(resultCode==RESULT_OK){
                    //로그인 넘버로 판별식 짜기
                    //
                    nicknumber = data.getLongExtra("nicknumber",0);
                    //로그인사진
                    kNickname = data.getStringExtra("nickname");
                    kNickimage = data.getStringExtra("nicknameimage");
                    //로그아웃 사진
                    logoutNickname = data.getStringExtra("logoutNickname");
                    logoutImage = data.getIntExtra("logoutImage",0);
                    //로그인,로그아웃 임의토큰
                    logincheckin = data.getIntExtra("login",0);
                    logoutcheckout  = data.getIntExtra("logout",0);
                    doLogout=data.getStringExtra("dologout");

                    //네비게이션뷰 헤더부분 - 카카오톡 프로필 이미지 사진 나옴.
                    headerView = navigationView.getHeaderView(0);
                    navUsername = headerView.findViewById(R.id.tv_navi_header_name);
                    navUserimage = headerView.findViewById(R.id.iv_header);

                    //로그인
                    if(logincheckin==KAKAOLOGIN){
                        Glide.with(this).load(kNickimage).into(navUserimage);
                        navUsername.setText(kNickname+"님"); //유저이름+"님"
                    }
                    //로그아웃
                    else{
                        Glide.with(this).load(logoutImage).into(navUserimage);
                        navUsername.setText(logoutNickname+"님"); //유저이름+"님"
                    }
                    kakaodatas.add(0,new kakaoVO(kNickname,kNickimage,logincheckin,logoutNickname,logoutImage,logoutcheckout,nicknumber));
                    DataSaveKakao();
                }

        }
    }

    //카카오톡 로그인 정보 저장
    public void DataSaveKakao(){
        try {
            File file = new File(getFilesDir(),"kakao.tmp");
            FileOutputStream  fos  = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(kakaodatas);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //카카오톡 로그인 정보 불러오기
    public void DataloadKakao(){
        try {
            File file = new File(getFilesDir(),"kakao.tmp");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            kakaodatas= (ArrayList<kakaoVO>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    //Pause일때 업데이트 되도록
    @Override
    protected void onPause() {
        super.onPause();
        UpdateDataLoad();
    }


    public void search(String text){
        //adapter에 추가한 list를 기반으로하고 이를 복제한 list로 바꾸면서 보여지는 것.
        nationSelect.clear();
        nation.notifyDataSetChanged();
        if(text.length()==0){
            nationSelect.addAll(nationSelectCopy);
        }else{
            for(int i=0; i<nationSelectCopy.size(); i++){
                String choName = HangulUtils.getHangulInitialSound(nationSelectCopy.get(i).getTv(), text); //제목
                //문자의 text가 하나라도 써져있다면 이에 해당하는 itemlist를 추가.
                if(choName.indexOf(text)>=0){
                    nationSelect.add(nationSelectCopy.get(i));
                }
            }
        }
        nation.notifyDataSetChanged();
    }

    //////////////////////////////나라 선택 다이얼로그 - 초성으로 선택가능 /////////////////////////////////////////////

    public void ClikNationSelction(View view) {
        if(exchangeMonies.length!=0) {
            //초성으로 찾을 수 있는 기능.
            nationSelect.clear();
            nationSelectCopy.clear();

            //다이얼로그에 모두 나라 추가.
            for (int i = 0; i < JsonExchangeRate.cur_nm.size(); i++) {
                nationSelect.add(new nationVO(JsonExchangeRate.cur_nm.get(i), JsonExchangeRate.iv_nationflag.get(i)));
            }
            nationSelectCopy.addAll(nationSelect);
            fab.close(true); //자동으로 닫히도록.

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View view2 = inflater.inflate(R.layout.nation_dialog, null); //R.layout.nation_dialog 실행중

            et_nation_title = view2.findViewById(R.id.et_nation_title);
            et_nation_title.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String str = et_nation_title.getText().toString();
                    search(str);
                }
            });
            builder.setView(view2);
            //다이얼로그의 커스텀뷰를 listview와 연결
            final ListView nationlistview = view2.findViewById(R.id.listviewSelct);
            final AlertDialog dialog = builder.create();

            nation = new natioDialongSelctionAdapter(nationSelect, context);
            nationlistview.setAdapter(nation);
            nationlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int num = nationSelectCopy.indexOf(nation.getItem(position));
                    //num=num%22;

                    datasCopy.add(new Itemlist(JsonExchangeRate.cur_nm.get(num), JsonExchangeRate.cur_unit.get(num), JsonExchangeRate.kftc_deal_bas_r.get(num), JsonExchangeRate.iv_nationflag.get(num)
                            , GlobalTime.newstime2, GlobalTime.dateFormat2[num].format(GlobalTime.date2),
                            WeatherJSonArray.todayC1[num], WeatherJSonArray.todayweather[num], GlobalTime.timedifferent[num]

                    ));
                    //Log.d("kekekjeDone",JsonExchangeRate.cur_nm[num]+"");//여긴 국가이름이 잘 나오는데.
                    Toast.makeText(MainActivity.this, JsonExchangeRate.cur_nm.get(num) + " 추가 완료", Toast.LENGTH_SHORT).show();

                    //중복허용제거
                    for (int k = 0; k < datasCopy.size(); k++) { //1번칸일때
                        for (int j = 0; j < k; j++) { //0번 칸
                            if (datasCopy.get(j).getCur_nm().equals(datasCopy.get(k).getCur_nm())) { //0번과 1번칸을 비교
                                Toast.makeText(context, "이미 추가되어있습니다.", Toast.LENGTH_SHORT).show();
                                datasCopy.remove(k); //add했을때 맨 마지막에 추가된 것을 제거.
                                k--; //제거하고 한발짝 뒤로 옮김.
                            }
                        }
                    }

                    DataSave();
                    recyclerAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }else{
            Toast.makeText(context, "공휴일에는 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    //뒤로가기 - 네비게이션뷰 drawlayout, fab
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else if(fab.isOpened()) {
            fab.close(true);
        }else{
            finish(); //뒤로가기 누른 경우 앱을 종료.
            super.onBackPressed();
        }
    }

    //환율, 시간, 정보 불러오기
    public void Dataload(){
        try {
            File file = new File(getFilesDir(),"t.tmp");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            datasCopy = (ArrayList<Itemlist>) ois.readObject();  //앱 꺼도 새롭게 저장한 파일이 있음을 파악함.
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //환율, 시간, 정보 저장하기
    public void DataSave(){
        try {
            File file = new File(getFilesDir(),"t.tmp");
            FileOutputStream  fos  = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(datasCopy); //현재의 datas를 저장.
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //계산기로 이동
    public void clickCalculator(View view) {
        if(exchangeMonies.length!=0){
            fab.close(true);
            Intent intent = new Intent(this, CalCalculator.class);
            startActivity(intent);
        }else{
            Toast.makeText(context, "공휴일에는 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    //메모장으로 이동
    public void clicktext(View view) {
        fab.close(true);
        Intent intent = new Intent(this, NoteText.class);
        startActivity(intent);

    }

    /////////////////////////////////////////////////////////새로고침기능//////////////////////////////////////////////////////////////
    public void swiperefresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //공휴일이거나, 리사이클러뷰 자체에 데이터가 없는 경우 새로고침모양 소멸
                if(exchangeMonies.length==0 || datasCopy.size()==0){
                    swipeRefreshLayout.setRefreshing(false);
                }else {
                    //평일이거나 데이터가 있따면 업데이트 실행
                    UpdateDataLoad();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }//refres

    //for문 두개로 돌릴려고 했으나 datacopy의 이미지 번호가 실제 이미지 번호가 달름.
    public void UpdateDataLoad(){
            for(int i=0; i<datasCopy.size(); i++){
                int k=0;
                switch (datasCopy.get(i).getNationimage()){
                    case R.drawable.a01_arabemirates:
                        SetData(i,k);
                        break;
                    case R.drawable.a02_australia:
                        k=1;
                        SetData(i,k);
                        break;
                    case R.drawable.a03_1_bahrain:
                        k=2;
                        SetData(i,k);
                        break;
                    case R.drawable.a03_2_brunei:
                        k=3;
                        SetData(i,k);
                        break;
                    case R.drawable.a04_canada:
                        k=4;
                        SetData(i,k);
                        break;
                    case R.drawable.a05_switzerland:
                        k=5;
                        SetData(i,k);
                        break;
                    case R.drawable.a06_china:
                        k=6;
                        SetData(i,k);
                        break;
                    case R.drawable.a07_denmark:
                        k=7;
                        SetData(i,k);
                        break;
                    case R.drawable.a08_europeaninion:
                        k=8;
                        SetData(i,k);
                        break;
                    case R.drawable.a09_unitedkingdom:
                        k=9;
                        SetData(i,k);
                        break;
                    case R.drawable.a10_hongkong:
                        k=10;
                        SetData(i,k);
                        break;
                    case R.drawable.a11_indonesia:
                        k=11;
                        SetData(i,k);
                        break;
                    case R.drawable.a12_japan:
                        k=12;
                        SetData(i,k);
                        break;
                    case R.drawable.a13_korea:
                        k=13;
                        SetData(i,k);
                        break;
                    case R.drawable.a14_kuwait:
                        k=14;
                        SetData(i,k);
                        break;
                    case R.drawable.a15_malaysia:
                        k=15;
                        SetData(i,k);
                        break;
                    case R.drawable.a16_norway:
                        k=16;
                        SetData(i,k);
                        break;
                    case R.drawable.a17_newzealand:
                        k=17;
                        SetData(i,k);
                        break;
                    case R.drawable.a18_saudiarabia:
                        k=18;
                        SetData(i,k);
                        break;
                    case R.drawable.a19_sweden:
                        k=19;
                        SetData(i,k);
                        break;
                    case R.drawable.a20_singapore:
                        k=20;
                        SetData(i,k);
                        break;
                    case R.drawable.a21_thailand:
                        k=21;
                        SetData(i,k);
                        break;
                    case R.drawable.a22_usa:
                        k=22;
                        SetData(i,k);
                        break;
                }

            }
            DataSave();
            recyclerAdapter.notifyDataSetChanged();
        }

    public void SetData(int i, int k){
        datasCopy.set(i,new Itemlist(JsonExchangeRate.cur_nm.get(k), JsonExchangeRate.cur_unit.get(k), JsonExchangeRate.kftc_deal_bas_r.get(k), JsonExchangeRate.iv_nationflag.get(k)
                , GlobalTime.newstime2, GlobalTime.dateFormat2[k].format(GlobalTime.date2),
                WeatherJSonArray.todayC1[k], WeatherJSonArray.todayweather[k], GlobalTime.timedifferent[k]
        ));
    }


}
