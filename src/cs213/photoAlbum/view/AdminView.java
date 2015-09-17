package cs213.photoAlbum.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.UIManager.*;

import java.io.IOException;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import cs213.photoAlbum.control.Controller;

/**
 * @author Chritopher Rios
 * This Panel deals with all of the Admin Inputs, such as Adding, Deleting and Removing Users
 *
 */
public class AdminView extends JPanel implements ActionListener {
	Controller control;
	private DefaultListModel<String> listModel;
	private JList<String> userList;
	private JButton add_user,delete_user,logout;
	private GridLayout Buttons = new GridLayout(3,1);
	private GridLayout List = new GridLayout(0,1);
	private GridLayout Info = new GridLayout(1,2);
	private String[] users;
	tester padre;
	/**
	 * @param parent Parent Frame of this panel
	 */
	public AdminView(tester parent)
	{
		padre = parent;
		try {                                                                  //Start Setting the look and feel to nimbus
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, set gui to CrossPlatform
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException a) {
			// TODO Auto-generated catch block
		} catch (InstantiationException b ) {
			// TODO Auto-generated catch block
		} catch (IllegalAccessException c) {
			// TODO Auto-generated catch block
		} catch (UnsupportedLookAndFeelException d) {
			// TODO Auto-generated catch block
		}
		}
		try {				//start up the controller
			control = new Controller();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		//make the panels
		JPanel total = new JPanel(new BorderLayout());
		JPanel test = new JPanel();
		JPanel users = new JPanel();
		JPanel info = new JPanel();
		//set any gaps i want
		Buttons.setVgap(10);
		Info.setHgap(20);
		//set layouts for the panels
		users.setLayout(List);
		test.setLayout(Buttons);
		info.setLayout(Info);
		//Set up the list
		listModel = new DefaultListModel<String>();
		userList = new JList<String>(listModel);
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //only one thing at a time
		userList.setVisibleRowCount(10);
		populateUsers();
		//userList.setPreferredSize(new Dimension(200,200));

		userList.setLayoutOrientation(JList.VERTICAL); //lists items vertically
		//put a scroll pane all up in
		JScrollPane userscroller = new JScrollPane(userList,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		userscroller.setPreferredSize(new Dimension(200,200));

		//Initialize buttons
		add_user = new JButton("Add User");
		add_user.setPreferredSize(new Dimension(100,20));
		delete_user = new JButton("Delete User");
		delete_user.setPreferredSize(new Dimension(100,20));
		logout = new JButton("Logout");
		logout.setPreferredSize(new Dimension(100,20));
		//add listeners
		add_user.addActionListener(this);
		add_user.setActionCommand("Add");
		delete_user.addActionListener(this);
		delete_user.setActionCommand("Delete");
		logout.addActionListener(this);
		logout.setActionCommand("Logout");
		//add to panels
		test.add(add_user);
		test.add(delete_user);
		test.add(logout);
		test.setBorder(BorderFactory.createEmptyBorder(5, 10 ,10, 10));//spacing!
		users.add(userscroller);
		users.setBorder(BorderFactory.createEmptyBorder(5 ,0 , 10, 10));//spacing!

		total.add(users);  //everything is contained in total
		total.add(test,BorderLayout.WEST);
		total.setBorder(BorderFactory.createRaisedSoftBevelBorder());  //nice border
		total.setBorder(BorderFactory.createTitledBorder("Welcome Admin"));
		add(total);
	}	
	
		/**
		 * Called to populate the List of users
		 */
		private void populateUsers() 
		{
			listModel.clear();
			users = control.list_users();
			for(int i =0;i<users.length;i++)
			{
			listModel.addElement(users[i]);
			}
			userList.setSelectedIndex(0);
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			PopUp popup;
	        if ("Add".equals(e.getActionCommand())) {
	        	popup = new PopUp(1,0);
				if(popup.text!=null&&popup.text.length()!=0)
				{
   
					try {
						if(!control.create_user(popup.text, popup.secondText)){
							popup = new PopUp("User Already Exists");
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
					}
				}
	            populateUsers();
	        } 
	        else if("Logout".equals(e.getActionCommand())) {
	            try {
	            	JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
					topFrame.setVisible(false);
					popup = new PopUp(4,0);
					if(popup.response==0)
		            {
		            	padre.logout();
		            } else {
		            	topFrame.setVisible(true);
		            }
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            
	        }
	        else
	        {
	        	popup = new PopUp(3,0);
				if(popup.response==0)
				{
					if(userList.getSelectedValue()==null)//checks to see if the list is empty
					{
					}
					else    //deletes the user
					{
					try {
						System.out.println(control.delete_user(userList.getSelectedValue()));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
					}
	        	populateUsers();
	        }
	    }
}
