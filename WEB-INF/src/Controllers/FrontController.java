package Controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Models.ActionModels.ActionCommand;
import Models.ActionModels.ActionMapping;
import Models.ActionModels.UnknownAction;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("/FrontController")
/**
 * FrontController class is the gateway for all client requests. It reads config.xml and
 * stores the configurations into a map. The FrontController dynamically calls the 
 * appropriate models, based on the request URL. 
 * @author Kevin Dugan
 *
 */
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//Class Attributes
	private ServletContext context;
	private Map<String, ActionMapping> configMap;
	private ActionMapping actionMap;
	private final String DEFAULT_ACTION;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontController() {
        super();
        
        //Class Attribute instantiation
        context = null;
        configMap = new HashMap<String, ActionMapping>();
        actionMap = new ActionMapping();
        DEFAULT_ACTION = "ShowAllProducts.do";
    }

	/**
	 * The init() method for the FrontController. Gets the current ServletContext,
	 * and reads the configuration file via AppConfig class.
	 * 
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
		//Get the current ServletContext() & create an InputStream with the config file
		context = config.getServletContext();
		InputStream file = context.getResourceAsStream("/WEB-INF/config.xml");
		
		//Pass the InputStream to a helper class that reads the configurations and returns a map.
		AppConfig configs = new AppConfig(file);
		configs.parseDocument();
		configMap = configs.getConfigs();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);	
	}
	
	/**
	 * Processes the GET and POST requests. First retrieves the URI's from the request. Parses out the filename
	 * then retrieves, the configurations from the map.
	 * @param request The client request
	 * @param response The response to the client
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		//Get the request URI, parse out the filename
		String uri = request.getRequestURI();
		String filename = uri.substring(uri.lastIndexOf('/') + 1, uri.length());
		
		//If there is not a filename, make it the default homepage.
		if ( filename.length() == 0) {
			filename = DEFAULT_ACTION;
		}
		
		//Retrieve the configurations
		actionMap = configMap.get(filename);
		String className = actionMap.getClassName();
		
		//Find the Class to instantiate and action the request
		ActionCommand command = getAction(className);
		command.init(context, request, response);
		command.process(actionMap);
	}
	
	/**
	 * Creates a new instance of the Class from the configurations map.
	 * @param clName The class name to instantiate.
	 * @return ActionCommand The newly formed object.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private ActionCommand getAction(String clName) {
		try {
			return (ActionCommand) getActionClass(clName).newInstance();
		} catch (Exception e) {
			try {
				return (ActionCommand) getActionClass("UnknownAction").newInstance();
			} catch (InstantiationException | IllegalAccessException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Find the class based on the configuration.
	 * @param clName The class to find.
	 * @return The class.
	 */
	@SuppressWarnings("rawtypes")
	private Class getActionClass(String clName) {
		Class result;
		
		try {
			result = Class.forName(clName);
		} catch (ClassNotFoundException e) {
			result = UnknownAction.class;
		}
		
		return result;
	}
}
