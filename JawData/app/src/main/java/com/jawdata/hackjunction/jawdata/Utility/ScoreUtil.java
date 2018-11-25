package com.jawdata.hackjunction.jawdata.Utility;



public class ScoreUtil {
    public static final int
            CALM = 200,
            AGITATED = 300,
            IRRITATED = 400,
            STRESSED = 500;


    public static String getPersonalizedMsg(int computedScore) {
        String msg = "";
        if (computedScore < CALM) {
            msg = "Hey! You're doing fine - let's help you to keep it that way!";
        } else if (computedScore < AGITATED) {
            msg = "We can see you need some food to stay on top. Maybe try this recipe?";
        } else if (computedScore < IRRITATED) {
            msg = "We hope we're not bothering you - but if you'd made some food for yourself, yo'd feel better!";
        } else if (computedScore < STRESSED) {
            // oh shit they mad
            msg = "Not meant as an inconvenience - but we hope you're OK. Consider making some food to feel better";
        }
        return msg;
    }

    public static int calculateScore(int avgHeartbeat, int avgJaw, int deficit) {
        int base = 100+deficit;
        return (int) ((base*(avgHeartbeat)*0.32)/(1/avgJaw));
    }
}