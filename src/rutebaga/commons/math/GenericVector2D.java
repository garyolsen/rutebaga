package rutebaga.commons.math;

@SuppressWarnings("unchecked")
public interface GenericVector2D<T extends Number, U extends GenericVector2D<T, U>>
{
	U plus(GenericVector2D other);
	U minus(GenericVector2D other);
	U times(double f);
	U over(double f);
	T get(int idx);
	T getX();
	T getY();
	double getMagnitude();
}
