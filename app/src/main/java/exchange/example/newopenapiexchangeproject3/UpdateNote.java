package exchange.example.newopenapiexchangeproject3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.newopenapiexchangeproject3.R;

public class UpdateNote extends AppCompatActivity {


    TextView tv_updateContent;
    TextView tv_updateTitle;
    TextView tv_updateTime;

    int number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_note);


        //툴바
        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("공지사항");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        number = intent.getIntExtra("number",0);

        tv_updateContent=findViewById(R.id.tv_UpdateNote_content);
        tv_updateTitle = findViewById(R.id.tv_updateNote_title);
        tv_updateTime = findViewById(R.id.tv_updateNote_Time);

        tv_updateContent.setText(UpdateMain.updates.get(number).getUpdateContent());
        tv_updateTitle.setText(UpdateMain.updates.get(number).getUpdateTtitle());
        tv_updateTime.setText(UpdateMain.updates.get(number).getUpdateTime());


    }


    @Override
    public void onBackPressed() {
        //뒤로가기 누르면 계속 원래 페이지를 보여준다. 그래서 우선 intent를 시킴.
        super.onBackPressed();
        Intent intent = new Intent(this, UpdateMain.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :{
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);

    }
}
