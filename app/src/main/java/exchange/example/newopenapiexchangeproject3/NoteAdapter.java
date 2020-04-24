package exchange.example.newopenapiexchangeproject3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.newopenapiexchangeproject3.R;

import java.util.HashMap;
import java.util.Map;

import static exchange.example.newopenapiexchangeproject3.MainActivity.nicknumber;
import static exchange.example.newopenapiexchangeproject3.NoteRubbish.rubbishlist;
import static exchange.example.newopenapiexchangeproject3.NoteText.notelist;

public class NoteAdapter extends RecyclerView.Adapter{
    Context context;
    int longPosition;

    public NoteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemview = layoutInflater.inflate(R.layout.note_itemlist, parent, false);
        NoteVH noteVH = new NoteVH(itemview);
        return noteVH;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        NoteVH noteVH = (NoteVH) holder;
        NoteVO noteVO = notelist.get(position);
        noteVH.NoteDate.setText(noteVO.getNoteDate());
        noteVH.NoteTitle.setText(noteVO.getNoteTitle());
        noteVH.NoteContent.setText(noteVO.getNoteContent());
    }

    @Override
    public int getItemCount() {
        return notelist.size();
    }


    class NoteVH extends RecyclerView.ViewHolder{

        TextView NoteDate;
        TextView NoteTitle;
        TextView NoteContent;


        LinearLayout NoteLinearlayout;
        public NoteVH(@NonNull View itemView) {
            super(itemView);



            NoteDate = itemView.findViewById(R.id.tv_note_time);
            NoteTitle = itemView.findViewById(R.id.tv_note_title);
            NoteContent = itemView.findViewById(R.id.tv_note_content);

            NoteLinearlayout = itemView.findViewById(R.id.NoteLinearLayout);

            NoteLinearlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int number = getAdapterPosition();
                    Intent intent = new Intent(context, NoteInText.class);
                    intent.putExtra("number",number);
                    context.startActivity(intent);
//                    ((Activity)context).startActivityForResult(intent,100); //이것도 필요 없을 듯
//                    ((Activity)context).finish();
                    //리사이클러뷰는 no place이므로 이를 받아주는 main에서 onResult를 행해야함.
                    //100은 임의의 수로 나중에 FIANL로 상수로 바꿔주어야한다.

                }

            });

            NoteLinearlayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longPosition = getAdapterPosition();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("이 노트를 삭제하시겠습니까?");//창 이름
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //로그인회원이라면
                            if(nicknumber!=0.0){
                                DeleteDateDB();//DB에서 데이터를 지움.
                                rubbishlist.add(new NoteRubbishVO(notelist.get(longPosition).getNoteDate(),notelist.get(longPosition).getNoteTitle(),notelist.get(longPosition).getNoteContent()));
                                NoteRubbishupdate();


                                notelist.remove(longPosition); //이렇게해도 문제가 없음.
                                notifyDataSetChanged();
                            }else{
                                notelist.remove(longPosition);
                                notifyDataSetChanged();
                            }
                            Toast.makeText(context, "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //아무일도 안일어남.
                        }
                    });

                    AlertDialog dialog = builder.create(); //create 생성
                    dialog.show(); //.show()를 표시할것
                    return true; //return true를 써서 일반 click은 무시하도록.
                }
            });


        }

    }


    public void DeleteDateDB(){
        final String noetime = GlobalTime.noetime;
        final String title = notelist.get(longPosition).getNoteTitle();
        final String content = notelist.get(longPosition).getNoteContent();
        String serverurl = "http://chocojoa123.dothome.co.kr/Exchange/Delete.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> datas = new HashMap<>();

                //수정 완료 후 저장을 눌렀을 때의 edittext 내용
                datas.put("number",nicknumber+"");
                datas.put("time",noetime);
                datas.put("title",title);
                datas.put("content",content); //서버에 저장.
                return datas;
            }
        };//stringRequest

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    //지운 후 지운파일이 휴지통에 저장되도록.
    public void NoteRubbishupdate(){

        //longPosition은 잘못 됨 12번 추가했는데 12번 내놔.. 3번 밖에 없는데.. 끝에자리가 필요함.
        //삭제하자마자 여기서는 추가되는거니 끝에 추가되면 됨.
        final String noetime = rubbishlist.get(rubbishlist.size()-1).getRubbishDate();
        final String title = rubbishlist.get(rubbishlist.size()-1).getRubbishTitle();
        final String content = rubbishlist.get(rubbishlist.size()-1).getRubbishContent();
        String serverurl = "http://chocojoa123.dothome.co.kr/Exchange/NoteRubbish.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> datas = new HashMap<>();

                datas.put("number",nicknumber+"");
                datas.put("time",noetime);
                datas.put("title",title);
                datas.put("content",content); //서버에 저장.
                return datas;
            }
        };//stringRequest

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
}
