package exchange.example.newopenapiexchangeproject3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.newopenapiexchangeproject3.R;

import static exchange.example.newopenapiexchangeproject3.NoteRubbish.rubbishlist;

public class NoteRubbishAdapter extends BaseAdapter {
    Context context;


    public NoteRubbishAdapter(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return rubbishlist.size();
    }

    @Override
    public Object getItem(int i) {
        return rubbishlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.note_rubbish_itemlist,viewGroup,false);
        }
        NoteRubbishVO noteRubbishVO = rubbishlist.get(i);
        TextView rubbishTime = view.findViewById(R.id.tv_rubbish_time);
        TextView rubbishTitle = view.findViewById(R.id.tv_rubbish_title);
        TextView rubbishContent = view.findViewById(R.id.tv_rubbish_content);

        rubbishTime.setText(noteRubbishVO.getRubbishDate());
        rubbishTitle.setText(noteRubbishVO.getRubbishTitle());
        rubbishContent.setText(noteRubbishVO.getRubbishContent());

        return view;
    }



}

