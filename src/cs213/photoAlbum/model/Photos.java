package cs213.photoAlbum.model;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Christopher Rios
 *
 */

public class Photos implements java.io.Serializable,Comparable<Photos>
{
	
	private static final long serialVersionUID = 4476675560917289514L;
	private String name;
	private String caption;
	private TreeMap<String,Tags> tag_list;
	private Calendar time;
	private final String date_format = "MM/dd/YYYY-HH:mm:ss";
	private final String seperator = " : ";

 /**
  * Photo Constructor, initializes an empty tag list sets photos time to time when created 
 * @param name Unique name of a photo
 * @param caption caption of photo
 */
public Photos(String name, String caption)
{
	this.name= name;
	this.caption = caption;
	tag_list=new TreeMap<String, Tags>();
	time=Calendar.getInstance();
	setDate();

}
 
	/**
	 * Adds a given tag to a photo's tag list
	 * @param tag_type  type of tag
	 * @param tag_value tag value
	 * @return true if tag was successfully added 
	 */
	public boolean addTag(String tag_type, String tag_value  )
	{
		Tags tag = new Tags(tag_type,tag_value);
		String tagkey = tag_type.concat(seperator);
		tagkey=tagkey.concat(tag_value);
		if(tag_list.containsKey(tagkey))
		{
			return false;
		}
			tag_list.put(tagkey,tag);
			return true;
	}
	

	/**
	 * This method attempts to remove a given tag from a photo.
	 * @param tag_type type of tag
	 * @param tag_value value of the tag
	 * @return true if tag was successfully added
	 */
	public boolean deleteTag(String tagkey)
	{
		if(tag_list.containsKey(tagkey))
		{
			tag_list.remove(tagkey);
			return true;
		}
		return false;
	}
	
	/**
	 * Lists all of the info about a Photo
	 */
	public void listInfo()
	{
		System.out.println(this.toString());
		listTags();
	}
	/**
	 * custom toString
	 */
	public String toString()
	{
		return name+" - "+ (this.formatDate());
	}
	/**
	 * Sets the date of a photo. 
	 */
	public void setDate()
	{
		File huh = new File(name);
		long timestamp =huh.lastModified();
		time.setTimeInMillis(timestamp);
		time.set(Calendar.MILLISECOND,0);
		
	}
	
	
	/**
	 * Changes a given photo's caption
	 * @param caption new caption 
	 */
	public void changeCaption(String caption)
	{
		this.caption = caption;
	}
	
	/**
	 * Lists all of the tags within a photo
	 * 
	 */
	public String[] listTags()
	{
		if(tag_list.size()!=0)
		{
		Set<String> all_tags = tag_list.keySet();
		return all_tags.toArray(new String[0]);
		}
		return null;
		/*for(Map.Entry<String,Tags> entry :tag_list.entrySet()) 
		{
		  Tags curr_tag = entry.getValue();
		  System.out.println(curr_tag);
		}
		*/
	}

	/**
	 * Getter for photo's name
	 * @return photo's name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Formats the date in the patter MM/dd/YYYY-HH:mm:ss
	 * @return correctly formatted date
	 */
	public String formatDate()
	{
		setDate();
		SimpleDateFormat formatter = new SimpleDateFormat(date_format);//formats it the way that is wanted
		String formatted_date = formatter.format(time.getTime());
		return formatted_date;          
	}
	/**
	 * Getter for photo's caption
	 * @return photo's caption
	 */
	public String getCaption() {
		return caption;
	}
	@Override
	public int compareTo(Photos o) 
	{
		
		return this.time.compareTo(o.time);
	}
	
}
