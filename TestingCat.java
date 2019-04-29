import java.util.*;

public class TestingCat {
	public static void main(String[] args) {
		/*
		Matrix testMatrix = new Matrix(5, 4, 100);
		System.out.println(testMatrix.toString());
		System.out.println("numRows: " + testMatrix.getNumRows());
		System.out.println("numColumns: " + testMatrix.getNumColumns());

		//System.out.println(testMatrix.getRow(2).toString());

		//System.out.println(testMatrix.getColumn(0).toString());

		Matrix testMatrixTransposed = testMatrix.transpose();
		System.out.println(testMatrixTransposed.toString());
		System.out.println("numRows: " + testMatrixTransposed.getNumRows());
		System.out.println("numColumns: " + testMatrixTransposed.getNumColumns());
		*/


		int n = 2000000;
		int rows = 20;
		System.out.println("creating random matrix");
		Matrix m = new Matrix(rows, n, 10);
		System.out.println("done creating matrix");
		Vector<Double> v = new Vector<Double>();
		System.out.println("adding to vector");
		for (int i = 0; i < n; i++) {
			v.add(1.0);
		}
		System.out.println("done with vector");

		//System.out.println(m.toString());
		//System.out.println(v.toString());

		MatrixVectorMultiplier dispatcher = new MatrixVectorMultiplier();
		int numWorkers = m.getNumRows();
		Pair<Matrix, Vector<Double>> testPair = new Pair<>();
		testPair.setFirst(m);
		testPair.setSecond(v);
		System.out.println("pair created");

		System.out.println("starting parallel multiplication");
		long parallelStartTime = System.nanoTime();
		Vector<Double> parallelResult = dispatcher.service(testPair, numWorkers);
		long parallelEndTime = System.nanoTime();
		long parallelDuration = (parallelEndTime - parallelStartTime);
		System.out.println("done with parallel multiplication");

		System.out.println("starting sequential multiplication");
		long sequentialStartTime = System.nanoTime();
		Vector<Double> sequentialResult = m.mvMult(v);
		long sequentialEndTime = System.nanoTime();
		long sequentialDuration = (sequentialEndTime - sequentialStartTime);
		System.out.println("done with sequential multiplication");
		System.out.println("");


		//System.out.println("matrix:");
		//System.out.println(m.toString());
		//System.out.println("vector:");
		//System.out.println(v.toString());
		//System.out.println("");
		//System.out.println("parallel:");
		System.out.println("parallel time:   " + parallelDuration);
		//System.out.println("time:");


		//System.out.println("");
		//System.out.println("sequential:");
		System.out.println("sequential time: " + sequentialDuration);
		System.out.println("");
		boolean isCorrect = parallelResult.equals(sequentialResult);
		System.out.println("vectors are equal: " + isCorrect);
		System.out.println("");

		double speedup = ((double) sequentialDuration)/parallelDuration;
		System.out.println("speedup: " + speedup + "x");






	}
}
