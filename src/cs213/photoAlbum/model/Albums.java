package cs213.photoAlbum.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Christopher Rios
 */

public class Albums implements java.io.Serializable
{
	
	private static final long serialVersionUID = 8388266949516494997L;
	private String date_range;
	private int num_photos;
	private String name;
	private TreeMap<String, Photos> photo_list; //makes a tree map with the key being photo name
	/**
	 * Initializes a new Album with given name, gives it a photo count of 0 and an empty photo List
	 * @param name name of the album
	 */
	public Albums(String name)
	{
		this.name = name;
		num_photos = 0;
		photo_list = new TreeMap<String, Photos>(); 
		
	}
	
	/**
	 * adds a given photo to the albums photo list iterates num_photos on success
	 * @photo photo to be added
	 * @return true if successful
	 */
	public boolean add_photo(Photos photo)
	{
		
		if(photo_list.containsKey(photo.getName()))
		{
			return false;
		}
		photo_list.put(photo.getName(), photo);
		this.setDateRange();
		num_photos++;
		return true;
		
	}
	/**
	 * This method adds a photo to an album's list
	 * @param name name of the photo
	 * @param caption caption of the photo
	 * @return
	 */
	public boolean add_photo(String name, String caption){
		if(photo_list.get(name)!=null){
			photo_list.put(name, new Photos(name, caption));
			this.setDateRange();
			num_photos++;
			return true;
		}
		return false;
	}
	
	/**
	 * This method attempts to remove a photo from an albums photo list, decrements num_photos on success
	 * @param name name of the photo
	 * @return true if successful
	 */
	public boolean delete_photo(String name)
	{
		if(photo_list.containsKey(name))
		{
			photo_list.remove(name);
			num_photos--;
			if(num_photos!=0)
			{
			this.setDateRange();
			}
			return true;
		}
		return false;
	}
    
	/**
	 * helper method to change the name of an album
	 * @param new_name New name of the album
	 */
	public void changeName(String new_name)
	{
		name = new_name;
	}
	
	/**
	 * Prints out all of the photos in a given album
	 */
	public String[] list_photos()
	{
		Set<String> all_photos = photo_list.keySet();
		return all_photos.toArray(new String[0]);
	}
	
	/**
	 * Sets the date range of an album, is called after every
	 * add and delete
	 */
	public void setDateRange()
	{
		List<Photos> list = new ArrayList<Photos>(photo_list.values());
		Collections.sort(list);
		int last_index = list.size()-1;
		Photos lower_range=(Photos)list.toArray()[0];
		Photos upper_range= (Photos)list.toArray()[last_index];
		date_range = lower_range.formatDate().concat(" - ").concat(upper_range.formatDate());
	}
	
	
	/**
	 * @return the date range of the album
	 */
	public	String getDateRange()
	{
		return date_range;
	}
	
	
	/**
	 * Getter for number of photos
	 * @return number of photos in an album
	 */
	public int getnum_Photos()
	{
		return num_photos;
	}
	
	
	/**
	 * Sorts all of any albums photos by date
	 * @return a list of photos sorted by date
	 */
	public String getOldest()
	{
		if(num_photos==0)
		{
			return "";
		}
		List<Photos> list = new ArrayList<Photos>(photo_list.values());
		Collections.sort(list);
		System.out.println(list.size());
		return list.get(0).formatDate();
	}
	public Photos getPhoto(String name)
	{
		if(photo_list.containsKey(name))
		{
			return photo_list.get(name);
		}
		return null;
	}
	
	
	public String toString()
	{
		return name;
	}
}

