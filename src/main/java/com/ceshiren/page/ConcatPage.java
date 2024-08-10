package com.ceshiren.page;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class ConcatPage extends AppBasePage {
    public ConcatPage(AndroidDriver androidDriver) {
        super(androidDriver);
    }

    public AddMemberPage toAddMemberPage(){
        find(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"添加成员\"))"));
        click(AppiumBy.cssSelector("[text=\"添加成员\"]"));
        waitUtil().until(webDriver -> webDriver.getPageSource().contains("手动输入添加"));
        return new AddMemberPage(androidDriver);
    }

    public SearchPage toSearchPage(){
        click(By.xpath("//*[@text=\"红茶移动\"]/../../../../android.widget.LinearLayout[3]/android.widget.RelativeLayout"));
        waitUtil().until(webDriver -> webDriver.getPageSource().contains("搜索"));
        return new SearchPage(androidDriver);
    }
}
