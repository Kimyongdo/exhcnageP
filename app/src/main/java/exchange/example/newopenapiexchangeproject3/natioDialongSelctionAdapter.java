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

public class natioDialongSelctionAdapter extends BaseAdapter {

    ArrayList<nationVO> arraylist;
    Context context;

    public natioDialongSelctionAdapter(ArrayList<nationVO> arraylist, Context context) {
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
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.nationsecltionadapter, null);
//            view = inflater.inflate(R.layout.calculator_dialog_itemlist,null);
        }

        nationVO nationVO2 = arraylist.get(position);

        ImageView iv = view.findViewById(R.id.iv_nation_selection_alert3);
        TextView tv = view.findViewById(R.id.tv_nation_selection_alert3);


        Glide.with(context).load(nationVO2.getIv()).into(iv);
        //  iv.setImageResource(cal_alertdialog.getIv());
        tv.setText(nationVO2.getTv());
        return view;
    }
}