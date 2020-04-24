package exchange.example.newopenapiexchangeproject3;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.newopenapiexchangeproject3.R;
import java.util.ArrayList;


public class NewsAdapter extends RecyclerView.Adapter {
    Context context;
    static ArrayList<NewsSearchVO> newsDatas;

    public NewsAdapter(Context context, ArrayList<NewsSearchVO> newsDatas) {
        this.context = context;
        this.newsDatas = newsDatas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context );
        View view = layoutInflater.inflate(R.layout.news_paper_itemlist, parent,false);
        NewsVH newsVH = new NewsVH(view);
        return newsVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewsVH newsVH = (NewsVH)holder;
        NewsSearchVO newsSearchVO = newsDatas.get(position);
        newsVH.newsOnlyContnet.setText(newsSearchVO.getNewsTitle()); //newsSearchVO가 이미 get(position)까지 한 상태.
        newsVH.newsOnlyTime.setText(newsSearchVO.getNewsTime());

    }

    @Override
    public int getItemCount() {
        return newsDatas.size();
    }

    class NewsVH extends RecyclerView.ViewHolder{

        LinearLayout newsLinearLayout;
        TextView newsOnlyContnet;
        TextView newsOnlyTime;


        public NewsVH(@NonNull View itemView) {
            super(itemView);

            newsOnlyContnet = itemView.findViewById(R.id.paperContent);
            newsOnlyTime = itemView.findViewById(R.id.paperTime);
            newsLinearLayout = itemView.findViewById(R.id.newsLinearLayout);

            newsLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int a = getAdapterPosition();
                    String newsurl1 = NewsPaper.newsLinks.get(a);
                    Intent intent = new Intent(context, newsWebview.class);
                    intent.putExtra("Link",newsurl1);
                    context.startActivity(intent);

                }
            });
        }

    }


}
