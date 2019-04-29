// have a transpose method to give row vs. column

// make an arraylist of paircapsules
// paircapsules have two things in them
// input is a vectorpair, output will be double
// has to be collection of paircapsules

// will have to unroll matrix into single list, think logically about how it goes

// will maybe want dimensions in matrix class?

// don't have to do anything with the paircapsule class
// only concrete dispatcher and the matrix class
// have to implement runnable that the workers execute
// have to to vectorPair class

// in constructor of concreteDispatcher:
// this.workerCommand = new Command<VP, double> {
// 		pub double execute(VP input) {
// 			do dot product of two vectors here
//			double total = 0.0;
//			for (int i = 0, i < size; i++) {
//				total += a.get(i) * b.get(i);
//			}
//			return total;
//		}
//	};

/**
 *
 * @author Madeleine Gibson
 */

import java.util.Vector;
import java.lang.Math;
import java.util.ArrayList;
import java.text.NumberFormat;

public class Matrix {

	/**
	 * fields
	 */
	private ArrayList<Vector<Double>> rows;
	private int numRows;
	private int numColumns;

	/**
	 * constructor
	 * creates an instance of Matrix
	 * entries are randomized with a user-defined (non-inclusive) absolute value max
	 * first parameter is the number of rows, second parameter is the number of columns, third is an upper bound on randomized numbers
	 */
	public Matrix (int numRows, int numColumns, double max) {
		this.numRows = numRows;
		this.numColumns = numColumns;
		this.rows = new ArrayList<Vector<Double>>();

		for (int r = 0; r < numRows; r++) {
			this.rows.add(new Vector<Double>());
			for (int c = 0; c < numColumns; c++) {
				int sign = 1; /* determines if the randomized number will be positive or negative */
				if (Math.random() < 0.5) {
					sign = -1;
				}
				this.rows.get(r).add((Double) Math.random()*max*sign);
			} /* end of inner for loop */
		} /* end of outer for loop */
	} /* end of constructor */


	/**
	 * constructor
	 * creates an empty instance of Matrix
	 */
	public Matrix () {
		this.numRows = 0;
		this.numColumns = 0;
		this.rows = new ArrayList<Vector<Double>>();
	} /* end of constructor */


	/**
	 * transpose
	 */
	public Matrix transpose () {
		Matrix newMatrix = new Matrix();
		Vector tempVector = new Vector<Double>();
		for (int i = 0; i < this.numColumns; i++) {
			tempVector = this.getColumn(i);
			newMatrix.addRow(tempVector);
		} /* end of for loop */
		return newMatrix;
	} /* end of transpose */


	/**
	 * getNumRows
	 */
	public int getNumRows () {
		return this.numRows;
	} /* end of getNumRows */


	/**
	 * getRow
	 */
	public Vector<Double> getRow (int rowNum) {
		return this.rows.get(rowNum);
	} /* end of getRow */


	/**
	 * setRow
	 */
	public void setRow (int rowNum, Vector<Double> newRow) {
		this.rows.set(rowNum, newRow);
	} /* end of setRow */


	/**
	 * addRow
	 */
	public void addRow (Vector<Double> newRow) {
		this.numRows++;
		this.rows.add(newRow);
		this.numColumns = newRow.size();
	} /* end of addRow */


	/**
	 * getNumColumns
	 */
	public int getNumColumns () {
		return this.numColumns;
	} /* end of getNumColumns */


	/**
	 * getColumn
	 */
	public Vector<Double> getColumn (int colIndex) {
		Vector<Double> col = new Vector<Double>();
		for (int i = 0; i < numRows; i++) {
			col.add(this.rows.get(i).get(colIndex));
		} /* end of for */
		return col;
	} /* end of getColumn */


	/**
	 * setColumn
	 */
	public void setColumn (int colIndex, Vector<Double> newCol) {
		for (int i = 0; i < numRows; i++) {
			this.rows.get(i).set(colIndex, newCol.get(i));
		} /* end of for */
	} /* end of setColumn */


	/**
	 * toString
	 */
	public String toString() {
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(3);
		String str = "";
		for (int r = 0; r < this.numRows; r++) {
			for (int c = 0; c < this.numColumns; c++) {
				str += (format.format(this.rows.get(r).get(c)) + "\t");
			} /* end of inner for loop */
			str += "\n";
		} /* end of outer for loop */
		return str;
	} /* end of toString */


	public static Double dotProduct (Vector<Double> v1, Vector<Double> v2) {
		int size = v1.size();
		Double result = 0.0;
		for (int i = 0; i < size; i++) {
			result += v1.get(i)*v2.get(i);
		} /* end of for loop */
		return result;
	} /* end of dotProduct */

	public Vector<Double> mvMult (Vector<Double> v) {
		int numRows = this.getNumRows();
		Vector<Double> result = new Vector<Double>();
		for (int i = 0; i < numRows; i++) {
			Double d = dotProduct(this.getRow(i), v);
			result.add(d);
		}
		return result;
	} /* end of mvMult */




} /* end of file */
