package exchange.example.newopenapiexchangeproject3;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import exchange.example.newopenapiexchangeproject3.WeatherApi.WeatherJson;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WeatherJSonArray {

    //이걸로 확인중
     static String[]  todayCity = new String[23];
     static String[] todayNation= new String[23];
     static String[] todayIcon= new String[23];
     static String[]  todayC1= new String[23];
     static String[] todayweather = new String[23];
     static String[] todayIcon1= new String[23];
     static double[] todayF= new double[23];
     static int[] todayC= new int[23];
     static double[] todaywind1= new double[23];
     static String[] todaywind= new String[23];
     static double[] todayHum1= new double[23];
     static String[] todayHum= new String[23];

    static void WatherSendRequest(int cityid, final int q){ //q를 수정할 수 없을까?

        String key = "7574a21624ab7a502c88c128dff5f93d";
        String url = "http://api.openweathermap.org/data/2.5/" +
                "weather?id="+cityid+
                "&forecast?id=524901&"+
                "APPID="+key;

        StringRequest request = new StringRequest(
                Request.Method.GET, //Get방식, url , key : 준비물
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();         //gson 라이브러리 사용
                        WeatherJson weatherJson = gson.fromJson(response, WeatherJson.class); //json -> gson으로 변환작업
                        if (weatherJson != null) {
                            todayweather[q] = weatherJson.getWeather().get(0).getMain();
                            todayCity[q]=(weatherJson.getName());
                            todayNation[q] = weatherJson.getSys().getCountry();
                            todayIcon1[q] = weatherJson.getWeather().get(0).getIcon(); //아이콘
                            todayIcon[q] = "http://openweathermap.org/img/wn/" + todayIcon1[q] + "@2x.png";
                            todayF[q] = weatherJson.getMain().getTemp();
                            todayC[q] = (int) Math.round(((todayF[q] - 273) * 100) / 100.0);
                            todayC1[q] = todayC[q] + "";
                            todaywind1[q] = weatherJson.getWind().getSpeed();
                            todaywind[q] = todaywind1[q] + "";
                            todayHum1[q] = weatherJson.getMain().getHumidity();
                            todayHum[q] = todayHum1[q] + "";
                        }
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
        request.setShouldCache(false);
        AddHelper.requestQueue.add(request);
    }//sendRequest

}
