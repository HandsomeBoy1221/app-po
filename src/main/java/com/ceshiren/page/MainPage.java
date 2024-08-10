package com.ceshiren.page;

import org.openqa.selenium.By;

public class MainPage extends AppBasePage {
    public MainPage() {}

    public ConcatPage toConcatPage(){
        click(By.xpath("//*[@text=\"通讯录\"]"));
        return new ConcatPage(androidDriver);
    }
}
