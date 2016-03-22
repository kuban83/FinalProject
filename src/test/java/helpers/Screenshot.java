package helpers;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

/**
 * Created by Анна on 22.03.2016.
 */
public class Screenshot {
    private WebDriver driver;
    private String pathToDir;
    private int ScreenshotNumber =0;


    public Screenshot(WebDriver driver, String pathToDir) {
        this.driver = driver;
        this.pathToDir = pathToDir;
    }
    public void takeScreenShot () {
        File srcfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {

            FileUtils.copyFile(srcfile, new File(this.pathToDir + "screenshot_" + ScreenshotNumber + ".png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Screenshot taken");
        ScreenshotNumber++;
    }

}