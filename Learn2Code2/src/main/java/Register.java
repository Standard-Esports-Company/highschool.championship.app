import java.io.IOException;
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
	private static Logger LOGGER = Logger.getLogger(Register.class.getName());
	
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
	
        String state = request.getParameter("state");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String email = request.getParameter("email");
        long phone = Integer.parseInt(request.getParameter("phone"));
        String sname = request.getParameter("sname");
        String city = request.getParameter("city");
        
        System.out.println("postcodeValues: " + 
        request.getParameterValues("postcode") + "\n");
        System.out.println("postcode: " + 
                request.getParameter("postcode") + "\n");
        
        int postcode = 0;
        try {
        	postcode = Integer.parseInt(request.getParameter("postcode"));
        } catch (Exception e) {
        	e.printStackTrace();
        	LOGGER.severe("Exception thrown at Register.java ln:51. This was "
        			+ "the received postcode:" 
        			+ request.getParameter("postcode"));
        }
        String registrantClass = request.getParameter("registrantClass");
        boolean subscribeCheck = false;
        System.out.println(request.getParameter("subscribeCheck"));
        System.out.println(request.getParameter("subscribeCheck") != null);
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
	}
}
