package exchange.example.newopenapiexchangeproject3;


import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;

import exchange.example.newopenapiexchangeproject3.NaverSearchApi.SearchApi;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class SearchNaverJson1 {

     static  String[][] newstitle = new String[23][3];
     static  String[][] newsUrl = new String[23][3];
     static  String[][] newsContent = new String[23][3];

    public static void searching(String keyword, final int q){
        final String clientId ="rlPAa31qpMfyxB0oWzu9";
        final String clientSecret = "oq_aYFtbM9";

        try {
            keyword = URLEncoder.encode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            new Thread().sleep(25);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Volley는 비동기방식이라서 큐방식. 들어오는게 중구난방.
        String NAVERURL = "https://openapi.naver.com/v1/search/news?query="+keyword+"&display=3&start=1&sort=sim"; //keywrod로 문자 그대로 받아도 오류 생김.

        StringRequest postRequest = new StringRequest(Request.Method.GET, NAVERURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        SearchApi searchApi = gson.fromJson(response, SearchApi.class);

                        for (int i = 0; i < searchApi.getItems().size(); i++) { //i는 0 1 2 총 3개, == &display=3&에서 3을 의미함.
                            newstitle[q][i] = searchApi.getItems().get(i).getTitle(); //이 0번은 한 기사의 첫번쨰를 의미함.
                            newstitle[q][i] = newstitle[q][i].replaceAll("<b>", "");
                            newstitle[q][i] = newstitle[q][i].replaceAll("</b>", "");    //파싱 후 나오는 <b> </b>를 제거하기.
                            newstitle[q][i] = newstitle[q][i].replaceAll("&quot;", "");
                            newsUrl[q][i] = searchApi.getItems().get(i).getLink();
                            newsContent[q][i] = searchApi.getItems().get(i).getDescription();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {       //params는 반드시 필요,
                Map<String, String> params = new HashMap();                       //기존 volley와 다르게 인증키 위치가 다르다 (네이버)
                params.put("X-Naver-Client-Id", clientId);
                params.put("X-Naver-Client-Secret", clientSecret);
                return params;
            }
        };
        //volley는 이전 작업의 결과가 있는 경우 그대로 보여주는 경향이 있음
        //새로 최신의 모습을 보여주어야하기 때문에 이렇게 변경함.
        postRequest.setShouldCache(false);
        AddHelper.requestQueue.add(postRequest);
    }

}
