package codeviber.concurrency;

import org.apache.commons.lang3.time.StopWatch;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ComplexCalculation {

    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2)
            throws InterruptedException {
        BigInteger result = BigInteger.ONE;

        PowerCalculatingThread t1 =  new PowerCalculatingThread(base1,  power1);
        PowerCalculatingThread t2 =  new PowerCalculatingThread(base2,  power2);
        t1.start(); t2.start();
        t1.join(); t2.join();

        result = t1.getResult().add(t2.getResult());

        /*
            Calculate result = ( base1 ^ power1 ) + (base2 ^ power2).
            Where each calculation in (..) is calculated on a different thread
        */
        return result;
    }

    public BigInteger calculateResultWithFutures(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2)
            throws InterruptedException, ExecutionException {
        BigInteger result = BigInteger.ONE;

        CompletableFuture<BigInteger> future1 = CompletableFuture.supplyAsync(() -> {
            return computePow(base1, power1);
        });
        CompletableFuture<BigInteger> future2 = CompletableFuture.supplyAsync(() -> {
            return computePow(base2, power2);
        });

         return future2.thenCombine(future1, BigInteger::add)
                 .get();


    }

        private BigInteger computePow(BigInteger base, BigInteger pow) {
            BigInteger result = BigInteger.ONE;
            for (BigInteger i = BigInteger.ONE; i.compareTo(pow) <= 0; i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
            return result;
        }


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ComplexCalculation calc = new ComplexCalculation();
        System.out.println(calc.calculateResult(new BigInteger("12342"),
                new BigInteger("12342"),
                new BigInteger("12342"),
                new BigInteger("12342")));

        stopWatch.stop();
        System.out.println("Total time: " + stopWatch.getTime() + " ms");
        stopWatch.reset();
        stopWatch.start();

        System.out.println( calc.calculateResultWithFutures(new BigInteger("12342"),
                new BigInteger("12342"),
                new BigInteger("12342"),
                new BigInteger("12342")));
        stopWatch.stop();
        System.out.println("Total time: " + stopWatch.getTime() + " ms");

    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            for(BigInteger i = BigInteger.ONE; i.compareTo(power) <= 0; i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
        }

        public BigInteger getResult() { return result; }
    }
}