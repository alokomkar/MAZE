package mtechproject.filesysmonitor;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.LinkOption.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import mtechproject.datarecovery.RaidSplit;
import mtechproject.mazedb.FloggerDB;
import mtechproject.mazedb.GetFileAttr;
import mtechproject.mazedb.InsertFileAttr;
import mtechproject.mazedb.InsertTable;
import mtechproject.useraccounts.Database_Credentials;
import mtechproject.useraccounts.JavaFXLogin;

/**
 * Example to watch a directory (or tree) for changes to files.
 */

public class DirectoryWatch implements Runnable, Database_Credentials {

	private final WatchService watcher;
	private final Map<WatchKey,Path> keys;
	private final boolean recursive;
	private boolean trace = false;
	String username = null;
	@SuppressWarnings("unchecked")
	static <T> WatchEvent<T> cast(WatchEvent<?> event) {
		return (WatchEvent<T>)event;
	}

	/**
	 * Register the given directory with the WatchService
	 */
	private void register(Path dir) throws IOException {
		WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		//System.setOut(new PrintStream(new FileOutputStream("E:/DirMonitorLog.txt")));
		if (trace) {
			Path prev = keys.get(key);
			if (prev == null) {
				System.out.println("register:"+ dir);
			} else {
				if (!dir.equals(prev)) {
					System.out.println("update: "+prev+" ->"+dir);
				}
			}
		}
		keys.put(key, dir);
	}

	/**
	 * Register the given directory, and all its sub-directories, with the
	 * WatchService.
	 */
	private void registerAll(final Path start) throws IOException {
		// register directory and sub-directories
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
			throws IOException
			{
				register(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	/**
	 * Creates a WatchService and registers the given directory
	 * @param checkUser 
	 */
	public DirectoryWatch(Path dir, boolean recursive, String checkUser) throws IOException {
		this.watcher = FileSystems.getDefault().newWatchService();
		this.keys = new HashMap<WatchKey,Path>();
		this.recursive = recursive;
		this.username = checkUser;
		//System.setOut(new PrintStream(new FileOutputStream("E:/DirMonitorLog.txt")));

		if (recursive) {
			System.out.println("Scanning "+dir+"...\n");
			registerAll(dir);
			System.out.println("Done.");
		} else {
			register(dir);
		}

		// enable trace after initial registration
		this.trace = true;
	}


	/**
	 * Process all events for keys queued to the watcher
	 * @throws FileNotFoundException 
	 */
	//static int counter1 = 1;
	public void processEvents() throws FileNotFoundException {
		for (;;) {

			// wait for key to be signalled
			WatchKey key;
			try {
				key = watcher.take();
			} catch (InterruptedException x) {
				return;
			}

			Path dir = keys.get(key);
			if (dir == null) {
				System.err.println("WatchKey not recognized!!");
				// continue;
			}

			for (WatchEvent<?> event: key.pollEvents()) {
				WatchEvent.Kind kind = event.kind();

				// TBD - provide example of how OVERFLOW event is handled
				if (kind == OVERFLOW) {
					continue;
				}

				// Context for directory entry event is the file name of entry
				WatchEvent<Path> ev = cast(event);
				Path name = ev.context();
				Path filename = name;
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();

				//Make entry of newly created files
				InsertTable.InsertTable(filename, date.toString());
				Path child = dir.resolve(name);
				// System.setOut(new PrintStream(new FileOutputStream("E:/DirMonitorLog.txt")));
				// print out event

				//System.out.println(event.kind().name()+":"+ child);
				//int counter1 = 1;
				FileWriter fstream;
				try {
					fstream = new FileWriter("E:/monitorlog.doc",true);
					BufferedWriter out = new BufferedWriter(fstream);
					out.write(event.kind().name()+":"+ child.toString()+"|"+ username+"|"+dateFormat.format(date)+"\n");
					out.close();

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//counter1  = CreateExcelFile.InsertLogtoExcel(event.kind().name()+":"+ child.toString(), username, dateFormat.format(date), counter1);

				try{
					if(event.kind().name().toString() == "ENTRY_CREATE" && !child.toString().contains("~$") && !child.toString().contains(".tmp") && !child.toString().contains("~") && !child.toString().contains("$") ){
						System.out.println("File Created :: "+child.toString() );
						//RaidSplit r = new RaidSplit(child.toString());
						
						String dir1 = child.toString().replaceFirst("E", "C");
						System.out.println("File needs to be created at path :: " + dir1);

						// if the directory does not exist, create it
						File theDir = new File(child.toFile().getParent().toString().replaceFirst("E", "C"));

						// if the directory does not exist, create it
						if (!theDir.exists())
						{
							System.out.println("creating directory: " + child.toFile().getParent());
							boolean result = theDir.mkdir();  
							if(result){    
								System.out.println("DIR created");  
							}

						}
						if(child.toFile().isDirectory()){

							InsertFileAttr.InsertFileAttrs(child.toFile().getName(), username, InsertFileAttr.GetGroup(username));
							File f = new File(child.toFile().toString().replaceFirst("E", "C"));
							if(!f.exists())
							{
								f.mkdirs();
							}
							DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
							Date date1 = new Date();
							String file_group = GetFileAttr.GetFileGroup(child.toFile().getName());
							String user_group = GetFileAttr.GetUserGroup(username);
							update_user_file_forensics(child.toFile().getAbsolutePath(), username, "CREATE_DIR", dateFormat1.format(date1).toString(),user_group,file_group,"YES" );
						}else{
							InsertFileAttr.InsertFileAttrs(child.toFile().getName(), username, InsertFileAttr.GetGroup(username));
							
							RandomAccessFile f = new RandomAccessFile(child.toFile().toString().replaceFirst("E", "C"), "rw");
				            f.setLength(child.toFile().length());
							//File f1 = new File(child.toFile().toString().replaceFirst("E", "C"));
							//(works for both Windows and Linux)
							//f.mkdirs(); 
							//f1.createNewFile();
							DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
							Date date1 = new Date();
							String file_group = GetFileAttr.GetFileGroup(child.toFile().getName());
							String user_group = GetFileAttr.GetUserGroup(username);
							update_user_file_forensics(child.toFile().getAbsolutePath(), username, "CREATE_FILE", dateFormat1.format(date1).toString(),user_group,file_group,"YES" );

						}

						//Entry needs to be made in corresponding tables

					}

					FloggerDB.InsertLog(event.kind().name()+":"+ child.toString(), username,dateFormat.format(date) );

					if(!filename.getFileName().toString().endsWith(".tmp")){
						String group = InsertFileAttr.GetGroup(username);
						InsertFileAttr.InsertFileAttrs(filename.getFileName().toString(), username, group );	
					}


				}catch(Exception e){

				}
				// if directory is created, and watching recursively, then
				// register it and its sub-directories
				if (recursive && (kind == ENTRY_CREATE)) {
					try {
						if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
							registerAll(child);
						}
					} catch (IOException x) {
						// ignore to keep sample readbale
					}
				}
			}

			// reset key and remove from set if directory no longer accessible
			boolean valid = key.reset();
			if (!valid) {
				keys.remove(key);

				// all directories are inaccessible
				if (keys.isEmpty()) {
					// break;
				}
			}
		}
		//System.exit(0);
	}

	//update_user_file_forensics(chosenfile.getAbsolutePath(), checkUser, "ACCESS", dateFormat1.format(date1).toString(),user_group,file_group );
	private void update_user_file_forensics(String absolutePath,
			String checkUser, String action, String accesstime,
			String user_group, String file_group, String privileged) {
		final String DB_URL = "jdbc:mysql://localhost:3306/FLOGGERDB";

		Connection conn = null;
		//STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = null;
		//STEP 3: Open a connection
		//System.out.println("Connecting to a selected database...");
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt1=conn.createStatement();

			int count = 0;
			sql = "Select count(Filename) from user_document_access_table where Filename = '"+absolutePath+"'";
			ResultSet rs = stmt1.executeQuery(sql);

			if(!rs.next()){
				count = 1;
			}
			else count = rs.getInt(1)+1;

			sql = "Insert into user_document_access_table values('"+absolutePath+"','"+checkUser+"','"+action+"','"+accesstime+"','"+count+"','"+user_group+"','"+file_group+"','"+privileged+"') ";
			stmt1.executeUpdate(sql);
			//System.out.println(rs.getString(3).toString());


			stmt1.close();
			conn.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}



	}


	static void usage() {
		System.err.println("usage: java DirectoryWatch [-r] dir");
		System.exit(-1);
	}

	/*public static void main(String[] args) throws IOException {
        // parse arguments
        /*if (args.length == 0 || args.length > 2)
            usage();
        boolean recursive = false;
        int dirArg = 0;
        if (args[0].equals("-r")) {
            if (args.length < 2)
                usage();
            recursive = true;
            dirArg++;
        }*/
	/*boolean recursive = true;
        // register directory and process its events
        Path dir = Paths.get("E:/DVFS");
        new DirectoryWatch(dir, recursive).processEvents();
    }*/

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			this.processEvents();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

