package Tools;

import okio.Timeout;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageUtils {

    public static List<String> extractUrls(String text)
    {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?!\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find())
        {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return containedUrls;
    }

    public static void selectPlayer(String dropdown, String url) {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Luna\\Downloads\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        Select player = new Select(driver.findElement(By.className("playerselect2 select_servers")));
        player.selectByVisibleText("Fembed Server");
    }

    public static String getBrowserSource(String url) throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("load-extension=C:\\Users\\Luna\\AppData\\Local\\Google\\Chrome Beta\\User Data\\Default\\Extensions\\gighmmpiobklfepjocnamgkkbiglidom\\4.24.1_0");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        System.setProperty("webdriver.gecko.driver","C:\\Users\\Luna\\Downloads\\geckodriver-v0.28.0-win64\\geckodriver.exe");
        WebDriver driver = new ChromeDriver(capabilities);
        driver.get(url);
        String source;
        try {
            Select player = new Select(driver.findElement(By.id("select_servers")));
            player.selectByVisibleText("Fembed Server");
        }catch (Exception e) {
            e.printStackTrace();
        }
        source = driver.getPageSource();
        driver.quit();
        return source;
    }

}
