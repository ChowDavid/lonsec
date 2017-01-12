package com.david.java.demo.lonsec;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Scanner;

import com.david.java.demo.lonsec.analyzer.Analyzer;
import com.david.java.demo.lonsec.exception.ConfigException;
import com.david.java.demo.lonsec.exception.FileReadingException;
import com.david.java.demo.lonsec.exception.IncompleteArgumentException;
import com.david.java.demo.lonsec.exception.IncorrectDataException;

/**
 * Main program.
 * @author david
 *
 */
public class MainStart {

	public static void main(String[] args) {	

		SystemChecker checker = new SystemChecker();
		if (checker.isPassed()) {
			System.out.println("Process Start");
			Analyzer analyzer = new Analyzer();
			try {
				analyzer.process();
				System.out.println("Process Finished!");
			} catch (ConfigException e) {
				System.err.println("Error in configuration. Please check the configuration detail " + e.getMessage());
				System.err.println(e.getMessage());
			} catch (IOException | FileReadingException|IncorrectDataException|IncompleteArgumentException e) {
				System.err.println(e.getMessage());
			}
			

		}
		
	
	}

}
