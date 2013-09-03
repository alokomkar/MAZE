package Block;

//External Libraries
import java.io.*;
import java.util.Vector;

public class BlockContainer extends BlockEnvironment implements Runnable{

	// Attributes

	protected Vector<DataBlock> DataBlocks; //holds the DataBlock Objects
	protected int lower = -1;
	protected int nextVal = 0;
			//ID for all BlockContainers
	protected static int bcid = 0; // The ID will correspond to the DataContainers that are created
	protected int BlockContainerID; // The Unique Identifier for this Container	
	
	
		//ID for the DataBlocks specific to each BlockContainer
	protected int bid = 0;
	
	
		//I/O Streams
	protected FileOutputStream fos; //The FileOutputStream for this BlockContainer
	
	
		//Attributes
	protected int BlockSize; //The total size of each individual block object (we are setting it as 1024 bytes, made up of Integer indexes
	protected static int numDrives; // The number of hard drives that we have to work with
	protected static Vector<Integer> buffer; //This is a buffer that will be used to house runs of values, mainly for the parity

	protected Boolean isParityMode = false;
	
	
	
	//Default Constructor
	public BlockContainer() {}
	
	
	public DataBlock getDataBlock(int pos) {
		return this.DataBlocks.get(pos);
	}
	
	
	// Overloaded Constructor
	public BlockContainer(int numDrives, String path) {	
		this.BlockContainerID = ++BlockContainer.bcid; //Sets our ID for the BlockContainer
		this.DataBlocks = new Vector<DataBlock>(); //Instantiates our DataBlock Vector
		BlockContainer.numDrives = numDrives;
		buffer = new Vector<Integer>();
	}


	public int getBlockContainerSize(){
		return this.DataBlocks.size();
	}
	
	// Adds a DataBlock into the DataContainers Array of DataBlocks
	public void addBlock(int data) {
		++this.bid;
		DataBlock temp = new DataBlock(bid, data);
		temp.setParity(isParityMode); 
		this.DataBlocks.add(temp);
		if(temp.getParity() == true) {
		this.isParityMode = false; //directly	
		System.out.print("\tP"+data);	
		} else {
		System.out.print("\tD"+data);	
		}
		//System.out.println("Adding a new block in block container " + this.BlockContainerID + ", the block id is " + this.bid + ", the number value " + data);
	}

	
	public void addParityBlock() {
	
		int Val = 0;
		for(int i = 0; i < BlockContainer.buffer.size(); ++i) {
			Val = Val ^ BlockContainer.buffer.get(i);
		}
		addBlock(Val); //associate the id of this with the add
		
	}
	
	
		//We are hacking with bid
	public Boolean checkParityConstruction() {
		int temp = this.bid + 1;
		if( (temp % (BlockContainer.numDrives) == this.BlockContainerID) || (temp % (BlockContainer.numDrives) == this.BlockContainerID % (BlockContainer.numDrives))) {
				temp = 0;
				return true;
		} 
		temp = 0;
		return false;
	}
	

	public void addToBuffer(int data) {
		BlockContainer.buffer.add(data);
		//System.out.println("Adding into buffer " + data);	
	}
	
	public int getFromBuffer(int index) {
		return BlockContainer.buffer.get(index);
	}
	
	public void setIsParityMode() {
		this.isParityMode = true;
	}
	

	public void run() {
		String s = BlockEnvironment.operation;
		    if(s.compareTo("outputToSource") == 0) {
		    	outputToSource(BlockEnvironment.PATH);
		} else 
			if(s.compareTo("sourceToInput") == 0) {
			sourceToInput(BlockEnvironment.PATH);
			} 				
	}
	
	public void outputToSource(String type) {
		//System.out.println("ID is " + this.BlockContainerID);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("etc//" + type +"//OutputFile"+this.BlockContainerID);
		} catch (FileNotFoundException e1) {}
		for(int i = 0; i < this.DataBlocks.size(); ++i) {
			try {
				fos.write(this.DataBlocks.get(i).getData());
			} catch (IOException e) {}	
		}
		try {
			fos.close();
			//System.out.println("Closing connection");
		} catch (IOException e) {}
		//System.out.println("Were done");
		this.DataBlocks.removeAllElements(); //cleans up our work
	}
	
	
	
	public void sourceToInput(String type) { 
			
			this.DataBlocks.removeAllElements(); 
			this.bid = 0;
		
			//System.out.println("Inside " + this.BlockContainerID);
			
			FileInputStream fis = null;
			try {
				fis = new FileInputStream("etc//" + type +"//OutputFile"+this.BlockContainerID);
			} catch (FileNotFoundException e1) {
				System.out.println("OutputFile" + this.BlockContainerID + " not found...");
				try {
					fis.close();
				} catch (IOException e) {
				}
				return;
			}
			
						
			int val = 0;
			int parityCheck = 0;
			try {
				
			while( (val = fis.read()) != -1) {

				//System.out.println("Reading from container " + this.BlockContainerID + " value + " + val);
					DataBlock temp = null;
					temp = new DataBlock(this.bid, val);
					if(parityCheck %BlockContainer.numDrives == this.BlockContainerID - 1) //-1 is because our ids start at 1 and not 0, so mod will be bad
					{
						temp.setParity(true);
					}
					++parityCheck;
					
					//insert checkHasParity
					Boolean a = BlockContainer.parityCheck(val);
					temp.setHasParity(a);
		
					this.DataBlocks.add(temp);
			
				}
			
			} catch (IOException e1) 
			{ 
				try {
				fis.close();
				
			} catch (IOException e) {
				try {
					fis.close();
				} catch (IOException e2) {}
			}
		}
			
				
			try {
				fis.close();
				} catch (IOException e) {}
	}
			
	

	public int getNextDataBlockData() {
		
		if(this.nextVal != this.DataBlocks.size()) {
			++this.nextVal;
			return this.DataBlocks.get(this.nextVal).getData();
		}
		
		return -1;
	}
	
	
	public int getBlockContainerID() {
		return this.BlockContainerID;
	}
	
	
	public void printvall() {
		
		
	}
	
	
} //end of BlockContainer