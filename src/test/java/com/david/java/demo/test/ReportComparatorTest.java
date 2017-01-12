package com.david.java.demo.test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.david.java.demo.lonsec.analyzer.DetailEntity;
import com.david.java.demo.lonsec.analyzer.comparator.RankComparator;
import com.david.java.demo.lonsec.analyzer.comparator.ReportComparator;

public class ReportComparatorTest {

	@Test(expected=NullPointerException.class)
	public void reportExceptionTest(){
		Collections.sort(null,new ReportComparator());
	}
	
	
	
	@Test
	public void reportEmptyListTest(){
		List<DetailEntity> lists=new ArrayList<>();
		Collections.sort(lists,new ReportComparator());
	}
	
	@Test
	public void reportOneListTest(){
		List<DetailEntity> lists=new ArrayList<>();
		lists.add(new DetailEntity(null, null, null));
		Collections.sort(lists,new ReportComparator());
	}
	
	@Test(expected=NullPointerException.class)
	public void reportTwoListNullTest(){
		List<DetailEntity> lists=new ArrayList<>();
		lists.add(new DetailEntity("1", null, null));
		lists.add(new DetailEntity("2", null, null));
		Collections.sort(lists,new ReportComparator());
	}
	
	@Test
	public void reportTwoListNormalTest(){
		List<DetailEntity> lists=new ArrayList<>();
		DetailEntity first=new DetailEntity("1", LocalDate.of(2016, 01, 16), BigDecimal.ONE);
		first.setRank(1);
		
		DetailEntity second=new DetailEntity("1", LocalDate.of(2016, 01, 16), BigDecimal.ONE);
		second.setRank(2);
		
		lists.add(first);
		lists.add(second);
		Collections.sort(lists,new ReportComparator());
		
	}
	@Test
	public void reportTwoListNormal2Test(){
		List<DetailEntity> lists=new ArrayList<>();
		DetailEntity first=new DetailEntity("1", LocalDate.of(2016, 01, 16), BigDecimal.ONE);
		first.setRank(1);
		
		DetailEntity second=new DetailEntity("2", LocalDate.of(2016, 01, 16), BigDecimal.ONE);
		second.setRank(2);
		
		lists.add(first);
		lists.add(second);
		Collections.sort(lists,new ReportComparator());
		Assert.assertEquals("1", lists.get(0).getFundCode());
		Assert.assertEquals("2", lists.get(1).getFundCode());
		
		
	}
	@Test
	public void reportTwoListNormal3Test(){
		List<DetailEntity> lists=new ArrayList<>();
		DetailEntity first=new DetailEntity("1", LocalDate.of(2015, 01, 16), BigDecimal.ONE);
		first.setRank(1);
		
		DetailEntity second=new DetailEntity("2", LocalDate.of(2016, 01, 16), BigDecimal.ONE);
		second.setRank(1);
		
		lists.add(first);
		lists.add(second);
		Collections.sort(lists,new ReportComparator());
		Assert.assertEquals("2", lists.get(0).getFundCode());
		Assert.assertEquals("1", lists.get(1).getFundCode());
		
		
	}
	
	@Test
	public void reportTwoListNormal4Test(){
		List<DetailEntity> lists=new ArrayList<>();
		DetailEntity first=new DetailEntity("1", LocalDate.of(2015, 01, 16), BigDecimal.ONE);
		first.setRank(1);
		
		DetailEntity second=new DetailEntity("2", LocalDate.of(2016, 01, 16), BigDecimal.ONE);
		second.setRank(2);
		
		lists.add(first);
		lists.add(second);
		Collections.sort(lists,new ReportComparator());
		Assert.assertEquals("2", lists.get(0).getFundCode());
		Assert.assertEquals("1", lists.get(1).getFundCode());
		
		
	}
}
