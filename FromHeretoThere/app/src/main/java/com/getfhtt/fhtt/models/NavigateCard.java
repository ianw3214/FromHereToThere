package com.getfhtt.fhtt.models;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getfhtt.fhtt.R;

/**
 * Created by Angelo on 2/4/2017.
 */

public class NavigateCard extends CardView{
    private TextView mTitleView;
    private TextView mMessageView;
    private TextView mCostView;
    private TextView mEmissionView;
    private ImageView mStar1, mStar2;
    private View mRoot;
    public static final int ANIM_DURATION = 200;

    public NavigateCard(Context context) {
        super(context, null, 0);
        initialize(context, null, 0);
    }

    public NavigateCard(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        initialize(context, attrs, 0);
    }

    public NavigateCard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context, attrs, defStyle);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyle) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        mRoot = inflater.inflate(R.layout.card_navigate, this, true);
        mTitleView = (TextView) mRoot.findViewById(R.id.tvCardTitle);
        mMessageView = (TextView) mRoot.findViewById(R.id.tvCardInfo);
        mEmissionView = (TextView) mRoot.findViewById(R.id.tvCardEmission);
        mCostView = (TextView) mRoot.findViewById(R.id.tvCardCost);
        mStar1 = (ImageView) mRoot.findViewById(R.id.ivLeaf1) ;
        mStar2 = (ImageView) mRoot.findViewById(R.id.ivLeaf2) ;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NavigateCard, defStyle, 0);
        String title = a.getString(R.styleable.NavigateCard_fc_title);
        setTitle(title);
        String emission = a.getString(R.styleable.NavigateCard_fc_emission);
        setEmission(emission);
        String text = a.getString(R.styleable.NavigateCard_fc_text);
        String cost = a.getString(R.styleable.NavigateCard_fc_cost);
        setCost(cost);
        setText(text);

        int stars = a.getInteger(R.styleable.NavigateCard_fc_stars,0);
        setStars(stars);

        int dpValue = 16; // margin in dips
        float d = this.getResources().getDisplayMetrics().density;
        int margin = (int) (dpValue * d);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(margin, 0, margin, margin);

        setLayoutParams(params);
        setRadius(getResources().getDimensionPixelSize(R.dimen.card_corner_radius));
        setClickable(true);

        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);

        setForeground(ContextCompat.getDrawable(context, outValue.resourceId));
        setCardElevation(getResources().getDimensionPixelSize(R.dimen.card_elevation));
        setPreventCornerOverlap(false);
    }


    /**
     * Use sparingly.
     */
    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            mTitleView.setVisibility(View.GONE);
        } else {
            mTitleView.setVisibility(View.VISIBLE);
            mTitleView.setText(title);
        }
    }

    public void setEmission(String emission) {
        if (TextUtils.isEmpty(emission)) {
            mEmissionView.setVisibility(View.GONE);
        } else {
            mEmissionView.setVisibility(View.VISIBLE);
            mEmissionView.setText(emission);
        }
    }

    public void setStars(int stars){
        if(stars>0){
            if (stars == 1){
                mStar1.setVisibility(VISIBLE);
            }else{
                mStar1.setVisibility(VISIBLE);
                mStar2.setVisibility(VISIBLE);
            }
        }
    }

    public void setCost(String cost) {
        if (TextUtils.isEmpty(cost)) {
            mCostView.setText("FREE");
        } else {
            mCostView.setText(cost);
        }
    }
    public void setText(String text) {
        if (TextUtils.isEmpty(text)) {
            mMessageView.setVisibility(View.GONE);
        } else {
            mMessageView.setVisibility(View.VISIBLE);
            mMessageView.setText(text);
        }
    }

    public void dismiss() {
        dismiss(false);
    }

    public void dismiss(boolean animate) {
        if (!animate) {
            setVisibility(View.GONE);
        } else {
            animate().scaleY(0.1f).alpha(0.1f).setDuration(ANIM_DURATION);
        }
    }

    public void show() {
        setVisibility(View.VISIBLE);
    }
}
