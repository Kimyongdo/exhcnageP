package exchange.example.newopenapiexchangeproject3;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newopenapiexchangeproject3.R;

import java.util.ArrayList;
import java.util.Locale;


public class Address extends AppCompatActivity {
     ArrayList<AdressVO> dataList = new ArrayList();
     ArrayList<AdressVO> dataListSearch = new ArrayList();

     ArrayList<String> numberlist = new ArrayList();
    ArrayList<String> namelist = new ArrayList();

     ListView mListview;
    AddressAdapter addressAdapter;

    ImageView imageView;
    int i=0; //while문 돌리기
    String number;//전화번호
    String name;//이름

    EditText phoneBookSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address);
        mListview = findViewById(R.id.phonelistview);
        imageView = findViewById(R.id.arrow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " asc");

        while (c.moveToNext()) {
            // 연락처 id 값
            String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            // 연락처 대표 이름
            name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
            // ID로 화 정보 조회
            Cursor phoneCursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                    null, null);

            // 데이터가 있는 경우
            if (phoneCursor.moveToFirst()) {
                 number = phoneCursor.getString(phoneCursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                 //전화번호 0104931 이렇게 된 것과 010-4931 있어서 하나로 통일함.
                number = number.replace("-","");
            }

            phoneCursor.close();

            //주소록에 전화번호, 이름이 둘 중 하나라도 없다면 add하지 않고 넘기기.
            if(number==null || name ==null){

            }else{
                numberlist.add(number);
                namelist.add(name);
                dataList.add(new AdressVO(name,number));
                dataListSearch.add(new AdressVO(name,number));
            }
            i++;

        }
        c.close();

        addressAdapter = new AddressAdapter(this, dataList);
        mListview.setAdapter(addressAdapter);


        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //리스튜뷰에 보이는 숫자를 나타냄 3 4 5
                addressAdapter.getItem(i);
                //복사한 datalistSearch(전체) 중 그에 속한 번호를 알려줌  5라면 전체중에서 몇번일까?
                int actualPostion=dataListSearch.indexOf(addressAdapter.getItem(i));
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL); //다이얼번호
                Uri uri = Uri.parse("tel:"+numberlist.get(actualPostion));
                intent.setData(uri);
                startActivity(intent);
            }
        });

        phoneBookSearch = findViewById(R.id.phonebook_search);
        phoneBookSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //변화가 일어나면
                String text = phoneBookSearch.getText().toString();
                search(text);
            }
        });
    }////////////////////////////////////////////onCreate///////////////////////////////////////////////////////////////

    public void search(String text){
        //기본적으로 보이는 arraylist를 지우고
        dataList.clear();
        if(text.length()==0){
            //edittext에 아무것도 없다면 기존datalist에 이를 복사한 datlistSearch를 넣어주고
            dataList.addAll(dataListSearch);
        }else{

            for(int i=0; i<dataListSearch.size(); i++){
                    if(numberlist.get(i)==null || namelist.get(i)==null){
                        numberlist.set(0,"");
                        namelist.set(0,"");
                    }
                    //초성 java파일을 가져와서. 이름배열에 해당하는 text가 있다면 추가하기.
                    //굳이 따로 namelist를 안만들어도 되었던거 같은데 ㅋㅋ...
                    String choName = HangulUtils.getHangulInitialSound(dataListSearch.get(i).getName(), text);
                    //문자의 text가 하나라도 써져있다면 이에 해당하는 itemlist를 추가.
                    if(choName.indexOf(text)>=0){
                        dataList.add(dataListSearch.get(i));
                    }else if(numberlist.get(i).toLowerCase(Locale.getDefault()).contains(text)){ //null이 나는 이유 : 맨 처음에 null이 떠서.
                        dataList.add(dataListSearch.get(i));
                    }
            }
        }

        addressAdapter.notifyDataSetChanged();
    }


}



