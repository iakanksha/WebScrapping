package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.HashMap;
import java.util.List;
import java.time.Instant;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import org.openqa.selenium.NoSuchElementException;

public class wrapperAction {

    public static HashMap<String, String> extractRowData0(WebDriver driver, WebElement row, String[] columns){
        HashMap<String, String> teamInfo = new HashMap<>();

        teamInfo.put("epochTime", String.valueOf(Instant.now().toEpochMilli()));
        teamInfo.put("teamName", row.findElement(By.xpath("//td[@class='"+columns[0]+"']")).getText().trim());
        
        teamInfo.put("year", row.findElement(By.xpath("//td[@class='"+columns[1]+"']")).getText().trim());
        teamInfo.put("WinPercentage", row.findElement(By.xpath("//td[@class='"+columns[2]+"' or @class='"+columns[3]+"']")).getText().trim());
        
        return teamInfo;
    }
    

    public static HashMap<String, String> extractRowData(WebDriver driver, WebElement row, String[] columnNames){
        HashMap<String, String> rowData = new HashMap<>();
        List<WebElement> cells = row.findElements(By.xpath(".//td"));
        
        try{
            for (int i = 0; i < columnNames.length; i++) {
                String value = cells.get(i).getText().trim();
                rowData.put("epochTime", String.valueOf(Instant.now().toEpochMilli()));
                rowData.put(columnNames[i],value);
            }
        }catch(Exception e){
            System.out.println("Error when extracting data for the row" +row.getText());
            e.printStackTrace();
        }
        return rowData;
    }

    public static List<WebElement> getTableRows(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table//tbody//tr")));
    }

    public static Boolean isFlagPresent(WebDriver driver, WebElement row){
        try{
            row.findElement(By.xpath(".//td//i[@class='glyphicon glyphicon-flag']"));
               return true; 
        }catch(NoSuchElementException e){
            return false;
        }  
    }
}
    

