import java.util.Vector;

/**
 * File: IntArrayMapper
 * User: djskrien
 * Date: 3/29/14
 */
public class IntArrayMapper
    extends AbstractDispatcher<IntArrayMapper.Range, int[], int[], int[]>
{
    public IntArrayMapper(final Command<Integer, Integer> command) {
        this.workerCommand = new Command<Range, int[]>() {
            @Override
            public int[] execute(Range input) {
                for(int i = input.start; i < input.end; i++) {
                    input.data[i] = command.execute(input.data[i]);
                }
                return input.data;
            }
        };
    }

    @Override
    protected void splitWork(int[] input, int numberOfWorkers) {
        this.aggregateData = new Vector<PairCapsule<Range, int[]>>(numberOfWorkers);
        for(int i = 0; i < numberOfWorkers; i++) {
            ((Vector<PairCapsule<Range, int[]>>) this.aggregateData).add(
                    new PairCapsule<Range, int[]>(
                            new Range(i*input.length/numberOfWorkers,
                                    (i+1)*input.length/numberOfWorkers,
                                    input)));
        }
    }

    @Override
    protected int[] combineResults() {
        return ((Vector<PairCapsule<Range, int[]>>)this.aggregateData).get(0).getOutput();
    }

    /**
     * Unit test
     */
    public static void main(String[] args) {
        // double all the values in the array
        Command<Integer,Integer> times2 = new Command<Integer, Integer>() {
            @Override
            public Integer execute(Integer input) {
                return input*2;
            }
        };
        IntArrayMapper mapper = new IntArrayMapper(times2);
        int[] data = new int[]{1,2,3,4,5,6,7,8,9,10};
        data = mapper.service(data);
        System.out.println("\nNew matrix:");
        for (int value : data)
            System.out.print(value + " ");

        // square all the values in the array
        Command<Integer,Integer> square = new Command<Integer, Integer>() {
            @Override
            public Integer execute(Integer input) {
                return input*input;
            }
        };
        mapper = new IntArrayMapper(square);
        data = new int[]{1,2,3,4,5,6,7,8,9,10};
        data = mapper.service(data);
        System.out.println("\nNew matrix:");
        for (int value : data)
            System.out.print(value + " ");
    }

    //--------- static inner class --------
    public class Range {
        public int start, end;
        public int[] data;

        public Range(int s, int e, int[] d) {
            this.start = s; this.end = e; this.data = d;
        }
    }
}
