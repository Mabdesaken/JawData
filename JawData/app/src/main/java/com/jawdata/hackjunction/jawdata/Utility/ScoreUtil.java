package com.jawdata.hackjunction.jawdata.Utility;



public class ScoreUtil {
    public static final double
            CALM = 400,
            AGITATED = 600,
            IRRITATED = 800,
            STRESSED = 1000;


    public static String getPersonalizedMsg(double score) {
        String msg = "";
        if (score < CALM) {
            msg = "Hey! You're doing fine - let's help you to keep it that way!";
        } else if (score < AGITATED) {
            msg = "We can see you need some food to stay on top. Maybe try this recipe?";
        } else if (score < IRRITATED) {
            msg = "We hope we're not bothering you - but if you'd made some food for yourself, yo'd feel better!";
        } else if (score < STRESSED) {
            // oh shit they mad
            msg = "Not meant as an inconvenience - but we hope you're OK. Consider making some food to feel better";
        }
        return msg;
    }

    public static double calculateScore(int avgHR, int weight, int age, int duration, int intake, int limit) {
        if (avgHR == 0) return 0;

        double calculatedConsumption = ((-55.0969 + (0.6309 * avgHR) + (0.1988 * weight) + (0.2017 * age)) / 4.184) * 60 * (duration/1000);


        return (limit + calculatedConsumption) - intake;
    }
}