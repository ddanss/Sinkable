package com.ddanss.sinkingbutton;

import android.app.Activity;
import android.os.Bundle;

import com.ddanss.sinkable.Sink;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Sink.MakeSinkable(findViewById(R.id.theButton));
        Sink.MakeSinkable(findViewById(R.id.theImageButton));
        Sink.MakeSinkable(findViewById(R.id.theView));
        Sink.MakeSinkable(findViewById(R.id.theLayout));
    }
}
