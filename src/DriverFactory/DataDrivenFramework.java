package DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import CommomFunLibrary.ERP_Functions;
import Utilities.ExcelFileUtil;
public class DataDrivenFramework {
WebDriver driver;
// Creating reference object for function library

ERP_Functions erp=new ERP_Functions();

@BeforeTest
public void launchapp() throws Throwable
{
	String app=erp.lauchuerl("http://webapp.qedge.com");
	System.out.println(app);
	// Calling Login
	String login=erp.verifyLogin("admin","master");
	System.out.println(login);
}
@Test
public void suppliercreation() throws Throwable
{
	//to call excel util methods
	ExcelFileUtil xl=new ExcelFileUtil();
	//Count no of rows in a sheet
	int rc=xl.rowCount("supplier");
	//Count no of columns in a sheet
	int cc=xl.colCount("supplier");
	Reporter.log("no of rows are::"+rc+"  "+"no of column are::"+cc,true);
	for(int i=1;i<rc;i++)
	{
		String sname=xl.getdata("supplier", i, 0);
		String address=xl.getdata("supplier", i, 1);
		String city=xl.getdata("supplier", i, 2);
		String country=xl.getdata("supplier", i, 3);
		String cperson=xl.getdata("supplier", i, 4);
		String pnumber=xl.getdata("supplier", i, 5);
		String mail=xl.getdata("supplier", i, 6);
		String mnumber=xl.getdata("supplier", i, 7);
		String note=xl.getdata("supplier", i, 8);
		String result=erp.verifysupplier(sname, address, city, country, cperson, pnumber, mail, mnumber, note);
		xl.setCellData("supplier", i, 9, result);
	}
}
@AfterTest
public void logout() throws Throwable
{
	String logoutapp=erp.verifyLogout();
	System.out.println(logoutapp);
	
}
}
