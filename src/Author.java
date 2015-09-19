

public class Author {
	String name, 
		nickname, 
		email, 
		description;
	
	int numposts = 0;
	
	public Author() {
		
	}
		
	public Author(String _name, String _position, String _email, String _description){
		name = _name;
		nickname = _position;
		email = _email;
		description = _description;
	}

	
	public void setNumposts(int _numposts) {
		numposts = _numposts;
	}

	
	@Override
	public String toString() {
		String info = String.format("name: %s\nposition: %s\nemail: %s\ndescription: %s\n\n", name, nickname, email, description);
		return info;
	}

}
