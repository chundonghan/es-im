package com.es.es_im.common.util;

import java.util.Random;

public class Salt
{
    private static final String salt[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

    public static String salting()
    {
        StringBuffer s = new StringBuffer();
        Random r = new Random();
        for (int i = 0; i < 6; i++)
        {
          s.append(salt[r.nextInt(salt.length)]);
        }
        return s.toString();
    }
    
    public static void main(String[] args)
    {
        salting();
    }
}
