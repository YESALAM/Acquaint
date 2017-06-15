package io.github.yesalam.acquaint.Util;

import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RadioButton;

/**
 * Created by yesalam on 14-06-2017.
 */

public class HaveClickListener implements View.OnClickListener {
    View view;

    public HaveClickListener(View holder){this.view = holder;}

    @Override
    public void onClick(View v) {
        CheckBox button = (CheckBox) v ;
        if(button.isChecked()){
            view.setVisibility(View.VISIBLE);
        }else{
            view.setVisibility(View.GONE);
        }
    }
}
