import java.util.concurrent.TimeUnit;
import static org.junit.Assert.fail;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Test {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private final StringBuffer verificationErrors = new StringBuffer();

    ///..............................basic functions
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8080/DrugStore-master";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    public void tearDown() throws Exception{
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

    ///...............................test methods
    public boolean logInTest() throws Exception {

        //............................examine false user
        driver.get(baseUrl + "/index.php");
        driver.findElement(By.id("myBtn")).click();
        driver.findElement(By.id("usrname")).sendKeys("khoobi");
        driver.findElement(By.id("psw")).sendKeys("123");
        driver.findElement(By.id("lgin")).click();
        driver.get(baseUrl + "/index.php");
        if(!driver.findElement(By.id("error")).getCssValue("visibility").equals("visible"))//if it allow to false user to log in
        {
            return false;
        }

        //..............................test true user
        driver.get(baseUrl + "/index.php");
        driver.findElement(By.id("myBtn")).click();
        driver.findElement(By.id("usrname")).sendKeys("niousha");
        driver.findElement(By.id("psw")).sendKeys("123");
        driver.findElement(By.id("lgin")).click();
        driver.get(baseUrl + "/index.php");
        if(driver.findElement(By.id("error")).getCssValue("visibility").equals("visible"))//if it cant understand out user
        {
            return false;
        }

        return true;
    }

    public boolean addProductTest() throws Exception{
        driver.get(baseUrl + "/PL/shop/product/productsArayeshi.php?categ=1");
        driver.findElement(By.name("quantity")).sendKeys("4");
        System.out.println("1");
        driver.findElement(By.name("cart")).click();
        System.out.println("2");
        driver.findElement(By.id("cartIcon")).click();
        System.out.println("3");
        return true;
    }
    public boolean seeCartListTest() throws Exception{System.out.println("4");
        return driver.findElement(By.id("quantity")).getText().equals("4");

    }

    ///...............................main method
    public static void main(String[] args) throws Exception {
        Test t = new Test();
        t.setUp();

        if(!t.logInTest())
            System.out.print("ERROR:log in is incorrect\n");
        if(!t.addProductTest())
            System.out.print("ERROR:add product is incorrect\n");
        if(!t.seeCartListTest())
            System.out.print("ERROR:see cart list is incorrect\n");

        System.out.println("succes!!!!");

        t.tearDown();
    }


}
