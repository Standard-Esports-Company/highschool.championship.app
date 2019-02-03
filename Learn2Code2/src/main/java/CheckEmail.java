import java.io.IOException;
import java.util.HashMap;
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


@WebServlet("/CheckEmail")
public class CheckEmail extends HttpServlet {
  private static final long serialVersionUID = 1l;
  @SuppressWarnings("unused")
  private static Logger LOGGER = 
  					Logger.getLogger(CheckEmail.class.getName());
  DatastoreService datastore = 
		  			DatastoreServiceFactory.getDatastoreService();
  private static StopWatch STOPWATCH = new StopWatch();

public void doPost(HttpServletRequest req, HttpServletResponse resp)
		  throws ServletException, IOException {  
	  
	  STOPWATCH.reset();
	  STOPWATCH.start();
	  
	  System.out.print("CheckEmail:35: Running \n");
	  
	  //---- Load the correct file depending on State ----//	  
	  String email = req.getParameter("email");
	  
	  System.out.print("CheckEmail:35:Checking Adress: " 
				+ email +"\n");
	  
	  //---- initialize the hash-map for response back to web-app ----//
	  Map<String, Object> map = new HashMap<String, Object>();	  
	  int responsecode = 200;
	  
	  if(ServletUtils.isValidEmailAddress(email)) {
		  //email is valid
		  responsecode = 100;
	  }
	  
	  STOPWATCH.stop();
	  System.out.print("GetCSVData:54:Time to Process: " 
			  					+ STOPWATCH.getNanoTime() +"\n");
	  
	  map.put("responsecode", responsecode);
	  ServletUtils.writeback(resp, map);		  
  }
}	

