import java.util.Scanner;

public class SafeInput {

	/**
	 * @param pipe a Scanner opened to read from System.in
	 * @param prompt prompt for the user
	 * @return a String response that is not zero length
	 */
	public static String getNonZeroLenString(Scanner pipe, String prompt) {
		String retString = "";
		do {
			System.out.print("\n" + prompt + ": ");
			retString = pipe.nextLine();
		} while (retString.isEmpty());
		return retString;
	}

	/**
	 * Prompts for any integer and validates input
	 * @param pipe Scanner to read from
	 * @param prompt message to user
	 * @return a valid integer
	 */
	public static int getInt(Scanner pipe, String prompt) {
		int retInt = 0;
		boolean isValid = false;
		String trash = "";

		do {
			System.out.print("\n" + prompt + ": ");
			if (pipe.hasNextInt()) {
				retInt = pipe.nextInt();
				pipe.nextLine(); // clear newline
				isValid = true;
			} else {
				trash = pipe.nextLine();
				System.out.println("Invalid input: " + trash + ". Please enter an integer.");
			}
		} while (!isValid);

		return retInt;
	}

	/**
	 * Prompts for any double and validates input
	 * @param pipe Scanner to read from
	 * @param prompt message to user
	 * @return a valid double
	 */
	public static double getDouble(Scanner pipe, String prompt) {
		double retDouble = 0;
		boolean isValid = false;
		String trash = "";

		do {
			System.out.print("\n" + prompt + ": ");
			if (pipe.hasNextDouble()) {
				retDouble = pipe.nextDouble();
				pipe.nextLine(); // clear newline
				isValid = true;
			} else {
				trash = pipe.nextLine();
				System.out.println("Invalid input: " + trash + ". Please enter a numeric value.");
			}
		} while (!isValid);

		return retDouble;
	}

	/**
	 * Prompts for an integer within an inclusive range
	 * @param pipe Scanner to read from
	 * @param prompt message to user
	 * @param low low value of range
	 * @param high high value of range
	 * @return a valid integer within range
	 */
	public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
		int retInt = 0;
		boolean isValid = false;

		do {
			retInt = getInt(pipe, prompt + " [" + low + " - " + high + "]");
			if (retInt >= low && retInt <= high) {
				isValid = true;
			} else {
				System.out.println("Input out of range [" + low + " - " + high + "]: " + retInt);
			}
		} while (!isValid);

		return retInt;
	}

	/**
	 * Prompts for a double within an inclusive range
	 * @param pipe Scanner to read from
	 * @param prompt message to user
	 * @param low low value of range
	 * @param high high value of range
	 * @return a valid double within range
	 */
	public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {
		double retDouble = 0;
		boolean isValid = false;

		do {
			retDouble = getDouble(pipe, prompt + " [" + low + " - " + high + "]");
			if (retDouble >= low && retDouble <= high) {
				isValid = true;
			} else {
				System.out.println("Input out of range [" + low + " - " + high + "]: " + retDouble);
			}
		} while (!isValid);

		return retDouble;
	}

	/**
	 * Gets a [Y/N] confirmation from user
	 * @param pipe Scanner to read from
	 * @param prompt message to user
	 * @return true for Y, false for N
	 */
	public static boolean getYNConfirm(Scanner pipe, String prompt) {
		String input = "";
		boolean retVal = false;
		boolean isValid = false;

		do {
			System.out.print("\n" + prompt + " [Y/N]: ");
			input = pipe.nextLine();
			if (input.equalsIgnoreCase("Y")) {
				retVal = true;
				isValid = true;
			} else if (input.equalsIgnoreCase("N")) {
				isValid = true;
			} else {
				System.out.println("Invalid input: " + input + ". Please enter Y or N.");
			}
		} while (!isValid);

		return retVal;
	}

	/**
	 * Prompts for a string that matches a RegEx pattern
	 * @param pipe Scanner to read from
	 * @param prompt message to user
	 * @param regEx the regex pattern string
	 * @return a string matching the pattern
	 */
	public static String getRegExString(Scanner pipe, String prompt, String regEx) {
		String input = "";
		boolean isValid = false;

		do {
			System.out.print("\n" + prompt + ": ");
			input = pipe.nextLine();
			if (input.matches(regEx)) {
				isValid = true;
			} else {
				System.out.println("Input does not match pattern: " + input);
			}
		} while (!isValid);

		return input;
	}

	/**
	 * Prints a pretty header centered in stars
	 * @param msg message to center
	 */
	public static void prettyHeader(String msg) {
		int totalWidth = 60;
		int msgLength = msg.length();
		int padding = (totalWidth - 6 - msgLength) / 2;

		// Top Row
		for (int i = 0; i < totalWidth; i++) System.out.print("*");
		System.out.println();

		// Middle Row
		System.out.print("***");
		for (int i = 0; i < padding; i++) System.out.print(" ");
		System.out.print(msg);

		// Handle odd number padding to ensure exactly 60 width
		int currentWidth = 3 + padding + msgLength;
		while (currentWidth < totalWidth - 3) {
			System.out.print(" ");
			currentWidth++;
		}
		System.out.println("***");

		// Bottom Row
		for (int i = 0; i < totalWidth; i++) System.out.print("*");
		System.out.println();
	}
}