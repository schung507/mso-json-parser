

public class Author {
	String name, 
		nickname, 
		email, 
		description;
	
	int numposts;
	
	public Author() {
		
	}
		
	public Author(String _name, String _position, String _email, String _description, int _numposts){
		name = _name;
		nickname = _position;
		email = _email;
		description = _description;
		numposts = _numposts;
	}

	
	public void setNumposts(int _numposts) {
		numposts = _numposts;
	}

	
	@Override
	public String toString() {
		String info = String.format("name: %s\nnickname: %s\nemail: %s\ndescription: %s\nnumposts: %s\n\n", name, nickname, email, description, numposts);
		return info;
	}

}
