
/**
 * Record class for address book entries
 * 
 * Nathan Floor
 * Ryan Saunders
 *
 */

public class Record {
	//instance variables
	String firstname,lastname,company,address,city,country,state,ZIP,phone,fax,email,web;
	
	public Record(){}
	
	public Record(String[] args){		
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
	}
	
	public String getKey(){
		return firstname;
	}
	
	public String toString(){
		return firstname + " " + lastname + " " + phone;
	}
}
