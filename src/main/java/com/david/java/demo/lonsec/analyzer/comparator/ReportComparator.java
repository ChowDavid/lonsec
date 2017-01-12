package com.david.java.demo.lonsec.analyzer.comparator;

import java.util.Comparator;

import com.david.java.demo.lonsec.analyzer.DetailEntity;

/**
 * Comparator help to sort our the date and rank relation,
 * Compare the date first then if date is same then compare the rank,
 * It is requirement of report output sort by return date desc and rank asc.
 * @author david
 *
 */
public class ReportComparator implements Comparator<DetailEntity> {

	@Override
	public int compare(DetailEntity o1, DetailEntity o2) {
		int result= o2.getReturnDate().compareTo(o1.getReturnDate());
		if (result==0){
			result=o1.getRank().compareTo(o2.getRank());
		}
		return result;
	}

}
