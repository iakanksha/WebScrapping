package demo;

import demo.wrapperAction.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.google.common.collect.ArrayListMultimap;

import io.github.bonigarcia.wdm.WebDriverManager;


public class testcase {

    WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Setup WebDriverManager for Chrome
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        
    }

    @BeforeMethod 
    public void openUrl(){
        driver.get("https://www.scrapethissite.com/pages/");
    }

    @AfterClass
    public void tearDown(){
        driver.close();
        driver.quit();
    }

    @Test(description = "Scrape Hockey Team Details for the teams with win% <40%")
    public void scrapeHockeyTeamData(){

    int numberOfPages=4;
    Double targetWinPercent = 0.40;
    String hockeyTeamPage = "//a[@href='/pages/forms/']";

    String outputPath = "src/test/resources/hockey-team-data.json";

        //click on “Hockey Teams: Forms, Searching and Pagination”
        driver.findElement(By.xpath(hockeyTeamPage)).click();

        ArrayList<HashMap<String, String>> teamData = new ArrayList<HashMap<String, String>>();

        //columns foe extractRowData0
        //String[] columns = {"name", "year","pct text-success","pct text-danger"};

        String[] columns = {"Team name", "Year","Wins","Losses","Ot losses","WinPercentage"};

        //iterate through pages
        for(int i=1; i<=numberOfPages; i++){
            //get table rows
            List<WebElement> rows = wrapperAction.getTableRows(driver);

            //extract data from rows
            for(int rowIndex =1; rowIndex < rows.size(); rowIndex++){
                WebElement row = rows.get(rowIndex);
                HashMap<String, String> rowData = wrapperAction.extractRowData(driver, row, columns);

                //check if win% is less than traget win% andd to list
                Double winPercent = Double.parseDouble(rowData.get("WinPercentage"));
                if(winPercent<targetWinPercent){
                    teamData.add(rowData);
                }
            }

            //click on next page
            driver.findElement(By.xpath("//a[@href='/pages/forms/?page_num="+(i+1)+"']")).click();      
        }

        //bind java objects to json file
        try{
            jsonConverter.convertToJson(teamData, outputPath);
        }catch(Exception e){
            e.printStackTrace();
        }

        File outputFile = new File(outputPath);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(outputFile.exists(), "Output file does not exist");
        softAssert.assertTrue(outputFile.length() > 0, "Output file is empty");
        softAssert.assertAll();
    }     


    @Test (description = "Scrape top 5 films for each year and the best picture winner")
    public void scrapeWinningFilmsData(){

        String outputPath = "src/test/resources/oscar-winning-movies-data.json";
        String oscarWinMoviesPage = "//a[@href='/pages/ajax-javascript/']";
        int topMovies = 5;

        //click on the oscar winning movies page
        driver.findElement(By.xpath(oscarWinMoviesPage)).click();

        ArrayList<HashMap<String, String>> topFiveMoviesData = new ArrayList<HashMap<String, String>>();
        List<WebElement> years = driver.findElements(By.xpath("//a[@href='#']"));
        String[] columns = {"Title", "Nominations","Awards"};
        
        
        //iterate through years
        for(WebElement year : years){
            String yearText = year.getText();
            year.click();
            
            int topMovieCount = 0;
            
            List<WebElement> rows = wrapperAction.getTableRows(driver);
            //iterate through rows
            for(int rowIndex =0; rowIndex< rows.size(); rowIndex++){
                WebElement row = rows.get(rowIndex);

                HashMap<String, String> movieInfo = wrapperAction.extractRowData(driver, row, columns);
                movieInfo.put("Year", yearText);

                Boolean isWinner = wrapperAction.isFlagPresent(driver, row);
                movieInfo.put("isWinner", String.valueOf(isWinner));

                topFiveMoviesData.add(movieInfo);

                topMovieCount++;
                if(topMovieCount == topMovies){
                    break;
                }
            }
        }

        try{
            jsonConverter.convertToJson(topFiveMoviesData, outputPath);
        }catch(Exception e){
            e.printStackTrace();
        }

        File outputFile = new File(outputPath);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(outputFile.exists(), "Output file does not exist");
        softAssert.assertTrue(outputFile.length() > 0, "Output file is empty");
        softAssert.assertAll();
    }
}        