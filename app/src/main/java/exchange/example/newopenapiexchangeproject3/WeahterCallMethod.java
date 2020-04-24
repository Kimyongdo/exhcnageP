package exchange.example.newopenapiexchangeproject3;

public class WeahterCallMethod {

   public void WeahterCallMethod(){
       new Thread(){
           @Override
           public void run() {
               super.run();

               WeatherJSonArray.WatherSendRequest(292223,0);
               WeatherJSonArray.WatherSendRequest(2172517,1);
               WeatherJSonArray.WatherSendRequest(290291,2);
               WeatherJSonArray.WatherSendRequest(1820906,3);
               WeatherJSonArray.WatherSendRequest(6087824,4);
               WeatherJSonArray.WatherSendRequest(4899170,5);
               WeatherJSonArray.WatherSendRequest(1796236,6);
               WeatherJSonArray.WatherSendRequest(2618425,7);
               WeatherJSonArray.WatherSendRequest(3337389,8);
               WeatherJSonArray.WatherSendRequest(2643741,9);
               WeatherJSonArray.WatherSendRequest(1819729,10);
               WeatherJSonArray.WatherSendRequest(1642907,11);
               WeatherJSonArray.WatherSendRequest(1850147,12);
               WeatherJSonArray.WatherSendRequest(1835848,13);
               WeatherJSonArray.WatherSendRequest(285570,14);
               WeatherJSonArray.WatherSendRequest(1735161,15);
               WeatherJSonArray.WatherSendRequest(6453366,16);
               WeatherJSonArray.WatherSendRequest(2193732,17);
               WeatherJSonArray.WatherSendRequest(108410,18);
               WeatherJSonArray.WatherSendRequest(2673730,19);
               WeatherJSonArray.WatherSendRequest(1880252,20);
               WeatherJSonArray.WatherSendRequest(1609350,21);
               WeatherJSonArray.WatherSendRequest(5128594,22);
           }
       }.start();

   }
}
