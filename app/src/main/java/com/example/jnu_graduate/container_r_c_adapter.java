package com.example.jnu_graduate;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.Constraints;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class container_r_c_adapter extends RecyclerView.Adapter<container_r_c_adapter.ViewHolder> {
    private ArrayList<containeritem> mData = null ;
    private int containerheight =80;
    Context context;

    public float dpToPx(float dp) {
        return context.getResources().getDisplayMetrics().density * dp;
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    container_r_c_adapter(ArrayList<containeritem> list, int _containerheight,Context context) {
        containerheight=_containerheight;
        mData = list ;
        this.context=context;
    }


    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public container_r_c_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_container, parent, false) ;
        container_r_c_adapter.ViewHolder vh = new container_r_c_adapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(container_r_c_adapter.ViewHolder holder, int position) {

        containeritem item = mData.get(position) ;
        holder.title.setText(item.getText()) ;
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView container;
        TextView title ;


        Constraints.LayoutParams params = new Constraints.LayoutParams((int) dpToPx(375), (int) dpToPx(containerheight));
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            container =itemView.findViewById(R.id.recyclecontainer) ;
            container.setLayoutParams(params);
            title = itemView.findViewById(R.id.recycletitle) ;

        }
    }
}