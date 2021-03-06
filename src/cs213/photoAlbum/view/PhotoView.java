package cs213.photoAlbum.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;

import cs213.photoAlbum.control.Controller;
import cs213.photoAlbum.model.Photos;

/**
 * @author Christopher Rios
 *This Panel is displays a picture, as well as all relevant information about it
 */
public class PhotoView extends JPanel implements ActionListener{
	private Controller control;
	private DefaultListModel<String> listModel;
	private JList<String> tagList;
	private JButton add_tag,delete_tag,back;
	private JLabel caption,capture_time,tags,picture;
	private JTextField caption_field,capture_field;
	private GridLayout Buttons = new GridLayout(4,1);
	private GridLayout Photo = new GridLayout(0,1);
	private GridLayout Info = new GridLayout(2,3);
	private String[] tag;
	private JComboBox<String> tag_list;
	private tester padre;
	private ImageIcon photo_img;
	private Photos curr_photo;
	private Image scaleImage;
	private String photo_name;
	private String album;
	private DefaultComboBoxModel<String> model;
	
	/**
	 * @param photo Name of photo that was opened
	 * @param album Name of album that was opened
	 * @param control Session controller
	 * @param parent Parent Frame of this panel
	 * 
	 */
	public PhotoView(String photo,String album,Controller control,tester parent)
	{
		this.padre = parent;
		this.control = control;
		this.curr_photo = control.getPhoto(album, photo);
		this.album = album;
		this.photo_name = photo;
		JPanel total = new JPanel(new BorderLayout());
		JPanel buttons = new JPanel();
		JPanel display = new JPanel();
		JPanel info = new JPanel();
		//set any gaps i want
		Buttons.setVgap(10);
		Info.setHgap(20);
		display.setLayout(Photo);   //load up layouts in the panels
		buttons.setLayout(Buttons);
		info.setLayout(Info);
		//add stuff to the list
		listModel = new DefaultListModel<String>();
		tagList = new JList<String>(listModel);
		tagList.setLayoutOrientation(JList.VERTICAL); //lists items vertically
		tagList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //only one thing at a time
		tagList.setSelectedIndex(0);
		tagList.setVisibleRowCount(5);
		//set up the photo display
		photo_img = new ImageIcon(curr_photo.getName());
		scaleImage = photo_img.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT);
		photo_img.setImage(scaleImage);
		picture= new JLabel(photo_img);
		display.add(picture);
		picture.setToolTipText(curr_photo.getCaption());
		total.add(display);
		//set up buttons
		add_tag = new JButton("Add Tag");
		add_tag.setPreferredSize(new Dimension(100,20));
		delete_tag = new JButton("Delete Tag");
		delete_tag.setPreferredSize(new Dimension(100,20));
		back = new JButton("Back");
		back.setPreferredSize(new Dimension(100,20));
		//listeners for the buttons
		add_tag.addActionListener(this);
		add_tag.setActionCommand("add");
		delete_tag.addActionListener(this);
		delete_tag.setActionCommand("delete");
		back.addActionListener(this);
		back.setActionCommand("back");
		//set up button panel
		buttons.add(add_tag);
		buttons.add(delete_tag);
		buttons.add(back);
		buttons.setBorder(BorderFactory.createEmptyBorder(5, 10 ,10, 10));
		//set up text fields
		capture_field = new JTextField(10);
		capture_field.setEditable(false);
		capture_field.setBackground(new Color(0xd6d9df));
		capture_field.setText(curr_photo.formatDate());
		capture_field.setCaretPosition(0);
		caption_field = new JTextField(10);
		caption_field.setEditable(false);
		caption_field.setBackground(new Color(0xd6d9df));
		caption_field.setText(curr_photo.getCaption());
		caption_field.setCaretPosition(0);
		//set up labels
		caption = new JLabel("Caption",SwingConstants.CENTER);
		capture_time = new JLabel("Capture Time",SwingConstants.CENTER);
		tags = new JLabel("Tags",SwingConstants.CENTER);
		model = new DefaultComboBoxModel<String>();
		tag_list = new JComboBox<String>(model);
		tag_list.setEditable(false);
		populateTags();
		//add to info
		info.add(caption);
		info.add(capture_time);
		info.add(tags);
		info.add(caption_field);
		info.add(capture_field);
		info.add(tag_list);
		//add everything to total
		total.add(display);
		total.add(buttons,BorderLayout.WEST);
		total.add(info,BorderLayout.SOUTH);
		total.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		total.setBorder(BorderFactory.createTitledBorder("Photo Info"));
		add(total);
		
	}
	/**
	 * This method populates the Tags of a photo into a combo box
	 */
	private void populateTags() 
	{
		tag_list.removeAllItems();
		tag=control.getTags(album,photo_name);
		if(tag!=null)
		{
			for(int i = 0;i<tag.length;i++)
			{
				model.addElement(tag[i]);
				
			}
		}
	}
	

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		PopUp popup;
		if("add".equals(e.getActionCommand()))
		{
			popup = new PopUp(1,3);
			if(popup.text!=null)
			{
			control.addTag(album,photo_name,popup.text,popup.secondText);
			}
			populateTags();
		}
		else if("delete".equals(e.getActionCommand()))
		{
			popup = new PopUp(3,3);
			if(popup.response==0)
			{
			control.deleteTag(album,photo_name,tag_list.getSelectedItem().toString());
			}
			populateTags();
		}
		else
		{
			padre.loadPhotos(album);
		}
	}
	
}
