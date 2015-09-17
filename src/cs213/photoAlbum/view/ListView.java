package cs213.photoAlbum.view;

import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import cs213.photoAlbum.control.Controller;

public class ListView extends JFrame {
	
	GridLayout All = new GridLayout(0,1);
	AlbumPanel albumView;
	// type declares the type of panel to be made
	// 0 for album
	// 1 for photo
	public ListView(int type, Controller control)
	{
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
		if(type==0){
			AlbumPanel total = new AlbumPanel(control);
			add(total);
		} else {
			PhotoPanel total = new PhotoPanel(control);
			add(total);
		}
		
		
	}
	public static void main(String[] args) throws ClassNotFoundException, IOException
	{
		Controller control = new Controller();
		ListView view = new ListView(0,control);
		control.login("chris");
		control.addPhoto("data/Lab.jpg", "cute", "hello");
		control.logout();
	view.setDefaultCloseOperation(EXIT_ON_CLOSE);
	view.setResizable(false);
	view.setLocationRelativeTo(null);
	view.setSize(300, 400);
	view.setVisible(true);
	view.pack();
	}
}
