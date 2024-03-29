package com.example.omaruokis.utilities;

/**
 * BodyCalc includes calculations for BMR: Basal metabolic rate, TEE: total energy expenditure,
 * BMI: body mass index and Age.
 * @author Juha
 */

public class BodyCalc {
    private static final double PAL_INCREMENTS = 0.3;
    private static final double PAL_MINIMUM = 1.0;
    private int weight;
    private int height;
    private int age;
    private String sex;
    private double activityLevel;

    /**
     * Constructs and initializes weight, height, sex, activityLevel.
     * @param weight amount
     * @param height height
     * @param sex defines male or female
     * @param activityLevel defines PAL, physical activity level
     * @param dateOfBirth Personal date of birth
     * @param date date
     */

    public BodyCalc(int weight, int height, String sex, double activityLevel,
                    String dateOfBirth, String date) {
        this.weight = weight;
        this.height = height;
        this.sex = sex;
        this.activityLevel = activityLevel;
        this.age = calcAge(dateOfBirth, date);
    }

    /**
     *
     * @return returns BMR, Basal metabolic rate
     */

    private int calcBmr(){
        int sex = (this.sex.equals("M")) ? 5 : -161;
        int bmrKcal = (int) (10 * this.weight + 6.25 * this.height - 5 * this.age + sex);
        return (int) (bmrKcal * 4.1868);
    }

    /**
     *
     * @return returns total energy expenditure
     */

    public int calcTee(){
        return (int) (calcBmr() * (this.activityLevel * PAL_INCREMENTS + PAL_MINIMUM));
    }

    /**
     *
     * @return returns body mass index
     */

    public double calcBmi(){
        return this.weight / ((this.height / 100.0) * (this.height / 100.0));
    }

    /**
     *
     * @param dateOfBirth date of birth set by user
     * @param date date today
     * @return returns age using calendar
     */

    private int calcAge(String dateOfBirth, String date){
        String[] partsDOB = dateOfBirth.split("\\.");
        String[] partsDate = date.split("\\.");

        //

        int age = Integer.parseInt(partsDate[2]) - Integer.parseInt(partsDOB[2]);
        if ((Integer.parseInt(partsDOB[1]) > Integer.parseInt(partsDate[1])
                || (Integer.parseInt(partsDOB[1]) == Integer.parseInt(partsDate[1]))
                && (Integer.parseInt(partsDOB[0])) > Integer.parseInt(partsDate[0]))){
            age--;
        }
        return age;
    }
}