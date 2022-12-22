package com.cscie97.ledger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.cscie97.ledger.test.TestDriver;

public class CommandProcessor {

	private static Ledger ledger;
	private static int lineNumber;
	private static String operationName = "";
	private static final Logger LOGGER = Logger.getLogger(CommandProcessor.class.getClass().getName());
	
	@SuppressWarnings("unused")
	public Object processCommand(String command) throws CommandProcessorException, LedgerException {
		String[] args= command.split(" ");
		Object response = null;
		if(command.startsWith("create-ledger")) {
			operationName = "create-ledger";
			if(command.contains("description") && command.contains("seed")) {
					String ledgerName= command.substring(13, command.indexOf("description")).trim();
					String description= command.substring(command.indexOf("description")+13, command.indexOf("seed")-2).trim();
					String seed= command.substring(command.indexOf("seed")+6).trim();
					response =  new Ledger(ledgerName,description.replaceAll("\"", ""), seed.substring(0, seed.length()-1));
					ledger=(Ledger)response;
				}else {
					throw new CommandProcessorException(operationName," Missing Description or seed in the create-ledger command ",lineNumber);
				}
			}else if(command.startsWith("create-account")) {
				operationName = "create-account";
				if(args.length==2) {
					response = ledger.createAccount(args[1]);
				}else {
					throw new CommandProcessorException(operationName," Missing accountId in the create-account command ",lineNumber);
				}
			}else if(command.startsWith("process-transaction")) {
				operationName = "process-transaction";
				if(command.contains("amount") && command.contains("fee") && command.contains("note") && command.contains("payer") && command.contains("receiver") && ledger!=null) {
					String transactionId= command.substring(20, command.indexOf("amount")).trim();
					int amount= Integer.parseInt(command.substring(command.indexOf("amount")+6, command.indexOf("fee")).trim());
					int fee= Integer.parseInt(command.substring(command.indexOf("fee")+3, command.indexOf("note")).trim());
					String note= command.substring(command.indexOf("note")+6, command.indexOf("payer")-2).trim();
					String payer= command.substring(command.indexOf("payer")+5, command.indexOf("receiver")).trim();
					String reciever= command.substring(command.indexOf("receiver")+8).trim();
					Transaction transaction = new Transaction(transactionId, amount, fee, note.replaceAll("\"", ""), payer, reciever);
					response= "Transaction processed with the transactionId of: " +ledger.processTransaction(transaction);
				}else {
					throw new CommandProcessorException(operationName," Missing arguments in the process-transaction command or the Ledger Object ",lineNumber);
				}
				
			}else if(command.startsWith("get-account-balances")) {
				operationName = "get-account-balances";
				if(args.length==1  && ledger!=null) {
					response = ledger.getAccountBalances();
				}else {
					throw new CommandProcessorException(operationName," Missing Ledger Object for the get-account-balances ",lineNumber);
				}
				
			}else if(command.startsWith("get-account-balance")) {
				operationName = "get-account-balance";
				if(ledger!=null && args.length==2) {
					response = ledger.getAccountBalance(args[1]);
				}else {
					throw new CommandProcessorException(operationName," Missing accountId in the get-account-balance command or the Ledger Object ",lineNumber);
				}	
			}else if(command.startsWith("get-block")) {
				operationName = "get-block";
				if(args.length==2 || ledger!=null && ledger.getBlockMap().containsKey(Integer.parseInt(args[1]))) {
					response = ledger.getBlock(Integer.parseInt(args[1])).toString();
				}else {
					throw new CommandProcessorException(operationName," Missing BlockId in the get-block command or the Ledger Object ",lineNumber);
				}
			}else if(command.startsWith("get-transaction")) {
				operationName = "get-transaction";
				if(args.length==2 && ledger!=null) {
					Transaction transaction = ledger.getTransaction(args[1]);
					response= transaction.toString();
				}else {
					throw new CommandProcessorException(operationName," Missing TransactionId in the get-transaction command or the Ledger Object ",lineNumber);
				}
			}else if(command.startsWith("validate")) {
				operationName = "validate";
				if(args.length==1 && ledger!=null) {
					ledger.validate();
					response ="Validated Successfully !!!!!";
				}else {
					throw new CommandProcessorException(operationName," Missing Ledger Object ",lineNumber);
				}
			}
		System.out.println(response.toString());
		return response.toString();
	}
	
	public void processCommandFile(String filepath)throws CommandProcessorException, LedgerException {
		LOGGER.info("Process the each commands indiviually from the file at the filePath location.");
		  File file = new File(filepath);
		  try {
			  BufferedReader br= new BufferedReader(new FileReader(file));
			  String st;
			  while ((st = br.readLine()) != null) {
				  System.out.println(st);
				  lineNumber++;
				  if(!st.startsWith("#")) {
					  Object output = processCommand(st);
				  }
				  
			  }
		} catch (FileNotFoundException e) {
			throw new CommandProcessorException(operationName,"No File found",lineNumber);
		} catch (IOException e) {
			throw new CommandProcessorException(operationName,"Exception while reading the script ",lineNumber);
		}
	}

	/*	
	private String exceptionMessage(String operation) {
		String exceptionMessage ="";
		switch(operation) {
		case "create-ledger":
			exceptionMessage= "Exception while creating a Ledger. ";
			break;
		case "create-account":
			exceptionMessage= "Exception while creating an Account. ";
			break;
		case "process-transaction":
			exceptionMessage= "Exception while processing a Transaction. ";
			break;
		case "get-account-balances":
			exceptionMessage= "Exception while fetching all account balances. ";
			break;
		case "get-account-balance":
			exceptionMessage= "Exception while fetching specific account balance. ";
			break;
		case "get-block":
			exceptionMessage= "Exception while fetching specific Block. ";
			break;
		case "get-transaction":
			exceptionMessage= "Exception while fetching specific Transaction. ";
			break;
		}
		return exceptionMessage;
	}
	*/
	
	
}
