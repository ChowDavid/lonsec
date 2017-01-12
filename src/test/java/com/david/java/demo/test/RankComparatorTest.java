package com.david.java.demo.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.david.java.demo.lonsec.analyzer.DetailEntity;
import com.david.java.demo.lonsec.analyzer.comparator.RankComparator;

public class RankComparatorTest {
	
	@Test(expected=NullPointerException.class)
	public void rankExceptionTest(){
		Collections.sort(null,new RankComparator());
	}
	
	@Test
	public void rankEmptyListTest(){
		List<DetailEntity> lists=new ArrayList<>();
		Collections.sort(lists,new RankComparator());
	}
	
	@Test
	public void rankOneListTest(){
		List<DetailEntity> lists=new ArrayList<>();
		lists.add(new DetailEntity(null, null, null));
		Collections.sort(lists,new RankComparator());
	}
	
	@Test(expected=AssertionError.class)
	public void rankTwoListNullTest(){
		List<DetailEntity> lists=new ArrayList<>();
		lists.add(new DetailEntity("1", null, null));
		lists.add(new DetailEntity("2", null, null));
		Collections.sort(lists,new RankComparator());
	}
	
	@Test
	public void rankTwoListNormalTest(){
		List<DetailEntity> lists=new ArrayList<>();
		lists.add(new DetailEntity("1", null, BigDecimal.ONE));
		lists.add(new DetailEntity("2", null, BigDecimal.ONE));
		Collections.sort(lists,new RankComparator());
		
	}
	@Test
	public void rankTwoListNormal2Test(){
		List<DetailEntity> lists=new ArrayList<>();
		lists.add(new DetailEntity("1", null, BigDecimal.ONE));
		lists.add(new DetailEntity("2", null, BigDecimal.ZERO));
		Collections.sort(lists,new RankComparator());
		Assert.assertEquals("1", lists.get(0).getFundCode());
		Assert.assertEquals("2", lists.get(1).getFundCode());
		
		
	}
	@Test
	public void rankTwoListNormal3Test(){
		List<DetailEntity> lists=new ArrayList<>();
		lists.add(new DetailEntity("1", null, BigDecimal.ZERO));
		lists.add(new DetailEntity("2", null, BigDecimal.ONE));
		Collections.sort(lists,new RankComparator());
		Assert.assertEquals("2", lists.get(0).getFundCode());
		Assert.assertEquals("1", lists.get(1).getFundCode());
		
		
	}

}
