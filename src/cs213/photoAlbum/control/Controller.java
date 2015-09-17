package cs213.photoAlbum.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cs213.photoAlbum.model.*;

/**
 * @author John Ferrer
 *
 */
public class Controller {
	// current objects being manipulated
	private Backend backend=new Backend();
	private Backend other = new Backend();
	public User currentUser;
	/**
	 * Creates new user, sets it as current
	 * @param username name desired for user
	 * @param fullname user's full name
	 * @return true, false whether successful Creating user
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public Controller() throws ClassNotFoundException, IOException
	{
		backend=backend.readUser();
	}
	public boolean create_user(String username, String fullname) throws IOException{
		
		String[] name = fullname.split("\"");
		if(backend.addUser(username, name[0]))
		{
			backend.writeUser(backend); // alittle wonky but should be okay
			return true;
		}
		
		return false;
	}
		
	
	public String getName(String username)
	{
		User user;
		if((user = backend.getUser(username))!=null)
		{
			return user.getName();
		}
		return null;
	}
	/**
	 * Deletes current user
	 * @param id User name of the user to be deleted.
	 * @return whether successful deleting user
	 * @throws IOException 
	 */
	public boolean delete_user(String id) throws IOException{
	
		String[] usrid=id.split("\"");
		if(backend.deleteUser(usrid[0]))
		{
			backend.writeUser(backend);
			return true;
		}
		return false;
	}
	
	/**
	 * Generates a String array of all users
	 * @return String array of all users
	 */
	public String[] list_users()
	{
		return backend.listUsers();
	}
	
	/**
	 * @param username username to attempt to log in as
	 * @return true, false depending on success of log in
	 */
	// no password requested in test runs
	public boolean login(String username){
		try {
			backend = other.readUser();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			backend = new Backend();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			backend = new Backend();
		}
		if(backend.getUser(username)!=null){  //maybe redundant?
			currentUser =  backend.getUser(username);
			return true;
		}
		return false;
	}
	/**
	 * Logs out current user
	 * @return true, false depending on successful logout
	 * @throws IOException 
	 */
	public void logout() throws IOException{
		other.writeUser(backend);
	}
	/**
	 * Gets the requested user
	 * @param username name of user to get
	 * @return requested user
	 */
	public User getUser(String username)
	{
		return backend.getUser(username);
	}
	
	/**
	 * Returns Requested album
	 * @param album album name
	 * @return requested album
	 */
	public Albums getAlbum(String album)
	{
		return currentUser.getAlbum(album);
	}
	
	/**
	 * This method adds an album
	 * @param album name to album to add
	 */
	public void addAlbum(String album)
	{
		if(currentUser.addAlbum(album)==false)
		{
			//System.out.println("album exists for user "+currentUser+":\n"+album);
		}
		else
		{
			//System.out.println("created for user "+currentUser.getName()+":\n"+album);
		}
	}
	
	/**
	 * Lists all albums for a user
	 * @return list of all albums
	 */
	public String[] listAlbums()
	{
		return currentUser.listAlbums();
	}
	
	/**
	 * Delete requested album
	 * @param album name of album to be deleted
	 */
	public void deleteAlbum(String album)
	{
		if(currentUser.deleteAlbum(album)==false)
		{
			//System.out.println("album"+album+"does not exist");
		}
		else
		{
			//System.out.println("removed for user "+currentUser.getName()+":\n"+album);
		}
	}
	
	/**
	 * This method lists an albums photos
	 * @param album name of album to list photos of
	 * @return list of album's photos
	 */
	public String[] listPhotos(String album)
	{
		return currentUser.getAlbum(album).list_photos();
	}
	
	
	/**
	 * Makes a new photo adds it to an album
	 * @param name name of photo
	 * @param caption caption of photo
	 * @param album album to be added to
	 */
	public void addPhoto(String name, String caption,String album)
	{
		Albums current_album = currentUser.getAlbum(album);
		Photos photo = new Photos(name,caption);
		if(current_album==null)
		{
			//System.out.println("album does not exist");
		}
		else
		{
			if(current_album.add_photo(photo))
			{
				//System.out.println("added photo"+name);
			}
			else
			{
				System.err.println("photo "+name+" already exists");
			}
		}
		
	}
	
	/**
	 * Deletes a photo from an album
	 * @param album name of album picture is to be deleted from
	 * @param name name of photo to be deleted
	 */
	public void deletePhoto(String album,String name)
	{
		Albums current_album = currentUser.getAlbum(album);
		if(current_album==null)
		{
			//System.out.println("album does not exist");
		}
		else
		{
			if(current_album.delete_photo(name))
			{
				//System.out.println("Photo "+name+ " deleted");
			}
			else
			{
				//System.out.println("Photo "+name+" does not exist");
			}
		}
	}
	
	/**
	 * Moves a photo from one album to another
	 * @param name name of photo
	 * @param source source album
	 * @param dest dest album
	 */
	public void movePhoto(String name,String source,String dest )
	{
		Albums source_album = currentUser.getAlbum(source);
		Albums dest_album = currentUser.getAlbum(dest);
		Photos photo;
		if(source_album==null)
		{
			//System.out.println("Album "+source+" does not exist");
			return;
		}
		if(dest_album ==null)
		{
			//System.out.println("Album "+dest+" does not exist");
			return;
		}
		
		photo=source_album.getPhoto(name);
		if(photo!=null)
		{
			
			if(dest_album.add_photo(photo))
			{
				//System.out.println("photo"+name+"successfully moved");
				source_album.delete_photo(name);
			}
			else
			{
			//System.out.println("Error photo exists in that album");
			}
		}
		
	}
	/**
	 * Gets the date of the oldest photo in the album
	 * @param album_name name of album
	 * @return date of oldest
	 */
	public String getOldest(String album_name)
	{
		Albums album = currentUser.getAlbum(album_name);
		return album.getOldest();
	}
	
	/**
	 * Get the Date range of an album
	 * @param album_name name of album
	 * @return date range 
	 */
	public String getRange(String album_name)
	{
		Albums album = currentUser.getAlbum(album_name);
		return album.getDateRange();
	}
	/**
	 * Gets a requested photo from an album
	 * @param album_name name of album
	 * @param photo_name name of photo
	 * @return photo
	 */
	public Photos getPhoto(String album_name,String photo_name)
	{
		return currentUser.getAlbum(album_name).getPhoto(photo_name);
	}
	
	/**
	 * Gets a list of all a photo's tags
	 * @param album_name name of an album
	 * @param photo_name name of the photo
	 * @return
	 */
	public String[] getTags(String album_name,String photo_name)
	{
		Photos photo = getPhoto(album_name,photo_name);
		return photo.listTags();
	}
	
	/**
	 * Adds a tag to an album
	 * @param album_name name of album
	 * @param photo_name name of photo
	 * @param tag_type tag type
	 * @param tag_value tag value
	 */
	public void addTag(String album_name,String photo_name,String tag_type,String tag_value)
	{
		Photos photo = getPhoto(album_name,photo_name);
		photo.addTag(tag_type, tag_value);
	}
	
	/**
	 * Adds a photo to an album
	 * @param album_name album name
	 * @param photo photo name
	 */
	public void addPhoto(String album_name,Photos photo)
	{
		Albums album = currentUser.getAlbum(album_name);
		album.add_photo(photo);
	}
	
	/**
	 * Deletes a tag from an album
	 * @param album_name album name
	 * @param photo_name photo name
	 * @param tag_name tag name
	 */
	public void deleteTag(String album_name,String photo_name,String tag_name)
	{
		Photos photo = getPhoto(album_name,photo_name);
		photo.deleteTag(tag_name);
	}
	

}