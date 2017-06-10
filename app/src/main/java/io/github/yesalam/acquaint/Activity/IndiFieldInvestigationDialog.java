package io.github.yesalam.acquaint.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import io.github.yesalam.acquaint.R;

/**
 * Created by yesalam on 10-06-2017.
 */

public class IndiFieldInvestigationDialog extends Activity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_indie_field_investigation);




        setupHolderLayout();

    }

    public void setupHolderLayout(){
        View holder = findViewById(R.id.address_confimation_holder_linearlayout);
        ViewGroup parent = (ViewGroup) holder.getParent();
        int index = parent.indexOfChild(holder);
        parent.removeView(holder);
        holder = getLayoutInflater().inflate(R.layout.include_address_confirmed_resident,parent,false);
        parent.addView(holder,index);
    }
}
