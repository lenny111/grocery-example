package uk.co.exesys.grocery.rest.errorhandling;


/**
 * Class to map application related exceptions
 * 
 * @author rl
 *
 */
public class AppException extends Exception {

	private static final long serialVersionUID = -8999932578270387947L;
	
	/** 
	 */
	Integer status;
	
	/** application specific error code */
	int code; 
	
	/** detailed error description */
	String additionalMessage;	
	
	/**
	 * 
	 * @param status
	 * @param code
	 * @param message
	 * @param additionalMessage
	 * @param link
	 */
	public AppException(int status, int code, String message,
			String additionalMessage) {
		super(message);
		this.status = status;
		this.code = code;
		this.additionalMessage = additionalMessage;
	}

	public AppException() { }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getAdditionalMessage() {
		return additionalMessage;
	}

	public void setAdditionalMessage(String additionalMessage) {
		this.additionalMessage = additionalMessage;
	}

					
}
