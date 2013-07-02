package Models.StoreModels;

import java.util.regex.Pattern;

public class Payment {

	private String cardName;
	private String cardType;
	private Long cardNum;
	private String expDate;
	private int csc;
	
	private static final String VALID_NAME = "^[a-z A-Z 0-9,. ]+$";
	private static final String EXP_DATE = "((?:(?:0[1-9])|(?:1[0-2]))\\/(?:\\d{2}))(?![\\d])";
	
	public Payment(String name, String type, Long num, String date, String secCode) {
		this.cardName = name;
		this.cardType = type;
		this.cardType = type;
		this.cardNum = num;
		this.expDate = date;
		this.csc = Integer.parseInt(secCode);
	}
	
	public Payment() {
		//default constructor.
	}

	/*****************************************
	 * Getters and Setters
	 ****************************************/
	
	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Long getCardNum() {
		return cardNum;
	}

	public void setCardNum(Long cardNum) {
		this.cardNum = cardNum;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public int getCsc() {
		return csc;
	}

	public void setCsc(int csc) {
		this.csc = csc;
	}
	
	/*****************************************
	 * Business Logic
	 ****************************************/
	/**
	 * Validates user-entered payment information.
	 * @param cardName The customer's name.
	 * @param type The type of card.
	 * @param cardNum The card number.
	 * @param cardExp The card expiration date.
	 * @param cardCSC The card security code.
	 * @return The payment if valid data, false otherwise.
	 */
	public Payment validatePayment(String cardName, String type, String cardNum, String cardExp, String cardCSC) {
		
		if (cardName == null || cardName.toUpperCase().equals("NAME ON CARD") || !Pattern.matches(VALID_NAME, cardName) 
				|| cardName.isEmpty() || cardName.trim().length() == 0) {
			
			return null;
		} else if (type == null || type.isEmpty() || type.trim().length() == 0) {

			return null;
		} else if (cardNum == null || cardNum.contains("-") || cardNum.isEmpty() || cardNum.trim().length() == 0 
				|| cardNum.toUpperCase().equals("CARD NUMBER (NO DASHES")) {

			return null;
		} else if (cardExp == null || cardExp.toUpperCase().equals("EXP DATE (MM/YY)") || cardExp.isEmpty() 
				|| cardExp.trim().length() == 0 || !Pattern.matches(EXP_DATE, cardExp)) {
			System.out.println("CardExp failed");
			return null;
		} else if (cardCSC == null || cardCSC.isEmpty() || cardCSC.trim().length() == 0 
				|| cardCSC.toUpperCase().equals("CARD SECURITY CODE")) {
			
			return null;
		}
		
		this.cardName = cardName;
		this.cardType = type;
		this.expDate = cardExp;
		
		try {
			this.cardNum = Long.valueOf(cardNum);
			this.csc = Integer.parseInt(cardCSC);
		} catch (NumberFormatException e) {
			return null;
		}
		
		return this;
	}
	
	/**
	 * Would control the access to the payment processing.
	 * @return true if successful payment, false otherwise.
	 */
	public boolean processPayment() {
		
		//link to payment center, verify credit card.
		return true;
	}
	
}
