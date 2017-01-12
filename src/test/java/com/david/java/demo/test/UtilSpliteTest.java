package com.david.java.demo.test;

import com.david.java.demo.lonsec.Util;
import com.david.java.demo.lonsec.exception.FileReadingException;


import static org.junit.Assert.*;

import org.junit.Test;

public class UtilSpliteTest {
	
	@Test(expected=FileReadingException.class)
	public void testException() throws FileReadingException{
		Util.splite(null, 0, null, 0);
	}
	
	@Test(expected=FileReadingException.class)
	public void testException2() throws FileReadingException{
		Util.splite(null, 0, "1,2,3", 0);
	}
	
	@Test
	public void testHappyCase() throws FileReadingException{
		String[] expected={"1","2","3"};
		String[] actual=Util.splite(null, 0, "1,2,3", 3);
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void testHappyCase2() throws FileReadingException{
		String[] expected={"1","2","3"};
		String[] actual=Util.splite(null, 0, " 1 , 2 , 3 ", 3);
		assertArrayEquals(expected, actual);
	}

}
