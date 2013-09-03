package mtechproject.catcher;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import mtechproject.useraccounts.FullScreenJFrame;

public class ChrootFileSystemView extends FileSystemView
{
    private final FileSystemView view = null;
    private final File root;
    FullScreenJFrame frame1 = null;

    public ChrootFileSystemView(File root, FullScreenJFrame frame)
    {
        //this.view = view;
    	this.frame1 = frame;
        this.root = root;
    }
    
    public ChrootFileSystemView(File root)
    {
    	this.root = root;
    }

    public File[] getRoots()
    {
        return new File[] {root};
    }

    public File getHomeDirectory()
    {
        return root;
    }

    public File getDefaultDirectory()
    {
        return root;
    }

    public boolean isRoot(File file)
    {
        return root.equals(file);
    }

    // override all other methods to delegate to view, e.g.
    public File getChild(File parent, String fileName)
    {
        return view.getChild(parent, fileName);
    }

	@Override
	public File createNewFolder(File containingDir) throws IOException {
		String Dirname = JOptionPane.showInputDialog("Enter the name of the folder");
		
		boolean success = new File(containingDir.getAbsolutePath()+"\\"+Dirname).mkdir();
		
		return new File(containingDir.getAbsolutePath()+"\\"+Dirname);
		
	}
}