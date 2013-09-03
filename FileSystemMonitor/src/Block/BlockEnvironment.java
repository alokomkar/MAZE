package Block;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

public class BlockEnvironment {

	protected FileInputStream fis; // all blocks share the input stream
	private Vector<BlockContainer> myBlockContainers;
	@SuppressWarnings("unused")
	private Vector<BlockContainer> buffer; // this is the buffer for each
											// operation of reading
	private int counter; // counter for the drive to get
	private int numDrives;

	// @SuppressWarnings("unused")
	// private int parityCounter = 0; //we use this to count before using parity
	// @SuppressWarnings("unused")
	// private BlockContainer parityRef = null; //reference to use parity block
	// when needed
	protected static String PATH;

	protected static String operation = null;

	public BlockEnvironment() {
	}

	public BlockEnvironment(int numDrives, String PATH) {
		this.myBlockContainers = new Vector<BlockContainer>();
		this.numDrives = numDrives;
		this.buffer = new Vector<BlockContainer>();
		BlockEnvironment.PATH = PATH;
	}

	public int getBlockContainerSizeofFirstField() {
		return myBlockContainers.get(0).DataBlocks.size();
	}

	public void setOperation(String operation) {
		BlockEnvironment.operation = operation;
	}

	public String getOperation() {
		return BlockEnvironment.operation;
	}

	public void AddBlock(BlockContainer bc) {
		this.myBlockContainers.add(bc);
	}

	public void execute() {

		if (!(this.myBlockContainers.isEmpty())) {
			for (int i = 0; i < this.myBlockContainers.size(); ++i) {
				this.myBlockContainers.get(i).DataBlocks.removeAllElements(); // cleanup
																				// all
																				// our
																				// elements
				this.myBlockContainers.get(i).bid = 0;
			}

			this.counter = -1; // cleanup
			// might have to cleanup bid also
			int s;
			try {

				while ((s = fis.read()) != -1) {
					// System.out.println(s);
					BlockContainer.buffer.add(s);
					// Once we have a buffer that is the same size - 1 as our
					// drives
					if (BlockContainer.buffer.size() == (this.numDrives - 1)) {
						// System.out.println("Buffer is full, its size is at "
						// + BlockContainer.buffer.size());
						int pointer = 0; // we are basing our for loop off of a
											// +1 index, rather, this will
											// correct our values
						for (int i = 0; i < this.numDrives; ++i) {
							++counter;
							BlockContainer temp = this.myBlockContainers
									.elementAt(this.counter % this.numDrives);

							if (temp.checkParityConstruction()) {
								temp.setIsParityMode();
								temp.addParityBlock(); // need to fix, id is
														// being incremented
														// twice here

							} else {
								temp.addBlock(BlockContainer.buffer
										.get(pointer));
								++pointer;
							}

						}// end for loop
						BlockContainer.buffer.removeAllElements();
						System.out.println();
					} // end if

				} // end while
			}// end try
			catch (Exception e) {
				try {
					fis.close();
				} catch (IOException e1) {
				}
			}
			// System.out.println("Buffer is full, its size is at " +
			// BlockContainer.buffer.size());
			int pointer = 0; // we are basing our for loop off of a +1 index,
								// rather, this will correct our values
			for (int i = 0; i < this.numDrives; ++i) {
				++counter;
				BlockContainer temp = this.myBlockContainers
						.elementAt(this.counter % this.numDrives);

				if (temp.checkParityConstruction()) {
					temp.setIsParityMode();
					temp.addParityBlock(); // need to fix, id is being
											// incremented twice here

				} else {
					try {
						temp.addBlock(BlockContainer.buffer.get(pointer));
						++pointer;
					} catch (Exception e) {
					}

				}

			}// end for loop
			BlockContainer.buffer.removeAllElements();
			System.out.println();

			try {

				fis.close();
			} catch (IOException e) {
			}

		}// end of it
	}

	public void reconstructFile(String PATH) {
		// relieve other connection handlers for deletion of certain files
		try {
			fis.close();
		} catch (Exception e2) {
		}

		// Remove original InputFile
		File file = new File("etc//" + PATH + "//InputFile." + PATH);
		if (file.exists()) {
			//System.out.println("Deleting Original Image...");
			//file.delete();
		}

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("etc//" + PATH + "//InputFileFinal."
					+ PATH);
		} catch (FileNotFoundException e) {
			 try {fos.close();} catch (IOException e1) {}
		}

		// Reconstruction of file
		Boolean exit = false;
		int nextBlock = 0;
		while (exit == false) {
			try {
				for (int i = 0; i < this.numDrives; ++i) {

					//System.out.println(this.myBlockContainers.get(i).DataBlocks.get(nextBlock).SSC());
					if (this.myBlockContainers.get(i).DataBlocks.get(nextBlock).SSC() == true) {
						System.out.println("We detected a bad byte that has flipped and it must be fixed, please run Recovery Mode right now...");
						System.out.println("The error was found in Disk "+ (i + 1) + " and in Block " + (nextBlock + 1));
						fos.close();
						return;
					}
					else 
					if (!(this.myBlockContainers.get(i).DataBlocks.get(nextBlock).getParity() == true)) {
						try {
							fos.write(this.myBlockContainers.get(i).DataBlocks.get(nextBlock).getData());
							
						} catch (Exception e) {
							try {
								fos.close();
							} catch (IOException e1) {

								e1.printStackTrace();
							}
							return;
						}

					}

				}
		
			} catch (Exception e) {
				
					
				return;
			}
			++nextBlock;

		}
		try {
			fos.close();
		} catch (IOException e) {

		}
	
		  
		  
	}

	public void outputToSource(String path) {
		for (int i = 0; i < this.myBlockContainers.size(); ++i) {
			this.myBlockContainers.get(i).outputToSource(path);
		}
	}

	public BlockContainer getBlock(int pos) {
		return this.myBlockContainers.get(pos);
	}

	public int getBlockContainerSize() {
		if (this.myBlockContainers == null) {
			return 0;
		}
		return this.myBlockContainers.size();
	}

	public void fis(FileInputStream fis) {
		this.fis = fis;
	}

	public void closeSession() {
		try {
			this.fis.close();
		} catch (Exception e) {
		}

	}

	public Boolean manipulateFile(int choice) {
		File f = new File("etc//" + PATH + "//OutputFile" + choice);
		if (f.exists()) {
			f.delete();
			return true;
		}
		return false;
	}

	public void recoverBlockContainer(int OutputFileID) {

		System.out.println("Outputfile is " + OutputFileID);

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("etc//" + PATH + "//OutputFile"
					+ OutputFileID);
		} catch (FileNotFoundException e) {
		}

		// Reconstruction of single file
		Vector<Integer> bufferVals = new Vector<Integer>();
		Boolean exit = false;
		int nextBlock = 0;
		while (exit == false) {

			try {
				for (int i = 0; i < this.numDrives; ++i) {
					// System.out.println("We are on block " + nextBlock);
					if (this.myBlockContainers.get(i).BlockContainerID != OutputFileID) {
						// System.out.println("Adding to buffer " +
						// this.myBlockContainers.get(i).DataBlocks.get(nextBlock).getData());
						bufferVals.add(this.myBlockContainers.get(i).DataBlocks
								.get(nextBlock).getData());

						// try {
						if (bufferVals.size() == this.numDrives - 1) {
							// System.out.println("Buffer full, expelling onto you");
							int myVals = 0;
							for (int j = 0; j < bufferVals.size(); ++j) {
								// in bytes for data
								myVals = myVals ^ bufferVals.get(j);
								//--TotalFileSize;
							}
							++nextBlock;

							// System.out.println("It is at " + TotalFileSize);
							//if (TotalFileSize >= 0) {
								fos.write(myVals);

								// --TotalFileSize;
								// System.out.println("Writting " + myVals);
							//}
							bufferVals.removeAllElements();

						}// end of if

						// } catch(Exception e) {System.out.println("first");}
					} // end of outer if

				} // end of for loop
			} catch (Exception e) {
				// Here we do the remainder of our values

				// FINAL write
				int myVals = 0;
				for (int j = 0; j < bufferVals.size(); ++j) {
					// in bytes for data
					myVals = myVals ^ bufferVals.get(j);
					//--TotalFileSize;
				}

				// System.out.println("It is at " + TotalFileSize);
				//if (TotalFileSize >= 0) {
					try {
						fos.write(myVals);
					} catch (IOException e1) {
	
					}

					// --TotalFileSize;
					// System.out.println("Writting " + myVals);
				//}
				bufferVals.removeAllElements();

				try {
					fos.close();
				} catch (IOException e1) {
					// System.out.println("OKI3");
					return;
				}
				return;
			}

		} // end of while
		try {
			// System.out.println("OKI1");
			fos.close();
		} catch (IOException e) {
			// System.out.println("OKI2");

		}
	}// end of function

	
	public static Boolean parityCheck(int val) {
		//System.out.println("Checking parity for " + val);
		String s = Integer.toBinaryString(val);
		//System.out.println(s);

		char[] myNumbers = s.toCharArray();
		int occurances = 0;
		for (int j = 0; j < myNumbers.length; ++j) {
			if (myNumbers[j] == '1') {
				++occurances;
			}
		}

		//System.out.println("There are " + occurances + " 1");

		if ((occurances % 2) == 0) {
			//System.out.println("parity to false, we dont need it");
			return true;
		}
		//System.out.println("parity to true, we need it");
		return false;
	}

	public void recoverBits(String PATH) {

		//Vector<Integer> bufferVals = new Vector<Integer>();
		Boolean exit = false;
		int nextBlock = 0;
		//Boolean hasError = false;
		//int recoveredValue = 0;
		int DriveError = -1; // +1 to represent the real value since we start at
								// 0
		int BlockError = -1; // +1 to represent the real value since we start at
								// 0
		int fixedValue = 0;
		int loc = 0;
		while (exit == false) {
			try {
				for (int i = 0; i < this.numDrives; ++i) {

					// if true, it means we have a leading zero which destroys
					// our even parity truth
					if (this.myBlockContainers.get(i).DataBlocks.get(nextBlock).SSC() == true) {
						DriveError = i + 1;
						BlockError = nextBlock + 1;
						System.out.println("Found error bit at Disk "+ DriveError + " and inside Block " + BlockError + " holding value "
								+ this.myBlockContainers.get(i).DataBlocks.get(nextBlock).getData());
						// here we fix it
						//fixes issue
						//this.myBlockContainers.get(DriveError-1).DataBlocks.get(BlockError-1).setHasParity(false);
						loc = i;

						for(int j = 0; j < this.numDrives; ++j) {
							if(j != i) {
								fixedValue = fixedValue ^ this.myBlockContainers.get(j).DataBlocks.get(BlockError-1).getData();
							}
						}
						this.myBlockContainers.get(i).DataBlocks.get(nextBlock).setData(fixedValue);
						return;
						/*
						
						//int temp = BlockError - 1;
						for (int j = 0; j < this.numDrives; ++j) {

							// System.out.println(j);
							// System.out.println("Our data is " +
							// this.myBlockContainers.get(j).DataBlocks.get(temp).getData());
							if (j == (DriveError - 1)) {


								System.out.println("Recovering from error ");
								//System.out.println("etc//" + PATH + "//OutputFile"+(DriveError));
								File f = new File("etc//" + PATH + "//OutputFile"+(DriveError));
								if(!f.delete()) {
									System.out.println("Error removing file...");
									
								}
								return;
								
								
						}
						
						// this.myBlockContainers.get(i).DataBlocks.get(nextBlock).setData(val);
						// this.myBlockContainers.get(i).DataBlocks.get(nextBlock).setHasParity(false);
						// //even parity before, now its bad to normal
					
						}
						*/
					} else {
					//	System.out.println("Block is good");
					}

				}
				++nextBlock;
			}// end of try

			catch (ArrayIndexOutOfBoundsException e) { 
				this.myBlockContainers.get(loc).DataBlocks.get(nextBlock).setData(fixedValue);
			
					return;
			
				// will be out of bounds if it finds error in last row, this is
				// a todo, which is just copy past code.

			}
		}

	} // end recoverBits

	public void setFlip(int container, int block) {

		System.out.println("C:" + container);
		System.out.println("B: " + block);

		this.myBlockContainers.get((container - 1)).DataBlocks.get((block - 1)).flip();

		// this.myBlockContainers.get(container
		// System.out.println("Modified " +
		// myBlockContainers.get(container).BlockContainerID);
		System.out.println("Flip complete...");
	}

}// end of class BlockEnvironment