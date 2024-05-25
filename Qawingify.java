package assignment_qawingify;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
public class Qawingify {
	
    WebDriver driver;
    
    @BeforeMethod
    public void setup() {
    	WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get("https://sakshingp.github.io/assignment/login.html");
        driver.manage().window().maximize();
    }
    
    @Test(enabled = true)
    public void login() throws InterruptedException {
        driver.findElement(By.id("username")).sendKeys("deepak5398patel@gmail.com");
        Thread.sleep(2500);
        driver.findElement(By.id("password")).sendKeys("Deepak@123#5398");
        Thread.sleep(2500);
        driver.findElement(By.xpath("//input[@type='checkbox']")).click();
        Thread.sleep(2500);
        driver.findElement(By.id("log-in")).click();
        Thread.sleep(2500);
    	
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        WebElement Amount_Header = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//th[@onclick='sortTable(4)']")));
        
        Amount_Header.click();
        Thread.sleep(2500);
        
        List<Double> amount = retrieveAmounts();
        boolean isSortedAscending = isSortedAscending(amount);
        System.out.println("Ascending values sorting check: " + isSortedAscending);
        Assert.assertTrue(isSortedAscending, "The amounts are not sorted in ascending order");
        Amount_Header.click();
        Thread.sleep(2500);
        
        amount = retrieveAmounts();
        boolean isSortedDescending = isSortedDescending(amount);
        System.out.println("Descending values sorting check: " + isSortedDescending);
        Assert.assertTrue(isSortedDescending, "The amounts are not sorted in descending order");
    }
    
    private List<Double> retrieveAmounts() {
        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='transactionsTable']/tbody/tr"));
        List<Double> amounts = new ArrayList<>();
        
        for (WebElement row : rows) {
            String amountText = row.findElements(By.tagName("td")).get(4).getText();
            double amount = Double.parseDouble(amountText.replace("+", "").replace("-", "").replace(" USD", "").replace(",", "").trim());
            if (amountText.contains("-")) {
                amount = -amount;
            }
            amounts.add(amount);
        }
        return amounts;
    }
    
    private boolean isSortedAscending(List<Double> amounts) {
        for (int i = 0; i < amounts.size() - 1; i++) {
            if (amounts.get(i) > amounts.get(i + 1)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isSortedDescending(List<Double> amounts) {
        for (int i = 0; i < amounts.size() - 1; i++) {
            if (amounts.get(i) < amounts.get(i + 1)) {
                return false;
            }
        }
        return true;
    }
    
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}