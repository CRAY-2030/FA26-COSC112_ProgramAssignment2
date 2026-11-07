/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

// import libraries

/**
 *
 * @author Starter file(change to match your github username)
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class ElectricBillCalculator {
    public static void main(String[] args) throws FileNotFoundException {

        // Constants
        final double RATE1 = 0.10;      // first 100 kWh
        final double RATE2 = 0.15;      // next 200 kWh (101-300)
        final double RATE3 = 0.20;      // above 300 kWh
        final double LATE_FEE = 15.00;

        File inputFile = new File("Customer_Data.txt");

        if (!inputFile.exists()) {
            JOptionPane.showMessageDialog(null,
                    "Input file Customer_Data.txt was not found.");
        } else {
            Scanner cin = new Scanner(inputFile);

            // Read the record, checking that each piece is there
            if (!cin.hasNext()) {
                JOptionPane.showMessageDialog(null,
                        "Customer ID and customer name must be provided.");
            } else {
                String id = cin.next();

                if (!cin.hasNext()) {
                    JOptionPane.showMessageDialog(null,
                            "Customer ID and customer name must be provided.");
                } else {
                    String firstName = cin.next();

                    if (!cin.hasNext()) {
                        JOptionPane.showMessageDialog(null,
                                "Customer ID and customer name must be provided.");
                    } else {
                        String lastName = cin.next();

                        if (!cin.hasNextInt()) {
                            JOptionPane.showMessageDialog(null,
                                    "Electricity usage is missing or not a whole number.");
                        } else {
                            int usage = cin.nextInt();

                            if (usage < 0) {
                                JOptionPane.showMessageDialog(null,
                                        "Electricity usage cannot be negative.");
                            } else if (!cin.hasNext()) {
                                JOptionPane.showMessageDialog(null,
                                        "Payment status must be Y or N.");
                            } else {
                                String status = cin.next().toUpperCase();

                                if (!status.equals("Y") && !status.equals("N")) {
                                    JOptionPane.showMessageDialog(null,
                                            "Payment status must be Y or N.");
                                } else {

                                    // Electricity charge (progressive tiers)
                                    double charge;
                                    if (usage <= 100) {
                                        charge = usage * RATE1;
                                    } else if (usage <= 300) {
                                        charge = 100 * RATE1 + (usage - 100) * RATE2;
                                    } else {
                                        charge = 100 * RATE1 + 200 * RATE2
                                                + (usage - 300) * RATE3;
                                    }

                                    // Late fee and payment status text
                                    double lateFee;
                                    String statusText;
                                    if (status.equals("Y")) {
                                        lateFee = LATE_FEE;
                                        statusText = "Previous bill overdue";
                                    } else {
                                        lateFee = 0.0;
                                        statusText = "Previous bill not overdue";
                                    }

                                    double total = charge + lateFee;

                                    // Build the bill
                                    String bill = "MONTHLY UTILITY BILL\n"
                                            + "================================\n"
                                            + "Customer ID: " + id + "\n"
                                            + "Customer Name: " + firstName + " " + lastName + "\n"
                                            + "Electricity Usage: " + usage + " kWh\n\n"
                                            + "Electricity Charge: $" + String.format("%.2f", charge) + "\n"
                                            + "Late-Payment Fee: $" + String.format("%.2f", lateFee) + "\n\n"
                                            + "TOTAL AMOUNT DUE: $" + String.format("%.2f", total) + "\n"
                                            + "Payment Status: " + statusText + "\n";

                                    // Display and save
                                    JOptionPane.showMessageDialog(null, bill);
                                    PrintWriter out = new PrintWriter("CustomerBillReport.txt");
                                    out.print(bill);
                                    out.close();
                                }
                            }
                        }
                    }
                }
            }
            cin.close();
        }
    }


    
}



