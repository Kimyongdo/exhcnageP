package exchange.example.newopenapiexchangeproject3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newopenapiexchangeproject3.R;
import com.skyfishjy.library.RippleBackground;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static exchange.example.newopenapiexchangeproject3.MainActivity.datas;


public class NationSelectionRecylcerAdapter extends RecyclerView.Adapter {

    ArrayList<Itemlist_nation> NSdata;
    ArrayList<Itemlist> OverlapNsdata = new ArrayList<>();


    static Context context;
    //public static int a;
    static String[] newstime = new String[22];
    static TimeZone[] timeZone = new TimeZone[22];
    static DateFormat[] dateFormat = new DateFormat[22];
    static Date[] date = new Date[22];
    int k;
//    static String[][] newstitle = new String[22][3];
//    static String[][] newsUrl = new String[22][3];
//    static String[][] newsContent = new String[22][3];

    public NationSelectionRecylcerAdapter(ArrayList<Itemlist_nation> datas,Context context) {
        this.NSdata = datas;
        this.context = context;

    }


    ////////////////////////체크박스 중복출현을 막는 코드///////////
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    ///////////////////////////////////////////////////////////////////

    //연결하는 곳
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        for(int i=0; i<JsonExchangeRate.cur_unit.size(); i++){
            date[i] = new Date();
            dateFormat[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
            timeZone[i]  = TimeZone.getTimeZone("Asia/Dubai"); //그 나라 표준시
            dateFormat[i].setTimeZone(timeZone[i] );
            SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd a hh:mm ");
            newstime[i] = sdf.format(new Date());
        }

        //중복체크 위한 자료
        for(int a=0; a<JsonExchangeRate.cur_unit.size(); a++){
            OverlapNsdata.add(new Itemlist(JsonExchangeRate.cur_nm.get(a), JsonExchangeRate.cur_unit.get(a), JsonExchangeRate.kftc_deal_bas_r.get(a), JsonExchangeRate.iv_nationflag.get(a)
                    , GlobalTime.newstime2, GlobalTime.dateFormat2[a].format(GlobalTime.date2),
                    WeatherJSonArray.todayC1[a], WeatherJSonArray.todayweather[a], GlobalTime.timedifferent[a]
            ));
        }


        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemview = layoutInflater.inflate(R.layout.nation_selction_item_list,parent,false);
        itemview.findViewById(R.id.cardviewlayoutroot_second); //객체로 만들었기 때문에 findviewbyid가 가능해짐.
        VH2 vh2 = new VH2(itemview);
        return vh2;


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        //holder.setIsRecyclable(false);     //계속 되는 오류로 일단 추가해봄.
        VH2 vh2 = (VH2)holder;
        Itemlist_nation itemlist_nation  =  NSdata.get(position);
        vh2.tv_NationSeleciton.setText(itemlist_nation.getTv());
        Glide.with(context).load(itemlist_nation.getIv()).into(vh2.iv_NationSeleciton); //이미지는 글라이드로 처리
    }

    @Override
    public int getItemCount() {
        return NSdata.size();
    }

    class VH2 extends RecyclerView.ViewHolder{

        ImageView iv_NationSeleciton;                    //나라이미지
        TextView tv_NationSeleciton;                    //국가이름
        LinearLayout Linear_cardview_content_NS;
        CardView cardviewlayoutroot_second;
        RelativeLayout Relativelayout_cardview_title;
        final RippleBackground rippleBackground;

        public VH2(@NonNull View itemView) {
            super(itemView);

            iv_NationSeleciton = itemView.findViewById(R.id.iv_nation_selection);  //각 나라 이미지 추가해야함.
            tv_NationSeleciton = itemView.findViewById(R.id.tv_nation_selection);
            Linear_cardview_content_NS = itemView.findViewById(R.id.Linear_cardview_content_NS);
            cardviewlayoutroot_second = itemView.findViewById(R.id.cardviewlayoutroot_second);
            Relativelayout_cardview_title = itemView.findViewById(R.id.Relativelayout_cardview_title);

            rippleBackground=(RippleBackground)itemView.findViewById(R.id.content);


            Linear_cardview_content_NS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int a = getAdapterPosition(); //내가 선택한 숫자가 나옴.

                    a=a%JsonExchangeRate.cur_unit.size();                   //이렇게 하면 ArrayoutofBounds을 잘 막을 수 있음.
                    if(a!=RecyclerView.NO_POSITION){  //이 코드를 추가해야 recyclerview ArrayoutofBounds의 발생을 막을 수 있다.
                        for(int i=0; i<NSdata.size(); i++) { //datas와 같은 크기.
                            if (a == i) { //22번 돌면서 a와 같은게 있다면

//                                Animation anim = AnimationUtils.loadAnimation(context,R.anim.bounce);
//                                iv_NationSeleciton.startAnimation(anim);
                                rippleBackground.startRippleAnimation();


                                Toast.makeText(context, "성공적으로 추가하였습니다.", Toast.LENGTH_SHORT).show();
                                datas.add(new Itemlist(JsonExchangeRate.cur_nm.get(a), JsonExchangeRate.cur_unit.get(a), JsonExchangeRate.kftc_deal_bas_r.get(a), JsonExchangeRate.iv_nationflag.get(a)
                                        , GlobalTime.newstime2, GlobalTime.dateFormat2[a].format(GlobalTime.date2),
                                        WeatherJSonArray.todayC1[a], WeatherJSonArray.todayweather[a], GlobalTime.timedifferent[a]

                                ));

//                                datasCopy.add(new Itemlist(JsonExchangeRate.cur_nm[a], JsonExchangeRate.cur_unit[a], JsonExchangeRate.kftc_deal_bas_r[a], JsonExchangeRate.iv_nationflag[a]
//                                        , GlobalTime.newstime2, GlobalTime.dateFormat2[a].format(GlobalTime.date2),
//                                        WeatherJSonArray.todayC1[a], WeatherJSonArray.todayweather[a], GlobalTime.timedifferent[a]
//
//                                ));
                                DataSave();
                                MainActivity.recyclerAdapter.notifyDataSetChanged();
                            }//추가 된 상태
                        }

                    }

                    //추가를 한 후에 중복제거하기.
                    //확인해봤으나 누르는 속도가 너무 빨라서 datas의 size의 변경을 따라까지 못 함.

//                    for(int k=0; k<datasCopy.size(); k++){ //1번칸일때
//                        for(int j=0; j<k; j++){ //0번 칸
//                            if(datasCopy.get(j).getCur_nm().equals(datasCopy.get(k).getCur_nm())){ //0번과 1번칸을 비교
//                                Toast.makeText(context, "이미 추가되어있습니다.", Toast.LENGTH_SHORT).show();
//                                datasCopy.remove(k); //add했을때 맨 마지막에 추가된 것을 제거.
//                                k--; //제거하고 한발짝 뒤로 옮김.
//                                DataSave();
//                                MainActivity.recyclerAdapter.notifyItemRemoved(k);
//                            }
//                        }
//                    }

//                    for(int k=0; k<datas.size(); k++){ //1번칸일때
//                        for(int j=0; j<k; j++){ //0번 칸
//                            if(datas.get(j).getCur_nm().equals(datas.get(k).getCur_nm())){ //0번과 1번칸을 비교
//                                Toast.makeText(context, "이미 추가되어있습니다.", Toast.LENGTH_SHORT).show();
//                                datas.remove(k); //add했을때 맨 마지막에 추가된 것을 제거.
//                                k--; //제거하고 한발짝 뒤로 옮김.
//                                DataSave();
//                                MainActivity.recyclerAdapter.notifyItemRemoved(k);
//                            }
//                        }
//                    }


                }
            });
        }

    }//VH2

    public static void DataSave(){
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
