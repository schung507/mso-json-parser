

public class Author {
	String name, 
		position, 
		email, 
		description;
	
	int numposts;
	
	public Author() {
		
	}
		
	public Author(String _name, String _position, String _email, String _description){
		name = _name;
		position = _position;
		email = _email;
		description = _description;
	}
	
	public void setNumposts(int _numposts) {
//		numposts = _numposts;
	}
	
	@Override
	public String toString() {
		String info = String.format("name: %s\nposition: %s\nemail: %s\ndescription: %s\nnumposts: %d\n", name, position, email, description, numposts);
		return info;
	}

}
