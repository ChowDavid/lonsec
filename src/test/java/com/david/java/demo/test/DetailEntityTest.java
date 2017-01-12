package com.david.java.demo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Test;

import com.david.java.demo.lonsec.analyzer.DetailEntity;


public class DetailEntityTest {
	

	
	@Test
	public void exceptionTest(){
		String fundCode = "TestCode";
		LocalDate date = LocalDate.of(2017, 01, 12);
		BigDecimal percent = new BigDecimal(12);
		DetailEntity record = new DetailEntity(fundCode,date,percent);
		try {
			record.toReportString();
			fail("AssertionError should expected");
		} catch (AssertionError e){
			assertEquals("Excess should provided", e.getMessage());
		}
		
	}
	
	@Test
	public void exceptionTest2(){
		String fundCode = "TestCode";
		LocalDate date = LocalDate.of(2017, 01, 12);
		BigDecimal percent = new BigDecimal(12);
		DetailEntity record = new DetailEntity(fundCode,date,percent);
		record.addFund("TestFundName", "BM-001");
		try {
			record.toReportString();
			fail("AssertionError should expected");
		} catch (AssertionError e){
			assertEquals("Excess should provided", e.getMessage());
		}

	}
	
	@Test
	public void exceptionTest3(){
		String fundCode = "TestCode";
		LocalDate date = LocalDate.of(2017, 01, 12);
		BigDecimal percent = new BigDecimal(12);
		DetailEntity record = new DetailEntity(fundCode,date,percent);
		record.addFund("TestFundName", "BM-001");
		record.setBenchmarkReturnPercent(BigDecimal.ONE);
		try {
			record.toReportString();
			fail("AssertionError should expected");
		} catch (AssertionError e){
			assertEquals("Excess should provided", e.getMessage());
		}

	}
	@Test
	public void exceptionTest4(){
		String fundCode = "TestCode";
		LocalDate date = LocalDate.of(2017, 01, 12);
		BigDecimal percent = new BigDecimal(12);
		DetailEntity record = new DetailEntity(fundCode,date,percent);
		record.addFund("TestFundName", "BM-001");
		record.setBenchmarkReturnPercent(BigDecimal.ONE);
		try {
			record.toReportString();
			fail("AssertionError should expected");
		} catch (AssertionError e){
			assertEquals("Excess should provided", e.getMessage());
		}

	}
	@Test
	public void exceptionTest5(){
		String fundCode = "TestCode";
		LocalDate date = LocalDate.of(2017, 01, 12);
		BigDecimal percent = new BigDecimal(12);
		DetailEntity record = new DetailEntity(fundCode,date,percent);
		record.addFund("TestFundName", "BM-001");
		record.setBenchmarkReturnPercent(BigDecimal.ONE);
		record.setExcess(BigDecimal.ONE);
		try {
			record.toReportString();
			fail("AssertionError should expected");
		} catch (AssertionError e){
			assertEquals("outPerformance should provided", e.getMessage());
		}

	}
	
	@Test
	public void exceptionTest6(){
		String fundCode = "TestCode";
		LocalDate date = LocalDate.of(2017, 01, 12);
		BigDecimal percent = new BigDecimal(12);
		DetailEntity record = new DetailEntity(fundCode,date,percent);
		record.addFund("TestFundName", "BM-001");
		record.setBenchmarkReturnPercent(BigDecimal.ONE);
		record.setExcess(BigDecimal.ONE);
		record.setOutPerformance("N/A");
		try {
			record.toReportString();
			fail("AssertionError should expected");
		} catch (AssertionError e){
			assertEquals("rank should provided", e.getMessage());
		}

	}
	@Test
	public void exceptionTest7(){
		String fundCode = "TestCode";
		LocalDate date = LocalDate.of(2017, 01, 12);
		BigDecimal percent = new BigDecimal(12);
		DetailEntity record = new DetailEntity(fundCode,date,percent);
		record.addFund("TestFundName", "BM-001");
		record.setExcess(BigDecimal.ONE);
		record.setOutPerformance("N/A");
		record.setRank(1);
		try {
			record.toReportString();
			fail("AssertionError should expected");
		} catch (AssertionError e){
			assertEquals("benchmarkReturnPercent should provided", e.getMessage());
		}

	}
	
	@Test
	public void exceptionTest8(){
		String fundCode = "TestCode";
		LocalDate date = LocalDate.of(2017, 01, 12);
		BigDecimal percent = new BigDecimal(12);
		DetailEntity record = new DetailEntity(fundCode,date,null);
		record.addFund("TestFundName", "BM-001");
		record.setExcess(BigDecimal.ONE);
		record.setOutPerformance("N/A");
		record.setBenchmarkReturnPercent(percent);
		record.setRank(1);
		try {
			record.toReportString();
			fail("AssertionError should expected");
		} catch (AssertionError e){
			assertEquals("fundReturnPercent should provided", e.getMessage());
		}

	}
	@Test
	public void exceptionTest9(){
		String fundCode = "TestCode";
		LocalDate date = LocalDate.of(2017, 01, 12);
		BigDecimal percent = new BigDecimal(12);
		DetailEntity record = new DetailEntity(fundCode,date,percent);
		record.addFund(null, "BM-001");
		record.setExcess(BigDecimal.ONE);
		record.setOutPerformance("N/A");
		record.setBenchmarkReturnPercent(percent);
		record.setRank(1);
		try {
			record.toReportString();
			fail("AssertionError should expected");
		} catch (AssertionError e){
			assertEquals("fullName should provided", e.getMessage());
		}

	}
	@Test
	public void exceptionTest10(){
		String fundCode = "TestCode";
		LocalDate date = LocalDate.of(2017, 01, 12);
		BigDecimal percent = new BigDecimal(12);
		DetailEntity record = new DetailEntity(fundCode,null,percent);
		record.addFund("TestFundName", "BM-001");
		record.setExcess(BigDecimal.ONE);
		record.setOutPerformance("N/A");
		record.setBenchmarkReturnPercent(percent);
		record.setRank(1);
		try {
			record.toReportString();
			fail("AssertionError should expected");
		} catch (AssertionError e){
			assertEquals("returnDate should provided", e.getMessage());
		}

	}
	
	@Test
	public void happyCase(){
		String fundCode = "TestCode";
		LocalDate date = LocalDate.of(2017, 01, 12);
		BigDecimal percent = new BigDecimal(12);
		DetailEntity record = new DetailEntity(fundCode,date,percent);
		record.addFund("TestFundName", "BM-001");
		record.setExcess(BigDecimal.ONE);
		record.setOutPerformance("N/A");
		record.setBenchmarkReturnPercent(percent);
		record.setRank(1);		
		assertEquals("TestFundName,12/01/2017,1.00,N/A,12.00,1"+System.getProperty("line.separator"),record.toReportString());
		

	}



}
