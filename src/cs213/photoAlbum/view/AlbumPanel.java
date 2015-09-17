package cs213.photoAlbum.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.ParseException;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cs213.photoAlbum.control.Controller;

/**
 * @author Christopher Rios
 * @author John Ferrer
 *This class makes a new panel, which displays all of the current user's Albums, and allows them to Create
 *Add Delete and View a slideshow of them.
 */
public class AlbumPanel extends JPanel implements ActionListener, ListSelectionListener {
	
	private Controller control;
	private DefaultListModel<String> listModel;
	private JList<String> albumList;
	private JButton add_album,delete_album,logout,rename_album,open_album,start_slideshow,search_dates,search_tags;
	private JLabel num_photos,oldest_date,date_range;
	private JTextField range_info,num_info,oldest_info;
	private GridLayout Buttons = new GridLayout(8,1);
	private GridLayout List = new GridLayout(0,1);
	private GridLayout Info = new GridLayout(2,3);
	private String[] albums;
	private tester padre;
	
	/**
	 * @param control Main controller of this session
	 * @param parent Parent Frame of this panel
	 */
	public AlbumPanel(Controller control, tester parent)
	{
		this.control = control;
		this.padre = parent;
		JPanel total = new JPanel(new BorderLayout());
		JPanel buttons = new JPanel();
		JPanel albums = new JPanel();
		JPanel info = new JPanel();
		//set any gaps i want
		Buttons.setVgap(10);
		Info.setHgap(20);
		albums.setLayout(List);   //load up layouts in the panels
		buttons.setLayout(Buttons);
		info.setLayout(Info);
		//add stuff to the list
		listModel = new DefaultListModel<String>();
		albumList = new JList<String>(listModel);
		albumList.setLayoutOrientation(JList.VERTICAL); //lists items vertically
		albumList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //only one thing at a time
		albumList.setSelectedIndex(0);
		albumList.setVisibleRowCount(10);
		//put a scroll pane all up in
		JScrollPane albumscroller = new JScrollPane(albumList,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		albumscroller.setPreferredSize(new Dimension(200,200));
		//TODO add listeners for list
		//populate list
		populateAlbums();
		albumList.addListSelectionListener(this);
		//Initialize buttons
		add_album = new JButton("Add Album");
		add_album.setPreferredSize(new Dimension(120,20));
		delete_album = new JButton("Delete Album");
		delete_album.setPreferredSize(new Dimension(80,20));
		logout = new JButton("Logout");
		logout.setPreferredSize(new Dimension(80,20));
		rename_album = new JButton("Rename Album");
		rename_album.setPreferredSize(new Dimension(80,20));
		search_dates = new JButton("Search Dates");
		search_dates.setPreferredSize(new Dimension(80,20));
		search_tags = new JButton("Search Tags");
		search_tags.setPreferredSize(new Dimension(80,20));
		//Initialize labels
		num_photos = new JLabel("Num Photos",SwingConstants.CENTER);
		oldest_date = new JLabel("Oldest Date",SwingConstants.CENTER);
		date_range = new JLabel("Date Range",SwingConstants.CENTER);
		num_info = new JTextField(10);
		oldest_info= new JTextField(10);
		range_info = new JTextField(10);
		open_album = new JButton("Open Album");
		open_album.setPreferredSize(new Dimension(80,20));
		start_slideshow = new JButton("Slide Show");
		start_slideshow.setPreferredSize(new Dimension(80,20));
		//add buttons
		buttons.add(add_album);
		buttons.add(delete_album);
		buttons.add(rename_album);
		buttons.add(open_album);
		buttons.add(start_slideshow);	
		buttons.add(search_dates);
		buttons.add(search_tags);
		buttons.add(logout);
		//add listeners for these buttons
		add_album.addActionListener(this);
		delete_album.addActionListener(this);
		rename_album.addActionListener(this);
		open_album.addActionListener(this);
		start_slideshow.addActionListener(this);
		search_dates.addActionListener(this);
		search_tags.addActionListener(this);
		logout.addActionListener(this);
		//add action commands
		add_album.setActionCommand("Add");
		delete_album.setActionCommand("Delete");
		rename_album.setActionCommand("Rename");
		open_album.setActionCommand("Open");
		start_slideshow.setActionCommand("SlideShow");
		search_dates.setActionCommand("Search Dates");
		search_tags.setActionCommand("Search Tags");
		logout.setActionCommand("Logout");
		
		//add list
		albums.add(albumscroller);
		//add labels
		info.add(num_photos);
		info.add(date_range);
		info.add(oldest_date);
		//add text fields
		info.add(num_info);
		info.add(range_info);
		info.add(oldest_info);
		//Configure text field, add some junk to it
		range_info.setEditable(false);
		range_info.setBackground(new Color(0xd6d9df));
		num_info.setEditable(false);
		num_info.setBackground(new Color(0xd6d9df));
		oldest_info.setEditable(false);
		oldest_info.setBackground(new Color(0xd6d9df));
		//set borders and spacing of the panels
		buttons.setBorder(BorderFactory.createEmptyBorder(5, 10 ,10, 10));
		info.setBorder(BorderFactory.createEmptyBorder(5, 0 ,10, 10));
		//add everything to total
		total.add(buttons,BorderLayout.WEST);
		total.add(albums);
		total.add(info,BorderLayout.SOUTH);
		add(total);
		total.setBorder(BorderFactory.createRaisedSoftBevelBorder());  
		total.setBorder(BorderFactory.createTitledBorder("Welcome "+ control.currentUser.getName()));
		showSelected();
	}

	
	/**
	 *  Populates the Album list of a User 
	 */
	public void populateAlbums() {
		listModel.clear();
		albums = control.listAlbums();
		for(int i =0;i<albums.length;i++)
		{
		listModel.addElement(albums[i]);
		}
		albumList.setSelectedIndex(0);
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		PopUp input;
		if("Add".equals(e.getActionCommand()))
		{
			input = new PopUp(1,1);
			if(input.text!=null&&input.text.length()>0&&!input.text.equals("fa645d32dhs4tsr5h65zg23gztxh1yuf46g/d3gf4bh14jg7fd3)2s3gr645hj")){
				control.addAlbum(input.text);
			} else if(input.text!=null&&input.text.length()>0){
				PopUp temp = new PopUp("Album already exists");
			}
		}
		else if("Delete".equals(e.getActionCommand()))
		{
			input = new PopUp(3,1);
			if(input.response==0){
				control.deleteAlbum(albumList.getSelectedValue());
			}
			
		}
		else if("Rename".equals(e.getActionCommand()))
		{
			input = new PopUp(1,albumList.getSelectedValue(),null);
			if(input.text!=null&&input.text.length()>0&&!input.text.equals(albumList.getSelectedValue())&&!input.text.equals("fa645d32dhs4tsr5h65zg23gztxh1yuf46g/d3gf4bh14jg7fd3)2s3gr645hj")&&!control.currentUser.renameAlbum(albumList.getSelectedValue(), input.text)){
				input = new PopUp("Name already exists");
			} 
		}
		else if("Open".equals(e.getActionCommand()))
		{
			padre.loadPhotos(albumList.getSelectedValue());
		}
		else if("SlideShow".equals(e.getActionCommand()))
		{
			padre.slideShow(0, albumList.getSelectedValue());
		}
		else if("Search Dates".equals(e.getActionCommand()))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
			try {
				Date date1 = null;
				Date date2 = null;
				Date date3 = null;
				input = new PopUp(4,4);
				albums = control.listAlbums();
				// create album called temp
				String current = "fa645d32dhs4tsr5h65zg23gztxh1yuf46g/d3gf4bh14jg7fd3)2s3gr645hj";
	        	date1 = sdf.parse(input.text);
	        	date2 = sdf.parse(input.secondText);
				if(input.text!=null&&!input.text.equals("")&&input.secondText!=null&&!input.secondText.equals("")&&date1!=null&&date2!=null){
		        	if(date1!=null&&date2!=null){
		        		control.currentUser.addAlbum(current);
						// go through all albums
						for(int i = 0; i<albums.length; i++){
							// go through all photos
							String[] photos = control.listPhotos(albums[i]);
							for(int j=0; j<photos.length; j++){
								// if photo is in range then add it to the album
								date3 = sdf.parse(control.getPhoto(albums[i], photos[j]).formatDate());
								if(date3.compareTo(date1)>0&&date3.compareTo(date2)<=0){
									control.addPhoto(current, control.getPhoto(albums[i], photos[j]));
								}
							}	
						}
		        	}
					padre.loadPhotos(current);
				}
				
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				input = new PopUp("Invalid format used. Please use format MM/dd/YYYY");
			}
			
		}
		else if("Search Tags".equals(e.getActionCommand()))
		{
			input = new PopUp(4,3);
			albums = control.listAlbums();
			// create album called temp
			String current = "fa645d32dhs4tsr5h65zg23gztxh1yuf46g/d3gf4bh14jg7fd3)2s3gr645hj";
			control.currentUser.addAlbum(current);
			// go through all albums
			if(input.secondText!=null){
				for(int i = 0; i<albums.length; i++){
					// go through all photos
					String[] photos = control.listPhotos(albums[i]);
					for(int j=0; j<photos.length; j++){
						 
						String[] tags = control.getTags(albums[i], photos[j]);
						//go through all tags
						if(tags!=null){
							for(int k=0; k<tags.length; k++){
								//if tag type is not declared find all with that name
								if(input.text!=null&&!input.text.equals("")){
									if(tags[k].contains(input.text)&&tags[k].contains(input.secondText)){
										// add current photo to temp photo
										control.addPhoto(current, control.getPhoto(albums[i], photos[j]));
									}
								} else {
									// if it isn't get all with the same value
									if(tags[k].contains(input.secondText)){
										// add current photo to temp photo
										control.addPhoto(current, control.getPhoto(albums[i], photos[j]));
									}
								}
							}
						}
					}	
				}
			}
			padre.loadPhotos(current);
			// create the window showing the results
			// window will have borrowed a lot from photo panel
			// save function will be there, back will delete the album.
		}
		else
		{
			System.out.println("Logout");
			try {
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
				topFrame.setVisible(false);
				input = new PopUp(4,0);
				if(input.response==0)
				{
					padre.logout();
				}
				else
				{
					topFrame.setVisible(true);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
			}
		}
		if(!("Logout".equals(e.getActionCommand())))
		{
			populateAlbums();
		}
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		showSelected();
	}

	/**
	 * Shows the selected albums information  
	 */
	private void showSelected() 
	{
		String range;
		String seperator =" - ";
		if(albumList.getSelectedValue()!=null)
		{	
			if(control.getAlbum(albumList.getSelectedValue()).getnum_Photos()==1)
			{
			range=control.getOldest(albumList.getSelectedValue());
			range=range.concat(seperator);
			range = range.concat(control.getOldest(albumList.getSelectedValue()));
			range_info.setText(range);
			}
			else
			{
				range=control.getRange(albumList.getSelectedValue());
				range_info.setText(range);
			}
			num_info.setText(Integer.toString(control.getAlbum(albumList.getSelectedValue()).getnum_Photos()));
			oldest_info.setText(control.getOldest(albumList.getSelectedValue()));
			oldest_info.setCaretPosition(0);
			range_info.setCaretPosition(0);
			num_info.setCaretPosition(0);
		}
		
			
	}


	
}
