package exchange.example.newopenapiexchangeproject3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.newopenapiexchangeproject3.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UpdateMain extends AppCompatActivity {

    RecyclerView UpdateRecyclerView;
    static ArrayList<UpdateVO> updates = new ArrayList<>();
    UpdateAdapter updateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_content);

        //툴바
        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("공지사항");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loading();
        //리사이클러뷰 이용
        UpdateRecyclerView = findViewById(R.id.update_recyclerview);
        updateAdapter = new UpdateAdapter(this, updates);
        UpdateRecyclerView.setAdapter(updateAdapter);

    }//onCreate

    //myphpadmin의 표를 php에서 배열을 json으로 만들어서 바꿔준 다음
    //html(index.html -> adminphp.php(내용작성) -> updatephp.php(insert into) -> db에 저장, -> 안드 -> ExloadPHP에서 불러오는 셈.
    //현 문제점 : 업데이트 내용을 지우기 위해서는 DB에 직접 접속해야한다느 점

    public void loading(){
        String serverUrl ="http://chocojoa123.dothome.co.kr/Exchange/ExloadPHP.php";//여기서 php의 자료들이 json으로 대기중
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, serverUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                updates.clear();
                updateAdapter.notifyDataSetChanged();

                for (int i = 0; i < response.length(); i++) {
                    try {
                        //json을 받아오는 jsonObject를 사용
                        JSONObject jsonObject = response.getJSONObject(i);
                        int no = Integer.parseInt(jsonObject.getString("no")); //db테이블의 제목이 no라고 하는 것을 가져옴
                        String title = jsonObject.getString("title");     //db테이블의 content 제목에 해당하는 값을 가져옴
                        String nickname = jsonObject.getString("name");
                        String content = jsonObject.getString("content");
                        String now = jsonObject.getString("date");
                        updates.add(new UpdateVO(title, nickname, content,now));
                        updateAdapter.notifyDataSetChanged();
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

    //뒤로가기 선택되었을때 보내기.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :{
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        return super.onOptionsItemSelected(item);

    }
}
