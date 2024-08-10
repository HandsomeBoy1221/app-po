package com.ceshiren.page;

import com.ceshiren.entity.Member;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class SearchPage extends AppBasePage {
    public SearchPage(AndroidDriver androidDriver) {
        super(androidDriver);
    }

    public SearchResultPage send_Member(String name){
        send(By.xpath("//android.widget.EditText[@text=\"搜索\"]"),name);
        return new SearchResultPage(androidDriver);

    }

}
