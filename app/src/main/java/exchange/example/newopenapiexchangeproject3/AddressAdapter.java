package exchange.example.newopenapiexchangeproject3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.newopenapiexchangeproject3.R;

import java.util.ArrayList;

public class AddressAdapter extends BaseAdapter {
    Context context;
    ArrayList<AdressVO>  addresslist;

    public AddressAdapter(Context context, ArrayList<AdressVO> adresslist) {
        this.context = context;
        this.addresslist = adresslist;
    }


    @Override
    public int getCount() {
        return addresslist.size();
    }

    @Override
    public Object getItem(int i) {
        return addresslist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.address_itemlist,viewGroup,false);
        }

        AdressVO addressVO = addresslist.get(i);
        TextView phoneName= view.findViewById(R.id.phonebook_name);
        TextView phoneNumber = view.findViewById(R.id.phonebook_callnumber);

        phoneName.setText(addressVO.getName());
        phoneNumber.setText(addressVO.getPhone());
        return view;
    }
}
