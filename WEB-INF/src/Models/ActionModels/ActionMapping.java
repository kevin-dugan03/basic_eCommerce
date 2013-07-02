package Models.ActionModels;

import java.io.Serializable;

/**
 * The configuration object representation used to store individual configurations
 * in the configurations map. 
 * @author Kevin Dugan
 *
 */
public class ActionMapping implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//Class attributes
	private String name;
	private String className;
	private String success;
	private String failure;
	private String param;
	
	/*****************************************
	 * Getters and Setters
	 ****************************************/
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getFailure() {
		return failure;
	}
	public void setFailure(String failure) {
		this.failure = failure;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String params) {
		this.param = params;
	}
}
