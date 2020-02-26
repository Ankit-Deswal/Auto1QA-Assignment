package base;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseTest {
	public static WebDriver driver;
	public static Properties prop;
	
public BaseTest(){
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "\\src\\main\\java\\resources\\auto1.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void initialization(){
		String browserName = prop.getProperty("browser");
		if(browserName.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");	
			 ChromeOptions options = new ChromeOptions();
	    	 options.addArguments("--disable-extensions");
	    	 options.setExperimentalOption("useAutomationExtension", false);
			driver = new ChromeDriver(options); 
		}
		else if(browserName.equals("FireFox")){
			System.setProperty("webdriver.gecko.driver", "/Users/Documents/SeleniumServer/geckodriver");	
			driver = new FirefoxDriver(); 
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		//driver.manage().timeouts().pageLoadTimeout(2, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));
	}
}
