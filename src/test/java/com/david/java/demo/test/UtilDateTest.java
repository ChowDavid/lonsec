package com.david.java.demo.test;

import static org.junit.Assert.assertEquals;
import java.time.DateTimeException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.david.java.demo.lonsec.Util;

public class UtilDateTest {
		
	@Test(expected=NullPointerException.class)
	public void testGetDateException(){
		Util.getDate(null, null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testGetDateException2(){
		Util.getDate("2017/01/12", null);
	}
	@Test(expected=NullPointerException.class)
	public void testGetDateException3(){
		Util.getDate("", null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testGetDateException4(){
		List<DateTimeFormatter> formatters=new ArrayList<>();
		Util.getDate(null, formatters);
	}
	
	@Test(expected=DateTimeException.class)
	public void testGetDateException5(){
		List<DateTimeFormatter> formatters=new ArrayList<>();
		Util.getDate("2017/01/12", formatters);
	}
	@Test(expected=NullPointerException.class)
	public void testGetDateException6(){
		List<DateTimeFormatter> formatters=new ArrayList<>();
		Util.getDate("", formatters);
	}
	
	@Test(expected=NullPointerException.class)
	public void testGetDateException7(){
		List<DateTimeFormatter> formatters=new ArrayList<>();
		Util.getDate(null, formatters);
	}
	
	@Test(expected=DateTimeException.class)
	public void testGetDateException8(){
		List<DateTimeFormatter> formatters=new ArrayList<>();
		Util.getDate("2017/01/12", formatters);
	}
	@Test(expected=NullPointerException.class)
	public void testGetDateException9(){
		List<DateTimeFormatter> formatters=new ArrayList<>();
		Util.getDate("", formatters);
	}
	
	@Test(expected=NullPointerException.class)
	public void testGetDateException10(){
		List<DateTimeFormatter> formatters=new ArrayList<>();
		formatters.add(null);
		Util.getDate(null, formatters);
	}
	
	@Test(expected=NullPointerException.class)
	public void testGetDateException11(){
		List<DateTimeFormatter> formatters=new ArrayList<>();
		formatters.add(null);
		Util.getDate("2017/01/12", formatters);
	}
	@Test(expected=NullPointerException.class)
	public void testGetDateException12(){
		List<DateTimeFormatter> formatters=new ArrayList<>();
		formatters.add(null);
		Util.getDate("", formatters);
	}
	
	@Test(expected=DateTimeException.class)
	public void testGetDateException13(){
		LocalDate expectedDate=LocalDate.of(2017, 01, 12);
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy/MM/dd");
		List<DateTimeFormatter> formatters=new ArrayList<>();
		formatters.add(formatter);
		assertEquals(expectedDate.toString(), Util.getDate("2017-01-12", formatters).toString());
	}
	
	@Test
	public void testGetDate(){
		LocalDate expectedDate=LocalDate.of(2017, 01, 12);
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy/MM/dd");
		List<DateTimeFormatter> formatters=new ArrayList<>();
		formatters.add(formatter);
		assertEquals(expectedDate.toString(), Util.getDate("2017/01/12", formatters).toString());
	}
	
	@Test
	public void testGetDate2(){
		LocalDate expectedDate=LocalDate.of(2017, 01, 12);
		List<DateTimeFormatter> formatters=new ArrayList<>();
		formatters.add(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		formatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		assertEquals(expectedDate.toString(), Util.getDate("2017/01/12", formatters).toString());
	}
	
	@Test
	public void testGetDate3(){
		LocalDate expectedDate=LocalDate.of(2017, 01, 12);
		List<DateTimeFormatter> formatters=new ArrayList<>();
		formatters.add(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		formatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		assertEquals(expectedDate.toString(), Util.getDate("2017-01-12", formatters).toString());
	}
	
	

}
