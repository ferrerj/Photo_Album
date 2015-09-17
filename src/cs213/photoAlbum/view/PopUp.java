package cs213.photoAlbum.view;



import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.*;

import cs213.photoAlbum.control.Controller;

/**
 * @author John Ferrer
 * manages most input and warning dialogs
 */
public class PopUp extends JFrame{
	// fields for double text boxes
    JTextField aField = new JTextField(40);
    JTextField bField = new JTextField(40);
    // return values
	/**
	 * {@value} response
	 * Integer used to get return value from other objects
	 */
	public int response;
	/**
	 * {@value} text
	 * String used to return the textbox value to other objects, useful when 2 text fields are needed
	 */
	public String text = null;
	/**
	 * {@value} secondText
	 * String used to return the textbox value to other objects for when 2 text fields are needed
	 */
	public String secondText = null;
	private static String data(int s){
		switch(s){
		case 0: return "User";
		case 1:	return "Album";
		case 2:	return "Photo";
		case 3:	return "Tag";
		default: return null;
		}
	}
	private static String func(int t){
		switch(t){
			case 0:	return "Login";
			case 1:	return "Add";
			case 2:	return "Edit";
			case 3:	return "Delete";
			case 4:	return "Search By ";
			default: return null;
		}
	}
	private void setDualFields(int struct, int type){
		text = "Name ";
		switch(struct){
		case 0: // user
			if(type==1){
				secondText = "Full Name";
			}
			break;
		case 2: // photo
			text = "Caption";
			break;
		case 3: // tag, search by tag
			text = "Tag Type ";
			secondText = "Tag Name";
			break;
		case 4: // search date range
			text = "Start Range";
			secondText = "End Range  ";
			break;
		}
		JPanel main = new JPanel(new BorderLayout());
		JPanel top = new JPanel();
	    top.add(new JLabel(text));
	    top.add(aField);
	    main.add(top, BorderLayout.NORTH);
	    System.out.println(secondText);
	    if(secondText!=null&&((type>1)||(type>1&&struct==0)||(type==1&&struct==3))){
	    	JPanel bot = new JPanel();
	    	//myPanel.add(Box.createHorizontalStrut(15)); // a spacer
	    	bot.add(new JLabel(secondText));
	    	bot.add(bField);
	    	main.add(bot, BorderLayout.SOUTH);
	    }
		if((response = JOptionPane.showConfirmDialog(this, main, 
	               ((struct==4&&type==4)?"Search By Date":func(type)+" "+data(struct)), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE))==JOptionPane.OK_OPTION){
			text = aField.getText();
			secondText = bField.getText();
		} else {
			text = null;
			secondText = null;
		}
	}
	/**
	 * @param type enumerates functions to be performed as the following:
	 * 0: login
	 * 1: add
	 * 2: edit
	 * 3: delete
	 * 4: search or logout, really more of a specialty function
	 * @param struct enumerates data types to operate on as the following:
	 * 0: user
	 * 1: album
	 * 2: photo
	 * 3: tag
	 */
	public PopUp(int type, int struct){
		super((type!=4)?((type==0)?func(type):func(type)+" "+data(struct)):(((struct==2)?func(type)+"  Tag":func(type)+"  Date Range")));
		// find the type of data represented
		int numTextBoxes = 0;
		switch(struct){
		case 0: // user
			switch(type){
			case 1:
				// add
				numTextBoxes++;
			case 0:
				// login
				numTextBoxes++;
			}
			break;
		case 1:	// album
			if(type==1){
				numTextBoxes++;
			}
			break;
		case 2: // photo
			if(type!=3){
				numTextBoxes = 2;
			}
			break;
		case 3: // tag
			if(type!=3){
				numTextBoxes = 2;
			}
			break;
		case 4:
			if(type==4){
				numTextBoxes = 2;
			}
		}
		switch(numTextBoxes){
			case 0: // is an error or delete
				if(type==3){
					// delete
					response = JOptionPane.showConfirmDialog(this, "Are You Sure you want to delete this "+data(struct)+"?");
					// 0 = yes, 1 = no, 2 = cancel
				} else if(type==4&&struct==0){
					response = JOptionPane.showConfirmDialog(this,  "Are you sure you want to logout?");
				} else {
					// error
					JOptionPane.showMessageDialog(this, "There has been a generic issue.");
				}
				break;
			case 1: // is add (user or album), or login
				text = (String)JOptionPane.showInputDialog(this, (type!=0)?"Add "+data(struct):"Enter User Credentials:", "", JOptionPane.PLAIN_MESSAGE);
				break;
			case 2: // is add or search photo or tag
				// edit not necessary in this case. overridden object will be provided below
				//setDualFields(struct, (type<4)?type:(struct==2)?4:5);
				setDualFields(struct,type);
				break;
		}
	}
	// object to make for editing, use struct int corresponding with the above
	// the next two objects are for setting the fields
	/**
	 * @param struct data type to be operated on
	 * @param a value of first field to be edited
	 * @param b value of second field to be edited, null if not needed
	 */
	public PopUp(int struct, String a, String b){
		super("Edit "+data(struct));
		aField.setText(a);
		if(b!=null&&struct>1){
			bField.setText(b);
		}
		setDualFields(struct, 2);
	}
	/**
	 * @param error error message desired in response to user actions
	 */
	public PopUp(String error){
		response = JOptionPane.showConfirmDialog(this,  error);
	}
}