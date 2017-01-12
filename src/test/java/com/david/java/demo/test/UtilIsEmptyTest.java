package com.david.java.demo.test;

import org.junit.Test;
import com.david.java.demo.lonsec.Util;
import static org.junit.Assert.*;


public class UtilIsEmptyTest {
	
	@Test
	public void testIsEmpty(){
		String input=null;
		assertEquals(true, Util.isEmpty(input));
		input="";
		assertEquals(true, Util.isEmpty(input));
		input="Hello";
		assertEquals(false, Util.isEmpty(input));
	}
	@Test
	public void testIsEmptyArgs(){
		String[] input=null;
		assertEquals(true, Util.isEmpty(input));
		String[] input2={""};
		assertEquals(true, Util.isEmpty(input2));
		String[] input3={"",""};
		assertEquals(true, Util.isEmpty(input3));
		String[] input4={null,""};
		assertEquals(true, Util.isEmpty(input4));
		String[] input5={"Hello",null};
		assertEquals(true, Util.isEmpty(input5));
		String[] input6={"Hello",""};
		assertEquals(true, Util.isEmpty(input6));
		String[] input7={"Hello","1"};
		assertEquals(false, Util.isEmpty(input7));
	}
	

	
	

}
