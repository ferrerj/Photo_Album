package cs213.photoAlbum.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import cs213.photoAlbum.control.Controller;
import cs213.photoAlbum.model.Albums;

/**
 * @author Christopher Rios
 * This Panel holds all of the Photo information of an album, and grants various manipulations of it
 */
public class PhotoPanel extends JPanel implements ActionListener, ListSelectionListener {
	private Controller control;
	private DefaultListModel<ImageIcon> listModel;
	private JList<ImageIcon> photo_list;
	private JButton add_photo,delete_photo,back,recaption_photo,open_photo,move_photo;
	private JLabel caption;
	private JTextField caption_info;
	private GridLayout Buttons = new GridLayout(6,1);
	private BorderLayout List = new BorderLayout();
	private String album;
	private String[] photos;
	private tester padre;
	public PhotoPanel(Controller control, String album, tester parent)
	{
		padre = parent;
		this.album = album;
		this.control=control;
		JPanel total = new JPanel(new BorderLayout());
		JPanel buttons = new JPanel();
		JPanel photos = new JPanel();
		JPanel info = new JPanel();
		//set any gaps i want
		Buttons.setVgap(10);
		//Info.setHgap(20);
		photos.setLayout(List);   //load up layouts in the panels
		buttons.setLayout(Buttons);	
		listModel = new DefaultListModel<ImageIcon>();
		//photo list junk
		photo_list = new JList<ImageIcon>(listModel);
		photo_list.setLayoutOrientation(JList.HORIZONTAL_WRAP); //lists items 
		photo_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //only one thing at a time
		photo_list.setSelectedIndex(0);
		photo_list.setVisibleRowCount(3);
		//photo listener
		photo_list.addListSelectionListener(this);
		//put a scroll pane all up in
		JScrollPane albumscroller = new JScrollPane(photo_list,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		albumscroller.setPreferredSize(new Dimension(250,250));
		//Set up label
		caption = new JLabel("Caption:");
		//Set up Text
		caption_info = new JTextField(10);
		caption_info.setEditable(false);
		caption_info.setBackground(new Color(0xd6d9df));
		
		//Initialize buttons
		add_photo = new JButton("Add Photo");
		add_photo.setPreferredSize(new Dimension(80,20));
		delete_photo = new JButton("Delete Photo");
		delete_photo.setPreferredSize(new Dimension(80,20));
		back = new JButton("Back");
		back.setPreferredSize(new Dimension(80,20));
		recaption_photo = new JButton("Recaption Photo");
		open_photo = new JButton("Display Photo");
		move_photo = new JButton("Move Photo");
		move_photo.setPreferredSize(new Dimension(80,20));
		//add buttons
		buttons.add(add_photo);
		buttons.add(delete_photo);
		buttons.add(recaption_photo);
		buttons.add(open_photo);
		buttons.add(move_photo);
		buttons.add(back);
		//add listeners for buttons
		add_photo.addActionListener(this);
		delete_photo.addActionListener(this);
		recaption_photo.addActionListener(this);
		open_photo.addActionListener(this);
		back.addActionListener(this);
		move_photo.addActionListener(this);
		add_photo.setActionCommand("add");
		delete_photo.setActionCommand("delete");
		recaption_photo.setActionCommand("recaption");
		open_photo.setActionCommand("open");
		back.setActionCommand("back");
		move_photo.setActionCommand("move");
		//add list
		photos.add(albumscroller);
		info.add(caption);
		info.add(caption_info);
		
		//photos.add(caption_info,BorderLayout.PAGE_END);
		//TODO Set up a caption when highlighted
		
		//set borders and spacing of the panels
		buttons.setBorder(BorderFactory.createEmptyBorder(5, 10 ,10, 10));
		//add everything to total
		total.add(buttons,BorderLayout.WEST);
		total.add(photos);
		total.add(info,BorderLayout.SOUTH);
		add(total);
		total.setBorder(BorderFactory.createRaisedSoftBevelBorder());  
		total.setBorder(BorderFactory.createTitledBorder("Album:"+album));
		populatePhotos();
		//TODO Add listeners for all the buttons.
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
			if(photo_list.getSelectedIndex()!=-1)
			{
				caption_info.setText(control.getPhoto(album,photo_list.getSelectedValue().getDescription()).getCaption());
			}
		
	}
	/**
	 * Repopulates the photoList
	 */
	private void populatePhotos()
	{
		listModel.clear();
		photos = control.listPhotos(album.toString());
		ImageIcon current;
		String current_name;
		Image scaleImage;
		if(control.getAlbum(album).getnum_Photos()>0)
		{
		for(int i =0;i<photos.length;i++)
		{
			current_name = control.getPhoto(album, photos[i]).getName();
			current= new ImageIcon(current_name,current_name); //gives the path
			scaleImage = current.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
			current.setImage(scaleImage);
			listModel.addElement(current);
		}
		photo_list.setSelectedIndex(0);
		caption_info.setText(control.getPhoto(album, photos[0]).getCaption());
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
			System.out.println("Add Photo");
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "JPG & GIF Images", "jpg", "gif");
		    chooser.setFileFilter(filter);
		    if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)
		    {
				String caption = JOptionPane.showInputDialog("Caption Please:");
		    	control.addPhoto(chooser.getSelectedFile().getAbsolutePath(),caption, album.toString());
		    	populatePhotos();	
		    }
		    
		}
		else if("delete".equals(e.getActionCommand()))
		{
			System.out.println("Delete Photo");
			popup = new PopUp(3,2);
			if(popup.response==0)
			{
				control.deletePhoto(album,photo_list.getSelectedValue().getDescription());
			}
			populatePhotos();
		}
		else if("recaption".equals(e.getActionCommand()))
		{
			System.out.println("Recaption");
			popup = new PopUp(2,control.getPhoto(album, photo_list.getSelectedValue().getDescription()).getCaption(), null);
			if(popup.text!=null&&popup.text.length()>0)
			{
			control.getPhoto(album, photo_list.getSelectedValue().getDescription()).changeCaption(popup.text);
			}
			populatePhotos();
		}
		else if("open".equals(e.getActionCommand()))
		{
			System.out.println("Open Photo");
			padre.loadPhoto(album, photo_list.getSelectedValue().getDescription());
		}
		else if("move".equals(e.getActionCommand()))
		{
			String new_album = JOptionPane.showInputDialog("Which album would you like to Move this photo to?");
			
			if(new_album!=null)
			{
				if(new_album.length()==0||new_album.equals(" "))
				{
					JOptionPane.showMessageDialog(this, "Error! Please enter in an album name");
				}
				
				else if(control.getAlbum(new_album)==null)
				{
					JOptionPane.showMessageDialog(this, "Error! Album: "+new_album+"does not exist");
				}
				else if(control.getAlbum(new_album).add_photo(control.getPhoto(album,photo_list.getSelectedValue().getDescription()))==false)
				{
					JOptionPane.showMessageDialog(this, "Error! Photo already exists");
				}
				else
				{
					control.deletePhoto(album, photo_list.getSelectedValue().getDescription());
				}
			}
			populatePhotos();
		
		}
		else
		{
			System.out.println("Back");
			padre.loadAlbum();
		}
		
	}
}
