package it.sept;

import java.util.Random;
import java.util.Vector;

public class HumanSimulator
{
	private static String readBuffer = "";
	public static Vector key_buffer = new Vector();
	private static int progress = 0;
	private static int n1 = 0;
	private static int n2 = 0;

	private static String randomIntToOctalString()
	{
		int max = 2147483647;
		int min = -2147483648;
		Random random = new Random();
		int randomNum = random.nextInt(max);
		boolean randomNegative = random.nextBoolean();

		if(progress == 0)
			n1 = randomNum * (randomNegative ? -1 : 1);
		else if (progress == 1)
			n2 = randomNum * (randomNegative ? -1 : 1);

		String octal;
		if(randomNegative)
		{
			System.out.println("Negative");
			octal = String.format("%11s", Integer.toOctalString(-randomNum)).replace(' ', '0');
		}
		else
		{
			System.out.println("Positive");
			octal = String.format("%11s", Integer.toOctalString(randomNum)).replace(' ', '0');
		}
		System.out.println("Original number: " + randomNum * (randomNegative ? -1 : 1));
		System.out.println("Generated octal: " + octal);
		return octal;
	}

	public static void setInput(String data)
	{
		readBuffer += data;
		if (readBuffer.contains("Inserire il primo numero ottale (11 cifre):\n") || readBuffer.contains("Inserire il secondo numero ottale (11 cifre):\n"))
		{
			System.out.println("\n");
			String aa = randomIntToOctalString();
			for (int i = 0; i < 11; i++)
			{
				key_buffer.add(aa.charAt(i));
			}
			readBuffer = "";
			progress++;
		}
		String risultato = "";
		if(progress == 2 && readBuffer.contains("OVERFLOW!\n"))
		{
			risultato = "OVERFLOW";
			if(testRisultato(risultato))
				System.out.println("OK");
			else
				System.out.println("ERROR");
			progress = 0;

		}else if (progress == 2 && readBuffer.contains("continuare"))
		{
			risultato = readBuffer.substring(12, readBuffer.length());
			if(testRisultato(risultato))
				System.out.println("OK");
			else
				System.out.println("ERROR");
			progress = 0;
		}


	}

	static boolean testRisultato(String risultato)
	{
		int somma = n1+n2;
		if (n1 > 0 && n2 > 0 && somma < 0 && risultato.contains("OVERFLOW"))
			return true;
		String javaRes = String.format("%32s", Integer.toBinaryString(somma)).replace(' ', '0');

		return javaRes.equals(risultato);
	}

	public static void clearReadBuffer()
	{
		readBuffer = "";
	}
}
