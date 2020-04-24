package exchange.example.newopenapiexchangeproject3;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.newopenapiexchangeproject3.R;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static exchange.example.newopenapiexchangeproject3.JsonExchangeRate.cur_nm;
import static exchange.example.newopenapiexchangeproject3.JsonExchangeRate.exchangeMonies;
import static exchange.example.newopenapiexchangeproject3.MainActivity.datas;

public class MainRecylcerAdapter extends RecyclerView.Adapter {
    ArrayList<Itemlist> datasCopy;
    Context context;
    public MainRecylcerAdapter(ArrayList<Itemlist> datasCopy, Context context) {
        this.datasCopy = datasCopy;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.activity_cardview_recylcer, parent, false);
        VH vh = new VH(itemView);
        itemView.findViewById(R.id.cardviewlayoutroot);
        return vh;
    }

    //새로운 참조변수 - 연결된 XML - datas 연결
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        final VH vh = (VH)holder;
        final Itemlist itemlist = datasCopy.get(position);  //itemlist가 화물칸마다 position으로 연결

        //환율
        vh.flag_name.setText(itemlist.getCur_nm());
        vh.dollar_name.setText(itemlist.getCur_unit());
        vh.dollar_rate.setText(itemlist.getKftc_deal_bas_r());
        Glide.with(context).load(itemlist.getNationimage()).into(vh.iv_NationSeleciton);

        //날씨
        vh.TodayC.setText(itemlist.getTdoayC1());
        vh.WeatherConditon.setText(itemlist.getTodayConditon());
        vh.newtime_diffet.setText(itemlist.getTimedifferent());
        //세계시간
        vh.newstime_global.setText(itemlist.getNewstimeglobal());
    }

    @Override
    public int getItemCount() {
        return datasCopy.size();
    }

    //XML 위치 연결
    //여기서 카드뷰의 모양을 결정하고 있음.
    class VH extends RecyclerView.ViewHolder{

        //세계시간설정
        TextView newstime_global;
        TextView newtime_diffet;

        //환율
        ImageView iv_NationSeleciton; //나라이미지
        TextView flag_name; //국가이름
        TextView dollar_name;//달러이름
        TextView dollar_rate;//달러환율

        //날씨 설정
        TextView TodayC;
        TextView WeatherConditon;

        //뷰 설정
        RelativeLayout cardview_title;
        CardView cardviewlayoutroot;

        //기타설정
        ImageView cardview_remove;   // x 모양
        ImageView iv_expand;

        RelativeLayout Relativelayout_cardview_title;

        //연결하는 곳
        public VH(@NonNull View itemView) {
            super(itemView);

            //세계시간설정
            newstime_global = itemView.findViewById(R.id.cardview_news_dattime_global);

            //환율
            iv_NationSeleciton=itemView.findViewById(R.id.iv_flag);
            flag_name = itemView.findViewById(R.id.tv_nation_name);
            dollar_name = itemView.findViewById(R.id.tv_dollar_name);
            dollar_rate = itemView.findViewById(R.id.tv_dollar_rate);

            //시간
            TodayC = itemView.findViewById(R.id.tv_TodayC);
            WeatherConditon = itemView.findViewById(R.id.tv_WeahterCondtion);
            newtime_diffet = itemView.findViewById(R.id.cardview_news_dattime_different);

            //기타설정
            cardview_remove=itemView.findViewById(R.id.iv_remoce_cancel);
            cardview_title = itemView.findViewById(R.id.Relativelayout_cardview_title);
            cardviewlayoutroot = itemView.findViewById(R.id.cardviewlayoutroot);
            Relativelayout_cardview_title = itemView.findViewById(R.id.Relativelayout_cardview_title);

            if(cur_nm.size()!=0){
            Relativelayout_cardview_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int a= getAdapterPosition();
                    a=a%JsonExchangeRate.cur_unit.size(); //리스트뷰에 일어날 오류.
                    //Log.d("선택번호",a+"");
                    SearchNaverJson1 searchNaverJson1 = new SearchNaverJson1();
                    for(int i=0; i<JsonExchangeRate.cur_unit.size(); i++){
                        switch (datasCopy.get(a).getCur_unit()){ //
                            case "AED"://아랍에미리트 디르함cardview_news_dattime_global
                                searchNaverJson1.searching("아랍에미리트",0);
                                Intent intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",0);
                                intent0.putExtra("name","Arab");
                                intent0.putExtra("time","Asia/Dubai");

                                //여기서 차트정보를 보내주어야한다.

                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //기존 액티비티가 있다면 전부제거하고 호출
                                context.startActivity(intent0);
                                break;

                            case "AUD"://호주달러
                                searchNaverJson1.searching("호주",1);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",1);
                                intent0.putExtra("name","Australia");
                                intent0.putExtra("time","Australia/Canberra");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);


                                break;
                            case "BHD"://바레인
                                searchNaverJson1.searching("바레인",2);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",2);
                                intent0.putExtra("name","Bahrain");
                                intent0.putExtra("time","Asia/Bahrain");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;


                            case "BND"://브루나이
                                searchNaverJson1.searching("브루나이",3);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",3);
                                intent0.putExtra("name","Brunei");
                                intent0.putExtra("time","Asia/Brunei");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;

                            case "CAD"://캐나다
                                searchNaverJson1.searching("캐나다",4);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",4);
                                intent0.putExtra("name","Canada");
                                intent0.putExtra("time","America/Toronto");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;

                            case "CHF"://스위스
                                searchNaverJson1.searching("스위스",5);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",5);
                                intent0.putExtra("name","Swiss");
                                intent0.putExtra("time","Europe/Zurich");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;

                            case "CNH"://중국
                                searchNaverJson1.searching("중국",6);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",6);
                                intent0.putExtra("name","China");
                                intent0.putExtra("time","Asia/Shanghai");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;
                            case "DKK"://덴마크
                                searchNaverJson1.searching("덴마크",7);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",7);
                                intent0.putExtra("name","Denmark");
                                intent0.putExtra("time","Europe/Copenhagen");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;
                            case "EUR"://유로
                                searchNaverJson1.searching("유럽",8);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",8);
                                intent0.putExtra("name","Europe");
                                intent0.putExtra("time","Europe/Brussels");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;
                            case "GBP"://영국
                                searchNaverJson1.searching("영국",9);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",9);
                                intent0.putExtra("name","England");
                                intent0.putExtra("time","Europe/London");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;
                            case "HKD"://홍콩
                                searchNaverJson1.searching("홍콩",10);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",10);
                                intent0.putExtra("name","HongKong");
                                intent0.putExtra("time","Asia/Hong_Kong");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;
                            case "IDR(100)"://인도네시아
                                searchNaverJson1.searching("인도네시아",11);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",11);
                                intent0.putExtra("name","Indonesia");
                                intent0.putExtra("time","Asia/Jakarta");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;
                            case "JPY(100)"://일본
                                searchNaverJson1.searching("일본",12);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",12);
                                intent0.putExtra("name","Japan");
                                intent0.putExtra("time","Asia/Tokyo");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;
                            case "KRW"://한국
                                searchNaverJson1.searching("한국",13);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",13);
                                intent0.putExtra("name","Korea");
                                intent0.putExtra("time","Asia/Seoul");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;
                            case "KWD"://쿠웨이트
                                searchNaverJson1.searching("쿠웨이트",14);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",14);
                                intent0.putExtra("name","Kuwait");
                                intent0.putExtra("time","Asia/Kuwait");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;
                            case "MYR"://말레이지아
                                searchNaverJson1.searching("말레이지아",15);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",15);
                                intent0.putExtra("name","Malaysia");
                                intent0.putExtra("time","Asia/Kuala_Lumpur");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;
                            case "NOK"://노르웨이
                                searchNaverJson1.searching("노르웨이",16);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",16);
                                intent0.putExtra("name","Norway");
                                intent0.putExtra("time","Europe/Oslo");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;
                            case "NZD"://뉴질랜드
                                searchNaverJson1.searching("뉴질랜드",17);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",17);
                                intent0.putExtra("name","New Zealand");
                                intent0.putExtra("time","Pacific/Auckland");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;
                            case "SAR"://사우디
                                searchNaverJson1.searching("사우디",18);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",18);
                                intent0.putExtra("name","Saudi");
                                intent0.putExtra("time","Asia/Riyadh");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;
                            case "SEK"://스웨덴
                                searchNaverJson1.searching("스웨덴",19);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",19);
                                intent0.putExtra("name","Sweden");
                                intent0.putExtra("time","Europe/Stockholm");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;
                            case "SGD"://싱가포르
                                searchNaverJson1.searching("싱가포르",20);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",20);
                                intent0.putExtra("name","Singapore");
                                intent0.putExtra("time","Asia/Singapore");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;
                            case "THB"://태국
                                searchNaverJson1.searching("태국",21);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",21);
                                intent0.putExtra("name","Thailand");
                                intent0.putExtra("time","Asia/Bangkok");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;
                            case "USD"://미국
                                searchNaverJson1.searching("미국",22);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",22);
                                intent0.putExtra("name","USA");
                                intent0.putExtra("time","America/New_York");
                                intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent0);
                                break;

                        }
                    }

                }
            });
            }
            else{
                Toast.makeText(context, "주말에는 준비중입니다.", Toast.LENGTH_SHORT).show();
            }


            //////////////////////////////카드뷰 x버튼을 누르면 제거되도록 만드는 기능//////////////////////////////
            cardview_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getLayoutPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        if(datasCopy.size()==0) return;
                        datasCopy.remove(position);
                        notifyItemRemoved(position);
                        DataSave2();
                    }

                }
            });
        }//VH
    }



    public void DataSave2(){
        try {
            File file = new File(context.getFilesDir(),"t.tmp");
            FileOutputStream fos  = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(datas); //현재의 datas를 저장.
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
