package cs213.photoAlbum.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.io.EOFException;
import java.nio.file.Files;
/**
 * @author Christopher Rios
 *
 */
public class Backend implements java.io.Serializable{

	
	private static final long serialVersionUID = 7273306772022901253L;
	private TreeMap<String,User> users = new TreeMap<String,User>();
	private static final String storeDir = "data"; 
	private static final String storeFile = "photoAlbum.dat"; 
	
	public void writeUser(Backend backend) throws IOException { 
		String path = storeDir + File.separator + storeFile;
		File f = new File(path);
		if(!f.exists()){
			f.createNewFile();
		}
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile)); 
		oos.writeObject(backend); 
		oos.close();
	} 

	/**
	 * Reads a user list from a file
	 * @return a new backend item
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Backend readUser() throws IOException, ClassNotFoundException{ 
		String path = storeDir + File.separator + storeFile;
		File f = new File(path);
		if(f.exists()){
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile)); 
			Backend backend = (Backend)ois.readObject(); 
			ois.close();
			return backend;
		} else {
			return new Backend();
		}
	}

	/**
	 * Initializes backend
	 */
	public Backend()
	{
		
	}	
	/**
	 * This method returns of a list of all users on file as an array of strings
	 * @return a String Array of all users
	 */
	public String[] listUsers()
	{
		Set<String> all_users = users.keySet();
		return all_users.toArray(new String[0]);
	}
	
	/**
	 * Attempts to add a user
	 * @param login_name unique login name of a user
	 * @return true if successful
	 */
	
	public boolean addUser(String login_name,String name)
	{
		if(users.containsKey(login_name))
		{
			return false;
		}
		User new_user = new User(login_name,name);
		users.put(login_name, new_user);
		return true;
	}
	
	/**
	 * Deletes a user from the file
	 * @param login_name login name of user
	 * @return
	 */
	public boolean deleteUser(String login_name)
	{
		if(users.containsKey(login_name))
		{
			users.remove(login_name);
			return true;
		}
		return false;
	}
	
	public User getUser(String name)
	{
		if(users.containsKey(name))
		{
			return users.get(name);
		}
		return null;
	}
	
}
