package EssayAnalysis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import java.lang.reflect.Field;


public class KNEssayDB {
	//Debug fields
	private boolean _verboseMode = true;
	
	//functional fields
	private Connection dbConnection = null;
	
	//error messages
	private static String errorMsgOracleDriver;
	private static String errorMsgMySQLDriver;
	private static String errorMsgDBConnection;
	
	//critical strings
	private static String driverNameOracle;
	private static String driverNameMySQL;

	private static String stringJDBCOracle;
	private static String dbUsernameOracle;
	private static String dbPasswordOracle;

	private static String stringJDBCMySQL;
	private static String stringRemoteMySQL;
	private static String dbUsernameMySQL;
	private static String dbPasswordMySQL;
	private static String dbPswdRemoteMySQL;
	
	//SQL statement strings
	private static String essayCountSQL;
	private static String essaysSQL;
	private static String countReviewedEssaysSQL;
//	private static String essayTOEFLSQL;
	
	private static int totalEssays = 0;
	
	//------------------------------------------------------------------------------------------------------------------------  
	/**
	 * Constructor is creating a connection to the database. When success, set the total number of tickers to compute in Oscar
	 * 
	 * @param dbFlag:  1 for Oracle, 2 for MySQL.  Default is MySQL
	 * 
	 * Local reference:
	 * InitializeStrings():  initialize all the strings here.
	 */
	KNEssayDB(int dbFlag) {
		InitializeStrings();
		
		switch (dbFlag) {
		case 1:
			ConnectOracle();
			break;
		case 2:
			ConnectMySQL();
			break;
		case 3:
			ConnectLocalMySQL();
			break;
		default:
			ConnectMySQL();
			break;
		}
		
		SetTotalEssayCount();  //set the total number of tickers to compute
	}

	
	//------------------------------------------------------------------------------------------------------------------------  
	/**
	 * 
	 * Establish connection to designated Oracle database
	 * 
	 * @param none
	 * @see InitialzeStrings for db connection strings
	 */
	private void ConnectOracle() {
		try {
			Class.forName(driverNameOracle);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(errorMsgOracleDriver);
			System.exit(0);		//Database constructor force exit when there is problem with data
		}
		
		try {
			dbConnection =DriverManager.getConnection(stringJDBCOracle, dbUsernameOracle,dbPasswordOracle);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(errorMsgDBConnection);
			System.exit(0);		//Database constructor force exit when there is problem with data
		} 		
	}
	
	//------------------------------------------------------------------------------------------------------------------------  
	/**
	 * 
	 * Establish connection to designated MySQL database
	 * 
	 * @param none
	 * @see InitialzeStrings for db connection strings
	 */
	private void ConnectMySQL() {
		try {
			Class.forName(driverNameMySQL);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(errorMsgMySQLDriver);
			System.exit(0);		//Database constructor force exit when there is problem with data
		}
		
		try {
			dbConnection =DriverManager.getConnection(stringRemoteMySQL, dbUsernameMySQL,dbPswdRemoteMySQL);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(errorMsgDBConnection);
			System.exit(0);		//Database constructor force exit when there is problem with data
		} 		
	}
	
	private void ConnectLocalMySQL() {
		try {
			Class.forName(driverNameMySQL);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(errorMsgMySQLDriver);
			System.exit(0);		//Database constructor force exit when there is problem with data
		}
		
		try {
			dbConnection =DriverManager.getConnection(stringJDBCMySQL, dbUsernameMySQL,dbPasswordMySQL);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(errorMsgDBConnection);
			System.exit(0);		//Database constructor force exit when there is problem with data
		} 		
	}
		
	//------------------------------------------------------------------------------------------------------------------------  
	/**
	 * 
	 * Initialize strings used in the class.
	 * 
	 * Return: void
	 * 
	 */
	private static void InitializeStrings() {
		//critical strings
		driverNameOracle = "oracle.jdbc.driver.OracleDriver";
		stringJDBCOracle = "jdbc:oracle:thin:@strategypresentor.chu37dcmvh8r.us-east-1.rds.amazonaws.com:1521:MRKAY";
		dbUsernameOracle = "";
		dbPasswordOracle = "";
		
		driverNameMySQL = "com.mysql.jdbc.Driver";  //org.gjt.mm.mysql.Driver
		stringJDBCMySQL = "jdbc:mysql://192.168.1.7:3306/ewc";
		stringRemoteMySQL = "jdbc:mysql://fc.keem.net:3306/nlp";
		dbUsernameMySQL = "keem";
		dbPasswordMySQL = "zmxncb";
		dbPswdRemoteMySQL = "Xzmxncb1";

		//error messages
		errorMsgOracleDriver = "Database Oracle Driver loading Failure";
		errorMsgMySQLDriver = "Database MySQL Driver loading Failure";
		errorMsgDBConnection = "Database Connection Failure";
		
		//SQL statement strings
		//essayCountSQL = "SELECT count(distinct recno) as essayTotal FROM essays";
		//countReviewedEssaysSQL = countReviewedEssaysSQL
		//essaysSQL = "SELECT recno, essay, status, rate, wcount FROM essays where status = 'Reviewed'";
		
		essaysSQL = "SELECT essays.recno, essays.essay, status, essays.rate, essays.wcount FROM essays, nlp3 WHERE essays.`recno` = nlp3.`essayNo` and nlp3.type = 'GRE' and status = 'Reviewed' limit 2";
		countReviewedEssaysSQL = "SELECT count(distinct essays.recno) as essayTotal FROM essays, nlp3 WHERE essays.`recno` = nlp3.`essayNo` and nlp3.type = 'GRE' and status = 'Reviewed' limit 2";
		essayCountSQL = countReviewedEssaysSQL;
	}
	
	
	//------------------------------------------------------------------------------------------------------------------------  
	/**
	 * 
	 * @param none
	 */
	private void SetTotalEssayCount() {
		totalEssays = 0;
		
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet dataResult = stmt.executeQuery(essayCountSQL);
		
			dataResult.next();
			totalEssays = dataResult.getInt("essayTotal");
			dataResult.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		if(_verboseMode) System.out.println("Total Essays:"+totalEssays);
		
	}
	//------------------------------------------------------------------------------------------------------------------------  
	/**
	 *  Returns total Essays
	 */
	public Integer GetTotalEssayCount() {
		return totalEssays;
	}
	
	//------------------------------------------------------------------------------------------------------------------------  
	/**
	 * @param v: flag.  0 for non-verbose mode.  1 for verbose mode
	 */
	public void SetVerbose(boolean v) {
		_verboseMode = v;
	}

	public ArrayList <EssayData> PopulateEssayData() {
		ArrayList <EssayData> list = null; 	//return variable
		ResultSet dataResult;						//Database result connector
		Integer rowCounts = 0;
		
		try {
			PreparedStatement essays = dbConnection.prepareStatement(essaysSQL);
			dataResult = essays.executeQuery();

			rowCounts = GetRowCount();
			
			list = new ArrayList <EssayData>(); 
			dataResult.setFetchSize(0);
			
			int j = 0;
			int percentComplete = 0;

			while (dataResult.next()){
				EssayData tempData = new EssayData(); //Symbol
				
				tempData.recno = dataResult.getInt(1);		//recored number
				tempData.essay = dataResult.getString(2);	
				tempData.status = dataResult.getString(3);		
				tempData.rate = dataResult.getInt(4);	
				tempData.wordCount = dataResult.getInt(5);
				list.add(tempData);		//add newly read data to the ist

				if(_verboseMode) {
					int temp1 = rowCounts*(100-percentComplete)/100;
					int temp2 = (rowCounts-j); 
					if( temp1 >= temp2) {
						System.out.println(percentComplete+"% Completed");
						percentComplete += 10;
					}
				}
				j++;
			}		
			dataResult.close();			//close the result connection after copying
			
		} catch (Exception e) {			//error in getting data
			e.printStackTrace();		//print stack when fails to get data
		} 
			
		return list;					//return the list to copy the result
	}	
	//----------------------------------------------------------------------------------------------  
	/** 
	 * Determines the number of rows in a selected data table. 
	 * @return The number of rows. 
	 * @throws SQLException If the <code>ResultSet</code> is OK. 
	 * @see  
	 */  
	public int GetRowCount() throws SQLException 
	{  
	   int rowCount;  
	   ResultSet dataResult;						//Database result connector
	   PreparedStatement countSQL = null;
	   
	   countSQL = dbConnection.prepareStatement(countReviewedEssaysSQL);
	   dataResult = countSQL.executeQuery();
	   dataResult.next();
	   rowCount = dataResult.getInt(1);
	   dataResult.close();
	   countSQL.close();
	   
	   return rowCount;  
	}  
	
	public ArrayList <PosData> GetPosData(int essayNo) {
		ArrayList <PosData> list = null; 		//return variable
		ResultSet dataResult;					//Database result connector
//		Integer rowCounts = 0;
		
		try {
			String poeSQL = "SELECT essayNo, sentenceNo, pos, lemma FROM nlp1 where essayNo ="+String.valueOf(essayNo);
			PreparedStatement pos = dbConnection.prepareStatement(poeSQL);
			dataResult = pos.executeQuery();
//			rowCounts = GetRowCount();
			
			list = new ArrayList <PosData>(); 
			dataResult.setFetchSize(0);
			
			while (dataResult.next()){
				PosData tempData = new PosData(); //Symbol
				
				tempData.essayNo = dataResult.getInt(1);		//recored number
				tempData.sentenceNo = dataResult.getInt(2);	
				tempData.pos = dataResult.getString(3);		
				tempData.lemma = dataResult.getString(4);	
				list.add(tempData);		//add newly read data to the list
			}
			if(_verboseMode) {
				System.out.println(essayNo+" is fetched");
			}
			dataResult.close();			//close the result connection after copying
			
		} catch (Exception e) {			//error in getting data
			e.printStackTrace();		//print stack when fails to get data
		} 
			
		return list;					//return the list to copy the result
	}
	
}

