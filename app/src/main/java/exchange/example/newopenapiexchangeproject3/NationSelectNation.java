package exchange.example.newopenapiexchangeproject3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import exchange.example.newopenapiexchangeproject3.FXapi.ExchangeMoney;

import com.example.newopenapiexchangeproject3.R;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class NationSelectNation extends AppCompatActivity {
    //대량의 데이터에 필요한
    static ExchangeMoney[] exchangeMonies;
    static String[] cur_nm_2 = new String[22];
    static int[] iv_nationflag_2 = new int[22];

    Toolbar toolbar;

    static RecyclerView recyclerView2;
    static NationSelectionRecylcerAdapter nationSelectionRecylcerAdapter;
    static ArrayList<Itemlist_nation> Nsdatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nation_selection_recycler);

        //대량의데이터 실행-환율 - 이거 있어야 데이터 실행됨.
        sendRequest();

        //툴바내용
        toolbar = findViewById(R.id.toolbar_nationSelection);
        toolbar.setTitle("국가 선택");
        setSupportActionBar(toolbar);

        //대량의데이터
        recyclerView2 = findViewById(R.id.recyclerview2);
        nationSelectionRecylcerAdapter = new NationSelectionRecylcerAdapter(Nsdatas,this);
        recyclerView2.setAdapter(nationSelectionRecylcerAdapter);

    }//onCreate


    /////////////////////////////////대량의데이터 정보요청 -  자바 클래스로 이동시켜서 내용을 줄이자. ///////////////////////////////////
    public static void sendRequest(){
        Date date = new Date();
        date.setTime(date.getTime()-(1000*60*60*24));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(date);

        String mykey = "2u3tcj729pppDBhULM3oFrRI7iRfkGlQ";
        String url = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?"
                +"authkey="+mykey        //인증키
                +"&searchdate="+20191015  //날짜
                +"&data=AP01";           //(성공==AP01
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // println("응답"+response); //fAB누르면 JSON 포맷을 읽음.

                        processResponse(response);  //Gson으로 이동
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                return params;
            }
        };
        request.setShouldCache(false); //그대로 보여주세요  - 이거 따로 공부 필요
        AddHelper.requestQueue.add(request);
    }//sendRequest

    //gson-> 정보 가져오기 + 대량의데이터
    public static void processResponse(String response){
        Gson gson = new Gson();
        exchangeMonies = gson.fromJson(response,ExchangeMoney[].class); //Json 문서가 [ 로 시작하면 바로 배열부터 시작하는것이니 []로 시작해서 꺼내준다.
        if(exchangeMonies !=null){
            for(int i=0; i<exchangeMonies.length; i++){
                cur_nm_2[i] =  exchangeMonies[i].getCur_nm(); //나라이름
                iv_nationflag_2[i] = R.drawable.a01_arabemirates +i; //국가깃발이미지
                Nsdatas.add(new Itemlist_nation(cur_nm_2[i],iv_nationflag_2[i])); //  Nsdata==22임을 확정지음.
                nationSelectionRecylcerAdapter.notifyDataSetChanged();
            }
        }
    }//processResponse


}
