//package something

import java.util.*;

/**
 * This is a toy implementation of the AbstractDispatcher class.
 * @author Kyle Burke & Dale Skrien
 */
public class MatrixMultDispatcher
        extends AbstractDispatcher<MatrixMultDispatcher.TwoMatricesAndInteger,
                                   int[], MatrixMultDispatcher.TwoMatrices,int[][]> {

    //constructors
    /**
     * Creates a new instance of MatrixMultDispatcher.
     */
    public MatrixMultDispatcher() {
        this.workerCommand = new Command<MatrixMultDispatcher.TwoMatricesAndInteger,
                int[]>() {
            public int[] execute(TwoMatricesAndInteger input) {
                return new int[input.leftMatrix.length];
            }
        };
    }

    //protected methods

    protected Iterable<PairCapsule<TwoMatricesAndInteger, int[]>> splitWork(
            TwoMatrices input, int numberOfWorkers) {
        // ignores the numberOfWorkers and instead uses a worker for each
        // entry in the product matrix.
        Iterable<PairCapsule<TwoMatricesAndInteger, int[]>> aggregateData =
                new Vector<PairCapsule<TwoMatricesAndInteger, int[]>>();
        for (int i = 0; i< input.rightMatrix.length; i++) {
            ((Vector<PairCapsule<TwoMatricesAndInteger, int[]>>) aggregateData).add(
                    new PairCapsule<TwoMatricesAndInteger, int[]>(
                            new TwoMatricesAndInteger(input.leftMatrix,
                                    input.rightMatrix, i)
                    ));
        }
        return aggregateData;
    }

    protected int[][] combineResults(Iterable<PairCapsule<TwoMatricesAndInteger, int[]>>
                                     aggregateData) {
        int[][] productMatrix =
                 new int[aggregateData.iterator().next().getOutput().length][0];
        int i = 0;
        for (PairCapsule<TwoMatricesAndInteger,int[]> capsule : aggregateData) {
            productMatrix[i] = capsule.getOutput();
            i++;
        }
        return productMatrix;
    }

    /**
     * Unit test.
     */
    public static void main(String[] args) {
        MatrixMultDispatcher dispatcher = new MatrixMultDispatcher();
        int[][] leftMatrix = new int[][]{{1,2},{3,4}};
        int[][] rightMatrix = new int[][]{{5,6},{7,8}};
        int[][] productMatrix = dispatcher.service(new TwoMatrices(leftMatrix,rightMatrix));
        System.out.println("Product:");
        for(int[] row : productMatrix)
            System.out.println(Arrays.toString(row));
    }

    public static class TwoMatricesAndInteger {
        public TwoMatricesAndInteger(int[][] left, int[][] right, int index) {
            this.leftMatrix = left;
            this.rightMatrix = right;
            this.columnIndex = index;
        }
        public int[][] leftMatrix, rightMatrix;
        public int columnIndex;
    }

    public static class TwoMatrices {
        public TwoMatrices(int[][] leftMatrix, int[][] rightMatrix) {
            this.leftMatrix = leftMatrix;
            this.rightMatrix = rightMatrix;
        }

        public int[][] leftMatrix, rightMatrix;
    }

} //end of MatrixMultDispatcher
