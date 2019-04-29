/**
 *
 */

import java.util.*;

public class MatrixVectorMultiplier extends AbstractDispatcher< Pair<Vector<Double>, Vector<Double>>, /* TypeSentToWorkers */
															Double, /* TypeReturnedByWorkers */
															Pair<Matrix, Vector<Double>>, /* InputType */
															Vector<Double> >  /* OutputType */
{
	/**
	 * Constructor
	 */
	public MatrixVectorMultiplier () {
		this.workerCommand = new Command<Pair<Vector<Double>,Vector<Double>>, Double>() {
		 	public Double execute(Pair<Vector<Double>,Vector<Double>> input) {

		 		return dotProduct(input.getFirst(), input.getSecond());
			}
		};
	} /* end of constructor */


	/**
	 * splitWork
	 */
	public Iterable<PairCapsule<Pair<Vector<Double>, Vector<Double>>, Double>> splitWork (Pair<Matrix, Vector<Double>> pair, int numWorkers) {

		Matrix matrix = pair.getFirst();
		Vector vector = pair.getSecond();
		ArrayList<PairCapsule<Pair<Vector<Double>, Vector<Double>>, Double>> pcs = new ArrayList<PairCapsule<Pair<Vector<Double>, Vector<Double>>, Double>>();

		int numRows = matrix.getNumRows();
		int numColumns = matrix.getNumColumns();

		for (int i = 0; i < numRows; i++) {
			//PairCapsule<Pair<Vector<Double>, Vector<Double>>, Double> pc = new PairCapsule<Pair<Vector<Double>, Vector<Double>>, Double>();
			Pair<Vector<Double>, Vector<Double>> newPair = new Pair<Vector<Double>, Vector<Double>>();

			newPair.setFirst(matrix.getRow(i));
			newPair.setSecond(vector);
			PairCapsule<Pair<Vector<Double>, Vector<Double>>, Double> pc = new PairCapsule<Pair<Vector<Double>, Vector<Double>>, Double>(newPair);

			pcs.add(pc);
		} /* end of foreach loop */

		return pcs;

	} /* end of splitWork */


	/**
	 * combineResults
	 */
	public Vector<Double> combineResults (Iterable<PairCapsule<Pair<Vector<Double>, Vector<Double>>, Double>> doubles) {
		Vector<Double> v = new Vector<Double>();

		for (PairCapsule<Pair<Vector<Double>, Vector<Double>>, Double> pc : doubles) {
			double n = pc.getOutput();
			v.add(n);
		} /* end of foreach loop */
		return v;
	} /* end of combineResults */


	/**
	 * dotProduct
	 */
	public static Double dotProduct (Vector<Double> v1, Vector<Double> v2) {
		int size = v1.size();
		Double result = 0.0;
		for (int i = 0; i < size; i++) {
			result += v1.get(i)*v2.get(i);
		} /* end of for loop */
		return result;
	} /* end of dotProduct */

}
