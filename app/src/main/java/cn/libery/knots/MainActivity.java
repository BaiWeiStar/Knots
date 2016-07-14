package cn.libery.knots;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final BottomNavigationBar navigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        navigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Nearby").setActiveColorResource(R.color
                        .colorAccent))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Find").setActiveColorResource(R.color
                        .colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Categories").setActiveColorResource(R.color
                        .colorPrimaryDark))
                .setFirstSelectedPosition(0)
                .initialise();
    }
}
