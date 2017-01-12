package com.david.java.demo.lonsec.analyzer;

import static com.david.java.demo.lonsec.Constant.*;
import static com.david.java.demo.lonsec.Constant.FUNDS_FILEPATH;
import static com.david.java.demo.lonsec.Constant.FUND_RETURN_SERIES_FILEPATH;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.david.java.demo.lonsec.ConfigStore;
import com.david.java.demo.lonsec.Util;
import com.david.java.demo.lonsec.analyzer.comparator.RankComparator;
import com.david.java.demo.lonsec.analyzer.comparator.ReportComparator;
import com.david.java.demo.lonsec.exception.ConfigException;
import com.david.java.demo.lonsec.exception.DuplicateRecordException;
import com.david.java.demo.lonsec.exception.FileReadingException;
import com.david.java.demo.lonsec.exception.IncompleteArgumentException;
import com.david.java.demo.lonsec.exception.IncorrectDataException;
/**
 * Main program here
 * @author david
 *
 */
public class Analyzer {

	private Map<String, List<DetailEntity>> fundCodeMap;
	private Map<LocalDate, List<DetailEntity>> dateMap;
	private Map<String, List<DetailEntity>> benchmarkCodeMap;
	private List<DateTimeFormatter> dateFormatters;
	private List<DetailEntity> allRecords;

	public Analyzer() {
		fundCodeMap = new HashMap<>();
		dateMap = new HashMap<>();
		benchmarkCodeMap = new HashMap<>();
		dateFormatters = new ArrayList<>();
		for (String pattern : ConfigStore.getDateFormat().split(",")) {
			dateFormatters.add(DateTimeFormatter.ofPattern(pattern));
		}
		allRecords = new ArrayList<>();
	}

	/**
	 * follow the step to process. the only public method
	 * @throws ConfigException
	 * @throws IOException
	 * @throws FileReadingException
	 * @throws IncorrectDataException
	 * @throws IncompleteArgumentException
	 */
	public void process() throws ConfigException, IOException, FileReadingException, IncorrectDataException,
			IncompleteArgumentException {
		loadFundReturnSeries();
		loadFunds();
		loadBenchmark();
		loadBenchmarkReturnSeries();
		fillExcessAndPerformance();
		fillRankGroupByDate();
		exportToReport();

	}


	/**
	 * load the beanchmark file into the entity
	 * @throws IOException
	 * @throws IncorrectDataException
	 */
	private void loadBenchmark() throws IOException, IncorrectDataException {
		Set<String> primaryKey = new HashSet<>();

		Path benchmark = ConfigStore.getExistingFile(BENCHMARK_FILEPATH);

		List<String> lines = Files.readAllLines(benchmark);
		int lineNumber = 0;
		try {
		for (String line : lines) {
			lineNumber++;
			if (lineNumber <= 1 || line.trim().length() == 0)
				continue;
			String[] cells = Util.splite(benchmark, lineNumber, line, 2);
			String benchmarkCode = cells[0];
			String benchmarkName = cells[1];
			String pk=benchmarkCode;
			if (primaryKey.contains(pk)) {
				throw new DuplicateRecordException("benchmark cannot be duplicated ["+line+"]");
			} else {
				primaryKey.add(pk);
			}
			if (!Util.isEmpty(cells) && benchmarkCodeMap.containsKey(benchmarkCode)) {
				
				Collection<DetailEntity> records = benchmarkCodeMap.get(benchmarkCode);
				for (DetailEntity record : records) {
					record.setBenchmarkName(benchmarkName);
				}
			}
		}
		} catch (DateTimeException|FileReadingException|NumberFormatException|DuplicateRecordException e){
			throw new IncorrectDataException(benchmark.toString(), lineNumber, e.getMessage());
		}
		
	}

	/**
	 * export the entity into csv file
	 * @throws IOException
	 * @throws ConfigException
	 */
	private void exportToReport() throws IOException, ConfigException {
		for (Collection<DetailEntity> records : fundCodeMap.values()) {
			allRecords.addAll(records);
		}
		Collections.sort(allRecords, new ReportComparator());
		try (BufferedWriter writer = Files.newBufferedWriter(ConfigStore.getMonthlyOutperformanceFile(), CREATE,
				TRUNCATE_EXISTING)) {
			writer.write("FundName,Date,Excess,OutPerformance,Return,Rank" + System.getProperty("line.separator"));
			for (DetailEntity record : allRecords) {
				writer.write(record.toReportString());
			}
		}

	}

	/**
	 * calculate the rank 
	 */
	private void fillRankGroupByDate() {
		for (List<DetailEntity> records : dateMap.values()) {
			Collections.sort(records, new RankComparator());
			int ranking = 0;
			for (DetailEntity record : records) {
				ranking++;
				record.setRank(ranking);
			}
		}

	}

	/**
	 * calcualte the excess and fill the performance wording on the entity
	 * @throws ConfigException
	 * @throws IncompleteArgumentException
	 */
	private void fillExcessAndPerformance() throws  ConfigException, IncompleteArgumentException {

		try {
			for (Collection<DetailEntity> records : fundCodeMap.values()) {
				for (DetailEntity record : records) {
					calculateExcess(record);
				}
			}
		} catch (ScriptException e) {
			throw new ConfigException("There are error on execution the javascript script " + e.getMessage());
		}

	}

	/**
	 * call the Javascript and load the formula to calculate the excess. it is the requirement of externalize the rule outside the code.
	 * @param record
	 * @throws ScriptException
	 * @throws IncompleteArgumentException
	 */
	private void calculateExcess(DetailEntity record) throws ScriptException, IncompleteArgumentException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		if (record.getFundReturnPercent() != null && record.getBenchmarkReturnPercent() != null) {
			engine.put("fundReturn", record.getFundReturnPercent().doubleValue());
			engine.put("benchmarkReturn", record.getBenchmarkReturnPercent().doubleValue());
			engine.eval(ConfigStore.getExcessRule());
			BigDecimal excess = BigDecimal.valueOf((double) engine.get("excess"));
			record.setExcess(excess);
			if (excess.compareTo(new BigDecimal(-1)) < 0) {
				record.setOutPerformance(ConfigStore.getUnderPerformancedLabel());
			} else if (excess.compareTo(new BigDecimal(1)) > 0) {
				record.setOutPerformance(ConfigStore.getOutPerformancedLabel());
			} else {
				record.setOutPerformance("");
			}
		} else {
			throw new IncompleteArgumentException("return percent should not null fundCode["+record.getFundCode()+"], fund%["+record.getFundReturnPercent()+"], date["+record.getReturnDate()+"], benchmarkCode["+record.getBenchmarkCode()+"], benchmark%["+record.getBenchmarkReturnPercent()+"]");
		}

	}

	/**
	 * load the benchmarkReturn value into the entity
	 * @throws IOException
	 * @throws IncorrectDataException
	 */
	private void loadBenchmarkReturnSeries() throws IOException, IncorrectDataException{
		Path file = ConfigStore.getExistingFile(BENCH_RETURN_SERIES_FILEPATH);
		Set<String> primaryKey = new HashSet<>();
		List<String> lines = Files.readAllLines(file);
		int lineNumber = 0;
		try {
		for (String line : lines) {
			lineNumber++;
			if (lineNumber <= 1 || line.trim().length() == 0)
				continue;
			String[] cells = Util.splite(file, lineNumber, line, 3);
			String benchmarkCode = cells[0];
			if (!Util.isEmpty(cells)) {
				LocalDate date = Util.getDate(cells[1], dateFormatters);
				BigDecimal percent = new BigDecimal(cells[2]);
				String pk=benchmarkCode+" "+date.toString();
				if (primaryKey.contains(pk)){
					throw new DuplicateRecordException("benchmarkCode and date cannot be duplicated ["+line+"]");
				} else {
					primaryKey.add(pk);
				}
				if (benchmarkCodeMap.containsKey(benchmarkCode)){
					for (DetailEntity record:benchmarkCodeMap.get(benchmarkCode)){
						if (record.getReturnDate().isEqual(date)){
							record.setBenchmarkReturnPercent(percent);
						}
					}
				}
			}
		}
		} catch (DateTimeException|FileReadingException|NumberFormatException|DuplicateRecordException e){
			throw new IncorrectDataException(file.toString(), lineNumber, e.getMessage());
		}

	}

	/**
	 * load the funds information and update the entity.
	 * @throws IncorrectDataException
	 * @throws IOException
	 */
	private void loadFunds() throws IncorrectDataException, IOException {
		Set<String> primaryKey = new HashSet<>();

		Path funds = ConfigStore.getExistingFile(FUNDS_FILEPATH);

		List<String> lines = Files.readAllLines(funds);
		int lineNumber = 0;
		try {
		for (String line : lines) {
			lineNumber++;
			if (lineNumber <= 1 || line.trim().length() == 0)
				continue;
			String[] cells = Util.splite(funds, lineNumber, line, 3);
			String fundCode = cells[0];
			String fundName = cells[1];
			String benchmarkCode = cells[2];
			String pk=fundCode;
			if (!Util.isEmpty(cells) && fundCodeMap.containsKey(fundCode)) {
				if (primaryKey.contains(pk)) {
					throw new DuplicateRecordException("fundCode cannot be duplicated ["+line+"]");
				} else {
					primaryKey.add(pk);
				}
				Collection<DetailEntity> records = fundCodeMap.get(fundCode);
				for (DetailEntity record : records) {
					record.addFund(fundName,benchmarkCode);
					if (benchmarkCodeMap.containsKey(benchmarkCode)) {
						benchmarkCodeMap.get(benchmarkCode).add(record);
					} else {
						List<DetailEntity> allRecord = new ArrayList<>();
						allRecord.add(record);
						benchmarkCodeMap.put(benchmarkCode, allRecord);
					}
				}
			}
		}
		} catch (DateTimeException|FileReadingException|NumberFormatException|DuplicateRecordException e){
			throw new IncorrectDataException(funds.toString(), lineNumber, e.getMessage());
		}

	}

	/**
	 * load the fund return value and create entity. No other location would create entity.
	 * @throws IOException
	 * @throws IncorrectDataException
	 */
	private void loadFundReturnSeries() throws IOException, IncorrectDataException {
		Path fundReturn = ConfigStore.getExistingFile(FUND_RETURN_SERIES_FILEPATH);
		List<String> lines = Files.readAllLines(fundReturn);
		Set<String> primaryKey=new HashSet<>();
		int lineNumber = 0;
		try {
			for (String line : lines) {
				lineNumber++;
				if (lineNumber <= 1 || line.trim().length() == 0){
					continue;
				}
				
				String[] cells = Util.splite(fundReturn, lineNumber, line, 3);
				if (!Util.isEmpty(cells)) {
					String fundCode = cells[0];
					LocalDate date = Util.getDate(cells[1], dateFormatters);
					BigDecimal percent = new BigDecimal(cells[2]);
					DetailEntity record = new DetailEntity(fundCode,date,percent);
					String pk=fundCode+" "+date.toString();
					if (primaryKey.contains(pk)){
						throw new DuplicateRecordException("fundCode and date cannot be duplicated ["+line+"]");
					} else {
						primaryKey.add(pk);
					}
	
					if (fundCodeMap.containsKey(fundCode)) {
						fundCodeMap.get(fundCode).add(record);
					} else {
						List<DetailEntity> allRecord = new ArrayList<>();
						allRecord.add(record);
						fundCodeMap.put(fundCode, allRecord);
					}
					if (dateMap.containsKey(date)) {
						dateMap.get(date).add(record);
					} else {
						List<DetailEntity> allRecord = new ArrayList<>();
						allRecord.add(record);
						dateMap.put(date, allRecord);
					}
	
				}
	
			}
		} catch (DateTimeException|FileReadingException|NumberFormatException|DuplicateRecordException e){
			throw new IncorrectDataException(fundReturn.toString(), lineNumber, e.getMessage());
		}

	}

}
