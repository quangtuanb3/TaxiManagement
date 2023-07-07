import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class DateTimeInputExample {
    public static void main(String[] args) {
        // Create a Scanner object to read input from the keyboard
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter a date and time
        System.out.print("Enter a date and time (dd-MM-yyyy HH:mm:ss): ");
        String inputDateTime = scanner.nextLine();

        // Create a SimpleDateFormat object with the desired format
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        try {
            // Parse the inputted date and time using the SimpleDateFormat object
            Date date = sdf.parse(inputDateTime);

            // Format the parsed date and time using the SimpleDateFormat object
            String formattedDateTime = sdf.format(date);

            // Print the formatted date and time
            System.out.println("Formatted date and time: " + formattedDateTime);
        } catch (ParseException e) {
            System.out.println("Invalid date and time format!");
        }

        // Close the scanner
        scanner.close();
    }
}
