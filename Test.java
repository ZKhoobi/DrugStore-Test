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
        baseUrl = "http://localhost/DrugStore-Code-master";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    public void tearDown() throws Exception {
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
        //...............first we test true user
        driver.get(baseUrl + "/index.php");
        driver.findElement(By.id("myBtn")).click();
        driver.findElement(By.id("usrname")).sendKeys("niosha");
        driver.findElement(By.id("psw")).sendKeys("123");
        driver.findElement(By.id("lgin")).click();
        driver.get(baseUrl + "/index.php");

        //System.out.println("111"+driver.findElement(By.id("error")).getCssValue("visibility"));
        //System.out.println("222"+driver.findElement(By.id("successful")).getCssValue("visibility"));

        if(driver.findElement(By.id("error")).getCssValue("visibility").equals("visible"))//if it cant understand out user
        {
            //System.out.println("333"+driver.findElement(By.id("error")).getCssValue("visibility"));
            return false;
        }
        //................second we examine false user
        driver.get(baseUrl + "/PL/shop/product/productsArayeshi.php?categ=4");
        driver.findElement(By.id("myBtn")).click();
        driver.findElement(By.id("usrname")).sendKeys("khoobi");
        driver.findElement(By.id("psw")).sendKeys("123");
        driver.findElement(By.id("lgin")).click();
        driver.get(baseUrl + "/index.php");
        if(!driver.findElement(By.id("error")).getCssValue("visibility").equals("visible"))//if it allow to false user to log in
        {
            //System.out.println("444"+driver.findElement(By.id("error")).getCssValue("visibility"));
            return false;
        }
        return true;
    }
    public boolean searchTest() throws Exception{
        return true;
    }

    public boolean addProductTest() throws Exception{
        //try {
            driver.get(baseUrl + "/PL/shop/product/productsArayeshi.php?categ=1");
            driver.findElement(By.name("quantity")).sendKeys("4");
            driver.findElement(By.name("cart")).click();
        //}

        return true;
    }
    public boolean seeCartListTest() throws Exception{
        return true;
    }

    ///...............................main method
    public static void main(String[] args) throws Exception {
        Test t = new Test();
        t.setUp();

        if(!t.logInTest())
            System.out.print("ERROR:log in is incorrect\n");
        if(!t.searchTest())
            System.out.print("ERROR:search is incorrect\n");
        if(!t.addProductTest())
            System.out.print("ERROR:add product is incorrect\n");
        if(!t.seeCartListTest())
            System.out.print("ERROR:see cart list is incorrect\n");

        t.tearDown();
    }


}
