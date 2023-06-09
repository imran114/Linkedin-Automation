package org.imran.java;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class LinkedInAutomation2 {
    public static void main(String[] args) throws InterruptedException {
        WebElement email = null;
        WebElement password = null;
        // Set the path to chromedriver executable
        System.setProperty("webdriver.chrome.driver", "D:/Gitlab practice/test-project/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Launch LinkedIn website
        driver.get("https://www.linkedin.com");

        System.out.println("LinkedIn launched....");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        // Login to LinkedIn (replace 'username' and 'password' with actual credentials)
        email = driver.findElement(By.xpath("//div[@class='flex flex-col']//input[contains(@id, 'email-or-phone')] | //input[contains(@autocomplete, 'username')]"));
        email.sendKeys("imranullah114@gmail.com");
        password = driver.findElement(By.xpath("//div[@class='flex flex-col']//input[contains(@autocomplete, 'new-password')] | //input[contains(@autocomplete, 'current-password')]"));
        password.sendKeys("Imran@ust1");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement signIn = driver.findElement(By.cssSelector("button[type='submit']"));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        signIn.click();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Scroll until the matching text is found
        boolean postFound = false;
//        String targetText = "java"; // Modify the target text as needed

        while (!postFound) {
            List<WebElement> postElements = driver.findElements(By.xpath("//div[contains(@class, 'scaffold-finite-scroll__content')]"));

            for (WebElement postElement : postElements) {
                List<WebElement> spanElements = postElement.findElements(By.xpath(".//span[contains(@class, 'break-words')]"));

                for (WebElement spanElement : spanElements) {
                    String postText = spanElement.getText();

                    if (postText.toLowerCase().contains("hiring java") || postText.toLowerCase().contains("java developer")) {
                        System.out.println("Matching post found: " + postText);
                        postFound = true;
                        break;
                    }
                }

                if (postFound) {
                    break;
                }
            }

            if (!postFound) {
                Actions actions = new Actions(driver);
                actions.sendKeys(Keys.PAGE_DOWN).perform();
                Thread.sleep(1000);
            }
        }

//        driver.quit();
    }
}
