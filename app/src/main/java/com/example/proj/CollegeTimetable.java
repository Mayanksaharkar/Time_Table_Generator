package com.example.proj;

import java.util.*;

public class CollegeTimetable {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of subjects:");
        int numSubjects = scanner.nextInt();

        String[] subjects = new String[numSubjects];
        int[] numLectures = new int[numSubjects];

        for (int i = 0; i < numSubjects; i++) {
            System.out.println("Enter the name of subject " + (i+1) + ":");
            subjects[i] = scanner.next();

            System.out.println("Enter the number of lectures for " + subjects[i] + ":");
            numLectures[i] = scanner.nextInt();
        }

        System.out.println("Enter the number of working days:");
        int numWorkingDays = scanner.nextInt();

        System.out.println("Enter the total number of slots in a day:");
        int numSlots = scanner.nextInt();

        String[][] timetable = new String[numWorkingDays][numSlots];

        int subjectIndex = 0;
        int lectureCount = 0;

        for (int i = 0; i < numWorkingDays; i++) {
            for (int j = 0; j < numSlots; j++) {
                timetable[i][j] = subjects[subjectIndex];
                lectureCount++;

                if (lectureCount == numLectures[subjectIndex]) {
                    subjectIndex++;
                    lectureCount = 0;
                }

                if (subjectIndex == numSubjects) {
                    subjectIndex = 0;
                }
            }
        }

        /*List<List<String>> list = new ArrayList<>();
        for (String[] row : array) {
            list.add(Arrays.asList(row));
        }

        // shuffle the list of lists
        Collections.shuffle(list);

        String[][] shuffledArray = new String[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            shuffledArray[i] = list.get(i).toArray(new String[0]);
        }
*/
        System.out.println("College Timetable:");

        /*for (int i = 0; i < numWorkingDays; i++) {
            for (int j = 0; j < numSlots; j++) {
                System.out.print(timetable[i][j] + "\t");
            }
            System.out.println();
        }*/

        List<String[]> list = Arrays.asList(timetable);

        // shuffle list
        Collections.shuffle(list);

        // convert back to 2D array
        String[][] shuffledArr = list.toArray(new String[0][0]);

        // print shuffled array
        for (String[] row : shuffledArr) {
            System.out.println(Arrays.toString(row));
        }
    }
}
