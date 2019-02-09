

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class ServletUtils {
	
	/** Public static method for writing GSON data back to the javascript 
	 *  request that has been sent by the webapp
	 * 
	 * @param resp - Object of type HttpServletResponse
	 * @param map - Hashmap with <String, Object> to be converted to JSON
	 * @throws IOException
	 */
	public static void writeback(HttpServletResponse resp, 
			Map<String, Object> map) throws IOException {
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		System.out.print("ServletUtils:18: Sending JSON Response \n");	
		System.out.print(new Gson().toJson(map).toString() + "\n");	
		
		resp.getWriter().write(new Gson().toJson(map));
	}
	
	/** Public Static boolean method for check validity of an email address
	 *  
	 *  @param email - String email address
	 *  @return True address is valid, False if the address is not valid.
	 */
	public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
