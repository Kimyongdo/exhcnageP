package exchange.example.newopenapiexchangeproject3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.newopenapiexchangeproject3.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import static exchange.example.newopenapiexchangeproject3.JsonExchangeRate.kftc_deal_bas_r;

public class CalCalculator extends AppCompatActivity  {

    //리스트뷰
    boolean et1Focus, et2Focus;

    DecimalFormat df = new DecimalFormat("###,###.####");
    DecimalFormat dfTemp = new DecimalFormat("###,###.####");
    DecimalFormat df2 = new DecimalFormat("###,###.####");

    String result="";
    String result2="";

    Toolbar toolbar;
    ImageView iv_CompareNation1;
    EditText et_number;
    TextView tv_name;
    TextView tv_currency;

    ImageView iv_CompareNation2;
    EditText et_number2;
    TextView tv_name2;
    TextView tv_currency2;

    int nationPostionDown=22; //미국
    int nationPostionUp=13; //한국

    ArrayList<CalAlertVO> dialog_arraylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calaulator_main);

        //메뉴의 이름 적용하는 방법
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("환율계산기");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로가는 버튼 생성 및 클릭 시 뒤로 가기 됨.

        //첫번째 기준 환율
        tv_name = findViewById(R.id.click_nation_name);//이름
        iv_CompareNation1=findViewById(R.id.click_nation_flag);//이미지
        et_number=findViewById(R.id.click_nation_et);//edit_Text
        tv_currency=findViewById(R.id.click_nation_name_currency);//환율이름


        //두번째 기준 환율
        tv_name2 = findViewById(R.id.click_nation_name2);
        iv_CompareNation2=findViewById(R.id.click_nation_flag2);
        et_number2=findViewById(R.id.click_nation_et2);
        tv_currency2=findViewById(R.id.click_nation_name2_currency);


        //눌렀을 경우 지우기 누르면 바로 ""로 만들도록
        et_number.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                switch (i){
                    case KeyEvent.KEYCODE_DEL :
                        et_number.setText("");
                }
                return false;
            }
        });

        //////////////////////////////////////////////숫자입력하는 곳/////////////////////////////////////////////////////////
        et_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(et1Focus) {
                    String input = et_number.getText().toString().trim();
                    if (!input.equals("")) {
                        //소수점 추가코드
                        //result 처음이 "" 빈공간만 아니라면
                        if (!charSequence.toString().equals(result)) {
                            result = df.format(Long.parseLong(charSequence.toString().replaceAll(",", "")));
                            et_number.setText(result);
                            et_number.setSelection(result.length());
                        }

                        //String으로 받아왔을 경우 ,와 . 를 제거해줌.
                        String KftcUp = kftc_deal_bas_r.get(nationPostionUp);
                        String KftcDown = kftc_deal_bas_r.get(nationPostionDown);

                        //기존에 있는,를 제거
                        KftcUp = KftcUp.replace(",", "");
//                        KftcUp = KftcUp.replace(".", ""); //.까지 지우면 숫자가 훨씬 더 커지니까 여기서 변경
                        //1062.09가 나오는 상황
                        double tempup = Double.parseDouble(KftcUp); //double이 .은 허용함
                        tempup = (Math.round(tempup*10)/10.0);
                        //1062.5가 나옴

                        KftcDown = KftcDown.replace(",", "");
//                        KftcDown = KftcDown.replace(".", "");
                        double tempdown = Double.parseDouble(KftcDown); //double이 .은 허용함
                        tempdown = (Math.round(tempdown*10)/10.0);

                        input = input.replace(",", "");//지우는 순간 .은 인식하지 못해서. 오류가 나는 상황
                        double ExNum = Double.parseDouble(input);
                        double convert;
                        if(nationPostionUp==10 || nationPostionUp==11){  //첫째 edittext에서 위에가 일본,인도인 경우
                            convert = Math.round( (   ( (tempup/100) / (tempdown)  ) * ExNum * 1000)  );
                        }
                        else if(nationPostionDown==10 || nationPostionDown==11){ //첫째 edittext에서 아래가 일본,인도인 경우
                            //한국 선택했는데 아래가 일본인 경우
                            convert = Math.round( (   (tempup / (tempdown/100)  ) * ExNum * 1000)  );
                        }else{
                            convert = Math.round( (   (tempup / (tempdown)  ) * ExNum * 1000)  );
                        }
                        convert = convert/1000.0;

                        //숫자를 천단위마다 ,로 찍게 하는 코드
                        String convert2 = NumberFormat.getInstance().format(convert);
                        et_number2.setText(convert2);
                    }else{
                        et_number2.setText("");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //커서가 옮겨졌다고 해야 에디트 텍스트에서 textwatcher가 따로 분리되서 발동됨.
        et_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                et1Focus =b;
                et_number.setText("");
            }
        });

        et_number2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                switch (i){
                    case KeyEvent.KEYCODE_DEL :
                        et_number2.setText("");
                }
                return false;
            }
        });

        //두번째
        et_number2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et2Focus) {
                    String input2 = et_number2.getText().toString().trim();
                    Log.d("TAGG",input2);
                    //계속 지우기 누르면 끝에는 ""로 나오는데 이걸 double로 파싱할 수 없어서 오류가 나옴. //아무것도 없다면
                    if (!input2.equals("")) {
                        //소수점 추가해줌
                        if (!charSequence.toString().equals(result2)) {
                            result2 = df2.format(Long.parseLong(charSequence.toString().replaceAll(",", "")));
                            et_number2.setText(result2);
                            et_number2.setSelection(result2.length());
                        }
                        String KftcUp = kftc_deal_bas_r.get(nationPostionUp);
                        String KftcDown = kftc_deal_bas_r.get(nationPostionDown);

                        //여기에 숫자가 있는 상황 EX) 1,062.09라면
                        KftcUp = KftcUp.replace(",", "");
//                        KftcUp = KftcUp.replace(".", ""); //.까지 지우면 숫자가 훨씬 더 커지니까 여기서 변경
                        //1062.09가 나오는 상황
                        double tempup = Double.parseDouble(KftcUp); //double이 .은 허용함
                        tempup = (Math.round(tempup*10)/10.0);
                        //1062.5가 나옴
                        KftcDown = KftcDown.replace(",", "");
//                        KftcDown = KftcDown.replace(".", "");
                        double tempdown = Double.parseDouble(KftcDown); //double이 .은 허용함
                        tempdown = (Math.round(tempdown*10)/10.0);
                        /////////////////////////////////////////////////////////////////////////
                        input2 = input2.replace(",", ""); //지우는 순간 .은 인식하지 못해서. 오류가 나는 상황 .는 지워도 DOUBLE이기에 끝에 .0이 계속 남음.
                        double ExNum = Double.parseDouble(input2);//파서는 ""를 지우는 상황.
                        double convert;
                        if(nationPostionUp==11 || nationPostionUp==10){ //위에가 일본이면서 아래에서 숫자를 입력하는 경우 - 각각 위에꺼와 연동됨
                            convert = Math.round( (   ((tempdown*100) / (tempup)  ) * ExNum * 1000)  );
                        }else if(nationPostionDown==11 || nationPostionDown==10){ //두번째 eidttext이면서 아래가 일본인 경우
                            convert = Math.round( (   ((tempdown/100) / (tempup)  ) * ExNum * 1000)  );
                        } else {
                            convert = Math.round( (   (tempdown / (tempup)  ) * ExNum * 1000)  );
                        }
                        convert = convert/1000.0;
                        //숫자 천단위 마다 변형하는 코드.
                        //숫자 커지면 뒤에 E나오는 문제도 해결됨
                        String convert2 = NumberFormat.getInstance().format(convert);
                        et_number.setText(convert2);
                    }else{
                        et_number.setText("");
                    }

                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        et_number2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                et2Focus=b;
                et_number2.setText("");
            }
        });
///////////////////////////////////////////////////////숫자입력끝//////////////////////////////////////////////////////////////

        //////////////////////////키보드 고정///////////////////////////////////////
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_number ,
                InputMethodManager.SHOW_IMPLICIT);
       ////////////////////////////////////////////////////////////////////////////////

        //다이얼로그 리사이클러뷰
        for(int i=0; i<JsonExchangeRate.cur_unit.size(); i++){
            dialog_arraylist.add(new CalAlertVO(JsonExchangeRate.cur_nm.get(i), JsonExchangeRate.iv_nationflag.get(i)));
        }

    }////////////////////////////////////////oncreate//////////////////////////////////////////////////////////

    //뒤로가기 화살표
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :{
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //한국을 클릭하면
    public void clickNationKorea(View view) {
        firstshowAlert();
    }
    //미국을 클릭하면
    public void clickNationOther(View view) { secondshowAlert(); }

    //다이얼로그에 listview 넣기.
    public void firstshowAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CalCalculator.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.calcaulator_dialog, null);
        builder.setView(view);

        //다이얼로그의 커스텀뷰를 listview와 연결
        final ListView listview = view.findViewById(R.id.listview_alterdialog_list);
        final AlertDialog dialog = builder.create();

        CalDialogAdapter calDialogAdapter = new CalDialogAdapter(dialog_arraylist,this);
        listview.setAdapter(calDialogAdapter);

        //리스트뷰(국가들) 선택하면
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //그 나라의 데이터로 바꾸기.
                Glide.with(CalCalculator.this).load(JsonExchangeRate.iv_nationflag.get(position)).into(iv_CompareNation1);
                tv_name.setText(JsonExchangeRate.cur_nm.get(position));
                tv_currency.setText(JsonExchangeRate.cur_unit.get(position));
                nationPostionUp = position;
                et_number.setText("");
                et_number2.setText("");
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void secondshowAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(CalCalculator.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.calcaulator_dialog, null);
        builder.setView(view);

        final ListView listview = view.findViewById(R.id.listview_alterdialog_list);
        final AlertDialog dialog = builder.create();

        CalDialogAdapter calDialogAdapter = new CalDialogAdapter(dialog_arraylist,this);
        listview.setAdapter(calDialogAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Glide.with(CalCalculator.this).load(JsonExchangeRate.iv_nationflag.get(position)).into(iv_CompareNation2);
                tv_name2.setText(JsonExchangeRate.cur_nm.get(position));
                tv_currency2.setText(JsonExchangeRate.cur_unit.get(position));
                nationPostionDown = position;
                et_number.setText("");
                et_number2.setText("");
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}


