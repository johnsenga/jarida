
package com.example.jarida;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

public class NewspaperDetailsActivity extends SherlockActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //.actionBarBackground(R.drawable.ab_background)
        FadingActionBarHelper helper = new FadingActionBarHelper()
            .actionBarBackground(R.drawable.ab_solid_jaridatheme)
            .headerLayout(R.layout.header)
            .contentLayout(R.layout.newspaperdetails)
            .headerOverlayLayout(R.layout.header_overlay);
        setContentView(helper.createView(this));
        helper.initActionBar(this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getSupportMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }
}
