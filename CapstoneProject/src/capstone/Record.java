package capstone;

/**
 * Record class for address book entries
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 * 
 */

public class Record implements Comparable<String>{
	public enum SearchType{
		FIRSTNAME,
		LASTNAME,
		PHONE
	}
	
	public static SearchType currentSearchType = SearchType.PHONE;
	
	//instance variables
	String firstname, lastname, company, address, city, country, state, ZIP,
			phone, fax, email, web;

	public Record(String[] args) throws IncorrectNumberOfFieldsException {
		if (args.length == 12) {
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
		} else {
			throw new IncorrectNumberOfFieldsException();
		}
	}

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

	public String getKeyValue() {
		if(currentSearchType == SearchType.FIRSTNAME)		
			return firstname;
		else if(currentSearchType == SearchType.LASTNAME)		
			return lastname;
		else if(currentSearchType == SearchType.PHONE)		
			return phone;
		else
			return firstname;
	}
	
	public void setKey(SearchType type){
		currentSearchType = type;
	}

	@Override
	public String toString() {
		return firstname + " " + lastname;
	}

	@Override
	public boolean equals(Object obj) {
		Record rec = (Record) obj;

		return (firstname.equals(rec.firstname))
				&& (lastname.equals(rec.lastname))
				&& (company.equals(rec.company))
				&& (address.equals(rec.address))
				&& (city.equals(rec.city))
				&& (country.equals(rec.country))
				&& (state.equals(rec.state))
				&& (ZIP.equals(rec.ZIP))
				&& (phone.equals(rec.phone))
				&& (fax.equals(rec.fax))
				&& (email.equals(rec.email))
				&& (web.equals(rec.web));
	}
<<<<<<< HEAD
	
	@Override
	//compare key value of record with some identifier
	public int compareTo(String arg0) {
		return getKeyValue().compareTo(arg0);
=======

	public String getFirstnameValue() {
		return firstname;
	}

	public String getLastnameValue() {
		return lastname;
>>>>>>> master
	}
}
