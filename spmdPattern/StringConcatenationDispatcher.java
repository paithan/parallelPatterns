//package something

import java.util.*;

/**
 * This is a toy implementation of the AbstractDispatcher class.
 * @author Kyle Burke
 */
public class StringConcatenationDispatcher extends AbstractDispatcher<String, String, String, String> {

	
	//constructors
	/**
	 * Creates a new instance of StringConcatenationDispatcher.
	 */
	public StringConcatenationDispatcher() {
	    this.workerCommand = new Command<String, String>() {
            public String execute(String input) {
                return input + input.toUpperCase() + input;
            }
        };
	}
	
	//protected methods
    
    
    protected Iterable<PairCapsule<String, String>> splitWork(String input,
                                                              int numberOfWorkers) {
        // ignores the number of workers and uses one worker per character
        Iterable<PairCapsule<String, String>> aggregateData =
                                                new Vector<PairCapsule<String, String>>();
        for (int i = 0; i< input.length(); i++) {
            ((Vector<PairCapsule<String, String>>) aggregateData).add(new PairCapsule<String, String>(input.substring(i, i + 1)));
        }
        return aggregateData;
    }
    
    
    protected String combineResults(Iterable<PairCapsule<String, String>> aggregateData) {
        StringBuilder buffer = new StringBuilder();
        for (PairCapsule<String, String> capsule : aggregateData) {
            buffer.append(capsule.getOutput());
        }
        return buffer.toString();
    }
	
	/**
	 * Unit test.
	 */
	public static void main(String[] args) {
        StringConcatenationDispatcher dispatcher = new StringConcatenationDispatcher();
        System.out.println("Final string:\n  " + dispatcher.service("Sesame Street"));
	}

} //end of StringConcatenationDispatcher
