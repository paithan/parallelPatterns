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
    extends AbstractDispatcher<ArrayDoublerDispatcher.ArrayAnd2Ints,int[],int[],int[]>
{
    private int[] data;

    public ArrayDoublerDispatcher() {
        this.workerCommand = new Command<ArrayAnd2Ints, int[]>() {
            @Override
            public int[] execute(ArrayAnd2Ints input) {
                for(int i = input.start; i < input.end; i++) {
                    input.data[i] = 2*input.data[i];
                }
                return input.data;
            }
        };
    }

    @Override
    protected void splitWork(int[] input, int numberOfWorkers) {
        this.data = input;
        this.aggregateData = new Vector<PairCapsule<ArrayAnd2Ints, int[]>>(numberOfWorkers);
        for(int i = 0; i < numberOfWorkers; i++) {
            ((Vector<PairCapsule<ArrayAnd2Ints, int[]>>) this.aggregateData).add(
                new PairCapsule<ArrayAnd2Ints, int[]>(
                    new ArrayAnd2Ints(i*input.length/numberOfWorkers,
                            (i+1)*input.length/numberOfWorkers,
                            input)));
        }
    }

    @Override
    protected int[] combineResults() {
        return data;
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

    public class ArrayAnd2Ints {
        public int start, end;
        public int[] data;

        public ArrayAnd2Ints(int s, int e, int[] d) {
            this.start = s; this.end = e; this.data = d;
        }
    }
}
