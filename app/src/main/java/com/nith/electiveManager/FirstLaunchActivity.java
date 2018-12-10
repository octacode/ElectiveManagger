package com.nith.electiveManager;

import android.content.Intent;
import android.os.Bundle;

import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughActivity;
import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughCard;

import java.util.ArrayList;
import java.util.List;

public class FirstLaunchActivity extends FancyWalkthroughActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiInterface apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        if (!ApiClient.isFirstLaunch()) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            List<FancyWalkthroughCard> pages = new ArrayList<>();
            pages.add(getCard("Elective Manager", "An automated approach for elective allocation", R.drawable.election));
            pages.add(getCard("Student Interface", "Select the electives within a minute", R.drawable.student));
            pages.add(getCard("Choose wisely", "Your selection order decides your priority", R.drawable.university));
            pages.add(getCard("Made by", "Akash Sood (15518); Akshat Sharma (15567); Kumar Shashwat (15554); Sarthak Awasthi (15505);", R.drawable.classroom));
            pages.add(getCard("All set", "", R.drawable.checked));

            setImageBackground(R.drawable.bg_people);
            setFinishButtonTitle("Get Started");
            setOnboardPages(pages);
        }
    }

    public FancyWalkthroughCard getCard(String title, String desc, int icon) {
        FancyWalkthroughCard fancywalkthroughCard1 = new FancyWalkthroughCard(title, desc, icon);
        fancywalkthroughCard1.setBackgroundColor(R.color.white);
        fancywalkthroughCard1.setTitleColor(R.color.black);
        fancywalkthroughCard1.setDescriptionColor(R.color.black);
        return fancywalkthroughCard1;
    }

    @Override
    public void onFinishButtonPressed() {
        ApiClient.setFirstLaunch(false);
        finish();
        startActivity(new Intent(FirstLaunchActivity.this, MainActivity.class));
    }
}
