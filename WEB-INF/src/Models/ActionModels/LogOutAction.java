package Models.ActionModels;

import java.io.IOException;

import javax.servlet.ServletException;

/**
 * Invalidates the session.
 * @author KDugan
 */
public class LogOutAction extends ActionCommand {

	private static final long serialVersionUID = 1L;

	@Override
	public void process(ActionMapping actionMap) throws ServletException,
			IOException {
		
		request.getSession().invalidate();
		forward(actionMap.getSuccess());
		
	}

	
}
