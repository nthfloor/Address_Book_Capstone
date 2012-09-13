/**
 * Record class for address book entries
 * 
 * Nathan Floor
 * Ryan Saunders
 * 
 */

public class Record {
	//instance variables
	String firstname, lastname, company, address, city, country, state, ZIP,
			phone, fax, email, web;

	/**
	 * Copy constructor
	 * 
	 * @param r - the record to copy
	 */
	public Record(Record r) {
		firstname = r.firstname;
		lastname = r.lastname;
		company = r.company;
		address = r.address;
		city = r.city;
		country = r.country;
		state = r.state;
		ZIP = r.ZIP;
		phone = r.phone;
		fax = r.fax;
		email = r.email;
		web = r.web;
	}

	public Record(String[] args) throws IncorrectNumberOfFieldsException {
		try {
			firstname = args[0];
			lastname = args[1];
			company = args[2];
			address = args[3];
			city = args[4];
			country = args[5];
			state = args[6];
			ZIP = args[7];
			phone = args[8];
			fax = args[9];
			email = args[10];
			web = args[11];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IncorrectNumberOfFieldsException();
		}
	}

	public String getKey() {
		return phone;
	}

	public String toString() {
		return firstname + " " + lastname;
	}
}
