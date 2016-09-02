package com.example.sofina.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.example.sofina.myapplication.drawable.LoadingDrawable;
import com.example.sofina.myapplication.render.WaterDropRender;

/**
 * Created by sofina on 2016/8/29.
 */
public class DropActivity extends Activity {

    private LoadingDrawable mLoadingDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        addContentView(linearLayout, layoutParams);

        LinearLayout.LayoutParams waterDropParams = layoutParams;
        final View waterDrop = new View(this);
        mLoadingDrawable = new LoadingDrawable(new WaterDropRender(this));
        waterDrop.setBackgroundDrawable(mLoadingDrawable);

        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button start = new Button(this);
        start.setText("开始");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadingDrawable.start();
            }
        });

        linearLayout.addView(start, btnParams);
        linearLayout.addView(waterDrop, waterDropParams);
    }
}
