package cn.ytxu.androidbackflow.sample.normal.request_activity_count.letter;

import android.os.Bundle;

import cn.ytxu.androidbackflow.sample.normal.request_activity_count.base.BaseACLetterActivity;

public class ACLetterOActivity extends BaseACLetterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRollbackFlow(ACLetterIActivity.class);
    }

}
