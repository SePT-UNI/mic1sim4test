package it.sept;

import java.util.Random;
import java.util.Vector;

public class HumanSimulator
{
	private static String readBuffer = ""; //Contiene i caratteri che vengono scritti con l'istruzione OUT sull'output dell'emulatore
	public static Vector key_buffer = new Vector(); //Contiene la lista di tasti premuti dall'utente che il programma iJVM preleva con l'istruzione IN

	private static int progress = 0; //Fase del programma iJVM
	private static int n1 = 0; //Primo numero
	private static int n2 = 0; //Secondo numero
	private static int numSuccess = 0; //Numero di test che hanno dato esito positivo

	/**
	 * Metodo che genera un numero ottale casuale e assegna a n1 o n2(in base alla fase del programma) il numero generato
	 */
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

		String octal = String.format("%11s", Integer.toOctalString(randomNum)).replace(' ', '0');
		return octal;
	}

	/**
	 * Metodo che viene chiamato quando viene eseguita un'istruzione OUT
	 * Aggiunge i caratteri ricevuti al buffer ed esegue il test
	 */
	public static void setInput(String data)
	{
		readBuffer += data;
		if (progress < 2 && (readBuffer.contains("Inserire il primo numero ottale (11 cifre):\n") || readBuffer.contains("Inserire il secondo numero ottale (11 cifre):\n")))
		{
			String aa = randomIntToOctalString();
			for (int i = 0; i < 11; i++)
			{
				key_buffer.add(aa.charAt(i)); //Scrive il numero in ottale un carattere alla volta
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
			repeat(risultato.substring(0,31));
			progress = 0;
		}


	}

	/**
	 * Controlla se il risultato è giusto, in caso positivo prosegue e scrive sulla console il risultato del test
	 * @param risultato
	 */
	private static void repeat(String risultato)
	{
		if(testRisultato(risultato))
		{
			key_buffer.add('1'); //Se il risultato è giusto preme 1 per continuare l'eseguzione del programma
			numSuccess++;
			String n1Octal = String.format("%11s", Integer.toOctalString(n1)).replace(' ', '0');
			String n2Octal = String.format("%11s", Integer.toOctalString(n2)).replace(' ', '0');
			System.out.println("Test " + numSuccess + "\tN1: " + n1Octal + "("+n1+")" + "\tN2: " + n2Octal + "("+n2+")" + "\tRes: " + risultato);
		}
		else
			System.out.println("ERROR"); //Se il risultato è sbagliato non preme 1 e scrive ERROR, di conseguenza si ferma
	}

	/**
	 * Testa il risultato prodotto dal programma iJVM confrontandolo con quello calcolato da java
	 * @param risultato
	 * @return
	 */
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
