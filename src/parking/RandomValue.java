package Parking;

import java.util.Random;

public class RandomValue {

    Random random = new java.util.Random();

    public String generateRandomWords() {
        String randomStrings;

        char[] word = new char[7];
        for (int j = 0; j < 2; j++) {
            word[j] = (char) ('A' + random.nextInt(26));
        }
        for (int j = 2; j < 6; j++) {
            word[j] = (char) ('0' + random.nextInt(9));

        }
        word[6] = (char) ('A' + random.nextInt(26));
        randomStrings = new String(word);
        return randomStrings;
    }

    public int generateTimeFreeSpace() {
        int randomValue = ((random.nextInt(30) + 5) * 1000);
        return randomValue;

    }

    public int arrivalTimeCar() {
        int randomValue = (random.nextInt(100));
        return randomValue;
    }

    public boolean generateInvalid() {
        int intInvalid = random.nextInt(2);
        boolean invalid;
        if (intInvalid == 0)
            invalid = false;
        else invalid = true;
        return invalid;
    }

}
