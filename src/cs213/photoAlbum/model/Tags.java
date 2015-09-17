package cs213.photoAlbum.model;

/**
 * @author Christopher Rios
 *
 */
public class Tags implements java.io.Serializable
{
	private static final long serialVersionUID = 3159206692237996488L;
	private String tag_value;
	private String tag_type;

	/**
	 * Initilizes a tag object with given tag type and tag value
	 * @param tag_type type of tag
	 * @param tag_value String value of the tag
	 */
	public Tags(String tag_type,String tag_value ) 
	{
		this.tag_value = tag_value;
		this.tag_type  = tag_type;
	}
	
	
	@Override
	public String toString()
	{
		return tag_type+":"+tag_value;
	}
	
	
}
