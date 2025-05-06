package Statistics;

import org.apache.commons.math3.distribution.TDistribution;

public class Statistic {
    private double sum;
    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    private double sumSquare;
    public double getSumSquare() {
        return sumSquare;
    }

    public void setSumSquare(double sumSquare) {
        this.sumSquare = sumSquare;
    }

    private int count;

    public void setCount(int count) {
        this.count = count;
    }

    public Statistic() {
        this.sum = 0;
        this.sumSquare = 0;
        this.count = 0;
    }

    public void addValue(double value) {
        this.sum += value;
        this.sumSquare += value * value;
        this.count++;
    }
    public void reset(){
        this.sum = 0;
        this.sumSquare = 0;
        this.count = 0;
    }
    public double getAverage() {
        return this.count > 0 ? this.sum / this.count : 0;
    }

    public double getSampleVariance() {
        if (this.count < 2) {
            return 0; // Variance is undefined for n < 2
        }
        return (this.sumSquare - (this.sum * this.sum) / this.count) / (this.count - 1);
    }

    public double getSampleStandardDeviation() {
        return Math.sqrt(this.getSampleVariance());
    }

    public double[] getConfidenceInterval95() {
        if (this.count < 2) {
            return new double[]{0, 0}; // CI is undefined for n < 2
        }
        double mean = this.getAverage();
        double stdDev = this.getSampleStandardDeviation();

        // Použijeme t-rozdelenie z Apache Commons Math
        TDistribution tDist = new TDistribution(this.count - 1);
        double tValue = tDist.inverseCumulativeProbability(0.975); // 95% CI -> 0.975 kvantil

        double marginOfError = tValue * (stdDev / Math.sqrt(this.count));
        double downLimit = mean - marginOfError;
        double upLimit = mean + marginOfError;
        
        return new double[]{downLimit, upLimit};
    }

    public int getCount() {
        return this.count;
    }
}
