package com.ceshiren.page;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class AddMemberPage extends AppBasePage {
    public AddMemberPage(AndroidDriver androidDriver) {
        super(androidDriver);
    }

    public AddMemberPage click_AddMember(){
        click(By.cssSelector("[text=\"手动输入添加\"]"));
        return this;
    }

    public AddMemberPage send_addMember(String name, String phone){
        send(By.xpath("//*[@text=\"必填\"]"),name);
        send(By.xpath("//*[@text=\"选填\"]"),phone);
        click(By.xpath("//*[@text=\"保存\"]"));
        waitUtil().until(webDriver -> webDriver.getPageSource().contains("Toast"));
        String toastText = text(AppiumBy.xpath("//*[@class=\"android.widget.Toast\"]"),false);
        member.setToast(toastText);
        waitUtil().until(webDriver -> webDriver.getPageSource().contains("手动输入添加"));
        return this;
    }

    public ConcatPage toConcatPage(){
        back();
        return new ConcatPage(androidDriver);
    }
}
