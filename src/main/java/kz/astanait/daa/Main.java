package kz.astanait.daa;

import java.io.IOException;
public class Main {
    public static void main(String[] args) {
        int[] sizes = {100, 1_000, 10_000, 50_000};
        String outputPath = "results/results.csv";

        Experiment experiment = new Experiment();

        try {
            experiment.run(sizes, outputPath);
            System.out.println("Experiments completed. Results saved to " + outputPath);
        } catch (IOException e) {
            System.err.println("Failed to write results: " + e.getMessage());
        }
    }
}