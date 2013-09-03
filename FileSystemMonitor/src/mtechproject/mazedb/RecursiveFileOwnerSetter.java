package mtechproject.mazedb;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserPrincipalLookupService;

public class RecursiveFileOwnerSetter {

	static int counter = 0;
    public void walk( String path ) {

        File chosenfile = new File( path );
        File[] list = chosenfile.listFiles();
        for ( File f : list ) {
        	//Path file_dir = Paths.get(f.getParent());
    		//Path file = file_dir.resolve(f.getName());
    		//UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
    		
    		if ( f.isDirectory() ) {
                walk( f.getAbsolutePath() );
                InsertFileAttr.InsertFileAttrs(f.getName(),"Apoorva","Administrators");
                System.out.println( "Dir:" + f.getAbsoluteFile() );
            }
            else {
            	InsertFileAttr.InsertFileAttrs(f.getName(),"Apoorva","Administrators");
                System.out.println( "File:" + f.getAbsoluteFile() );
            }
        }
    }

    public static void main(String[] args) {
        RecursiveFileOwnerSetter fw = new RecursiveFileOwnerSetter();
        fw.walk("E:\\DVFS\\EmityCorp_DFS" );
    }
}
