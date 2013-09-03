package mtechproject.mazedb;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;

public class FileOwnerSetter {
	public static void main(String[] args){

		File chosenfile = new File("E:\\Student.xls");
		Path file_dir = Paths.get(chosenfile.getParent());
		Path file = file_dir.resolve(chosenfile.getName());
		UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
	    try {
			//Setting File Owner
	    	Files.setOwner(file, lookupService.lookupPrincipalByName("Alok Omkar"));
			
			//Getting File Owner
			UserPrincipal owner = Files.getOwner(file);
			/*PosixFileAttributes attr =
			    Files.readAttributes(file, PosixFileAttributes.class);
			System.out.format("%s %s %s%n",
			    attr.owner().getName(),
			    attr.group().getName(),
			    PosixFilePermissions.toString(attr.permissions()));*/
			//Getting File Group
			//GroupPrincipal group = Files
			String username = owner.getName();
			System.out.println(username);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
