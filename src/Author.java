

public class Author {
	String name, 
		position, 
		email, 
		description;
	
	public Author() {
		
	}
		
	public Author(String _name, String _position, String _email, String _description){
		name = _name;
		position = _position;
		email = _email;
		description = _description;
	}


	
	@Override
	public String toString() {
		String info = String.format("name: %s\nposition: %s\nemail: %s\ndescription: %s\n\n", name, position, email, description);
		return info;
	}

}
