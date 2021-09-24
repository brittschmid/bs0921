package bs0921;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import bs0921.Tool.ToolCode;

/**
 * This is a POS system for tool rentals
 * 
 * @author Brittnay Schmid
 *
 */
public class ToolPOS {

	private static Scanner in = new Scanner(System.in);

	/**
	 * @param args {tool code} {rental days} {discount percent} {checkout date}
	 */
	public static void main(String[] args) {
		ToolCode code = null;
		int rentalDays = 0;
		int discountPercent = 0;
		LocalDate checkoutDate = null;

		System.out.println("Welcome to the Tool POS system!");
		try {
			if (args.length == 4) {
				code = Tool.ToolCode.valueOf(args[0]);
				rentalDays = Integer.parseInt(args[1]);
				discountPercent = Integer.parseInt(args[2]);
				checkoutDate = LocalDate.parse(args[3], RentalAgreement.dateFormat);
			} else {

				List<String> toolCodeOptions = Tool.ToolCode.getToolCodes();
				String toolCodeInput = getStringInput("Please enter the code for the tool you would like to rent: ",
						"Invalid tool code! Valid tool codes are: " + toolCodeOptions, toolCodeOptions);
				code = Tool.ToolCode.valueOf(toolCodeInput);
		
				rentalDays = getIntInput("How many days would you like to rent " + code + "? ",
						"Invalid number of days. Please enter an integer greater than or equal to 1", 1, null);

				discountPercent = getIntInput("What % discount should be applied? ",
						"Please enter an integer greater than or equal to 0 and less than or equal to 100", 0, 100);

				checkoutDate = getDateInput("Check out date (mm/dd/yy): ", 
						"Invalid date format. EX: 09/22/21",
						RentalAgreement.dateFormat);
			}

			RentalAgreement agreement = rentTool(code, rentalDays, discountPercent, checkoutDate);
			System.out.println("\nHere is your rental agreement:\n" + agreement.toString());

		} catch (Exception e) {
			System.out.println("Invalid input provided. Goodbye!");
		}

		in.close();

	}

	public static RentalAgreement rentTool(ToolCode code, int rentalDays, int discountPercent, 
			LocalDate checkoutDate) throws Exception {
		if (rentalDays < 1) {
			throw new Exception("Whoops! Rental day count is not 1 or greater. Please try again.");
		}

		if (discountPercent < 0 || discountPercent > 100) {
			throw new Exception("Whoops! Discount percent is not in the range 0-100. Please try again.");
		}

		Tool tool = new Tool(code);

		return new RentalAgreement(tool, rentalDays, discountPercent, checkoutDate);
	}

	/**
	 * Gets a string input from the user
	 * 
	 * @param inputMessage The initial message to the user
	 * @param errorMessage The error message shown if input is invalid
	 * @param constraints A list of expected string values, null if none
	 * @return The string the user entered
	 */
	private static String getStringInput(String inputMessage, String errorMessage, List<String> constraints) {
		boolean tryAgain = true;
		String userInput = null;
		while (tryAgain) {
			try {
				System.out.print("\n" + inputMessage);
				userInput = in.nextLine();
				if (constraints != null && !constraints.contains(userInput)) {
					System.out.println(errorMessage);
					tryAgain = true;
					continue;
				}
				tryAgain = false;
			} catch (Exception e) {
				System.out.println(errorMessage);
				tryAgain = true;
			}
		}
		return userInput;
	}

	/**
	 * Gets a integer value from the user
	 * 
	 * @param inputMessage The initial message to the user
	 * @param errorMessage The error message shown if input is invalid
	 * @param minimum The minimum value allowed, inclusive
	 * @param maximum The maximum value allowed, inclusive
	 * @return The integer the user entered
	 */
	private static int getIntInput(String inputMessage, String errorMessage, 
			Integer minimum, Integer maximum) {
		boolean tryAgain = true;
		int userInput = 0;
		while (tryAgain) {
			try {
				System.out.print("\n" + inputMessage);
				userInput = Integer.parseInt(in.nextLine());
				if ((minimum != null && userInput < minimum) || 
						maximum != null && userInput > maximum) {
					System.out.println(errorMessage);
					tryAgain = true;
					continue;
				}
				tryAgain = false;
			} catch (Exception e) {
				System.out.println(errorMessage);
				tryAgain = true;
			}
		}
		return userInput;
	}

	/**
	 * Gets a date from the user
	 * 
	 * @param inputMessage The initial message to the user
	 * @param errorMessage The error message shown if input is invalid
	 * @param dateFormat The expected format of the date
	 * @return The date the user entered
	 */
	private static LocalDate getDateInput(String inputMessage, String errorMessage, 
			DateTimeFormatter dateFormat) {
		boolean tryAgain = true;
		LocalDate date = null;
		while (tryAgain) {
			try {
				System.out.print("\n" + inputMessage);
				String userInput = in.nextLine();
				date = LocalDate.parse(userInput, RentalAgreement.dateFormat);
				tryAgain = false;
			} catch (Exception e) {
				System.out.println(errorMessage);
				tryAgain = true;
			}
		}
		return date;
	}

}