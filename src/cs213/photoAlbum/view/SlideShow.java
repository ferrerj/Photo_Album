package cs213.photoAlbum.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import cs213.photoAlbum.control.Controller;
import cs213.photoAlbum.model.Photos;

/**
 * @author John Ferrer
 * Manual slideshow of photos within a specific album
 */
public class SlideShow extends JPanel implements ActionListener{
	Controller control;
	private JLabel picture;
	GridLayout Buttons = new GridLayout(3,1);
	GridLayout Photo = new GridLayout(0,1);
	String[] tag;
	tester padre;
	ImageIcon photo_img;
	Photos curr_photo;
	Image scaleImage;
	private int i;
	private String album;
	private final String[] texts = {"Next Photo", "Previous Photo", "Back"};
	private JButton[] button = new JButton[3];
	
	
	/**
	 * @param i current index of the array of photos in the album
	 * @param photo string name of photo to be viewed
	 * @param album string name of album to be in slideshow
	 * @param control the controller object
	 * @param parent the parent object, changing the window
	 */
	public SlideShow(int i, String photo, String album,Controller control,tester parent)
	{
		this.padre = parent;
		this.control = control;
		this.curr_photo = control.getPhoto(album, photo);
		this.i=i;
		this.album = album;
		JPanel total = new JPanel(new BorderLayout());
		JPanel buttons = new JPanel();
		JPanel display = new JPanel();
		//set any gaps i want
		Buttons.setVgap(10);
		display.setLayout(Photo);   //load up layouts in the panels
		buttons.setLayout(Buttons);
		//add stuff to the list
		//set up the photo display
		photo_img = new ImageIcon(curr_photo.getName());
		scaleImage = photo_img.getImage().getScaledInstance(480, 480, Image.SCALE_DEFAULT);
		photo_img.setImage(scaleImage);
		picture= new JLabel(photo_img);
		display.add(picture);
		total.add(display);
		//set up buttons
		for(int j = 0; j<button.length; j++){
			button[j] = new JButton(texts[j]);
			button[j].setPreferredSize(new Dimension(100,20));
			buttons.add(button[j]);
			button[j].setActionCommand(texts[j]);
			button[j].addActionListener(this);
		}
		//set up button panel
		buttons.setBorder(BorderFactory.createEmptyBorder(5, 10 ,10, 10));
		//add everything to total
		total.add(display);
		total.add(buttons,BorderLayout.SOUTH);
		total.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		total.setBorder(BorderFactory.createTitledBorder("Photo Info"));
		add(total);
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(texts[0].equals(e.getActionCommand())){
			padre.slideShow(i+1, album);
		} else if(texts[1].equals(e.getActionCommand())){
			padre.slideShow(i-1, album);
		} else if(texts[2].equals(e.getActionCommand())){
			padre.loadAlbum();
		}
	}
}
