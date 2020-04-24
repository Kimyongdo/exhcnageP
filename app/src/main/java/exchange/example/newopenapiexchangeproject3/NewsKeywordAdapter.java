package exchange.example.newopenapiexchangeproject3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newopenapiexchangeproject3.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static exchange.example.newopenapiexchangeproject3.NewsPaper.keywodsDatas;
import static exchange.example.newopenapiexchangeproject3.NewsPaper.newsAdapter;
import static exchange.example.newopenapiexchangeproject3.NewsPaper.newsKeywordAdapter;

public class NewsKeywordAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<NewsKeywordVO> keywords;
    ArrayList<NewsKeywordVO> keywordsCopy = new ArrayList<>();

    public NewsKeywordAdapter(Context context, ArrayList<NewsKeywordVO> keywords) {
        this.context = context;
        this.keywords = keywords;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.news_keyword_itemlist,parent,false);
        KeyWordVH keyWordVH = new KeyWordVH(view);
        return keyWordVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        KeyWordVH keyWordVH = (KeyWordVH)holder;
        NewsKeywordVO newsKeywordVO=keywords.get(position);
        keyWordVH.keyWord.setText(newsKeywordVO.getKeywrod());
    }

    @Override
    public int getItemCount() {
        return keywords.size();
    }

    class KeyWordVH extends RecyclerView.ViewHolder{

        TextView keyWord;
        LinearLayout LinearKeyword;


        public KeyWordVH(@NonNull View itemView) {
            super(itemView);


//            keywordsCopy.addAll(keywords);//복사버전
            keyWord = itemView.findViewById(R.id.tv_keyword);

            LinearKeyword = itemView.findViewById(R.id.Linear_keyword);


            keyWord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int a= getAdapterPosition();
                    String keyWordReader = keywords.get(a).getKeywrod();
                   //여기서 녹색판별하고 다 흰색
//                    keyWord.setTextColor(Color.parseColor("#47C83E"));
//                    for(int i=0; i<keywords.size(); i++){
//
//                    }



                    //다시 녹색으로


                    NewsNaverSearch.NewSearching(keyWordReader);
                    newsAdapter.notifyDataSetChanged(); //뉴스기사
                    newsKeywordAdapter.notifyDataSetChanged(); //키워드
                    Toast.makeText(context, keyWordReader+" 검색완료", Toast.LENGTH_SHORT).show();
                }
            });


            keyWord.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final int a = getAdapterPosition();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("키워드를 제거하시겠습니까?");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            keywodsDatas.remove(a);
                            notifyItemRemoved(a); //adapter안에서는 오직 notify로만 쓴다. static이라고 자신꺼 썼다가 이상하게 지워지는 현상 발생함.
                            KeyWordDataSave();
                        }
                    });

                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //아무 일 없음.
                        }
                    });
                    builder.create();
                    builder.show();
                    return true; //롱클릭과 일반클릭 구별하기.
                }


            });
        }
    }


    public void KeyWordDataSave(){
        try {
            File file = new File(context.getFilesDir(),"keylist");
            FileOutputStream fos  = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(keywodsDatas); //현재의 datas를 저장.
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
