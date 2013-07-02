package Models.ActionModels;

import java.io.IOException;

import javax.servlet.ServletException;

/**
 * Sends the user to an error page if a bad request is sent.
 * @author KDugan
 */
public class UnknownAction extends ActionCommand {

	private static final long serialVersionUID = 1L;

	@Override
	public void process(ActionMapping actionMap) throws ServletException,
			IOException {
		forward("/error.jsp");
		
	}

}
