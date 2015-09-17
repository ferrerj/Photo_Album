package cs213.photoAlbum.model ;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Christopher Rios
 */
/**
 * @author user1
 *
 */
public class User implements java.io.Serializable
{
	
	private static final long serialVersionUID = 3832465083613248432L;
	private String name;
	private String login_name;
	private TreeMap<String,Albums>  albums;
	/**
	 * User Constructor
	 * @param login_name users unique login name
	 * @param name users full name
	 */
	public User(String login_name,String name)
	{
		this.name = name;
		this.login_name = login_name;
		albums = new TreeMap<String, Albums>();
	}
	
	/**
	 * Adds an album to a user's album list
	 * @param name name of the album that it so be added
	 * @return true on success, false on failure
	 */
	public boolean addAlbum(String name)
	{
		
		if(albums.containsKey(name))
		{
			return false;
		}
	
			Albums album = new Albums(name);
			albums.put(name, album);
			return true;
		
		
	}
	/**
	 * Deletes album from users album list
	 * @param name name of album to be deleted
	 * @return true if successful
	 */
	public boolean deleteAlbum(String name)
	{
		
		if(albums.containsKey(name))
		{
			albums.remove(name);
			return true;
		}
		return false;		
	}
	/**
	 * Renames an album to given name
	 * @param old_name name of the album the user wants to rename
	 * @param new_name new name of album
	 * @return true if successful
	 */
	public boolean renameAlbum(String old_name,String new_name)
	{
		if(albums.containsKey(old_name)==false||albums.containsKey(new_name))
		{
			return false;
		}
		Albums album = albums.get(old_name);
		album.changeName(new_name);
		albums.put(new_name, album);
		albums.remove(old_name);
		return true;
	}
	
	
	
	/**
	 * Generates a list of all the users albums
	 * @return returns a TreeMap of all the users albums
	 */
	public String[] listAlbums()
	{
		
		Set<String> all_albums = albums.keySet();
		return all_albums.toArray(new String[0]);
	}
	
	public Albums getAlbum(String name)
	{
		if(albums.containsKey(name))
		{
			return albums.get(name);
		}
		return null;
	}
	
	public String toString()
	{
		return login_name;
	}
	public String getName()
	{
		return name;
	}
}
