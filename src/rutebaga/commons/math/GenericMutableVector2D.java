package rutebaga.commons.math;

public interface GenericMutableVector2D<T extends Number, U extends GenericVector2D<T, U>, V extends GenericMutableVector2D<T, U, V>>
{
	V accumulate(GenericVector2D other);
	V detract(GenericVector2D other);
	V multiplyBy(double f);
	V divideBy(double f);
	V negate();
	void setX(T x);
	void setY(T y);
}
