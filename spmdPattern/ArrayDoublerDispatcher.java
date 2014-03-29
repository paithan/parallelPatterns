/*
 * File: ArrayDoublerDispatcher
 * User: djskrien
 * Date: 3/29/14
 */

import java.util.Vector;

/**
 * Its service method takes an array of integers as data and doubles all
 * the values in the array in place.
 */
public class ArrayDoublerDispatcher
    extends AbstractDispatcher<ArrayDoublerDispatcher.Range,int[],int[],int[]>
{

    public ArrayDoublerDispatcher() {
        this.workerCommand = new Command<Range, int[]>() {
            @Override
            public int[] execute(Range input) {
                for(int i = input.start; i < input.end; i++) {
                    input.data[i] = 2*input.data[i];
                }
                return input.data;
            }
        };
    }

    @Override
    protected Iterable<PairCapsule<Range,int[]>> splitWork(int[] input, int numberOfWorkers) {
        Iterable<PairCapsule<Range,int[]>> aggregateData =
                              new Vector<PairCapsule<Range, int[]>>(numberOfWorkers);
        for(int i = 0; i < numberOfWorkers; i++) {
            ((Vector<PairCapsule<Range, int[]>>) aggregateData).add(
                    new PairCapsule<Range, int[]>(
                            new Range(i * input.length / numberOfWorkers,
                                    (i + 1) * input.length / numberOfWorkers,
                                    input)));
        }
        return aggregateData;
    }

    @Override
    protected int[] combineResults(Iterable<PairCapsule<Range, int[]>> aggregateData) {
        return aggregateData.iterator().next().getOutput();
    }

    public static void main(String[] args) {
        ArrayDoublerDispatcher dispatcher = new ArrayDoublerDispatcher();
        int[] data = {1,2,3,4,5,6,7,8,9,10};
        data = dispatcher.service(data);
        System.out.println("New matrix:");
        for (int value : data)
            System.out.print(value + " ");
    }
    //------------- static inner class -------------

    public class Range {
        public int start, end;
        public int[] data;

        public Range(int s, int e, int[] d) {
            this.start = s; this.end = e; this.data = d;
        }
    }
}
