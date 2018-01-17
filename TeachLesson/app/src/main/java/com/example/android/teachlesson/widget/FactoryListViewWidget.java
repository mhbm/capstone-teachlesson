package com.example.android.teachlesson.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.teachlesson.R;

import java.util.ArrayList;


/**
 * Created by lsitec101.macedo on 17/01/18.
 */

public class FactoryListViewWidget implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<String> mPontuationList;
    private Context mContext;

    @Override
    public void onCreate() {
        //Not implemented yed
    }

    @Override
    public void onDestroy() {
        //Not implemented yed
    }

    public FactoryListViewWidget(Intent intent, Context mContext) {
        this.mPontuationList = intent.getStringArrayListExtra(UpdateServiceWidget.GET_LIST_INGREDIENTS);
        this.mContext = mContext;
    }

    @Override
    public void onDataSetChanged() {
        this.mPontuationList = MyAppWidgetProvider.mPontuationList;
    }

    @Override
    public int getCount() {
        if (mPontuationList != null) {
            return mPontuationList.size();
        } else {
            return 1;
        }
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public RemoteViews getViewAt(int i) {

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_pontuation_item);

        if (mPontuationList != null) {
            remoteViews.setTextViewText(R.id.tv_widget_pontuation_title, mPontuationList.get(i));
        } else {
            remoteViews.setTextViewText(R.id.tv_widget_pontuation_title, "None data to display!");
        }

        Intent intent = new Intent();
        remoteViews.setOnClickFillInIntent(R.id.tv_widget_pontuation_title, intent);
        return remoteViews;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

