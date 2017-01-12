package com.david.java.demo.lonsec.analyzer.comparator;

import java.util.Comparator;

import org.junit.Assert;

import com.david.java.demo.lonsec.analyzer.DetailEntity;

/**
 * Case on the Fund Return Percent to order, Largest on top and Less on bottom.
 * @author david
 *
 */
public class RankComparator implements Comparator<DetailEntity> {

	@Override
	public int compare(DetailEntity o1, DetailEntity o2) {
		Assert.assertNotNull(o1.getFundReturnPercent());
		Assert.assertNotNull(o2.getFundReturnPercent());
		return o2.getFundReturnPercent().compareTo(o1.getFundReturnPercent());
	}

}
