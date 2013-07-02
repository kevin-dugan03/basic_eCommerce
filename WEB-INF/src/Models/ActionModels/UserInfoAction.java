package Models.ActionModels;

import java.io.IOException;

import javax.servlet.ServletException;

/**
 * Takes user to the userInfo page.
 * @author KDugan
 *
 */
public class UserInfoAction extends ActionCommand{

	private static final long serialVersionUID = 1L;

	@Override
	public void process(ActionMapping actionMap) throws ServletException,
			IOException {
		forward(actionMap.getSuccess());
		
	}

}
