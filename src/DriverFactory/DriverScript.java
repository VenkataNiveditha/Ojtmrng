package DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import CommomFunLibrary.FunctionLibrary;
import Utilities.ExcelFileUtil;
import Utilities.ScreenShot;
public class DriverScript {
WebDriver driver;
ExtentReports report;
ExtentTest test;
@Test
//create method	
public void startTest()throws Throwable
{
//creating reference object for excel util methods	
ExcelFileUtil excel=new ExcelFileUtil();
//iterating all row in MasterTestCases sheet
for(int i=1;i<=excel.rowCount("MasterTestCases");i++)
{
	String ModuleStatus="";
if(excel.getdata("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
{
	//store module name into TCModule 
	String TCModule=excel.getdata("MasterTestCases", i, 1);
	report=new ExtentReports("./Reports/"+TCModule+FunctionLibrary.generateDate()+".html");
	//iterate all rows in TCModule sheet
	for(int j=1;j<=excel.rowCount(TCModule);j++)
	{
		test=report.startTest(TCModule);
		//read all columns in TCModule testcase	
	String Description=excel.getdata(TCModule, j, 0);
	String Object_Type=excel.getdata(TCModule, j, 1);
	String Locator_Type=excel.getdata(TCModule, j, 2);
	String Locator_Value=excel.getdata(TCModule, j, 3);
	String Test_Data=excel.getdata(TCModule, j, 4);
	//calling methods from function library
	try
	{
	if(Object_Type.equalsIgnoreCase("startBrowser"))
	{
	driver=FunctionLibrary.startBrowser(driver);
	System.out.println("Executing startbrowser");
	test.log(LogStatus.INFO, Description);
	}
	else if(Object_Type.equalsIgnoreCase("openApplication"))
	{
FunctionLibrary.openApplication(driver);	
System.out.println("Executing openApplication");
test.log(LogStatus.INFO, Description);
	}
	else if(Object_Type.equalsIgnoreCase("waitForElement"))
	{
FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
System.out.println("Executing waitForElement");
test.log(LogStatus.INFO, Description);
	}
	else if(Object_Type.equalsIgnoreCase("typeAction"))
	{
FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);		
System.out.println("Executing typeAction");
test.log(LogStatus.INFO, Description);
	}
	else if(Object_Type.equalsIgnoreCase("clickAction"))
	{
FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
System.out.println("Executing clickAction");
test.log(LogStatus.INFO, Description);
	}
	else if(Object_Type.equalsIgnoreCase("closeBrowser"))
	{
FunctionLibrary.closeBrowser(driver);
System.out.println("Executing closeBrowser");
test.log(LogStatus.INFO, Description);
	}
	else if(Object_Type.equalsIgnoreCase("captureData"))
	{
		FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
	test.log(LogStatus.INFO, Description);
	}
	else if(Object_Type.equalsIgnoreCase("tableValidation"))
	{
		FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
	test.log(LogStatus.INFO, Description);
	}
	else if(Object_Type.equalsIgnoreCase("stockCategories"))
	{
		FunctionLibrary.stockCategories(driver);
		test.log(LogStatus.INFO,Description);
	}
	else if(Object_Type.equalsIgnoreCase("tableValidation1"))
	{
		FunctionLibrary.tableValidation1(driver, Test_Data);
		test.log(LogStatus.INFO, Description);
	}
	//write as pass into status column
		excel.setCellData(TCModule, j, 5, "PASS");
		ModuleStatus="true";
		test.log(LogStatus.PASS, Description);
		ScreenShot.Takescreen(driver, Description);
	}catch(Exception e)
	{
	excel.setCellData(TCModule, j, 5, "FAIL");
	ModuleStatus="false";
	test.log(LogStatus.FAIL, Description);
	ScreenShot.Takescreen(driver, Description);
	System.out.println(e.getMessage());
	break;	
	}
if(ModuleStatus.equalsIgnoreCase("TRUE"))
{
excel.setCellData("MasterTestCases", i, 3, "pass");	
}
else if(ModuleStatus.equalsIgnoreCase("FALSE"))
	
{
excel.setCellData("MasterTestCases", i, 3, "Fail");	
}
report.flush();
report.endTest(test);
}

}
else 
{
//write as not executed in status column for flag N
excel.setCellData("MasterTestCases", i, 3, "Not Executed");	
}
}

}
}
