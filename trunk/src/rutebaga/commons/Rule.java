package rutebaga.commons;

public interface Rule<T>
{
	boolean determine(T context);
}
