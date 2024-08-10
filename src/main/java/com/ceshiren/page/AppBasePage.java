/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package com.ceshiren.page;
import com.ceshiren.entity.Member;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.app.SupportsAutoGrantPermissionsOption;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

//关键字对应的page页面
public class AppBasePage {
    static final Logger logger = getLogger(lookup().lookupClass());

    AndroidDriver androidDriver;
    static Member member = new Member();

    public AppBasePage(AndroidDriver androidDriver) {
        this.androidDriver = androidDriver;
    }

    public AppBasePage() {
        //driver需要声明
        if (null == androidDriver){
            deletedir();
            mkdir();
            //appium server
            CommandLine commandLine = new CommandLine("appium");
            commandLine.addArgument("-g");
            commandLine.addArgument("appiumserver.log");
            commandLine.addArgument("--port");
            commandLine.addArgument("4723");

            DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();
            DefaultExecutor executor = new DefaultExecutor();
            executor.setExitValue(1);
            try {
                executor.execute(commandLine, handler);
            } catch (IOException e) {
                e.printStackTrace();
            }

            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            //平台名称 安卓系统就是Android 苹果手机就是iOS
            desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            //使用的driver uiautomator2
            desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
            //设备的系统版本
            desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13");
            //启动的app的包名
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.tencent.wework");
            //启动的app的页面
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".launch.LaunchSplashActivity");
            //设备名称
            desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "5cdbdc95");
            //设备的UDID；adb devices -l 获取，多设备的时候要指定，若不指定默认选择列表的第一个设备
            desiredCapabilities.setCapability(MobileCapabilityType.UDID, "5cdbdc95");
            //app不重置
            desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
            //运行失败的时候打印page source到appium-log
            desiredCapabilities.setCapability(MobileCapabilityType.PRINT_PAGE_SOURCE_ON_FIND_FAILURE, true);
            //在假设客户端退出并结束会话之前，Appium 将等待来自客户端的新命令多长时间（以秒为单位） http请求等待响应最长5分钟
            desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300000);
            //默认权限通过
            desiredCapabilities.setCapability(SupportsAutoGrantPermissionsOption.AUTO_GRANT_PERMISSIONS_OPTION, true);
            try {
                //1、打开app操作
                androidDriver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"),desiredCapabilities);
                //隐式等待
                androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void deletedir() {
        try {
            File file = Paths.get("png").toFile();
            if(file.exists() && file.isDirectory()){
                FileUtils.deleteDirectory(file);
                logger.info(" png 文件夹删除");
            }else {
                logger.info(" png 文件夹不存在");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void mkdir() {
        File file = Paths.get("png").toFile();
        if(!file.exists() || !file.isDirectory()){
            file.mkdirs();
            logger.info("创建 png 文件夹");
        }else {
            logger.info(" png 文件夹已经存在");

        }
    }
    @Step("元素定位：{by}")
    public WebElement find(By by){
        return find(by,true);
    }
    @Step("元素定位：{by}")
    public WebElement find(By by,Boolean flag){
        WebElement element = androidDriver.findElement(by);
        logger.info("元素定位：{}",by);
        if(flag){
            try {
                ElementScreenBase(element, by.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return element;
    }
    public List<WebElement> finds(By by){
        return androidDriver.findElements(by);

    }

    public void click(By by){
        find(by).click();
    }
    public void click(By by, int index){
        finds(by).get(index).click();
    }
    public WebDriverWait waitUtil(){
        WebDriverWait wait = new WebDriverWait(androidDriver,
                Duration.ofSeconds(20), //每隔多少秒去查找一次显示等待的条件
                Duration.ofSeconds(1));//总共查找等待条件的时间
        return wait;
    }

    //输入操作
    public void send(By by, String str){
        WebElement webElement = find(by);
        webElement.clear();
        webElement.sendKeys(str);
    }
    public void send(By by, int index, String str){
        WebElement webElement = finds(by).get(index);
        webElement.clear();
        webElement.sendKeys(str);
    }
    //返回 安卓手机的滑动返回
    public void back(){
        Dimension dimension = androidDriver.manage().window().getSize();
        //x 0 0.5
        Point startPoint = new Point((int) (dimension.width * 0), (int) (dimension.height * 0.5));
        //x 0.9 0.5
        Point endPoint = new Point((int) (dimension.width * 0.9), (int) (dimension.height * 0.5));
        swipe(startPoint, endPoint);

    }
    public void swipe(Point startPoint, Point endPoint){

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence dragNDrop = new Sequence(finger, 1)
                //手指移动到起始坐标点
                .addAction(finger.createPointerMove(Duration.ofMillis(0),
                        PointerInput.Origin.viewport(), startPoint.x, startPoint.y))
                //手指按下
                .addAction(finger.createPointerDown(0))
                //滑动到第二个坐标点 滑动时间是1秒
                .addAction(finger.createPointerMove(Duration.ofMillis(1000),
                        PointerInput.Origin.viewport(),endPoint.x, endPoint.y))
                //手指释放
                .addAction(finger.createPointerUp(0));
        androidDriver.perform(Arrays.asList(dragNDrop));

    }

    //getText
    public String text(By by, boolean flag){
       return find(by,flag).getText();
    }
    public String text(By by){
        return find(by).getText();
    }
    public String page(){
        return androidDriver.getPageSource();
    }


    private void ElementScreenBase(WebElement element, String message) throws IOException {
        File screenshot = ((TakesScreenshot) androidDriver).getScreenshotAs(OutputType.FILE);
        org.openqa.selenium.Point elementLocation = element.getLocation();
        org.openqa.selenium.Dimension elementSize = element.getSize();
        int eleX = elementLocation.x;
        int eleY = elementLocation.y;
        int eleH = elementSize.height;
        int eleW = elementSize.width;
        logger.info("location=${} size=${} x=${} y=${} width=${} height=${}",
                elementLocation,elementSize,eleX,eleY,eleW,
                eleH);
        //读取截图文件
        BufferedImage img = ImageIO.read(screenshot);
        //创建一个 Graphics2D，可用于绘制到此 BufferedImage 中
        Graphics2D graph = img.createGraphics();
        //BasicStroke 指定线宽的实心  描边 Shape 的 Stroke 对象
        graph.setStroke(new BasicStroke(5));
        graph.setColor(Color.RED);//绘制形状的颜色
        graph.drawRect(eleX, eleY, eleW, eleH);//绘制指定矩形的轮廓
        graph.dispose();//处理此图形上下文并释放它正在使用的任何系统资源
        Path pngPath = getPngPath();
        ImageIO.write(img, "png", pngPath.toFile());
        Allure.addAttachment(message,"image/png",new FileInputStream(pngPath.toFile()),".png");
    }

    private Path getPngPath() {
        long l = System.currentTimeMillis();
        return Paths.get("png",l+".png");

    }

    public void quit(){
        androidDriver.quit();
    }
}
