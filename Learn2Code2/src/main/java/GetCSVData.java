import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import org.apache.commons.lang3.time.StopWatch;


@WebServlet("/GetCSVData")
public class GetCSVData extends HttpServlet {
	private static final long serialVersionUID = 1l;
	private static Logger LOGGER = 
						Logger.getLogger(GetCSVData.class.getName());
	DatastoreService datastore = 
	  			DatastoreServiceFactory.getDatastoreService();
	private static StopWatch STOPWATCH = new StopWatch();
	
	String postcodeString;
	String suburb;
	String filename;
	String searchval;
	int responsecode = 200;
	boolean searchType; // TRUE = Suburb search, FALSE = Postcode search
	
	/**
	 * 
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		  throws ServletException, IOException {  
		  
		  STOPWATCH.reset();
		  STOPWATCH.start();
		  
		  //---- Load the correct file depending on State ----//	  
		  postcodeString = req.getParameter("postcode");
		  suburb = req.getParameter("suburb");
		  
		  //---- Load the correct file depending on State ----//
		  setSearchVars();
		  getCSVFile();
		  
		  //---- initialise the hash-map for response back to web-app ----//
		  Map<String, Object> map = new HashMap<String, Object>();	  
		  
		  // Why is this here?
		  @SuppressWarnings("unused") 
		  List<List<String>> records = new ArrayList<>();
		  
		  try (BufferedReader br = 
				  			new BufferedReader(new FileReader(filename))) {
		      String line;
		      while ((line = br.readLine()) != null) {
		          String[] values = line.split(",");
		          
		          if(values[0].equals(searchval)) {
		        	  if(searchType) {
		        		  values[0] = "Select School";
		        	  } else {
		        		  values[0] = "Select Suburb";
		        	  }
		        	  map.put("postcodelist", values);
		        	  responsecode = 100;
		          }
		      }
		  }
		  
		  STOPWATCH.stop();
		  
		  map.put("responsecode", responsecode);
		  ServletUtils.writeback(resp, map);
		  
		  LOGGER.info("Time to Process: " 
				  	  + (STOPWATCH.getNanoTime()/1000000) +"ms \n");
		  
	  }
	
	/**
	 * This method reads the suburb and determines if it should be reading a 
	 * postcode or suburb CSV file. 
	 */
	private void setSearchVars() {	
		
		if(suburb.equals("nosub")) {
			searchType = false;
			searchval = postcodeString;
		} else {
			searchType = true;
			searchval = suburb;
		}
	}
	
	/**
	 * This method takes the floor of the postcode/1000 to determine which state it 
	 * should be retrieving. 
	 * <p>
	 * It then sets the {@code filename} to be the appropriate CSV file. 
	 */
	private void getCSVFile() {
		int postcode = Integer.parseInt(postcodeString);
		
		if((postcode/1000) == 7){
			  // Load Tasmania Data
			  if(searchType) {
				  filename = "csv/NTTASSA_Suburb_School.csv"; //FIXME NT?
			  } else {
				  filename = "csv/TAS_Postcode_Suburb.csv";
			  }
			  LOGGER.fine("GetCSVData:ln 119: Loading TAS file: " 
					  	  + filename + "\n");
		  } else if ((postcode/1000) == 6){
			  // Load WA Data
			  if(searchType) {
				  filename = "csv/WA_Suburb_School.csv";
			  } else {
				  filename = "csv/WA_Postcode_Suburb.csv";
			  }
			  LOGGER.fine("GetCSVData:ln 128: Loading WA file: " 
					  	  + filename + "\n");
		  } else if ((postcode/1000) == 5){
			  // Load SA Data
			  if(searchType) {
				  filename = "csv/NTTASSA_Suburb_School.csv"; //FIXME NT?
			  } else {
				  filename = "csv/NT_Postcode_Suburb.csv"; //FIXME is this right?
			  }
			  LOGGER.fine("GetCSVData:ln 137: Loading SA file: "
			  			  + filename + "\n");
		  } else if ((postcode/1000) == 4){
			  //Load QLD Data
			  if(searchType) {
				  filename = "csv/QLD_Suburb_School.csv";
			  } else {
				  filename = "csv/QLD_Postcode_Suburb.csv";
			  }
			  LOGGER.fine("GetCSVData:ln 146: Loading QLD file: "
			  		+ filename + "\n");
		  } else if ((postcode/1000) == 3){
			  //Load VIC Data
			  if(searchType) {
				  filename = "csv/VIC_Suburb_School.csv";
			  } else {
				  filename = "csv/VIC_Postcode_Suburb.csv";
			  }
			  LOGGER.fine("GetCSVData:ln 155: Loading VIC file: "
			  		+ filename + "\n");
		  } else if ((postcode/1000) == 2){
			  //Load NSW/ACT Data
			  if(searchType) {
				  filename = "csv/NSW_Suburb_School.csv";
			  } else {
				  filename = "csv/NSW_Postcode_Suburb.csv";
			  }
			  LOGGER.fine("GetCSVData:ln 164: Loading NSW/ACT file: "
			  		+ filename + "\n");
		  } else {
			  //Load NT Data
			  if(searchType) {
				  filename = "csv/NTTASSA_Postcode_Suburb.csv";
			  } else {
				  filename = "csv/NT_Postcode_Suburb.csv";
			  }
			  LOGGER.fine("GetCSVData:ln 173: Loading NT file: "
			  		+ filename + "\n");
		  }
	}
}	
	
