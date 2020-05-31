package com.example.jnu_graduate;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.Constraints;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class progressbar_r_c_adapter extends RecyclerView.Adapter<progressbar_r_c_adapter.ViewHolder> {
    private ArrayList<progressbar_item> mData = null ;
    private Context context;
    private int here;
    private int max;
    private int progress;
    private float margin;
    // 생성자에서 데이터 리스트 객체를 전달받음.
    progressbar_r_c_adapter(ArrayList<progressbar_item> list, String here, String max) {
        mData = list ;
        this.here=Integer.parseInt(here);
        this.max=Integer.parseInt(max);
        this.progress=caculateprogress(this.here,this.max);
        this.margin=caculatemargin(this.progress);
    }

    public float dpToPx(float dp) {
        return context.getResources().getDisplayMetrics().density * dp;
    }
    public float caculatemargin(int progress){
        double imsi=progress*3.4;
        if(imsi>340){
            imsi=340;
        }
        int imsi2=(int) imsi;
        float margin = imsi2;
        return margin;
    }

    public int caculateprogress(int here, int max){

        double imsi1= (double)here/max;
        double imsi2= imsi1*100;

        int progress=(int)imsi2;

        return progress;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
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
        holder.progressBar.setProgress(progress);
        holder.heretext.setText(String.valueOf(here));
        holder.maxtext.setText(String.valueOf(max));
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar ;
        TextView heretext;
        TextView maxtext;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        ViewHolder(View itemView) {
            super(itemView) ;
            // 뷰 객체에 대한 참조. (hold strong reference)
            progressBar = itemView.findViewById(R.id.recycleprogressbar) ;
            heretext = itemView.findViewById(R.id.here_text);
            Constraints.LayoutParams params = new Constraints.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.startToStart = R.id.recycleprogressbar;
            params.topToBottom = R.id.recycleprogressbar;
            System.out.println(margin+":margin3");
            params.leftMargin=(int) dpToPx(margin);
            heretext.setLayoutParams(params);
            maxtext = itemView.findViewById(R.id.max_text);
        }
    }
}
