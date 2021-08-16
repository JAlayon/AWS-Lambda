package com.alayon.aws.lambda;

import com.amazonaws.services.lambda.runtime.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataTypes {

    private Double instanceVariable = Math.random();
    private static Double staticVariable = Math.random();

    static{
        System.out.println("Inside Static Block");
    }
    public DataTypes() {
        System.out.println("Inside Constructor");
    }

    public void coldstartBasic(){
        Double localVariable = Math.random();
        System.out.println("Instance: " + instanceVariable);
        System.out.println("Static: " + staticVariable);
        System.out.println("Local: " + localVariable);
    }

    public String getNumber(float number){
        return "The number " + number + " multiplied by 10 is equals to: " + number*10;
    }

    public List<Float> getScores(List<String> names){
        Map<String,Float> studentsScores = new HashMap<>();
        studentsScores.put("John",92.4F);
        studentsScores.put("Bob", 80.5F);
        studentsScores.put("Jair", 95F);
        studentsScores.put("Mariana", 100F);
        studentsScores.put("Joel", 75.7F);

        List<Float> namesFiltered = names.stream()
                                .filter(studentsScores::containsKey)
                                .map(studentsScores::get)
                                .collect(Collectors.toList());

        return namesFiltered;
    }

    public void saveEmployeeData(Map<String,Integer> data){
        System.out.println(data);
    }

    public Map<String,List<Integer>> getStudentScores(){
        return Map.ofEntries(
                Map.entry("Jair",List.of(100,80,90,100)),
                Map.entry("Mariana", List.of(100,100,100,80))
        );
    }

    public ClinicalData getClinicals(Patient patient){
        System.out.println(patient.getName());
        System.out.println(patient.getSsn());
        var clinicalData = new ClinicalData();
        clinicalData.setBp("80/120");
        clinicalData.setHeartRate("80");
        return clinicalData;
    }

    public void getOutput(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        System.out.println("REQUEST-ID: " + context.getAwsRequestId());
        System.out.println("FUNCTION-NAME: " + context.getFunctionName());
        System.out.println("REMAINING TIME IN MILLIS: " + context.getRemainingTimeInMillis());
        System.out.println("MEMORY LIMIT: " + context.getMemoryLimitInMB());
        System.out.println(System.getenv("rest-api-url"));
        int data;
        while((data= inputStream.read()) != -1){
            outputStream.write(Character.toLowerCase(data));
        }
    }

}
