package exchange.example.newopenapiexchangeproject3;



import android.util.Log;
import android.view.View;
import android.widget.Toast;

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


import static exchange.example.newopenapiexchangeproject3.NewsPaper.newsAdapter;
import static exchange.example.newopenapiexchangeproject3.NewsPaper.newsDatas;
import static exchange.example.newopenapiexchangeproject3.NewsPaper.newsLayoutGone;
import static exchange.example.newopenapiexchangeproject3.NewsPaper.newsLinks;


public class NewsNaverSearch {

    static int contatin;
    static String newsNumber="10"; //기본은 10개

     static void NewSearching(String keyword){
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
        String NAVERURL = "https://openapi.naver.com/v1/search/news?query="+keyword+
                "&display="+100+
                "&start=1&sort=sim"; //keywrod로 문자 그대로 받아도 오류 생김.

        StringRequest postRequest = new StringRequest(Request.Method.GET, NAVERURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Gson gson = new Gson();
                        SearchApi searchApi = gson.fromJson(response, SearchApi.class);

                        //제목 중 같은 일정 단어를 포함하고 있다면 제거하기로 하자.
                        //날짜도 필요하긴 한데

                        newsLayoutGone.setVisibility(View.GONE);
                        newsDatas.clear();
                        newsLinks.clear();
                        newsAdapter.notifyDataSetChanged();

                        for(int i=0; i<searchApi.getItems().size(); i++){
                            String newsTitle = searchApi.getItems().get(i).getTitle(); //이 0번은 한 기사의 첫번쨰를 의미함.
                            String newsLink = searchApi.getItems().get(i).getLink();
                            String time  = searchApi.getItems().get(i).getPubDate();

                            //뉴스기사 html 특징 제거.
                            newsTitle = newsTitle.replaceAll("<b>", "");
                            newsTitle = newsTitle.replaceAll("</b>", "");    //파싱 후 나   오는 <b> </b>를 제거하기.
                            newsTitle = newsTitle.replaceAll("&quot;", "\"");
                            newsTitle = newsTitle.replaceAll("&amp;", "&");
                            newsTitle = newsTitle.replaceAll("&lt;", "<");
                            newsTitle = newsTitle.replaceAll("&gt", ">");

                            newsLinks.add(newsLink);
                            newsDatas.add(new NewsSearchVO(newsTitle,time+""));
                        }



                        //데이터가 없다면 없다고 이미지를 뜨게 해야함.
                        if(newsDatas.size()==0){
                            newsLayoutGone.setVisibility(View.VISIBLE);
                        }


                        //중복을 제거하면서 문제가 생기는거 같아. 111을 쳐도 위에서 newsData.get(8).newsTitle은 나옴.
                        for(int k=0; k<newsDatas.size(); k++){
                            for(int j=0; j<k; j++){
                                String Kstr = newsDatas.get(k).getNewsTitle(); //각 기사
                                String Jstr = newsDatas.get(j).getNewsTitle(); //각 기사

                                String[] str1=Kstr.split(" ");//긴 기사 -> 띄어쓰기 기준으로 분류하고 배열로 만듦.
                                                                     //str1.length는 배열의 갯수를 의미함.

                                contatin=0; //2개 이상이면 제거하기 위한 카운트체커

                                for(int i=0; i<str1.length; i++){  //k가 비교하면서 가장 끝으로 가니까. k를 분해해서 j 제목과 구별하고 제거
                                    if(newsDatas.get(j).getNewsTitle().contains(str1[i])){
                                       contatin++;
                                    }
                                }

                                //뉴스 링크도 제거해주어야한다.
                                if(contatin>=2){ //제목의 키워드가 두개가 같다면 제거.
                                    newsDatas.remove(k);
                                    newsAdapter.notifyItemRemoved(k);
                                    newsLinks.remove(k);
                                    newsAdapter.notifyItemRemoved(k);
                                    k--; //중간에 제거를 하면 모든 배열이 왼쪽으로 한칸 이동하는데 k--;가 없다면 k의 순서도 배열에 맞게 한칸 왼쪽으로 이동할 필요가 있음.
                                }
                            }
                        }

                            newsAdapter.notifyDataSetChanged();

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
