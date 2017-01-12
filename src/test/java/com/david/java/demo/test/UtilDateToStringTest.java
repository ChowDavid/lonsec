package com.david.java.demo.test;

import java.time.LocalDate;

import org.junit.Test;
import static org.junit.Assert.*;
import com.david.java.demo.lonsec.Util;

public class UtilDateToStringTest {
	
	@Test(expected=NullPointerException.class)
	public void EmptyTest(){
		Util.dateToString(null);
	}
	
	@Test
	public void happyCase(){
		LocalDate date=LocalDate.of(2017, 01, 30);
		assertEquals("30/01/2017",Util.dateToString(date));
	}
	
	

}
