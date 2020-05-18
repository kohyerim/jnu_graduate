package com.example.jnu_graduate;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class subject_r_c_adapter extends RecyclerView.Adapter<subject_r_c_adapter.ViewHolder> {

    private boolean textcolor;
    private ArrayList<String> mData =null;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text ;

        ViewHolder(View itemView, boolean textcolor) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)

            text = itemView.findViewById(R.id.text1);
            if(textcolor==true){
                text.setTextColor(Color.parseColor("#0054FF"));
            }
            if(textcolor==false){
                text.setTextColor(Color.parseColor("#FF0000"));
            }
        }
    }


    subject_r_c_adapter(ArrayList<String> List, boolean setTextcolor){
        mData= List;
        this.textcolor=setTextcolor;
    }


    @Override
    public subject_r_c_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false) ;
        subject_r_c_adapter.ViewHolder vh = new subject_r_c_adapter.ViewHolder(view, textcolor) ;

        return vh ;
    }
    @Override
    public void onBindViewHolder(subject_r_c_adapter.ViewHolder holder, int position) {
        String text = mData.get(position);

        holder.text.setText(text);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}
