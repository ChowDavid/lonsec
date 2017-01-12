package com.david.java.demo.lonsec.analyzer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.junit.Assert;

import com.david.java.demo.lonsec.Util;

public class DetailEntity {

	private String fundCode;
	private String fundName;
	private LocalDate returnDate;
	private BigDecimal fundReturnPercent;
	private String benchmarkCode;
	private String benchmarkName;
	private BigDecimal benchmarkReturnPercent;
	private BigDecimal excess;
	private Integer rank;
	private String outPerformance;



	public DetailEntity(String fundCode, LocalDate returnDate, BigDecimal fundReturnPercent) {
		super();
		this.fundCode = fundCode;
		this.returnDate = returnDate;
		this.fundReturnPercent = fundReturnPercent;
	}
	
	public void addFund(String fundName, String benchmarkCode){
		this.fundName=fundName;
		this.benchmarkCode=benchmarkCode;
	}

	public String getFundCode() {
		return fundCode;
	}


	public String getFundName() {
		return fundName;
	}

	

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public String getBenchmarkCode() {
		return benchmarkCode;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getOutPerformance() {
		return outPerformance;
	}

	public void setOutPerformance(String outPerformance) {
		this.outPerformance = outPerformance;
	}

	public BigDecimal getFundReturnPercent() {
		return fundReturnPercent;
	}

	public BigDecimal getBenchmarkReturnPercent() {
		return benchmarkReturnPercent;
	}

	public void setBenchmarkReturnPercent(BigDecimal benchmarkReturnPercent) {
		this.benchmarkReturnPercent = benchmarkReturnPercent;
	}

	public BigDecimal getExcess() {
		return excess;
	}

	public void setExcess(BigDecimal excess) {
		this.excess = excess;
	}
	
	

	public String getBenchmarkName() {
		return benchmarkName;
	}

	public void setBenchmarkName(String benchmarkName) {
		this.benchmarkName = benchmarkName;
	}

	@Override
	public String toString() {
		return "DetailEntity [rank=" + rank + ", returnDate=" + returnDate + ", fundReturnPercent=" + fundReturnPercent
				+ ", excess=" + excess + ", outPerformance=" + outPerformance + ", fundCode=" + fundCode + ", fundName="
				+ fundName + ", benchmarkCode=" + benchmarkCode + ", benchmarkReturnPercent=" + benchmarkReturnPercent
				+ "]";
	}
	
	/**
	 * convert the entity into Report output format.
	 * there are some fields does not allow null in order to execute this method, otherwise the implementation has some error
	 * @return
	 * @throws AssertionError, in case excess or fundReportPercent do not  provide
	 */
	public String toReportString(){
		Assert.assertNotNull("Excess should provided",excess);
		Assert.assertNotNull("outPerformance should provided",outPerformance);
		Assert.assertNotNull("benchmarkReturnPercent should provided",benchmarkReturnPercent);
		Assert.assertNotNull("fundReturnPercent should provided",fundReturnPercent);
		Assert.assertNotNull("rank should provided",rank);
		Assert.assertNotNull("fullName should provided",fundName);
		Assert.assertNotNull("returnDate should provided",returnDate);
		StringBuilder sb=new StringBuilder();
		sb.append(fundName).append(",");
		sb.append(Util.dateToString(returnDate)).append(",");
		sb.append(excess.setScale(2, RoundingMode.HALF_DOWN)).append(",");
		sb.append(outPerformance).append(",");
		sb.append(fundReturnPercent.setScale(2, RoundingMode.HALF_DOWN)).append(",");
		sb.append(rank).append(System.getProperty("line.separator"));
		return sb.toString();
	}






}
