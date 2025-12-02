package automationpractice;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UploadExcel {

	WebDriver driver;

	@BeforeClass
	public void openbrowser() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.rediff.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}

	@BeforeMethod
	public void signin() {
		WebElement signin = driver.findElement(By.xpath("//a[text() = \"Sign in\"]"));
		signin.click();
	}

	@DataProvider(name = "dp")
	public Object[][] data() throws IOException {

	    FileInputStream fis = new FileInputStream("D:\\Eclipse Upload Excel\\Book1.xlsx");
	    XSSFWorkbook book = new XSSFWorkbook(fis);
	    Sheet sheet = book.getSheetAt(0);

	    int rows = sheet.getPhysicalNumberOfRows();
	    int cells = sheet.getRow(0).getPhysicalNumberOfCells();

	    Object[][] data = new Object[rows - 1][cells];

	    for (int i = 1; i < rows; i++) {
	        Row row = sheet.getRow(i);

	        for (int j = 0; j < cells; j++) {
	            Cell cell = row.getCell(j);

	            switch (cell.getCellType()) {
	                case STRING:
	                    data[i - 1][j] = cell.getStringCellValue();
	                    break;
	                case NUMERIC:
	                    data[i - 1][j] = cell.getNumericCellValue();
	                    break;
	                default:
	                    data[i - 1][j] = "";
	            }
	        }
	    }

	    return data;
	}

	
	@Test(dataProvider = "dp")
	public void Login(String USername,String Password) {
		driver.findElement(By.xpath("//div/input[@type=\"text\"]")).sendKeys(USername);
		driver.findElement(By.xpath("//div/input[@type=\"password\"]")).sendKeys(Password);
	}
}
