public class Main {
    public static void main(String[] args) {
        int a = 11, b = 9, c = 11;
        int x = 0;

        if (a > b && a > c) {
            x = a;
        } else if (b > c) {
            x = b;
        } else {
            x = c;
        }

        System.out.println("Największa wartość: " + x);

        // choinka
        int layers = 4;
        int height = 5;
        for (int i = 0; i < layers; i++) {
            for (int j = 1; j <= height; j++) {
                int layer = layers - i - 1;
                int offset = height - j + (layer * height) / 2;
                int starsCount = (i * height) + (j * 2 - 1);
                String row = " ".repeat(offset) +
                        "*".repeat(starsCount);
                System.out.println(row);
            }
        }
        // pień
        int finalWidth = ((layers + 1) * height) - 1;
        int trunkOffset = (finalWidth - height) / 2;
        for (int i = 0; i < layers; i++) {
            String row = " ".repeat(trunkOffset) +
                    "#".repeat(height + 1);
            System.out.println(row);
        }

    }
}