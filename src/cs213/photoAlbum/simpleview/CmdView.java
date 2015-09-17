package cs213.photoAlbum.simpleview;

import cs213.photoAlbum.model.*;
import cs213.photoAlbum.control.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle.Control;

/**
 * @author Christopher Rios
 * @author John Ferrer
 */
public class CmdView {
	public static void main(String[] args) throws IOException
	{	
		// if login command is issued
		Controller control = new Controller();
		if(args.length>1){
			if(args[0].equalsIgnoreCase("login")){
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				if(control.login(args[1])){
					System.out.println("You have successfully logged in as "+control.currentUser.login_name);
					// start of interactive mode
					boolean done = false;
					while(!done){
				        String s = br.readLine();
				        String command;
				        String[] split = s.split("\\s+");
				        if(split[0].equalsIgnoreCase("createalbum")&&split.length==2){
				        	if(control.currentUser.addAlbum(split[1].substring(1,split[1].length()-1))){
				        		if(control.openAlbum(split[1].substring(1,split[1].length()-1))){
				        			System.out.println("Created album for user "+control.currentUser.login_name+":\n"+split[1]);
				        		} else {
				        			System.out.println("There was an issue trying to open the album.");
				        		}
				        	} else {
				        		System.out.println("Album exists for user "+control.currentUser.login_name+":\n"+split[1]);
				        	}
				        }
				        else if(split[0].equalsIgnoreCase("deletealbum")&&split.length==2){
				        	if(control.currentUser.deleteAlbum(split[1].substring(1,split[1].length()-1))){
				        		System.out.println("deleted album for user "+control.currentUser.login_name+":\n"+split[1]);
				        	} else {
				        		System.out.println("Album does not for user "+control.currentUser.login_name+":\n"+split[1]);
				        	}
				        }
				        else if(split[0].equalsIgnoreCase("listalbums")&&split.length==1){
				        	Object[] list = control.currentUser.albums.navigableKeySet().toArray();;
				        	if(list!=null){
				        		for(int i = 0; i<list.length; i++){
				        			System.out.println(list[i]);
				        		}
				        	}
				        }
				        else if(split[0].equalsIgnoreCase("listphotos")&&split.length==2){
				        	control.getAlbum(split[1].substring(1,split[1].length()-1)).list_photos();
				        }
				        else if(split[0].equalsIgnoreCase("addphoto")&&split.length==4){
				        	if(control.getAlbum(split[3].substring(1,split[3].length()-1)).add_photo(split[1],split[2])){
				        		System.out.println("Photo Successfully added.");
				        	} else {
				        		System.out.println(split[1]+" "+ split[2]);
				        		System.out.println("There seems to have been an error adding this photo.");
				        	}
				        }
				        else if(split[0].equalsIgnoreCase("movephoto")&&split.length==4){
				        	if(control.movePhoto(split[1].substring(1,split[1].length()-1), split[2].substring(1,split[2].length()-1), split[3].substring(1,split[3].length()-1))){
				        		System.out.println("Photo Successfully moved.");
				        	} else {
				        		System.out.println("There seems to have been an error moving this photo.");
				        	}
				        }	
				        else if(split[0].equalsIgnoreCase("removephoto")){
					        if(control.getAlbum(split[2].substring(1,split[2].length()-1)).delete_photo(split[1].substring(1,split[1].length()-1))){
					        	System.out.println("Photo successfully removed.");
					        } else {
					        	System.out.println("There seems to have been an error removing the photo.");
					        }
					    }
				        else if(split[0].equalsIgnoreCase("addtag")){
					        // assuming currently open album being used.
				        	// use the open album command to open another album
				        	String[] tag = split[2].split(":");
				        	if(control.getPhotoFromCurrentAlbum(split[1].substring(1,split[1].length()-1)).addTag(tag[0], tag[1].substring(1,tag[1].length()-1))){
				        		System.out.println("Tag Successfully Added.");
				        	} else {
				        		System.out.println("There seemed to be an issue adding the tag.");
				        	}
					    }
				        else if(split[0].equalsIgnoreCase("deletetag")){
					        // see above comment
				        	String[] tag = split[2].split(":");
				        	if(control.getPhotoFromCurrentAlbum(split[1].substring(1,split[1].length()-1)).deleteTag(tag[0], tag[1].substring(1,tag[1].length()-1))){
				        		System.out.println("Tag Successfully Added.");
				        	} else {
				        		System.out.println("There seemed to be an issue adding the tag.");
				        	}
				        }
				        else if(split[0].equalsIgnoreCase("listphotoinfo")){
					        // see the comment above the comment above.
				        	Photos current = control.getPhotoFromCurrentAlbum(split[1].substring(1,split[1].length()-1));
				        	if(current!=null){
					        	System.out.println("Photo file name: "+current.getName()); 
					        	System.out.println("Album: "+control.getCurrentAlbum());
					        	System.out.println("Date: "+current.formatDate());
					        	System.out.println("Caption: "+current.getCaption());
					        	System.out.println("Tags:"); 
					        	current.listTags();
				        	} else {
				        		System.out.println("Photo "+split[1].substring(1,split[1].length()-1)+" does not exist");
				        	}
				        }
				        else if(split[0].equalsIgnoreCase("getphotosbydate")&&split.length==3){
				        	System.out.println("getPhotosByDate <start date> <end date> ");
				        	System.out.println("Photos for user "+control.currentUser.toString()+" in range "+split[1].substring(1,split[1].length()-1)+" to "+split[2].substring(1,split[2].length()-1)+": ");
				        	
				        	System.out.println(" - Album: <albumName>[,<albumName>]... - Date: <date> ");
				        }
				        else if(split[0].equalsIgnoreCase("getphotosbytag")&&split.length>1){
				        	for(int i = 1; i<split.length-1; i++){
				        		String[] secondSplit = split[i].split(":");
				        		System.out.println("Photos for user "+control.currentUser+" with tags "+secondSplit[1]);
					        	control.getAllPhotosWithTag(secondSplit[0], secondSplit[1]);
					        }
				        }
				        else if(split[0].equalsIgnoreCase("openalbum")){
				        	if(control.openAlbum(split[1].substring(1,split[1].length()-1))){
				        		System.out.println("Album "+control.getCurrentAlbum()+" Successfully opened.");
				        	} else {
				        		System.out.println("There was an issue opening the album.");
				        	}
				        }
				        else if(split[0].equalsIgnoreCase("logout")&&split.length==1){
				        	control.logout();
				        	done=true;
				        } else {
				        	System.out.println("Please enter a valid command.");
				        }
					}
				}
					// end of interactive mode
			}  else if(args[0].equalsIgnoreCase("adduser")){
				if(control.create_user(args[1], args[2])){
					System.out.println("User successfully created.");
				} else {
					System.out.println("User not created.");
				}
			} else if(args[0].equalsIgnoreCase("deleteuser")){
				if(control.delete_user(args[1])){
					System.out.println("User Successfully deleted.");
				} else {
					System.out.println("User not deleted.");
				}
			} else if(args[0].equalsIgnoreCase("listusers")){
				User[] userList = control.listUsers();
				for(int i = 0; i<userList.length; i++){
					System.out.println(userList[i].login_name);
				}
			}
		}
		
	}
}