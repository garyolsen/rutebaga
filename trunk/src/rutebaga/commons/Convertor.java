package rutebaga.commons;

public interface Convertor<T, U>
{
	T convert(U value);
}
