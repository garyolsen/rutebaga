package rutebaga.commons;

/**
 * A vector in space.
 * 
 * @author Gary LosHuertos
 * 
 */
public class Vector {

        private final double[] components;
        private Double magnitude;
        private final int dimension;
        
        /**
         * Creates a new Vector using the specified components.
         * @param double The components to insert into the new Vector.
         */
        public Vector(double ... components)
        {
                dimension = components.length;
                this.components = new double[dimension];
                for(int idx=0; idx<components.length; idx++)
                {
                        this.components[idx] = components[idx];
                }
        }
        
        /**
         * Creates a new, empty Vector of the specified dimension.
         * @param int Dimension of the new Vector.
         */
        private Vector(int dimension)
        {
                this.components = new double[dimension];
        }
        
        /**
         * Returns the component of this Vector at the specified index.
         * @param idx Index of a component in this Vector.
         * @return The Component of this Vector at the location specified by idx.
         */
        public double get(int idx)
        {
                return components[idx];
        }
        
        /**
         * Returns the magnitude, or length, of this Vector.
         * @return The magnitude, or length, of this Vector.
         */
        public double getMagnitude()
        {
                if(magnitude == null)
                {
                        magnitude = 0.0;
                        for(double c : components)
                        {
                                magnitude += c*c; 
                        }
                        magnitude = Math.sqrt(magnitude);
                }
                return magnitude;
        }
        
        /**
         * Throws a IncompatibleDimensionException if the Vector passed in is not
         * of the same dimension as this Vector.
         * @param rhs A vector who's dimension is to be compared to the dimension of this Vector.
         */
        private void check(Vector rhs)
        {
                if(dimension != rhs.dimension)
                        throw new IncompatibleDimensionException(dimension, rhs.dimension);
        }
        
        /**
         * Returns the sum of this Vector and the Vector passed in while leaving this Vector alone.
         * Throws an IncompatibleDimensionException if the Vectors are of different dimension.
         * @param rhs A Vector to add to this Vector.
         * @return The Vector that is the sum of this vector and the Vector rhs that was passed in.
         */
        public Vector plus(Vector rhs)
        {
                check(rhs);
                Vector rval = new Vector(dimension);
                double components[] = rval.components;
                for(int idx=0; idx<dimension; idx++)
                {
                        components[idx] = this.components[idx] + rhs.components[idx];
                }
                return rval;
        }
        
        /**
         * Returns the difference between this Vector and the Vector passed in 
         * while leaving this Vector alone.  Throws an 
         * IncompatibleDimensionException when the vectors are of different dimension.
         * @param rhs A Vector to subtract from this Vector.
         * @return The Vector that is the difference between this vector and the Vector rhs that was passed in.
         * (this Vector minus Vector rhs).
         */
        public Vector minus(Vector rhs)
        {
                check(rhs);
                Vector rval = new Vector(dimension);
                double components[] = rval.components;
                for(int idx=0; idx<dimension; idx++)
                {
                        components[idx] = this.components[idx] - rhs.components[idx];
                }
                return rval;
        }
        
        /**
         * Returns the value of the dot product between this Vector and the Vector rhs.
         * This value will be rhs.getManitude()*this.getMagnitude()*cos(theta), where theta
         * is the angle between the two Vectors.
         * (If both Vectors have getMagnitude()==1, then the result will be the cosine
         * of the angle between the two vectors)
         * @param rhs A Vector to compare with this Vector with the dot product.
         * @return The value of the dot product between this Vector and the Vector rhs.
         * 
         */
        public double dot(Vector rhs)
        {
                check(rhs);
                double rval = 0;
                for(int idx=0; idx<dimension; idx++)
                {
                        rval += components[idx]*rhs.components[idx];
                }
                return rval;
        }
        
        /**
         * Leaves this Vector alone while returning a negative version of this Vector.
         * @return This Vector negated.
         */
        public Vector negate()
        {
                Vector rval = new Vector(dimension);
                double components[] = rval.components;
                for(int idx=0; idx<dimension; idx++)
                {
                        components[idx] = -this.components[idx];
                }
                return rval;
        }
}
