/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processfile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author GL 502V2
 */
public class ProcessFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader("MyFile.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);

            FileWriter writer = new FileWriter("NewFile.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                line = line.toLowerCase();
                //EMOJIS
                String regex = "[^\\p{L}\\p{N}\\p{P}\\p{Z}]";
                line = line.replaceAll(regex, "");

                //PUNTUACIONES
                String linea = "";
                StringTokenizer st = new StringTokenizer(line);
                while (st.hasMoreTokens()) {
                    String temp = st.nextToken();
                    linea += temp.replaceAll("\\W", "") + " ";
                }
                line = linea;

                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
