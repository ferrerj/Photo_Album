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
 * @author John Ferrer
 * Organizes search results, allows creation of albums from search results
 */
public class tagSearchResults extends JPanel implements ActionListener, ListSelectionListener {
	Controller control;
	private DefaultListModel<ImageIcon> listModel;
	private JList<ImageIcon> photo_list;
	private JButton add_photo,delete_photo,back,recaption_photo,open_photo;
	private JLabel num_photos,oldest_date,date_range;
	private JTextField range_info,num_info,oldest_info;
	GridLayout Buttons = new GridLayout(5,1);
	GridLayout List = new GridLayout(0,1);
	GridLayout Info = new GridLayout(2,3);
	ImageIcon icon;
	ImageIcon icon2;
	ImageIcon icon3;
	String album = "fa645d32dhs4tsr5h65zg23gztxh1yuf46g/d3gf4bh14jg7fd3)2s3gr645hj";
	String[] photos;
	tester padre;
	private final String[] texts = {"Make Album", "Cancel"};
	private JButton[] button = new JButton[2];
	/**
	 * @param control the controller object to get the photos from
	 * @param parent the parent frame to allow transitions to other screens
	 */
	public tagSearchResults(Controller control, tester parent)
	{
		padre = parent;
		this.control=control;
		JPanel total = new JPanel(new BorderLayout());
		JPanel buttons = new JPanel();
		JPanel albums = new JPanel();
		JPanel info = new JPanel();
		//set any gaps i want
		Buttons.setVgap(10);
		//Info.setHgap(20);
		albums.setLayout(List);   //load up layouts in the panels
		buttons.setLayout(Buttons);
		info.setLayout(Info);
		listModel = new DefaultListModel<ImageIcon>();
		//photo list junk
		photo_list = new JList<ImageIcon>(listModel);
		photo_list.setLayoutOrientation(JList.HORIZONTAL_WRAP); //lists items 
		photo_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //only one thing at a time
		photo_list.setSelectedIndex(0);
		photo_list.setVisibleRowCount(3);
		populatePhotos();
		//photo listener
		photo_list.addListSelectionListener(this);
		//put a scroll pane all up in
		JScrollPane albumscroller = new JScrollPane(photo_list,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		albumscroller.setPreferredSize(new Dimension(400,400));
		//Initialize buttons
		
		for(int j = 0; j<button.length; j++){
			button[j] = new JButton(texts[j]);
			button[j].setPreferredSize(new Dimension(100,20));
			buttons.add(button[j]);
			button[j].setActionCommand(texts[j]);
			button[j].addActionListener(this);
		}
		//add list
		albums.add(albumscroller);
		//TODO Set up a caption when highlighted
		
		//set borders and spacing of the panels
		buttons.setBorder(BorderFactory.createEmptyBorder(5, 10 ,10, 10));
		//add everything to total
		total.add(buttons,BorderLayout.WEST);
		total.add(albums);
		add(total);
		total.setBorder(BorderFactory.createRaisedSoftBevelBorder());  
		total.setBorder(BorderFactory.createTitledBorder("Search Results"));
		//TODO Add listeners for all the buttons.
	}
	private void populatePhotos()
	{
		listModel.clear();
		photos = control.listPhotos(album.toString());
		ImageIcon current;
		String current_name;
		Image scaleImage;
		for(int i =0;i<photos.length;i++)
		{
			current_name = control.getPhoto(album, photos[i]).getName();
			current= new ImageIcon(current_name,current_name); //gives the path
			scaleImage = current.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
			current.setImage(scaleImage);
		listModel.addElement(current);
		}
		
		photo_list.setSelectedIndex(0);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	//TODO Change the Action Commands
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(texts[0].equals(e.getActionCommand())){
			
			// make pop up
			PopUp input = new PopUp(1,1);
			// rename album to popup.text
			if(input.text!=null&&!input.text.equals("")&&!input.text.equalsIgnoreCase(album)&&control.currentUser.renameAlbum(album, input.text)){
				// all done. see what i did here?
				padre.loadPhotos(input.text);
			} else if(input.text!=null&&input.text.length()>0){
				input = new PopUp("The album already exists or an invalid name was provided.");
			}
			// load album 

		} else if(texts[1].equals(e.getActionCommand())){
			// delete current album
			control.deleteAlbum(album);
			// load home
			padre.loadAlbum();
		}
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
