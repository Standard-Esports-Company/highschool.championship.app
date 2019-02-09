import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

/**
 * Written by the yeetster
 * 
 * Steal my code:
 * @author ZThomas
 *
 */

@SuppressWarnings("serial")
@WebServlet("/register")
public class Register extends HttpServlet {
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	private static final Logger LOGGER = 
							Logger.getLogger(Register.class.getName());
	
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		LOGGER.fine("New registrant at: " + dtf.format(now));
		
        response.setContentType("text/html;charset=UTF-8");
        
        //TODO this is hardcoded...
        String state = "qld";
        
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        LOGGER.fine("New registrant details: " + fname + lname);
        String email = request.getParameter("email");
        long phone = 0;
        try {
        	phone = Integer.parseInt(request.getParameter("phone"));
        } catch (Exception e) {
        	e.printStackTrace();
        	LOGGER.log(Level.SEVERE, e.toString() + "Register.java ln:42 - "
        			+ "Phone had exception. Phone = " 
        			+ request.getParameter("phone"), e);
        }
        String sname = request.getParameter("sname");
        String city = request.getParameter("city");
        int postcode = 0;
        try {
        	postcode = Integer.parseInt(request.getParameter("postcode"));
        } catch (Exception e) {
        	e.printStackTrace();
        	LOGGER.log(Level.SEVERE, e.toString() + "Register.java ln:54 - "
        			+ "Postcode had exception. Postcode = " 
        			+ request.getParameter("postcode"), e);
        }      
        String registrantClass = request.getParameter("registrantClass");
        boolean subscribeCheck = false;
        if (request.getParameter("subscribeCheck") != null) {
        	subscribeCheck = true;
        }
       
        Entity registration = new Entity("registration", sname + fname + lname);

        registration.setProperty("firstName", fname);
        registration.setProperty("lastName", lname);
        registration.setProperty("email", email);
        registration.setProperty("state", state);
        registration.setProperty("phoneNumber", phone);
        registration.setProperty("schoolName", sname);
        registration.setProperty("schoolSuburb", city);
       	registration.setProperty("postcode", postcode);        
        registration.setProperty("registrantClass", registrantClass);
        registration.setProperty("subscribed", subscribeCheck);
        datastore.put(registration);
        
        response.sendRedirect("/thankyou.html");
	}
}
