package Block;


public class DataBlock extends BlockContainer {

//Attributes

	private int data;
	private int BID;
	private Boolean isParity = false; //corresponds to the parity for redundancy
	private Boolean hasParity = false; //for even parity purposes
	
	
	public DataBlock(int BID, int data) {
		setData(data);
		this.data = data;
	}

	
	public void setData(int data) {
		this.data = data;
	}
	
	public void setHasParity(Boolean truth) {
		this.hasParity = truth;
	}
	
	public Boolean getHasParity() {
		return this.hasParity;
	}
	
	public int getData() {
		return this.data;
	}
	
	public int getBID() {
		return this.BID;
	}

	public void setParity(Boolean p) {
		this.isParity = p;
	}
	
	public Boolean getParity() {
		return (this.isParity == true);
	}

	
	public void flip() {
		
		//the easy way out is below
		//this.setHasParity(!(this.getHasParity())); //flips it around also
		System.out.println("Data was " + this.data);
		//
		//
		//
		//
		//
		//harder implementation, im happy :D
		int temp = this.data;
		String s = Integer.toBinaryString(temp);
		char[] myNumbers = s.toCharArray();
		if(myNumbers[0] == '0') {
			myNumbers[0] = '1';
		} else {
			myNumbers[0] = '0';
		}
		//flipping complete
		
		
		this.data = Integer.parseInt(String.copyValueOf(myNumbers),2);
		System.out.println("The value of the data is now " + this.data);
	}
	
	
	public Boolean SSC() {
	
		String s = Integer.toBinaryString(this.data);
		char[] myNumbers = s.toCharArray();
			int occurances = 0;
			for(int j = 0; j < myNumbers.length; ++j) {
				if(myNumbers[j] == '1') {
					++occurances;
				}
			}
				
			if(this.hasParity == true) {
				++occurances;
			}
	
			
			//System.out.println("The occurances right now are " + occurances + " for value " + this.data);
			if(occurances % 2 == 0) {
				return true;
			}
			
		
		return false;
	}
}