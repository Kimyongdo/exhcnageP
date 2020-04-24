package exchange.example.newopenapiexchangeproject3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.newopenapiexchangeproject3.R;

import java.util.ArrayList;

public class UpdateAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<UpdateVO> updateVOS;


    public UpdateAdapter(Context context, ArrayList<UpdateVO> updateVOS) {
        this.context = context;
        this.updateVOS = updateVOS;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.update_itemlist, parent, false);
        UpdateVH holder = new UpdateVH(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UpdateVH updateVH = (UpdateVH)holder;
        UpdateVO updateVO = updateVOS.get(position);

        updateVH.Updatetitle.setText(updateVO.getUpdateTtitle());
        updateVH.UpdateName.setText(updateVO.getUpdateName());
        updateVH.UpdateTime.setText(updateVO.getUpdateTime());



    }

    @Override
    public int getItemCount() {
        return updateVOS.size();
    }

    class UpdateVH extends RecyclerView.ViewHolder{
        TextView Updatetitle;
        TextView UpdateName;
        TextView UpdateTime;
        RelativeLayout UpdateRelativeLayout;

        public UpdateVH(@NonNull View itemView) {
            super(itemView);
            Updatetitle = itemView.findViewById(R.id.update_title);
            UpdateName = itemView.findViewById(R.id.tv_update_name);
            UpdateTime = itemView.findViewById(R.id.update_tv_date);


            UpdateRelativeLayout = itemView.findViewById(R.id.UpdateRelativeLayout);
            UpdateRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int a = getAdapterPosition();
                    Intent intent = new Intent(context,UpdateNote.class);
                    intent.putExtra("number",a);
                    context.startActivity(intent);
                    ((Activity)context).finish();

                }
            });
        }


    }
}
