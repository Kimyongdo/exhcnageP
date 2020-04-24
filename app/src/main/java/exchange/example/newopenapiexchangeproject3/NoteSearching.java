package exchange.example.newopenapiexchangeproject3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.newopenapiexchangeproject3.R;

import java.util.ArrayList;

public class NoteSearching extends AppCompatActivity {

    EditText noteEditText;
    ListView notesearchlistview;
    NoteSearchAdapter noteSearchAdapter;
    ArrayList<NoteVO> notelistcopy = new ArrayList<>();
    ArrayList<NoteVO> testlistcopy = new ArrayList<>();

    String str;
    ImageView imageView;
    ListView notelistview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_searching);

        imageView = findViewById(R.id.arrow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteEditText.setText("");
                finish();
            }
        });

        noteEditText = findViewById(R.id.note_search2);
        notesearchlistview = findViewById(R.id.notesearchlistview2);

        notelistcopy.addAll(NoteText.notelist);
        testlistcopy.addAll(NoteText.notelist);

        noteSearchAdapter = new NoteSearchAdapter(this,testlistcopy);
        notesearchlistview.setAdapter(noteSearchAdapter);
        notesearchlistview.setVisibility(View.INVISIBLE);
//
//
//        notelistcopy.clear(); //clear쓴 이유는 onCreate할때마다 notelist가 copy버전에 계속 추가되서 list분량이 들어남; -> notelist를 쓰지 말고 다른걸 쓰자.
//        noteSearchAdapter.notifyDataSetChanged();

//
//        testlistcopy.clear();
//        noteSearchAdapter.notifyDataSetChanged();
//        testlistcopy.addAll(searchlist);


//키패드에 있는 엔터키를 눌렀을 때 반응하도록.
        noteEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode== KeyEvent.KEYCODE_ENTER)  ){
                    String keywrod = null;
                    keywrod = noteEditText.getText().toString();
                    NewsNaverSearch.NewSearching(keywrod);
                    return  true;
                }
                return false;
            }
        });
        //recylerview main에서 아이템클릭리스너로 전체 잡기. -> 체크박스도 여기서 바꾸면 되겠다 adapter에서 하나씩 바꾸려다가는 힘드니까.
        notesearchlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                noteSearchAdapter.getItem(i);
                int actualPostion=notelistcopy.indexOf(noteSearchAdapter.getItem(i));
                Intent intent = new Intent(NoteSearching.this, NoteInText.class); //이거랑 왜 noteintext와 연동되는지 모르겠음.. ---- 근데 일단 작동은 함..
                intent.putExtra("number2",actualPostion);
                intent.putExtra("searchingToken",555); //필터 된 후의 내용이 오류가 뜸.
                startActivity(intent);

            }
        });

        noteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                str = noteEditText.getText().toString();
                search(str);
            }
        });
    }/////////////////////oncreate///////////////////////////////////////////////


    public void search(String text){
        //adapter에 추가한 list를 기반으로하고 이를 복제한 list로 바꾸면서 보여지는 것.
        notesearchlistview.setVisibility(View.VISIBLE);
        testlistcopy.clear();
        if(text.length()==0){
            testlistcopy.addAll(notelistcopy);
        }else{

            for(int i=0; i<notelistcopy.size(); i++){
                String choName = HangulUtils.getHangulInitialSound(notelistcopy.get(i).getNoteTitle(), text); //제목
                String choContent = HangulUtils.getHangulInitialSound(notelistcopy.get(i).getNoteContent(), text); //내용
                //문자의 text가 하나라도 써져있다면 이에 해당하는 itemlist를 추가.
                if(choName.indexOf(text)>=0 || choContent.indexOf(text)>=0){
                    testlistcopy.add(notelistcopy.get(i));
                }
//                if(notelistcopy.get(i).getNoteTitle().contains(text) || notelistcopy.get(i).getNoteContent().contains(text)){
//                    testlistcopy.add(notelistcopy.get(i)); //계속 오류나는 이유가 필터를 써서 notelist의 개수가 줄어드는데 그걸 notelist에 number에 intent하려고하니 오류남.
//                }

            }
        }

        noteSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        noteEditText.setText("");
        finish(); //노트 수정하고 돋보기로 들어오면 계속 문제가 listview가 늘어나는 문제가 생기거나 원본인 notelist에 add를 했기에 검색 후 바로 뒤로 돌아가면
                  //그대로 notelist에 필터된 정보만 입력됨 -> 이를 피하기 위해서 뒤로가기 누르는 행위를 할 시 ""를 통해 notelist를 addall시킴.
                //이거 한글클래스 쓰면서 자동으로 해결해주는 듯..
    }
}
