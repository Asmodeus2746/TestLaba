package ru.asmi;

public class Main {
    public static void main(String[] args) {

        String[] words = new String[3];
        words[0] = "Garry";
        words[1] = "Germione";
        words[2] = "Ron";
        String[] resources = new String[3];
        resources[0] = "C:\\home\\asmi\\test\\result.txt";
        resources[1] = "C:\\home\\asmi\\test\\result.txt";
        resources[2] = "C:\\home\\asmi\\test\\result.txt";
        String res = "/home/asmi/result.txt";

        GetOccurencies occurenciesHandler = new OccurenciesHandler();
        try {
            occurenciesHandler.getOccurencies(resources, words, res);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
