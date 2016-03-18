package csc207project;

public class Admin extends User{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7355831089778010145L;

	/**
	 * creates an Admin object.
	 * @param lastName
	 * @param firstName
	 * @param email
	 * @param address
	 */
	public Admin(String lastName, String firstName, String email, 
			String address) {
		super(lastName, firstName, email, address);
	}
	
	@Override
	public String toString() {
		return this.lastName + "," + this.firstName + "," + this.email + "," + this.address;
	}
	
}
