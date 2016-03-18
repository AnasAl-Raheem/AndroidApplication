package csc207project;

public class Client extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String creditCardNumber;
	private String expiryDate;
	
	/**
	 * creates a Client object.
	 * @param lastName
	 * @param firstName
	 * @param email
	 * @param address
	 * @param creditCardNumber
	 * @param expiryDate
	 */
	public Client(String lastName, String firstName, String email, 
			String address, String creditCardNumber, String expiryDate) {
		super(lastName, firstName, email, address);
		this.creditCardNumber = creditCardNumber;
		this.expiryDate = expiryDate;
	}

	/**
	 * @return a string containing the client credit card number.
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	/**
	 * sets the client credit card number.
	 * @param creditCardNumber
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	/**
	 * a string containing the client credit card expiry date.
	 * @return
	 */
	public String getExpiryDate() {
		return expiryDate;
	}

	/**
	 * set the client credit card expiry date.
	 * @param expiryDate
	 */
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	@Override
	public String toString() {
		return this.lastName + "," + this.firstName + "," + this.email + "," 
				+ this.address + "," + this.creditCardNumber + "," + this.expiryDate;
	}
	
}
