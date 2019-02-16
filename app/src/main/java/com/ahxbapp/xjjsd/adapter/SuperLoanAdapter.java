package com.ahxbapp.xjjsd.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.ahxbapp.common.Global;
import com.ahxbapp.xjjsd.R;
import com.ahxbapp.xjjsd.api.APIManager;
import com.ahxbapp.xjjsd.model.SuperLoanModel;
import com.ahxbapp.xjjsd.utils.PrefsUtil;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by zzx on 2017/6/13 0013.
 * 贷超的adapter
 */

public class SuperLoanAdapter extends RecyclerView.Adapter<SuperLoanAdapter.SuperLoanHolder> {
    private Context mContext;
    private List<SuperLoanModel> modelList;
    private onItemClickListener onItemClickListener;
    private onWXClickListener onWXClickListener;

    public SuperLoanAdapter(Context mContext, List<SuperLoanModel> modelList) {
        this.mContext = mContext;
        this.modelList = modelList;
    }

    @Override
    public SuperLoanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_dc, parent, false);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new SuperLoanHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(SuperLoanHolder holder, final int position) {
        final SuperLoanModel model = modelList.get(position);
        int status = model.getStatus();

        if (status == 8) {
            holder.llAstrictDay.setVisibility(View.VISIBLE);
            holder.llApply.setBackgroundResource(R.drawable.gradient_gray);
            holder.tvAstrictDay.setText(model.getLimitDay() + "(天)");
            holder.tv_apply.setText("审核失败");
            holder.tvDay.setText("  " + model.getTermNum() + "(天)");
            holder.tvMoney.setText(model.getLoanNum() + "");
        } else if (status == 5) {
            holder.tv_apply.setText("我要还款");
            holder.tvAstrictDayText.setText("应还日期");
            holder.tvDay.setText(model.getRepayTime() + "");
            holder.tvMoneyText.setText("应还金额");
            holder.llApply.setBackgroundResource(R.drawable.gradient_red);
            holder.tvMoney.setText(model.getRepayLoan() + "(元)");
            holder.llApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onApplyItemClick(position);
                }
            });
        } else if (status == 4) {
            holder.tv_apply.setText("待放款");
            holder.llApply.setBackgroundResource(R.drawable.gradient_green);
            holder.tvDay.setText( model.getTermNum() + "(天)");
            holder.tvMoney.setText(model.getLoanNum() + "");
        } else if (status == 0) {

            holder.llkong.setVisibility(View.GONE);
            holder.WXservice.setVisibility(View.VISIBLE);
            holder.tv_apply.setText("待审核");
            holder.llApply.setBackgroundResource(R.drawable.gradient_yellow);
            holder.tvDay.setText(  model.getTermNum() + "(天)");
            holder.tvMoney.setText(model.getLoanNum() + "");
            holder.WXservice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                onWXClickListener.onApplyItemClick(position);
                }
            });
        } else {
            holder.tvDay.setText(model.getTermNum() + "(天)");
            holder.tvMoney.setText(model.getLoanNum() + "(元)");
            holder.llApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onApplyItemClick(position);
//                    APIManager.getInstance().getBorrowing(mContext, model.getLoanId(), model.getTermId(), token, model.getIdentifier(), new APIManager.APIManagerInterface.baseBlock() {
//
//                        @Override
//                        public void Success(Context context, JSONObject response) {
//
//
//
//
//                        }
//
//                        @Override
//                        public void Failure(Context context, JSONObject response) {
//
//                        }
//                    });

                }
            });
        }
        holder.tvName.setText(model.getAppName() + "");
        holder.tvDescribe.setText("老用户秒下款");
        Glide.with(mContext).load(Global.DEFAULT_IMAGE_HOST + model.getLogo() + "").into(holder.ivLogo);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class SuperLoanHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDescribe, tvDay, tvMoney, tvAstrictDayText, tvMoneyText, tvAstrictDay, tv_apply;
        LinearLayout llApply, llAstrictDay,llkong;
        ImageView ivLogo;
        Button WXservice;

        public SuperLoanHolder(View itemView) {
            super(itemView);

            WXservice = (Button) itemView.findViewById(R.id.btn_WXService);
            tv_apply = (TextView) itemView.findViewById(R.id.tv_apply);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvMoney = (TextView) itemView.findViewById(R.id.tvMoney);
            tvDescribe = (TextView) itemView.findViewById(R.id.tvDescribe);
            tvDay = (TextView) itemView.findViewById(R.id.tvDay);
            llApply = (LinearLayout) itemView.findViewById(R.id.llApply);
            llkong = (LinearLayout) itemView.findViewById(R.id.llkong);
            ivLogo = (ImageView) itemView.findViewById(R.id.ivLogo);
            tvAstrictDayText = (TextView) itemView.findViewById(R.id.tvAstrictDayText);
            tvMoneyText = (TextView) itemView.findViewById(R.id.tvMoneyText);
            tvAstrictDay = (TextView) itemView.findViewById(R.id.tvAstrictDay);
            llAstrictDay = (LinearLayout) itemView.findViewById(R.id.llAstrictDay);



        }
    }

    public void setonItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {
        void onApplyItemClick(int pos);

//        void onOneItemClick(View view);
    }


    public void setWXClickListener(onWXClickListener wxClickListener) {
        this.onWXClickListener = wxClickListener;
    }
    public interface onWXClickListener {
        void onApplyItemClick(int pos);

//        void onOneItemClick(View view);
    }
}
