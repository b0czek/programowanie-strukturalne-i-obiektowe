package Eksperyment;

public class Main {
    static long exceptionCounter = 0;

    public static void exHandler() {
        exceptionCounter++;
    }

    public  interface Test {
        void test1() throws Exception;
        void test2();
    }
    public static void tester(Test test) {
        long time1, time2;
        time1 = System.nanoTime();
        try {
            test.test1();
        }
        catch(Exception ex) {
            exHandler();
        }
        time2 = System.nanoTime();
        long deltaT1 = time2-time1;


        time1 = System.nanoTime();
        test.test2();
        time2 = System.nanoTime();
        long deltaT2 = time2-time1;
        System.out.println(deltaT1 +" " + deltaT2);
    }

    public static void main(String[] args) {

        // dokonczyc ten raport??
        tester(new Test() {
            @Override
            public void test1() throws ArithmeticException {
                int a = 1/0;
            }

            @Override
            public void test2() {
                try {
                    int a = 1/0;
                }
                catch(ArithmeticException ex) {
                    exHandler();
                }

            }
        });

        tester(new Test() {
            @Override
            public void test1() throws ArrayIndexOutOfBoundsException {
                int[] arr = new int[1];
                int a = arr[1];
            }

            @Override
            public void test2() {
                try {
                    int[] arr = new int[1];
                    int a = arr[1];
                }
                catch(ArrayIndexOutOfBoundsException ex) {
                    exHandler();
                }
            }
        });

    }


}
