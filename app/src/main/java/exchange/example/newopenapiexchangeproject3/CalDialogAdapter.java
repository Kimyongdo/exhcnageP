package exchange.example.newopenapiexchangeproject3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newopenapiexchangeproject3.R;

import java.util.ArrayList;

public class CalDialogAdapter extends BaseAdapter {

    ArrayList<CalAlertVO> arraylist;
    Context context;

    public CalDialogAdapter(ArrayList<CalAlertVO> arraylist, Context context) {
        this.arraylist = arraylist;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.calculator_dialog_itemlist,null);
//            view = inflater.inflate(R.layout.calculator_dialog_itemlist,null);
        }

        CalAlertVO cal_alertdialog = arraylist.get(position);

        ImageView iv = view.findViewById(R.id.iv_nation_selection_alert);
        TextView tv = view.findViewById(R.id.tv_nation_selection_alert);


        Glide.with(context).load(cal_alertdialog.getIv()).into(iv);
      //  iv.setImageResource(cal_alertdialog.getIv());
        tv.setText(cal_alertdialog.getTv());
        return view;
    }
}
