package com.example.android.teachlesson.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.teachlesson.R;
import com.example.android.teachlesson.ui.MainActivity;

import java.util.ArrayList;

/**
 * Created by lsitec101.macedo on 17/01/18.
 */

public class MyAppWidgetProvider extends AppWidgetProvider {

    public static ArrayList<String> mPontuationList;

    public static void updateWidgetApp(Context context, AppWidgetManager appWidgetManager, int appWidgetId, ArrayList<String> pontuationList) {

        // Construct the RemoteViews object
        RemoteViews widgets = new RemoteViews(context.getPackageName(), R.layout.widget_material_pontuation);

        Intent intentRecipe = new Intent(context, MainActivity.class);


        PendingIntent pendingIntent = PendingIntent.getActivity(context, 10, intentRecipe, PendingIntent.FLAG_UPDATE_CURRENT);

        widgets.setPendingIntentTemplate(R.id.lv_widget_app, pendingIntent);


        Intent widgetServiceIntent = new Intent(context, MyAppWidgetService.class);

        widgetServiceIntent.putStringArrayListExtra(UpdateServiceWidget.GET_LIST_INGREDIENTS, pontuationList);

        widgets.setRemoteAdapter(R.id.lv_widget_app, widgetServiceIntent);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, widgets);
    }

    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, ArrayList<String> ingredientList) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            updateWidgetApp(context, appWidgetManager, appWidgetIds[i], ingredientList);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, MyAppWidgetProvider.class));

        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {

            mPontuationList = intent.getStringArrayListExtra(UpdateServiceWidget.GET_LIST_INGREDIENTS);
        }
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_widget_app);

        updateWidget(context, appWidgetManager, appWidgetIds, mPontuationList);

        super.onReceive(context, intent);

    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}

