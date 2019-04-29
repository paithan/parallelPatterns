/**
 * Represents a Pair of values.
 *
 * @author  Madeleine Gibson
 */

public class Pair<FirstType, SecondType> {
    // the first element in the pair
    private FirstType first;

    // the second element in the pair
    private SecondType second;

    /**
     * Class constructor.
     *
     * @param first  The first element in this Pair.
     * @param second  The second element in this Pair.
     */
    public Pair(/* FirstType first, SecondType second */) {
		/*
		this.first = first;
        this.second = second;
		*/
    }

    /**
     * Returns a String representation of this Pair.
     *
     * @return  A String, "(X, Y)", where X is the first element and Y is the second element.
     */
    public String toString() {
        return "(" + this.first + ", " + this.second + ")";
    }

    /**
     * Returns the first element.
     *
     * @return  The first element in this.
     */
    public FirstType getFirst() {
        return this.first;
    }

    /**
     * Returns the second element.
     *
     * @return  The second element in this.
     */
    public SecondType getSecond() {
        return this.second;
    }

    /**
     * Sets the first element.
     *
     * @param newFirst  The value to set the first element to.
     */
    public void setFirst(FirstType newFirst) {
    this.first = newFirst;
    }

    /**
     * Sets the second element.
     *
     * @param newSecond The value to set the first element to.
     */
    public void setSecond(SecondType newSecond) {
    this.second = newSecond;
    }

    /**
     * Tests whether two Pairs are equal.
     *
     * @param other  Another pair that might be equivalent to this.
     * @return  True if both the first element of this equals the first element of other and the second element of this equals the second element of other, false otherwise.
     */
    public boolean equals(Pair<FirstType, SecondType> other) {
        boolean firstsEqual = this.getFirst().equals(other.getFirst());
        boolean secondsEqual = this.getSecond().equals(other.getSecond());
        return firstsEqual && secondsEqual;
    }

    /**
     * Returns whether this equals another object.
     *
     * @param obj  The object to determine equivalence with this.
     * @return  True if obj is a Pair with elements equivalent to this, false otherwise.
     */
     public boolean equals(Object obj) {
         try {
             Pair<FirstType, SecondType> other = (Pair<FirstType, SecondType>) obj;
             return this.equals(other);
         } catch (ClassCastException e) {
             return false;
         }
     }

    /**
     * Returns a pair with the elements swapped.
     *
     * @return  A new Pair with the elements in reverse order.
     */
    public Pair<SecondType, FirstType> getReverse() {
		Pair<SecondType, FirstType> pair = new Pair<SecondType, FirstType>();
		pair.setFirst(this.getSecond());
		pair.setSecond(this.getFirst());
        return pair;
    }
}
