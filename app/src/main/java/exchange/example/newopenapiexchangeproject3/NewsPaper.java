package exchange.example.newopenapiexchangeproject3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.newopenapiexchangeproject3.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static exchange.example.newopenapiexchangeproject3.NewsNaverSearch.newsNumber;

public class NewsPaper extends AppCompatActivity {

    //뉴스기사
    EditText etNewsPaper;
    static  RecyclerView newsPaperRecylcer;
    static NewsAdapter newsAdapter;


    //키워드 리사이클러뷰
    RecyclerView paperRecycler2;
    public static  ArrayList<NewsKeywordVO> keywodsDatas = new ArrayList<>();
    static NewsKeywordAdapter newsKeywordAdapter;

    //설정탭
    EditText addtext;

    static ArrayList<NewsSearchVO> newsDatas = new ArrayList<NewsSearchVO>(); //시간, 날짜
    static ArrayList<String> newsLinks = new ArrayList<>();


    ImageView imageView;
    ImageView iv_arrow;
    static RelativeLayout newsLayoutGone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_paper);


        newsLayoutGone = findViewById(R.id.newsLayoutGone);

    iv_arrow = findViewById(R.id.NewsArrow);
    etNewsPaper = findViewById(R.id.et_newspaper);
    imageView = findViewById(R.id.newsGlass);

    //뉴스


        newsPaperRecylcer = findViewById(R.id.paperRecycler);
        newsPaperRecylcer.setHasFixedSize(true); //리아시클러 크기 고정
        newsAdapter = new NewsAdapter(this,newsDatas);
         newsPaperRecylcer.setAdapter(newsAdapter);



    //키워드
        Dataload(); //처음에 keywrodsDatas가 0이면 뒤에 dataload하고 notify해도 무의미하므로 위에다가 써야한다
                    //그전에 되었던건 이미 추가되었던 것을 가져오니까 생기는 효과인듯 notetext꺼
        paperRecycler2=findViewById(R.id.paperRecycler2);
        paperRecycler2.setHasFixedSize(true);
        newsKeywordAdapter = new NewsKeywordAdapter(this,keywodsDatas);
        paperRecycler2.setAdapter(newsKeywordAdapter);


//        newsKeywordAdapter.notifyDataSetChanged();

    //뒤로가기 끝내기.
        iv_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    //키패드에 있는 엔터키를 눌렀을 때 반응하도록.
    etNewsPaper.setOnKeyListener(new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode== KeyEvent.KEYCODE_ENTER)  ){
                String keywrod = null;
                keywrod = etNewsPaper.getText().toString();
                NewsNaverSearch.NewSearching(keywrod);
                return  true;
            }
            return false;
        }
    });

    }


    //검색버튼을 눌렀을 때 반응하도록.
    //오른쪽 리사이클러뷰
    public void clickNewsBtn(View view) {
        String keywrod = null;
        keywrod = etNewsPaper.getText().toString();
        NewsNaverSearch.NewSearching(keywrod);
    }

    public void clickSetting(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = getLayoutInflater().inflate(R.layout.news_dialog,null);
        builder.setView(v);
//설정탭
        addtext =v.findViewById(R.id.et_addtext);
        builder.setPositiveButton("완료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String textR = addtext.getText().toString();
                keywodsDatas.add(new NewsKeywordVO(textR));


                for(int i=0; i<keywodsDatas.size(); i++){
                    for(int k=0; k<i; k++){
                        if(keywodsDatas.get(k).getKeywrod().equals(keywodsDatas.get(i).getKeywrod())){
                            Toast.makeText(NewsPaper.this, "중복된 키워드입니다.", Toast.LENGTH_SHORT).show();
                            keywodsDatas.remove(keywodsDatas.size()-1);
                            i--; //중복 만들 때 되면 꼭 이거 만들어서 이전으로 돌리기 이거 안쓰면 outarray 뜬다.
                        }
                    }
                }
                DataSave();
                newsKeywordAdapter.notifyDataSetChanged();//add remove 한번에.
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
//        DataSave();

    }

    //갤6 저장이 일단 안되고 있음.
    public void DataSave(){
        try {
            File file = new File(getFilesDir(),"keylist");
            FileOutputStream  fos  = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(keywodsDatas); //현재의 datas를 저장.
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Dataload(){
        try {
            File file = new File(getFilesDir(),"keylist");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            keywodsDatas = (ArrayList<NewsKeywordVO>) ois.readObject();  //앱 꺼도 새롭게 저장한 파일이 있음을 파악함.
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


}
