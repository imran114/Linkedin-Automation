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
        WebElement usernameORemail = null;
        WebElement password = null;
        // Set the path to chromedriver executable
        System.setProperty("webdriver.chrome.driver", "D:/Gitlab practice/test-project/chromedriver.exe");
        WebDriver driver = new ChromeDriver(); // Create a new ChromeDriver instance
        // In case you are using firefox browser, use below commented code and uncomment it
//        System.setProperty("webdriver.gecko.driver", "D:/Gitlab practice/test-project/geckodriver.exe");
//        WebDriver driver = new FirefoxDriver(); // Create a new FirefoxDriver instance
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Launch LinkedIn website
        driver.get("https://www.linkedin.com");

        System.out.println("LinkedIn launched....");
        Thread.sleep(3000); // Wait for 3 seconds
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        // Login to LinkedIn (replace 'username' and 'password' with actual credentials)
        usernameORemail = driver.findElement(By.xpath("//div[@class='flex flex-col']//input[contains(@id, 'email-or-phone')] | //input[contains(@autocomplete, 'username')]"));
        usernameORemail.sendKeys("username or email"); // Enter the username or email
        password = driver.findElement(By.xpath("//div[@class='flex flex-col']//input[contains(@autocomplete, 'new-password')] | //input[contains(@autocomplete, 'current-password')]"));
        password.sendKeys("password"); // Enter the password
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement signIn = driver.findElement(By.cssSelector("button[type='submit']")); // Find the Sign In button
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        signIn.click(); // Click on the Sign In button
        driver.manage().window().maximize(); // Maximize the browser window
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Search for a company
        Thread.sleep(3000); // Wait for 3 seconds
        WebElement searchInput = driver.findElement(By.cssSelector("input[role='combobox']"));
        searchInput.sendKeys("Techlogix"); // Enter the company name
        searchInput.sendKeys(Keys.RETURN); // Press Enter
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("Company name entered");

        // View page
        Thread.sleep(1000); // Wait for 1 second
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement viewPageButton = driver.findElement(By.xpath("//span[@class='image-text-lockup__text ']"));
        viewPageButton.click(); // Click on the View Page button
        System.out.println("View Page clicked...");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement postsTab = null;
        List<WebElement> navigationItems = driver.findElements(By.cssSelector(".org-page-navigation__item"));
        for (WebElement item : navigationItems) {
            WebElement anchorElement = item.findElement(By.cssSelector(".org-page-navigation__item-anchor"));
            if (anchorElement.getText().equals("Posts")) { // Check if the navigation item is the "Posts" tab
                postsTab = anchorElement;
                break;
            }
        }
        if (postsTab != null) {
            postsTab.click(); // Click on the "Posts" tab
            System.out.println("Posts tab clicked...");
        } else {
            System.out.println("Posts tab not found.");
        }

        boolean postFound;
        List<WebElement> postElements = driver.findElements(By.xpath("//div[contains(@class, 'scaffold-finite-scroll__content')]"));
        int previousSize = 0;


        postFound = false;

        while (!postFound) {
            for (int i = previousSize; i < postElements.size(); i++) {
                WebElement postElement = postElements.get(i);
                List<WebElement> spanElements = postElement.findElements(By.xpath(".//span[contains(@class, 'break-words')]"));

                for (WebElement spanElement : spanElements) {
                    String postText = spanElement.getText();


                    if (postText.contains("java") || postText.contains("Java") || postText.contains("JAVA")  ) {
                        System.out.println("Matching post found. . . . ");

                        // Save drop down click button.feed-shared-control-menu__trigger
                        List<WebElement> buttons = driver.findElements(By.xpath("//*[starts-with(@id, 'ember') and starts-with(@class, 'feed-shared-control-menu__trigger')]"));

                        if (buttons.size() >= 1) {
                            WebElement saveDropDown = buttons.get(0);
                            saveDropDown.click(); // Click on the Save dropdown button
                            System.out.println("Button clicked");

                            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.feed-shared-control-menu__content")));

                            // Find the first element in the dropdown
                            List<WebElement> dropdownItems = dropdown.findElements(By.cssSelector("*")); // Get all child elements
                            WebElement saveButton = null;

                            for (WebElement item : dropdownItems) {
                                if (item.getText().equals("Save") || item.getText() == "Save") { // Check if the item is the "Save" button
                                    saveButton = item;
                                    saveButton.click(); // Click on the "Save" button
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
                    actions.sendKeys(Keys.PAGE_DOWN).perform(); // Scroll down
                    Thread.sleep(1000); // Wait for 1 second
                    postElements = driver.findElements(By.xpath("//div[contains(@class, 'scaffold-finite-scroll__content')]"));
                }
            }
        }
    }
}
