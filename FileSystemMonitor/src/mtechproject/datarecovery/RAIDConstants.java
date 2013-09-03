/**
 * @author Adithya & Alok
 * @brief The class contains the interface for RAID FSRD implimentation.
 */

package mtechproject.datarecovery;

public interface RAIDConstants 
{
	String	TEMP_DIR_NAME = "C:\\TempDir";
	String	TEMP_FILE_PREFIX = "TEMP_FILE";
	int		NUM_BAK_FILES = 4;
	int		DISK_BLOCK_SIZE = 4096; 
	String 	BACK_UP_DIR = TEMP_DIR_NAME+"\\BackupDir";
}
