package cs213.photoAlbum.view;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import cs213.photoAlbum.control.Controller;

/**
 * @author John Ferrer
 * this is the main file, runs the entire program
 */
public class tester extends JFrame {
	static Controller control = null;
	JPanel cards;
	AlbumPanel album = null;
	PhotoPanel photo = null;
	PhotoView pic = null;
	AdminView admin = null;
	GridLayout All = new GridLayout(0,1);
	static tester test;
	
	public void logout() throws IOException{
		if(control!=null&&control.currentUser!=null){
			control.deleteAlbum("fa645d32dhs4tsr5h65zg23gztxh1yuf46g/d3gf4bh14jg7fd3)2s3gr645hj");
			control.logout();
			control = null;
		}
		this.dispose();
		test = new tester();
	}
	public void addStuff(JPanel i){
		this.getContentPane().removeAll();
		this.add(i);
		this.pack();
	}
	public void loadAlbum(){
		addStuff((album==null)?(album=new AlbumPanel(control, this)):album);
		album.populateAlbums();
	}
	public void loadPhotos(String album){
		if(album.equalsIgnoreCase("tempTagSearch")||album.equals("fa645d32dhs4tsr5h65zg23gztxh1yuf46g/d3gf4bh14jg7fd3)2s3gr645hj")){
			addStuff(new tagSearchResults(control, this));
		} else {
			addStuff(new PhotoPanel(control, album, this));
		}
	}
	public void loadPhoto(String album, String photo){
		addStuff(new PhotoView(photo, album, control, this));
	}
	public void slideShow(int i, String album){
		String[] photos = control.listPhotos(album);
		addStuff(new SlideShow(((i+=(i<0)?photos.length:0)%photos.length), photos[(i%photos.length)], album, control, this));
	}
	public void loadAdminView(){
		addStuff(new AdminView(this));
	}
	public tester(){
		super("Player");
		try {                                                                  //Start Setting the look and feel to nimbus
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		    // If Nimbus is not available, set gui to CrossPlatform
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException a) {
			// TODO Auto-generated catch block
			a.printStackTrace();
		} catch (InstantiationException b ) {
			// TODO Auto-generated catch block
			b.printStackTrace();
		} catch (IllegalAccessException c) {
			// TODO Auto-generated catch block
			c.printStackTrace();
		} catch (UnsupportedLookAndFeelException d) {
			// TODO Auto-generated catch block
			d.printStackTrace();
		}
		}
		setLayout(All);
		
		String text;
		try {
			text = (new PopUp(0,0)).text;
			if(text!=null&&(text.equalsIgnoreCase("admin")||text.equalsIgnoreCase("administrator"))){
				// you are an admin
				this.loadAdminView();
			} else {
				// you are not an admin
				control = new Controller();
				if(text!=null){
					if(control.login(text)){
						// if you are a user
						this.loadAlbum();
					} else {
						// user does not exist
						PopUp warning = new PopUp("User Does Not Exist. Try Again.");
						this.logout();
					}
				} else {
					System.exit(0);
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				try {
					if(control!=null&&control.currentUser!=null){
						control.deleteAlbum("fa645d32dhs4tsr5h65zg23gztxh1yuf46g/d3gf4bh14jg7fd3)2s3gr645hj");
						control.logout();
					}
					System.exit(0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.getWindow().dispose();
			}
		});
		this.setResizable(false);
		//this.setLocationRelativeTo(null);
		this.setSize(640,480);
		this.setVisible(true);
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String args[]) throws Exception{
		/*PopUp test = new PopUp(1, "test", "test2");
		System.out.println(test.text+" "+test.secondText);*/
		test = new tester();
	}
}
