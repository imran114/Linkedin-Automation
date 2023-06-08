package org.imran.java;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LinkedInAutomation {
    public static void main(String[] args) throws InterruptedException {
        WebElement email = null;
        WebElement password = null;
        // Set the path to chromedriver executable
        System.setProperty("webdriver.chrome.driver", "D:/Gitlab practice/test-project/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
//        System.setProperty("webdriver.gecko.driver", "D:/Gitlab practice/test-project/geckodriver.exe");
//        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Launch LinkedIn website
        driver.get("https://www.linkedin.com");

        System.out.println("LinkedIn launched....");
 Thread.sleep(3000);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        // Login to LinkedIn (replace 'username' and 'password' with actual credentials) // //*[@id="session_key"]
            email = driver.findElement(By.xpath("//div[@class='flex flex-col']//input[contains(@id, 'email-or-phone')] | //input[contains(@autocomplete, 'username')]"));
        email.sendKeys("imranullah114@gmail.com");
        password = driver.findElement(By.xpath("//div[@class='flex flex-col']//input[contains(@autocomplete, 'new-password')] | //input[contains(@autocomplete, 'current-password')]")); //*[@id="session_password"]
        password.sendKeys("Imran@ust1");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement signIn = driver.findElement(By.cssSelector("button[type='submit']")); //   //button[starts-with(@class, 'btn-md btn-primary flex-shrink-0')]
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        signIn.click();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        WebElement verifyButton = driver.findElement(By.xpath("//button[@id='home_children_button']"));
//        if (verifyButton.isDisplayed()) {
//            verifyButton.click();
//        } else {
        // Search for a company
        Thread.sleep(3000);
        WebElement searchInput = driver.findElement(By.cssSelector("input[role='combobox']"));
        searchInput.sendKeys("Techlogix");
        searchInput.sendKeys(Keys.RETURN);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("Company name entered");
        // View page
        Thread.sleep(1000);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement viewPageButton = driver.findElement(By.xpath("//span[@class='image-text-lockup__text ']"));
        viewPageButton.click();
        System.out.println("View Page clicked...");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement postsTab = null;
        List<WebElement> navigationItems = driver.findElements(By.cssSelector(".org-page-navigation__item"));
        for (WebElement item : navigationItems) {
            WebElement anchorElement = item.findElement(By.cssSelector(".org-page-navigation__item-anchor"));
            if (anchorElement.getText().equals("Posts")) {
                postsTab = anchorElement;
                break;
            }
        }
        if (postsTab != null) {
            postsTab.click();
            System.out.println("Posts tab clicked...");
        } else {
            System.out.println("Posts tab not found.");
        }

        // ...

        boolean postFound;
        List<WebElement> postElements = driver.findElements(By.xpath("//div[contains(@class, 'scaffold-finite-scroll__content')]"));
        int previousSize = 0;

// ...

        postFound = false;

        while (!postFound) {
            for (int i = previousSize; i < postElements.size(); i++) {
                WebElement postElement = postElements.get(i);
                List<WebElement> spanElements = postElement.findElements(By.xpath(".//span[contains(@class, 'break-words')]"));

                for (WebElement spanElement : spanElements) {
                    String postText = spanElement.getText();

                    // ...

                    if (postText.contains("java") || postText.contains("Java") || postText.contains("JAVA")  ) {
                        System.out.println("Matching post found. . . . ");

                        // Save drop down click    button.feed-shared-control-menu__trigger
                        List<WebElement> buttons = driver.findElements(By.xpath("//*[starts-with(@id, 'ember') and starts-with(@class, 'feed-shared-control-menu__trigger')]"));

                        if (buttons.size() >= 1) {
                            WebElement saveDropDown = buttons.get(0);
                            saveDropDown.click();
                            System.out.println("Button clicked");

                            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.feed-shared-control-menu__content")));

                            // Find the first element in the dropdown
                            List<WebElement> dropdownItems = dropdown.findElements(By.cssSelector("*")); // Get all child elements
                            WebElement saveButton = null;

                            for (WebElement item : dropdownItems) {
                                if (item.getText().equals("Save") || item.getText() == "Save") {
                                    saveButton = item;
                                    saveButton.click();
                                    System.out.println("Save Button clicked");
                                    break;
                                }
                            }

                        } else {
                            System.out.println("Button not found");
                        }

                        postFound = true;
                        break;
                    }

                }

                if (!postFound) {
                    Actions actions = new Actions(driver);
                    actions.sendKeys(Keys.PAGE_DOWN).perform();
                    Thread.sleep(1000);
                    postElements = driver.findElements(By.xpath("//div[contains(@class, 'scaffold-finite-scroll__content')]"));
                }
            }


        }


    }
}