//package something

/**
 * This represents an AbstractDispatcher for the SPMD Design Pattern.
 * @author Kyle Burke & Dale Skrien
 */
public abstract class AbstractDispatcher<TypeSentToWorkers, TypeReturnedByWorkers,
        InputType, OutputType> {

    //constants

    //instance variables

    //holds the data returned by the workers
    protected Iterable<PairCapsule<TypeSentToWorkers, TypeReturnedByWorkers>> aggregateData;
    //the command to send to the workers
    protected Command<TypeSentToWorkers, TypeReturnedByWorkers> workerCommand;

    //public methods

    /**
     * Performs a calculation.
     *
     * @param input  The data for the calculation.
     * @param numberOfWorkers the number of processors to use.
     * @return  The result of the calculation.
     */
    public OutputType service(InputType input, int numberOfWorkers) {
        this.splitWork(input, numberOfWorkers);
        this.callWorkers();
        return this.combineResults();
    }

    /**
     * Performs a calculation.
     * Uses all available processors.
     *
     * @param input  The data for the calculation.
     * @return  The result of the calculation.
     */
    public OutputType service(InputType input) {
        return service(input, Runtime.getRuntime().availableProcessors());
    }


    //non-public methods

    /**
     * Breaks down the data into different objects, which will be individually passed
     * to workers.  This initializes the aggregateData field.
     *
     * @param input  The data to be divided into separate pieces.
     * @param numberOfWorkers the number of processors to use.
     */
    protected abstract void splitWork(InputType input, int numberOfWorkers);

    /**
     * Combines the collected data to perform the final calculation.
     *
     * @return  The result of the overall calculation.
     */
    protected abstract OutputType combineResults();

    /**
     *  Calls the workers, filling in the output fields of this.aggregateData.
     */
    private void callWorkers() {
        //create and run the workers using new threads
        for (PairCapsule<TypeSentToWorkers, TypeReturnedByWorkers> capsule :
                this.aggregateData) {
            Worker<TypeSentToWorkers, TypeReturnedByWorkers> worker =
                    new Worker<TypeSentToWorkers, TypeReturnedByWorkers>(this.workerCommand, capsule);
            new Thread(worker).start();
        }
        //now wait for all the workers to finish
        try {
            while(!this.areWorkersDone()) {
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns whether the workers have all finished.
     * Important: Don't call this function until after callWorkers has been invoked!
     * @return true if all workers have finished.
    */
    private boolean areWorkersDone() {
        for (PairCapsule<TypeSentToWorkers, TypeReturnedByWorkers> capsule :
                this.aggregateData) {
            if (!capsule.hasOutput()) {
                return false;
            }
        }
        return true;
    }


} //end of AbstractDispatcher
