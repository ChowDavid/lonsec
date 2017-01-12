package com.david.java.demo.lonsec.analyzer.comparator;

import java.util.Comparator;

import com.david.java.demo.lonsec.analyzer.DetailEntity;

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
