package com.cscie97.ledger.test;

import java.util.logging.Logger;

import com.cscie97.ledger.CommandProcessor;
import com.cscie97.ledger.CommandProcessorException;
import com.cscie97.ledger.LedgerException;

public class TestDriver {
	private static final Logger LOGGER = Logger.getLogger(TestDriver.class.getClass().getName());
	
	public static void main(String [] args) throws CommandProcessorException,LedgerException {
		    String filePath = args[0];
		    //String filePath = "/Users/ggumbe350/workspace/HSEFall2022/gundeep_gumber_assignment1/src/com/cscie97/ledger/resources/ledgerScript.txt";
			CommandProcessor commandprocessor = new CommandProcessor();
			LOGGER.info("Process the commands from the file at the filePath location.");
			commandprocessor.processCommandFile(filePath);
		
	}

}
