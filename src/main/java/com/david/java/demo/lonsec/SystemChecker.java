package com.david.java.demo.lonsec;
import java.io.FileNotFoundException;
import static com.david.java.demo.lonsec.Constant.*;

import com.david.java.demo.lonsec.exception.ConfigException;

public class SystemChecker {

	public boolean isPassed() {
		try{
		System.out.println("Excess Rule:"+ConfigStore.getExcessRule());
		System.out.println("Out performanced label:"+ConfigStore.getOutPerformancedLabel());
		System.out.println("Under performanced label:"+ConfigStore.getUnderPerformancedLabel());
		System.out.println("Funds file:"+ConfigStore.getExistingFile(FUNDS_FILEPATH));
		System.out.println("Bench mark file:"+ConfigStore.getExistingFile(BENCHMARK_FILEPATH));
		System.out.println("Fund return series file:"+ConfigStore.getExistingFile(FUND_RETURN_SERIES_FILEPATH));
		System.out.println("Bench return series file:"+ConfigStore.getExistingFile(BENCH_RETURN_SERIES_FILEPATH));
		System.out.println("Output file name:"+ConfigStore.getMonthlyOutperformanceFile());
		
		return true;
		} catch (FileNotFoundException| ConfigException e){
			System.err.println("Self Check fail reason "+e.getMessage());
			return false;
		}
		
	}

	
}
