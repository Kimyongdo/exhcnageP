package exchange.example.newopenapiexchangeproject3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.newopenapiexchangeproject3.R;

import java.util.ArrayList;

public class NoteSearchAdapter extends BaseAdapter {
    Context context;
    ArrayList<NoteVO> testlistcopy;

    public NoteSearchAdapter(Context context, ArrayList<NoteVO> notelistcopy) {
        this.context = context;
        this.testlistcopy = notelistcopy;
    }

    @Override
    public int getCount() {
        return testlistcopy.size();
    }

    @Override
    public Object getItem(int i) {
        return testlistcopy.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.note_search_itemlist,viewGroup,false);
        }
        NoteVO noteVO = testlistcopy.get(i);
        TextView notetime = view.findViewById(R.id.tv_note_time2);
        TextView notetitle = view.findViewById(R.id.tv_note_title2);
        TextView noteContent = view.findViewById(R.id.tv_note_content2);

        notetime.setText(noteVO.getNoteDate());
        notetitle.setText(noteVO.getNoteTitle());
        noteContent.setText(noteVO.getNoteContent());

        return view;
    }
}

