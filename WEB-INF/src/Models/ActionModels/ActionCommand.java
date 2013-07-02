package Models.ActionModels;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Abstract class that holds common functionality for each Action class
 * that acts on a request.
 * @author Kevin Dugan
 *
 */
public abstract class ActionCommand implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Class attributes
	protected ServletContext context;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	
	/**
	 * Initializes the ServletContext, request, and response.
	 * @param context The ServletContext
	 * @param request The client HTTP request.
	 * @param response The HTTP response to the client.
	 */
	public void init(ServletContext context,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		this.context = context;
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}
	
	/**
	 * Abstract class to be used by each action class. Used to process the request.
	 * @param actionMap The map containing the individual configurations for that request.
	 * @throws ServletException
	 * @throws IOException
	 */
	abstract public void process(ActionMapping actionMap) throws ServletException, IOException ;
	
	/**
	 * Forwards the request and response back to the client with the appropriate view.
	 * @param target The view filename
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void forward(String target) throws ServletException, IOException {
		RequestDispatcher dispatcher = context.getRequestDispatcher(target);
		dispatcher.forward(request, response);
	}
}
