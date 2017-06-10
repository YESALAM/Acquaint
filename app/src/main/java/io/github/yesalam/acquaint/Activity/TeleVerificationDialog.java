package io.github.yesalam.acquaint.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import io.github.yesalam.acquaint.R;

/**
 * Created by yesalam on 10-06-2017.
 */

public class TeleVerificationDialog extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_televerification);
    }
}
