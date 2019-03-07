package com.example.omaruokis;

public class BodyCalc {
    private int weight;
    private int height;
    private int age;
    private String sex;
    private double activityLevel;


    public BodyCalc(int weight, int height, String sex, double activityLevel,
                    String dateofbirth, String date) {
        this.weight = weight;
        this.height = height;
        this.sex = sex;
        this.activityLevel = activityLevel;
        this.age = calcAge(dateofbirth, date);
    }

    public int calcBMR(){
        int sex = (this.sex.equals("M")) ? 5 : -161;
        return (int) (10 * this.weight + 6.25 * this.height - 5 * this.age + sex);

    }
    public int calcTEE(){
        return (int) (calcBMR() * this.activityLevel);

    }

    public double calcBMI(){
        return this.weight / (this.height / 100) * (this.height / 100);
    }

    public int calcAge(String dateofbirth, String date){
        String[] partsDOB = dateofbirth.split("\\.");
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