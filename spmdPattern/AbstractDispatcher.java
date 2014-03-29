//package something

/**
 * This represents an AbstractDispatcher for the SPMD Design Pattern.
 * @author Kyle Burke & Dale Skrien
 */
public abstract class AbstractDispatcher<TypeSentToWorkers, TypeReturnedByWorkers,
        InputType, OutputType> {

    //constants

    //instance variables

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
        return this.combineResults(callWorkers(splitWork(input, numberOfWorkers)));
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
    protected abstract Iterable<PairCapsule<TypeSentToWorkers, TypeReturnedByWorkers>>
        splitWork(InputType input, int numberOfWorkers);

    /**
     * Combines the collected data to perform the final calculation.
     *
     * @return  The result of the overall calculation.
     */
    protected abstract OutputType combineResults(
            Iterable<PairCapsule<TypeSentToWorkers, TypeReturnedByWorkers>> aggregateData
    );

    /**
     *  Calls the workers, filling in the output fields of this.aggregateData.
     */
    private Iterable<PairCapsule<TypeSentToWorkers, TypeReturnedByWorkers>>
       callWorkers(
            Iterable<PairCapsule<TypeSentToWorkers, TypeReturnedByWorkers>>
                aggregateData) {
        //create and run the workers using new threads
        for (PairCapsule<TypeSentToWorkers, TypeReturnedByWorkers> capsule :
                aggregateData) {
            Worker<TypeSentToWorkers, TypeReturnedByWorkers> worker =
                    new Worker<TypeSentToWorkers, TypeReturnedByWorkers>(
                            this.workerCommand, capsule);
            new Thread(worker).start();
        }
        //now wait for all the workers to finish
        try {
            while(!this.areWorkersDone(aggregateData)) {
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aggregateData;
    }

    /**
     * Returns whether the workers have all finished.
     * Important: Don't call this function until after callWorkers has been invoked!
     * @return true if all workers have finished.
    */
    private boolean areWorkersDone(
            Iterable<PairCapsule<TypeSentToWorkers, TypeReturnedByWorkers>> aggregateData) {
        for (PairCapsule<TypeSentToWorkers, TypeReturnedByWorkers> capsule :
                aggregateData) {
            if (!capsule.hasOutput()) {
                return false;
            }
        }
        return true;
    }


} //end of AbstractDispatcher
