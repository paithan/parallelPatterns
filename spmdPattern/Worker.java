

/**
 * This represents a Worker for the SPMD Design Pattern.  It uses the Command Pattern.
 * @author Kyle Burke
 */
public class Worker<InputType, OutputType> implements Runnable {
	
	//instance variables
	//the command this will execute when run
	private Command<InputType, OutputType> command;
	
	//the encapsulation object for the "return value" when this has finished computing.
	private PairCapsule<InputType, OutputType> capsule;
	
	//constructors
	/**
	 * Creates a new instance of Worker.
	 *
	 * @param command  The Command this will execute while running.
	 * @param capsule  The PairCapsule containing the input to the Command.  After execution, this will contain the result of the computation in the output field.
	 */
	public Worker(Command<InputType, OutputType> command, PairCapsule<InputType, OutputType> capsule) {
        this.command = command;
        this.capsule = capsule;
	}
	
	//public methods
	
	/**
	 * Class toString method.
	 *
	 * @return  A String version of this Worker.
	 */
	public String toString() {
	    String description = "Worker with capsule " + this.capsule;
	    return description;
	}
	
	/**
	 * Runs this, executing the command and passing the output back to the master.
	 */
	public void run() {
        this.capsule.setOutput(this.command.execute(this.capsule.getInput()));
    }
	
	//private methods
	
	/**
	 * Unit test.
	 */
	public static void main(String[] args) {
	    //create a not-really-anonymous command
	    Command<String, Integer> countCharacters = new Command<String, Integer>() {
	        public Integer execute(String input) {
	            return input.length();
	        }
	    };
	    PairCapsule<String, Integer> capsule = new PairCapsule<String, Integer>("Hello");
	    Worker<String, Integer> worker = new Worker<String, Integer>(countCharacters, capsule);
	    System.out.println("Haven't run the Worker yet.");
	    System.out.println("Worker: " + worker);
	    System.out.println("Running the Worker!");
	    worker.run();
	    System.out.println("Worker: " + worker);
	}

} //end of Worker
