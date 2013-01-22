/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myprojects.rasi;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 *
 * @author Lukasz
 */
public class DataGenerator 
{
    public static void main(String args[])
    {
        try
        {
            ProblemInstance pi = new ProblemInstance();
            pi.load_from_file("pgraph1");

            RunAlgorithm2 ra = new RunAlgorithm2(pi, new AlgorithmPartitionRandom(), AlgorithmSolveEnum.Brut);

            FileWriter fstream = new FileWriter("brut.txt");
            BufferedWriter out = new BufferedWriter(fstream);
            int result;
            for (int i = 100; i < 500; i += 20)
            {
                pi.W = i;
                result = ra.run();
                out.write(i + "\t" + result + "\n");
            }
            out.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
             
    }
}
