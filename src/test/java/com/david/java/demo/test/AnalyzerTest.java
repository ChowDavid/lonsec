package com.david.java.demo.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Ignore;
import org.junit.Test;

import com.david.java.demo.lonsec.ConfigStore;
import com.david.java.demo.lonsec.Constant;
import com.david.java.demo.lonsec.SystemChecker;
import com.david.java.demo.lonsec.analyzer.Analyzer;
import com.david.java.demo.lonsec.exception.ConfigException;
import com.david.java.demo.lonsec.exception.FileReadingException;
import com.david.java.demo.lonsec.exception.IncompleteArgumentException;
import com.david.java.demo.lonsec.exception.IncorrectDataException;



public class AnalyzerTest {

	@Test
	public void smallSizeTest() throws ConfigException, IOException, FileReadingException, IncorrectDataException, IncompleteArgumentException{
		System.setProperty(Constant.CONFIG,"./src/test/resources/test1/config.prop");
		ConfigStore.reload();
		Analyzer analyzer=new Analyzer();
		analyzer.process();
		try (BufferedReader br=new BufferedReader(new FileReader(ConfigStore.getMonthlyOutperformanceFile().toFile()))){
			assertTrue(br.readLine().startsWith("FundName,Date,Excess,OutPerformance,Return,Rank"));
			assertTrue(br.readLine().startsWith("Vanguard Wholesale Australian Fixed Interest Index,30/11/2016,-0.01,,-1.47,1"));
			assertTrue(br.readLine().startsWith("Vanguard Wholesale Australian Fixed Interest Index,31/10/2016,-0.02,,-1.32,1"));
			assertTrue(br.readLine().startsWith("Vanguard Wholesale Australian Fixed Interest Index,30/09/2016,-0.01,,-0.23,1"));
			assertTrue(br.readLine().startsWith("Vanguard Wholesale Australian Fixed Interest Index,31/08/2016,0.00,,0.42,1"));
			assertTrue(br.readLine().startsWith("Vanguard Wholesale Australian Fixed Interest Index,31/07/2016,-0.02,,0.71,1"));
			assertTrue(br.readLine().startsWith("Vanguard Wholesale Australian Fixed Interest Index,30/06/2016,-0.02,,1.29,1"));
		}
	}
	@Test
	public void smallSizefundReturnSeriesExceptionTest() throws ConfigException, IOException, FileReadingException, IncorrectDataException, IncompleteArgumentException{
		System.setProperty(Constant.CONFIG,"./src/test/resources/test2/config.prop");
		ConfigStore.reload();
		SystemChecker checker = new SystemChecker();
		checker.isPassed();
		Analyzer analyzer=new Analyzer();
		try {
			analyzer.process();
			fail("smallSizefundReturnSeriesExceptionTest should has exception");
		} catch (IncorrectDataException e){
			assertEquals("Error during process file:[./src/test/resources/test2/FundReturnSeries.csv], line[8], message[fundCode and date cannot be duplicated [MF-1-10685,30/09/2016,-0.232219351]]", e.getMessage());
		}
	}
	@Test
	public void smallSizefundsExceptionTest() throws ConfigException, IOException, FileReadingException, IncorrectDataException, IncompleteArgumentException{
		System.setProperty(Constant.CONFIG,"./src/test/resources/test3/config.prop");
		ConfigStore.reload();
		SystemChecker checker = new SystemChecker();
		checker.isPassed();
		Analyzer analyzer=new Analyzer();
		try {
			analyzer.process();
			fail("smallSizefundsExceptionTest should has exception");
		} catch (IncorrectDataException e){
			assertEquals("Error during process file:[./src/test/resources/test3/fund.csv], line[3], message[fundCode cannot be duplicated [MF-1-10685,Vanguard Wholesale Australian Fixed Interest Index,BM-19]]", e.getMessage());
		}
	}
	@Test
	public void smallSizeBenchReturnSeriesExceptionTest() throws ConfigException, IOException, FileReadingException, IncorrectDataException, IncompleteArgumentException{
		System.setProperty(Constant.CONFIG,"./src/test/resources/test4/config.prop");
		ConfigStore.reload();
		SystemChecker checker = new SystemChecker();
		checker.isPassed();
		Analyzer analyzer=new Analyzer();
		try {
			analyzer.process();
			fail("smallSizeBenchReturnSeriesExceptionTest should has exception");
		} catch (IncorrectDataException e){
			assertEquals("Error during process file:[./src/test/resources/test4/BenchReturnSeries.csv], line[9], message[benchmarkCode and date cannot be duplicated [BM-18,30/11/2016,-1.460981831]]", e.getMessage());
		}
		
	}
	@Test
	public void smallSizeBenchReturnSeriesException2Test() throws ConfigException, IOException, FileReadingException, IncorrectDataException, IncompleteArgumentException{
		System.setProperty(Constant.CONFIG,"./src/test/resources/test5/config.prop");
		ConfigStore.reload();
		SystemChecker checker = new SystemChecker();
		checker.isPassed();
		Analyzer analyzer=new Analyzer();
		try {
			analyzer.process();
			fail("smallSizeBenchReturnSeriesException2Test should has exception");
		} catch (IncompleteArgumentException e){
			System.out.println(e.getMessage());
			assertEquals("return percent should not null fundCode[MF-1-10685], fund%[-1.470981688], date[2016-11-30], benchmarkCode[BM-18], benchmark%[null]", e.getMessage());
		}
		
	}
	@Test
	public void smallSizebenchmarkException2Test() throws ConfigException, IOException, FileReadingException, IncorrectDataException, IncompleteArgumentException{
		System.setProperty(Constant.CONFIG,"./src/test/resources/test6/config.prop");
		ConfigStore.reload();
		SystemChecker checker = new SystemChecker();
		checker.isPassed();
		Analyzer analyzer=new Analyzer();
		try {
			analyzer.process();
			fail("smallSizebenchmarkException2Test should has exception");
		} catch (IncorrectDataException e){
			assertEquals("Error during process file:[./src/test/resources/test6/benchmark.csv], line[3], message[benchmark cannot be duplicated [BM-18,Bloomberg AusBond Composite 0+ Year Index AUD]]", e.getMessage());
		}	
	}
	
	@Test
	public void smallSizeExcessTest() throws ConfigException, IOException, FileReadingException, IncorrectDataException, IncompleteArgumentException{
		System.setProperty(Constant.CONFIG,"./src/test/resources/test7/config.prop");
		ConfigStore.reload();
		Analyzer analyzer=new Analyzer();
		analyzer.process();
		try (BufferedReader br=new BufferedReader(new FileReader(ConfigStore.getMonthlyOutperformanceFile().toFile()))){
			assertTrue(br.readLine().startsWith("FundName,Date,Excess,OutPerformance,Return,Rank"));
			assertTrue(br.readLine().startsWith("Vanguard Wholesale Australian Fixed Interest Index,30/11/2016,-2.93,under performed,-1.47,1"));
			assertTrue(br.readLine().startsWith("Vanguard Wholesale Australian Fixed Interest Index,31/10/2016,-2.61,under performed,-1.32,1"));
			assertTrue(br.readLine().startsWith("Vanguard Wholesale Australian Fixed Interest Index,30/09/2016,-0.46,,-0.23,1"));
			assertTrue(br.readLine().startsWith("Vanguard Wholesale Australian Fixed Interest Index,31/08/2016,0.84,,0.42,1"));
			assertTrue(br.readLine().startsWith("Vanguard Wholesale Australian Fixed Interest Index,31/07/2016,1.44,out performed,0.71,1"));
			assertTrue(br.readLine().startsWith("Vanguard Wholesale Australian Fixed Interest Index,30/06/2016,2.60,out performed,1.29,1"));
			
			
		}
	}
	@Test
	public void smallSizeRankTest() throws ConfigException, IOException, FileReadingException, IncorrectDataException, IncompleteArgumentException{
		System.setProperty(Constant.CONFIG,"./src/test/resources/test8/config.prop");
		ConfigStore.reload();
		Analyzer analyzer=new Analyzer();
		analyzer.process();
		try (BufferedReader br=new BufferedReader(new FileReader(ConfigStore.getMonthlyOutperformanceFile().toFile()))){
			assertTrue(br.readLine().startsWith("FundName,Date,Excess,OutPerformance,Return,Rank"));
			assertTrue(br.readLine().startsWith("Vanguard Wholesale Australian Fixed Interest Index,30/06/2016,2.60,out performed,1.29,1"));
			assertTrue(br.readLine().startsWith("Vanguard Wholesale Emerging Markets Shares Index,30/06/2016,-0.59,,1.22,2"));
			assertTrue(br.readLine().startsWith("Vanguard Australian Shares Index ETF,30/06/2016,-4.35,under performed,-2.54,3"));
			assertTrue(br.readLine().startsWith("Vanguard All-World Ex-US Shares Index ETF,30/06/2016,-5.38,under performed,-3.58,4"));
			assertTrue(br.readLine().startsWith("Goldman Sachs Emerging Leaders Fund,30/06/2016,-6.04,under performed,-4.23,5"));
			assertTrue(br.readLine().startsWith("Pengana Global Small Companies Fund,30/06/2016,-6.59,under performed,-4.78,6"));

			
		}
	}
}
