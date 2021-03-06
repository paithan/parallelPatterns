

/**
 * This is an encapsulation of two pieces of data.  It is used to handle passing of inputs and outputs between parts of code to ensure they remain coordinated.
 * @author Kyle Burke
 * @version 1.0
 */
public class PairCapsule<InputType, OutputType> {

	//constants
	
	//instance variables
	
	//the input field
	private InputType input;
	
	//the output field
	private OutputType output;
	
	//whether the output data has been set
	private boolean hasOutput;
	
	//constructors
	/**
	 * Creates a new instance of Capsule.
	 *
	 * @param input  The input for this capsule.  
	 */
	public PairCapsule(InputType input) {
        this.input = input;
        this.hasOutput = false;
	}
	
	//public methods
	
	/**
	 * Class toString method.
	 *
	 * @return String version of this capsule.
	 */
	public String toString() {
	    String description = "Pair Capsule (Input: " + this.getInput() + ", ";
	    if (this.hasOutput()) {
	        description += "Output: " + this.getOutput();
	    } else {
	        description += "No output has been set yet.";
	    }
	    description += ")";
	    return description;
	}
	
	/**
	 * Returns whether the data in here has not been set.
	 */
	public boolean hasOutput() {
        return this.hasOutput;
    }
    
    /**
     * Returns the input data.
     *
     * @return  The input this was instantiated with.
     */
    public InputType getInput() {
        return this.input;
    }
    
    /**
     * Returns the data stored within.
     *
     * @return  The output that was stored in the capsule.
     */
    public OutputType getOutput() {
        return this.output;
    }
    
    /** 
     * Sets the output contents of this.
     *
     * @param  The output to be stored in this capsule.
     */
    public synchronized void setOutput(OutputType output) {
        this.output = output;
        this.hasOutput = true;
    }
	
	//private methods
	
	/**
	 * Unit test for PairCapsule
	 */
	public static void main(String[] args) {
	    PairCapsule<String, Integer> capsule = new PairCapsule<String, Integer>("Hello!");
	    System.out.println("Created the capsule!");
	    System.out.println("Capsule: " + capsule);
	    System.out.println("Does the capsule have output yet?  " + (capsule.hasOutput() ? "Yes." : "No."));
	    capsule.setOutput(new Integer(5));
	    System.out.println("Added output!");
	    System.out.println("Capsule: " + capsule);
	}

} //end of PairCapsule class
