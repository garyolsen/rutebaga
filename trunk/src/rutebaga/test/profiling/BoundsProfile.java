package rutebaga.test.profiling;

import java.rmi.server.UID;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import rutebaga.commons.math.MutableIntVector2D;
import rutebaga.model.entity.CharEntity;
import rutebaga.model.environment.Instance;

public class BoundsProfile
{
	static int DEFAULT_TEST_LENGTH = 5000000;

	static int INTHASH_TEST_LENGTH = DEFAULT_TEST_LENGTH;

	public static void main(String... args)
	{
		for (int i = 0; i < 100; i++)
		{
			rutebaga.commons.Log.log();
			rutebaga.commons.Log.log();
			rutebaga.commons.Log.log();
			rutebaga.commons.Log.log("TEST " + i);
			rutebaga.commons.Log.log();
			rutebaga.commons.Log.log();
			run();
		}
	}

	/**
	 * @param args
	 */
	public static void run()
	{
		long time;
		Random random = new Random();

		int testLength = INTHASH_TEST_LENGTH;

		// LOOPING

		rutebaga.commons.Log.log("Testing...");
		time = System.currentTimeMillis();
		for (int i = 0; i < testLength; i++)
		{

		}
		double average = (double) (System.currentTimeMillis() - time)
				/ INTHASH_TEST_LENGTH;
		rutebaga.commons.Log.log("Average loop time: " + average + "ms");

		// INSTANCE HASHING

		rutebaga.commons.Log.log("Initializing values...");
		Instance instanceArr[] = new Instance[testLength / 1000];
		/*
		for (int i = 0; i < instanceArr.length; i++)
			instanceArr[i] = new CharEntity();
			*/

		rutebaga.commons.Log.log("Testing...");
		time = System.currentTimeMillis();
		for (int i = 0; i < instanceArr.length; i++)
		{
			instanceArr[i].hashCode();
		}
		average = (double) (System.currentTimeMillis() - time)
				/ instanceArr.length;
		System.out
				.println("Average hash time for instances: " + average + "ms");

		// UID HASHING

		rutebaga.commons.Log.log("Initializing values...");
		UID uidArr[] = new UID[testLength];
		for (int i = 0; i < uidArr.length; i++)
		{
			uidArr[i] = new UID();
		}

		rutebaga.commons.Log.log("Testing...");
		time = System.currentTimeMillis();
		for (int i = 0; i < uidArr.length; i++)
		{
			uidArr[i].hashCode();
		}
		average = (double) (System.currentTimeMillis() - time) / uidArr.length;
		rutebaga.commons.Log.log("Average hash time for UIDs: " + average + "ms");

		// RANDOM NUMBER GENERATION SPEED

		rutebaga.commons.Log.log("Testing...");
		time = System.currentTimeMillis();
		for (int i = 0; i < testLength; i++)
		{
			random.nextInt();
		}
		average = (double) (System.currentTimeMillis() - time) / testLength;
		rutebaga.commons.Log.log("Average random int time: " + average + "ms");

		// HASHING

		rutebaga.commons.Log.log("Initializing values...");
		MutableIntVector2D iv2dArr[] = new MutableIntVector2D[testLength];
		for (int i = 0; i < testLength; i++)
			iv2dArr[i] = new MutableIntVector2D((short) random
					.nextInt(Short.MAX_VALUE), (short) random
					.nextInt(Short.MAX_VALUE));

		rutebaga.commons.Log.log("Testing...");
		time = System.currentTimeMillis();
		for (int i = 0; i < testLength; i++)
		{
			iv2dArr[i].hashCode();
		}
		average = (double) (System.currentTimeMillis() - time)
				/ INTHASH_TEST_LENGTH;
		rutebaga.commons.Log.log("Average hash time for MutableIntVector2D: "
				+ average + "ms");

		// HASHSET

		rutebaga.commons.Log.log("Initializing values...");
		Set<MutableIntVector2D> set = new HashSet<MutableIntVector2D>();
		for (int i = 0; i < 100; i++)
			set.add(iv2dArr[i]);

		rutebaga.commons.Log.log("Testing...");
		time = System.currentTimeMillis();
		for (int i = 0; i < testLength; i++)
		{
			set.contains(iv2dArr[i]);
		}
		average = (double) (System.currentTimeMillis() - time)
				/ INTHASH_TEST_LENGTH;
		rutebaga.commons.Log.log("Average MutableIntVector2D lookup time: " + average
				+ "ms");

		// ADDITION (NOT MUTABLE)

		rutebaga.commons.Log.log("Testing...");
		time = System.currentTimeMillis();
		for (int i = 0; i < testLength; i += 2)
		{
			iv2dArr[i].plus(iv2dArr[i + 1]);
		}
		average = (double) (System.currentTimeMillis() - time)
				/ (INTHASH_TEST_LENGTH / 2);
		rutebaga.commons.Log.log("Average MutableIntVector2D add time: " + average
				+ "ms");

		// ADDITION (MUTABLE)

		rutebaga.commons.Log.log("Testing...");
		time = System.currentTimeMillis();
		for (int i = 0; i < testLength; i += 2)
		{
			iv2dArr[i].accumulate(iv2dArr[i + 1]);
		}
		average = (double) (System.currentTimeMillis() - time)
				/ (INTHASH_TEST_LENGTH / 2);
		rutebaga.commons.Log.log("Average MutableIntVector2D add time: " + average
				+ "ms");

		// TEST FOR SMALL VALUES

		// rutebaga.commons.Log.log("Initializing values...");
		// for(int i=0; i<testLength; i++)
		// iv2dArr[i] = new MutableIntVector2D(random.nextInt(100),
		// random.nextInt(100));
		//		
		// rutebaga.commons.Log.log("Testing...");
		// time = System.currentTimeMillis();
		// for(int i=0; i<testLength; i++)
		// {
		// iv2dArr[i].hashCode();
		// }
		// average =
		// (double)(System.currentTimeMillis()-time)/INTHASH_TEST_LENGTH;
		// rutebaga.commons.Log.log("Average hash time for small MutableIntVector2Ds:
		// " + average + "ms");

		rutebaga.commons.Log.log("Initializing values...");
		int vals[] =
		{ random.nextInt(), random.nextInt() };
		double center = Math.min(vals[0], vals[1]);
		double radius = Math.max(vals[0], vals[1]);

		rutebaga.commons.Log.log("Testing...");
		time = System.currentTimeMillis();
		for (int i = 0; i < testLength; i++)
		{
			MutableIntVector2D v = iv2dArr[i];
			double t1 = (v.getX() - center) / (radius);
			double t2 = (v.getY() - center) / (radius);
			boolean value = (t1 * t1 + t2 * t2 <= 1.0);
		}
		average = (double) (System.currentTimeMillis() - time)
				/ INTHASH_TEST_LENGTH;
		System.out
				.println("Average time for bounds checking MutableIntVector2Ds: "
						+ average + "ms");

	}

}
