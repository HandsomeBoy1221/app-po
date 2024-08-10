package com.ceshiren.page;

import com.ceshiren.entity.Member;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class SearchResultPage extends AppBasePage{
    public SearchResultPage(AndroidDriver androidDriver) {
        super(androidDriver);
    }

    public Member member_Result(){
        String text = text(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.TextView[@text=\"联系人\"]/following-sibling::*/android.widget.LinearLayout/android.widget.TextView"));
        member.setName(text);
        return member;
    }
    public ConcatPage toConcatPage(){
        click(By.xpath("//android.widget.ImageView"));
        click(By.xpath("//android.widget.TextView"));
        return new ConcatPage(androidDriver);
    }
}
