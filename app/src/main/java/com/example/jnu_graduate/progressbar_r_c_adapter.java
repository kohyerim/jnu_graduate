package com.example.jnu_graduate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class progressbar_r_c_adapter extends RecyclerView.Adapter<progressbar_r_c_adapter.ViewHolder> {
    private ArrayList<progressbar_item> mData = null ;
    private Context context;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    progressbar_r_c_adapter(ArrayList<progressbar_item> list) {
        mData = list ;
    }

    public float dpToPx(float dp) {
        return context.getResources().getDisplayMetrics().density * dp;
    }
    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public progressbar_r_c_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_progressbar, parent, false) ;
        progressbar_r_c_adapter.ViewHolder vh = new progressbar_r_c_adapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(progressbar_r_c_adapter.ViewHolder holder, int position) {

        progressbar_item item = mData.get(position) ;
        holder.progressBar.setProgress(item.getProgress());
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar ;


        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            progressBar = itemView.findViewById(R.id.recycleprogressbar) ;
        }
    }
}
