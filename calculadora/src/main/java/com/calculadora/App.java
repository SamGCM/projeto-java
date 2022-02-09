package com.calculadora;

import com.actions.Actions;
import com.operation.Operation;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
        Actions action = new Actions();
        action.Conjunto();
        action.loadFile();
        action.total();
        action.saveData();
    }
}
