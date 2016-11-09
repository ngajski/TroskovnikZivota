package dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import model.User;

public class DaoUser implements DAO {

	private List<User> users = new LinkedList<>();
	
	public DaoUser() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		String dateInString = "21/05/1996";
		Date date = null;
		try {
			date = sdf.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		User user = new User();
		user.setUsername("marko");
		user.setPassword("aabb");
		user.setDateOfBirth(date);
		users.add(user);
		
		user = new User();
		user.setUsername("filip");
		user.setPassword("bbaa");
		user.setDateOfBirth(date);
		
		users.add(user);
	}

	@Override
	public User getUserByUsername(String username) throws DAOException {
		for (User user : users) {
			if (user.getUsername().equals(username)){
				return user;
			}
		}
		return null;
	}

	@Override
	public List<User> getAllUsers() throws DAOException {
		return users;
	}

}
