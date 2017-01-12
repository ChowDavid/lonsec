package com.david.java.demo.test;

import static com.david.java.demo.lonsec.Constant.CONFIG;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.Test;

import com.david.java.demo.lonsec.ConfigStore;

public class ConfigTest {

	@Test(timeout = 5000)
	public void testConfigErrorMessage() throws IOException {
		System.err.flush();
		PrintStream ERR = System.err;

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream tempOutput = new PrintStream(bos, true);
		BufferedReader reader = new BufferedReader(new StringReader(bos.toString()));
		System.setErr(tempOutput);
		System.setProperty(CONFIG, "./src/test/resources/configTest.prop");

		ConfigStore.getDateFormat();
		String line = null;
		while ((line = reader.readLine()) != null) {
			assertEquals("Config Check Date Format Error Message", "Value not found, for key DATE_FORMAT", line);
		}

		ConfigStore.getExcessRule();
		while ((line = reader.readLine()) != null) {
			assertEquals("Config Check Execess Rule Error Message", "Value not found, for key EXCESS_RULE", line);
		}

		ConfigStore.getOutPerformancedLabel();
		while ((line = reader.readLine()) != null) {
			assertEquals("Config Check OutPerformanced Label Error Message",
					"Value not found, for key OUT_PERFORMED_LABEL", line);
		}

		ConfigStore.getUnderPerformancedLabel();
		while ((line = reader.readLine()) != null) {
			assertEquals("Config Check UnderPerformance Label Error Message",
					"Value not found, for key UNDER_PERFORMED_LABEL", line);
		}

		System.err.flush();
		System.setErr(ERR);
	}

	@Test(timeout = 1000)
	public void testConfigContent() throws IOException {

		System.setProperty(CONFIG, "./src/test/resources/configTest2.prop");

		String dateFormtString = ConfigStore.getDateFormat();
		assertEquals("dd/MM/yyyy,yyyy-MM-dd", dateFormtString);

		String excessRule = ConfigStore.getExcessRule();
		assertEquals("excess=fundReturn-benchmarkReturn", excessRule);

		String label1 = ConfigStore.getOutPerformancedLabel();
		assertEquals("out performed", label1);

		String label2 = ConfigStore.getUnderPerformancedLabel();
		assertEquals("under performed", label2);

	}

}
