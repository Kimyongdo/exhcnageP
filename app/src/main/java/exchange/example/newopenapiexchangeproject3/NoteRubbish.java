package exchange.example.newopenapiexchangeproject3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.newopenapiexchangeproject3.R;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static exchange.example.newopenapiexchangeproject3.KaKaoLoginclass.KAKAOLOGIN;
import static exchange.example.newopenapiexchangeproject3.KaKaoLoginclass.KAKAOLOGOUT;
import static exchange.example.newopenapiexchangeproject3.MainActivity.kakaodatas;
import static exchange.example.newopenapiexchangeproject3.MainActivity.nicknumber;

public class NoteRubbish extends AppCompatActivity {

    //레이아웃
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    ListView rubbishListview;
    NoteRubbishAdapter noteRubbishAdapter;

    static ArrayList<NoteRubbishVO> rubbishlist = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_rubbish);

        drawerLayout = findViewById(R.id.layout_drawer);
        navigationView = findViewById(R.id.navi);
        navigationView.setItemIconTintList(null); //네비게이션 아이콘 보임.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle); //화살표모양 누르면 navi소환
        actionBarDrawerToggle.syncState(); //화살표모양->삼선모양

        rubbishListview = findViewById(R.id.rubbish_listview);
        noteRubbishAdapter = new NoteRubbishAdapter(this); //삭제리스트
        rubbishListview.setAdapter(noteRubbishAdapter);

        NoteLoadRubbishFromDB();
        noteRubbishAdapter.notifyDataSetChanged();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_note :
                        Intent intent = new Intent(NoteRubbish.this,NoteMain.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(navigationView); //클 릭 후 네비뷰 닫힘
                        finish();
                        break;
                    case R.id.updateIcon:
                        Intent intent0 = new Intent(NoteRubbish.this, UpdateMain.class);
                        startActivity(intent0);
                        drawerLayout.closeDrawer(navigationView); //클릭 후 네비뷰 닫힘
                        break;
                    case R.id.address:
                        Intent intent1 = new Intent(NoteRubbish.this,Address.class); //여기로 들어가면 로그인 하도록 하고 싶은뎅.
                        startActivity(intent1);
                        drawerLayout.closeDrawer(navigationView); //클릭 후 네비뷰 닫힘
                        break;
                    case R.id.rubbish:
                        Intent intent2 = new Intent(NoteRubbish.this,NoteRubbish.class); //여기로 들어가면 로그인 하도록 하고 싶은뎅.
                        startActivity(intent2);
                        drawerLayout.closeDrawer(navigationView); //클릭 후 네비뷰 닫힘
                        finish(); //자기꺼 일 떄는 닫히도록.
                        break;
                    case R.id.newsSearch:
                        Intent intent3 = new Intent(NoteRubbish.this,NewsPaper.class); //여기로 들어가면 로그인 하도록 하고 싶은뎅.
                        startActivity(intent3);
                        drawerLayout.closeDrawer(navigationView); //클릭 후 네비뷰 닫힘
                        break;

                }
                return false;
            }
        });


        //로그인, 로그아웃 시 네비게이션뷰의 이미지 및 이름 변경
        MainActivity main = new MainActivity();

        if(kakaodatas.size()==0) return;
            //로그인
            //네비게이션의 헤더뷰를 제어하는 코드
        else if(kakaodatas.get(0).getLogintnumber()==KAKAOLOGIN){
            main.headerView = navigationView.getHeaderView(0);
            main.navUsername = main.headerView.findViewById(R.id.tv_navi_header_name);
            main.navUserimage = main.headerView.findViewById(R.id.iv_header);
            main.navUsername.setText(kakaodatas.get(0).getLoginNickname()+"님"); //유저이름+"님"
            Glide.with(this).load(kakaodatas.get(0).getLoginNickimage()).into(main.navUserimage);
        }

        //로그아웃
        else if(kakaodatas.get(0).getLogoutnumber()==KAKAOLOGOUT){
            main.headerView = navigationView.getHeaderView(0);
            main.navUsername = main.headerView.findViewById(R.id.tv_navi_header_name);
            main.navUserimage = main.headerView.findViewById(R.id.iv_header);
            main.navUsername.setText("Anonymous"+"님"); //유저이름+"님
            Glide.with(this).load(R.drawable.user).into(main.navUserimage);
        }
    }

    public void NoteLoadRubbishFromDB(){
        String serverUrl ="http://chocojoa123.dothome.co.kr/Exchange/NoteRubbishLoad.php";
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, serverUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                rubbishlist.clear();
                noteRubbishAdapter.notifyDataSetChanged();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int no = Integer.parseInt(jsonObject.getString("no")); //db테이블의 제목이 no라고 하는 것을 가져옴
                        String number = jsonObject.getString("number");     //db테이블의 content 제목에 해당하는 값을 가져옴
                        String time = jsonObject.getString("time");
                        String title = jsonObject.getString("title");
                        String content = jsonObject.getString("content");

                        if(number.equals(nicknumber+"")){ //배열을 돌리면서 db number와 카카오 아이디가 같으면 그것만 추가하도록.
                            rubbishlist.add(0,new NoteRubbishVO(time, title, content)); //첫 글씨를 쓴 것을 저장하는 만큼 문제 없음.
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //실제 요청작업을 수행해주는 요청큐 객체 생성
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        //요청큐에 요청객체 추가
        requestQueue.add(jsonArrayRequest);
    }

}
