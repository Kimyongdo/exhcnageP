package exchange.example.newopenapiexchangeproject3;

import android.util.Log;

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

public class JsonExchangeRate {
     public static ExchangeMoney[] exchangeMonies;
     static ArrayList<String> cur_nm=new ArrayList<>();
     static ArrayList<String> cur_unit=new ArrayList<>();
     static ArrayList<String> kftc_deal_bas_r=new ArrayList<>();
     static ArrayList<Integer> iv_nationflag=new ArrayList<>();

    /////////////////////////////////대량의데이터 정보요청///////////////////////////////////
    public void sendRequest(){

        Date date = new Date();
        date.setTime(date.getTime());//하루전날 의미
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(date); //null값 아직 해결 안 함.

        //공휴일 20201005 입니다.
        //dateStr
        //20200202
        String mykey = "2u3tcj729pppDBhULM3oFrRI7iRfkGlQ";
        String url = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?"
                +"authkey="+mykey        //인증키

                +"&searchdate="+  dateStr//날짜               주말에 null 값으로 변화되서 아무것도 나오지 않음.. 흠..... 대책이 필요함.
                +"&data=AP01";           //(성공==AP01)
        StringRequest request = new StringRequest(             //Volley 라이브러리
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("TAGad",response); //reponse에 json 내용이 담겨져이 있음.
                        processResponse(response);  //Gson으로 이동
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //println("에러발생"+error.getMessage());
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
    public void processResponse(String response){
        Gson gson = new Gson();
        exchangeMonies = gson.fromJson(response, ExchangeMoney[].class); //Json 문서가 [ 로 시작하면 바로 배열부터 시작하는것이니 []로 시작해서 꺼내준다.
        Log.d("TAGad11", exchangeMonies.length+"");
        if(exchangeMonies.length!=0){//json 오후에 끊기기 때문에.

            for(int i=0; i<exchangeMonies.length; i++){
                //환율Json
                cur_nm.add(exchangeMonies[i].getCur_nm());  //나라이름
                cur_unit.add(exchangeMonies[i].getCur_unit()); //통화종류
                kftc_deal_bas_r.add(exchangeMonies[i].getKftc_deal_bas_r()) ; //기준매매환율
                //환율이미지(안드로이드 내장)
                iv_nationflag.add(R.drawable.a01_arabemirates +i);       //순서를 통해 나라를 지정함.
                //Log.d("TAGad", exchangeMonies.length+"");
            }
        }else{ //공휴일 및 휴일에 값이 0인 경우 - 준비중으로 바꾸어야할 필요가 있음.
            Log.d("TAGad12", exchangeMonies.length+"");
            for(int k=0; k<exchangeMonies.length; k++){
                cur_nm.add("준비중입니다.");
                cur_unit.add("오전 10시에 열립니다.");
                kftc_deal_bas_r.add("") ;
                iv_nationflag.add(R.drawable.a01_arabemirates +k);

            }

        }

    }//processResponse
}
