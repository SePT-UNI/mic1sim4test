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
	private static int numSuccess = 0;

	private static String randomIntToOctalString()
	{
		int max = 2147483647;
		int min = -2147483648;
		Random random = new Random();
		int randomNum = random.nextInt(max);

		if(progress == 0)
			n1 = randomNum;
		else if (progress == 1)
			n2 = randomNum;

		String octal;
		octal = String.format("%11s", Integer.toOctalString(randomNum)).replace(' ', '0');
		System.out.println("Original number: " + randomNum);
		System.out.println("Generated octal: " + octal);
		return octal;
	}

	private static String randomNextIntToOctalString()
	{
		int max = 2147483647;
		int min = 0;
		Random random = new Random();
		int randomNum = random.nextInt(100);
		int tmp = 0;
		if(progress == 0)
		{
			n1 += randomNum;
			tmp = n1;
		}
		else if (progress == 1)
		{
			n2 += randomNum;
			tmp = n2;
		}

		String octal;
		octal = String.format("%11s", Integer.toOctalString(tmp)).replace(' ', '0');
		System.out.println("Original number: " + tmp);
		System.out.println("Generated octal: " + octal);
		return octal;
	}

	public static void setInput(String data)
	{
		readBuffer += data;
		if (readBuffer.contains("Inserire il primo numero ottale (11 cifre):\n") || readBuffer.contains("Inserire il secondo numero ottale (11 cifre):\n"))
		{
			System.out.println("\n");
			String aa = randomIntToOctalString();//randomIntToOctalString();
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
			repeat(risultato);
			progress = 0;

		}else if (progress == 2 && readBuffer.contains("continuare:"))
		{
			risultato = readBuffer.substring(12, readBuffer.length());
			repeat(risultato);
			progress = 0;
		}


	}

	private static void repeat(String risultato)
	{
		if(testRisultato(risultato))
		{
			System.out.println("OK");
			key_buffer.add('1');
			numSuccess++;
			System.out.println("Success:" + numSuccess);
		}
		else
			System.out.println("ERROR");
	}

	static boolean testRisultato(String risultato)
	{
		//System.out.println("D: n1:"+n1+" n2:"+n2);
		int somma = n1+n2;
		if (n1 > 0 && n2 > 0 && somma < 0 && risultato.contains("OVERFLOW"))
			return true;
		String javaRes = String.format("%32s", Integer.toBinaryString(somma)).replace(' ', '0');
		clearReadBuffer();
		//System.out.println("D: " + javaRes + " " + risultato.substring(0,31));
		return javaRes.substring(0,31).equals(risultato.substring(0,31));
	}

	public static void clearReadBuffer()
	{
		readBuffer = "";
	}
}
