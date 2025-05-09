package pageobjects;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UpcomingPageObjects {

    private WebDriver driver;
    private WebDriverWait wait;
    private final Random random = new Random();
    private final Set<String> previouslySelectedTexts = new HashSet<>();

    private By upcomingEventsLocator = By.cssSelector("[data-test-id='bpEvent']");
    private String childElementLocatorPattern = "[data-test-id^='Odd-']"; 

    public UpcomingPageObjects(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }

    public WebElement selectUniqueRandomEvent() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(upcomingEventsLocator));
        List<WebElement> allEvents = driver.findElements(upcomingEventsLocator);
        List<WebElement> uniqueEvents = new ArrayList<>();

        for (WebElement event : allEvents) {
            // Skip events that have a locked child
            if (!event.findElements(By.className("event-selection_locked")).isEmpty()) {
                continue;
            }

            // Use text to check uniqueness
            String eventText = event.getText().trim();

            if (!previouslySelectedTexts.contains(eventText)) {
                uniqueEvents.add(event);
            }
        }

        if (uniqueEvents.isEmpty()) {
            throw new NoSuchElementException("No more unique, unlocked events available.");
        }

        // Select a random one and mark it as used
        WebElement randomEvent = uniqueEvents.get(random.nextInt(10));
        previouslySelectedTexts.add(randomEvent.getText().trim());

        return randomEvent;
    }

    public WebElement selectRandomOdd() {
        WebElement event = selectUniqueRandomEvent();

        List<WebElement> odds = event.findElements(By.cssSelector(childElementLocatorPattern));
        WebElement randomOdd = odds.get(random.nextInt(odds.size()));
         
        return randomOdd;
    }

    public void clickRandomOdd(){
        selectRandomOdd().click();
    }

    public void clickRandomOdds(int numberOfOdds) {
        for (int i = 0; i < numberOfOdds; i++) {
            clickRandomOdd();
        }
    }

}
