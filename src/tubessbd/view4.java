/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubessbd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author TSR
 */
public class view4 {

    getData getter = new getData();
    private static Scanner scanner;
    private static Scanner inputStream;
    private static String inputQuery;

    private static String outputQuery;
    private static String inputAtributeName;
    private static String inputTableName;
    private static String inputWhereName;
    private static String inputWhereValue;
    private static String pkOn;
    private static String inputTable1;
    private static String inputTable2;

    private static boolean isTableFound;
    private static boolean isAttributeFound;
    private static boolean isTable1Found, isTable2Found, isPk1Found, isPk2Found;

    private static ArrayList<String> dbTableName;

    void doMainView4() throws IOException {
        scanner = new Scanner(System.in);
        String pil = "y";
        while (pil.equals("y")) {
            System.out.println("Input Query : ");
            System.out.print(">> ");
            inputQuery = scanner.nextLine();
//        takeAllData();
            checkSqlError(inputQuery.toLowerCase());
            System.out.println();
            System.out.println("Output : ");
            if (outputQuery == null) {
                System.out.println("Sql Syntax Error");
            }else{
                System.out.println(outputQuery);
            }
            System.out.println("Masih ingin menginput-query? (y/n)");
            pil = scanner.nextLine();
        }
    }
    //----------------------------------------------------------------------------------checkSqlTypo

    private static void checkSqlError(String input) throws IOException {
        String inputTemp = input;
        int numberOfSpace = 0;

        String regexBasic = "select.*from.*;";
        String regexBasicWhere = "select.*from.*where.*=.*;";
        String regexJoin = "select.*from.*join.*using.*(.*);";

        if (Pattern.matches(regexJoin, input)) { // kalo dia join
            //System.out.println("regex join");
            Matcher matcher = Pattern.compile("select (.*?) from").matcher(input);
            if (matcher.find()) {
                inputAtributeName = matcher.group(1);
            }
            matcher = Pattern.compile("from (.*?) join").matcher(input);
            if (matcher.find()) {
                inputTable1 = matcher.group(1);
            }
            matcher = Pattern.compile("join (.*?) using").matcher(input);
            if (matcher.find()) {
                inputTable2 = matcher.group(1);
            }
            matcher = Pattern.compile("using(.*?);").matcher(input);

            if (matcher.find()) {
                pkOn = matcher.group(1);
            }
            if (pkOn.contains("(")) {
                pkOn = pkOn.substring(pkOn.indexOf("(") + 1);
            }
            if (pkOn.contains(")")) {
                pkOn = pkOn.substring(0, pkOn.indexOf(")"));
            }

//            System.out.println("1. Join");
            checkSqlQuerryJoin();

        } else if (Pattern.matches(regexBasicWhere, input)) { // kalo dia where
            //System.out.println("regex basic where");
            Matcher matcher = Pattern.compile("select (.*?) from").matcher(input);
            if (matcher.find()) {
                inputAtributeName = matcher.group(1);
            }

            matcher = Pattern.compile("from (.*?) where").matcher(input);
            if (matcher.find()) {
                inputTableName = matcher.group(1);
            }

            matcher = Pattern.compile("where (.*?)=").matcher(input);
            if (matcher.find()) {
                inputWhereName = matcher.group(1);
            }

            matcher = Pattern.compile("=(.*?);").matcher(input);
            if (matcher.find()) {
                inputWhereValue = matcher.group(1);
            }

            //check space where
            matcher = Pattern.compile(" (.*?)").matcher(inputWhereName);
            if (matcher.find()) {
                inputWhereName = inputWhereName.substring(inputWhereName.indexOf(" ") + 1);
            }
            matcher = Pattern.compile("(.*?) ").matcher(inputWhereName);
            if (matcher.find()) {
                inputWhereName = inputWhereName.substring(0, inputWhereName.indexOf(" "));
            }
            matcher = Pattern.compile(" (.*?)").matcher(inputWhereValue);
            if (matcher.find()) {
                inputWhereValue = inputWhereValue.substring(inputWhereValue.indexOf(" ") + 1);
            }
            matcher = Pattern.compile("(.*?) ").matcher(inputWhereValue);
            if (matcher.find()) {
                inputWhereValue = inputWhereValue.substring(0, inputWhereValue.indexOf(" "));
            }

//            System.out.println("2. Select Where");
            checkSqlQuerySelectWhere();
        } else if (Pattern.matches(regexBasic, input)) { // kalo dia select biasa
            //System.out.println("regex basic");
            Pattern pattern = Pattern.compile("select (.*?) from");
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()) {
                inputAtributeName = matcher.group(1);
            }

            pattern = Pattern.compile("from (.*?);");
            matcher = pattern.matcher(input);
            if (matcher.find()) {
                inputTableName = matcher.group(1);
            }
//            System.out.println("select ajah");
            checkSqlQuerySelect();
        } else {
            System.out.println("regex error"); //error, gabisa di katgoriin jadi join/where/select biasa
            //mencari banyaknya spasi----------------------------
            while (!inputTemp.isEmpty()) {
                if (inputTemp.indexOf(" ") != -1) {
                    inputTemp = inputTemp.substring(inputTemp.indexOf(" ") + 1);
                    numberOfSpace++;
                } else {
                    inputTemp = "";
                }
            }

            inputTemp = input;
            //query basic-----------------------------------
            if (numberOfSpace == 3) {
                if (!inputTemp.substring(0, inputTemp.indexOf(" ")).toLowerCase().equals("select")) {
                    outputQuery = "SQL Error (Syntax Error : " + inputTemp.substring(0, inputTemp.indexOf(" ")) + ")";
                } else {
                    inputTemp = inputTemp.substring(inputTemp.indexOf(" ") + 1);
                    inputAtributeName = inputTemp.substring(0, inputTemp.indexOf(" "));
                    inputTemp = inputTemp.substring(inputTemp.indexOf(" ") + 1);
                    if (!inputTemp.substring(0, inputTemp.indexOf(" ")).toLowerCase().equals("from")) {
                        outputQuery = "SQL Error (Syntax Error : " + inputTemp.substring(0, inputTemp.indexOf(" ")) + ")";
                    } else {
                        if (!inputTemp.substring(inputTemp.length() - 1).equals(";")) {
                            outputQuery = "SQL Error (Missing ;)";
                        } else {
                            inputTemp = inputTemp.substring(inputTemp.indexOf(" ") + 1);
                            inputTableName = inputTemp.substring(0, inputTemp.length() - 1);
                            outputQuery = "";
//                            System.out.println("3.check select");
                            checkSqlQuerySelect();
                        }
                    }
                }
            } //query join--------------------------------------------
            else if (numberOfSpace == 9) {
                if (!inputTemp.substring(0, inputTemp.indexOf(" ")).toLowerCase().equals("select")) {
                    outputQuery = "SQL Error (Syntax Error : " + inputTemp.substring(0, inputTemp.indexOf(" ")) + ")";
                } else {
                    inputTemp = inputTemp.substring(inputTemp.indexOf(" ") + 1);
                    inputAtributeName = inputTemp.substring(0, inputTemp.indexOf(" "));
                    inputTemp = inputTemp.substring(inputTemp.indexOf(" ") + 1);
                    if (!inputTemp.substring(0, inputTemp.indexOf(" ")).toLowerCase().equals("from")) {
                        outputQuery = "SQL Error (Syntax Error : " + inputTemp.substring(0, inputTemp.indexOf(" ")) + ")";
                    } else {
                        inputTemp = inputTemp.substring(inputTemp.indexOf(" ") + 1);
                        inputTable1 = inputTemp.substring(0, inputTemp.indexOf(" "));
                        inputTemp = inputTemp.substring(inputTemp.indexOf(" ") + 1);
                        //varTable1 = inputTemp.substring(0, inputTemp.indexOf(" "));
                        //inputTemp = inputTemp.substring(inputTemp.indexOf(" ")+1);
                        if (!inputTemp.substring(0, inputTemp.indexOf(" ")).toLowerCase().equals("join")) {
                            outputQuery = "SQL Error (Syntax Error : " + inputTemp.substring(0, inputTemp.indexOf(" ")) + ")";
                        } else {
                            inputTemp = inputTemp.substring(inputTemp.indexOf(" ") + 1);
                            inputTable2 = inputTemp.substring(0, inputTemp.indexOf(" "));
                            //inputTemp = inputTemp.substring(inputTemp.indexOf(" ")+1);
                            //varTable2 = inputTemp.substring(0, inputTemp.indexOf(" "));
                            inputTemp = inputTemp.substring(inputTemp.indexOf(" ") + 1);
                            if (!inputTemp.substring(0, inputTemp.indexOf(" ")).toLowerCase().equals("using")) {
                                outputQuery = "SQL Error (Syntax Error : " + inputTemp.substring(0, inputTemp.indexOf(" ")) + ")";
                            } else {
                                inputTemp = inputTemp.substring(inputTemp.indexOf(" ") + 1);
                                pkOn = inputTemp.substring(1, inputTemp.length() - 2);
                                if (!inputTemp.substring(inputTemp.length() - 1).equals(";")) {
                                    outputQuery = "SQL Error (Missing ;)";
                                } else {
                                    outputQuery = "";
//                                    System.out.println("4. cek join");
                                    checkSqlQuerryJoin();
                                }
                            }
                        }
                    }
                }
            } else {
                outputQuery = "SQL Error (Syntax Error)";
            }
        }
    }
    //-----------------------------------------------------------------------------------check Sql Query

    private static void checkSqlQuerySelect() throws IOException {
        String fileNameDefined = "db.txt";
        File file = new File(fileNameDefined);

        try {
            isTableFound = false;
            isAttributeFound = false;
            inputStream = new Scanner(file);
            int index = 0;
            while (inputStream.hasNext()) {
                String dataTemp = inputStream.next();
                if (index > 0) {
                    String tableName = dataTemp.substring(0, dataTemp.indexOf(";"));
                    if (inputTableName.equals(tableName)) {
                        isTableFound = true;
                    }
                }
                index++;
            }
            if (!isTableFound) {
                outputQuery = "SQL Error (table " + inputTableName + " not found)";
            } else {
                index = 0;
                inputStream = new Scanner(file);
                while (inputStream.hasNext()) {
                    String dataTemp = inputStream.next();
                    if (index > 0) {
                        if (inputTableName.equals(dataTemp.substring(0, dataTemp.indexOf(";")))) {
                            dataTemp = dataTemp.substring(dataTemp.indexOf(";") + 1);
                            String attributeName = dataTemp.substring(0, dataTemp.indexOf(";"));
                            if (inputAtributeName.equals("*")) {
                                outputQuery = "\n";
                                outputQuery = outputQuery + ">> Tabel : " + inputTableName + "\n";
                                outputQuery = outputQuery + "   List kolom : " + attributeName;

                                checkQepBasic(inputAtributeName, inputTableName);
                            } else if (!inputAtributeName.contains(",")) {
                                String attNameTmp = attributeName;
                                while (!attNameTmp.isEmpty()) {
                                    if (attNameTmp.contains(",")) {
                                        if (attNameTmp.substring(0, attNameTmp.indexOf(",")).equals(inputAtributeName)) {
                                            isAttributeFound = true;
                                        }
                                        attNameTmp = attNameTmp.substring(attNameTmp.indexOf(",") + 1);
                                    } else {
                                        if (attNameTmp.equals(inputAtributeName)) {
                                            isAttributeFound = true;
                                        }
                                        attNameTmp = "";
                                    }
                                }
                                if (isAttributeFound) {
                                    outputQuery = "\n";
                                    outputQuery = outputQuery + ">> Tabel : " + inputTableName + "\n";
                                    outputQuery = outputQuery + "   List kolom : " + inputAtributeName;

                                    checkQepBasic(inputAtributeName, inputTableName);
                                } else {
                                    outputQuery = "SQL Error (attribute " + inputAtributeName + " not found on table " + inputTableName + ")";
                                }
                            } else {
                                String attNameFix = "";
                                isAttributeFound = true;
                                String attNameNotFound = "";
                                String attNameInputTmp = inputAtributeName;
                                while (!attNameInputTmp.isEmpty()) {
                                    String attNameTmp = attributeName;
                                    String attNameFound = "";
                                    while (!attNameTmp.isEmpty()) {
                                        String s1, s2;
                                        if (attNameInputTmp.contains(",") && attNameTmp.contains(",")) {
                                            s1 = attNameInputTmp.substring(0, attNameInputTmp.indexOf(","));
                                            s2 = attNameTmp.substring(0, attNameTmp.indexOf(","));
                                        } else if (attNameInputTmp.contains(",") && !attNameTmp.contains(",")) {
                                            s1 = attNameInputTmp.substring(0, attNameInputTmp.indexOf(","));
                                            s2 = attNameTmp;
                                        } else if (!attNameInputTmp.contains(",") && attNameTmp.contains(",")) {
                                            s1 = attNameInputTmp;
                                            s2 = attNameTmp.substring(0, attNameTmp.indexOf(","));
                                        } else {
                                            s1 = attNameInputTmp;
                                            s2 = attNameTmp;
                                        }

                                        if (s1.equals(s2)) {
                                            attNameFound = s1;
                                        }

                                        if (attNameTmp.contains(",")) {
                                            attNameTmp = attNameTmp.substring(attNameTmp.indexOf(",") + 1);
                                        } else {
                                            attNameTmp = "";
                                        }
                                    }

                                    if (attNameFound.isEmpty()) {
                                        isAttributeFound = false;
                                        if (attNameInputTmp.contains(",")) {
                                            attNameNotFound = attNameInputTmp.substring(0, attNameInputTmp.indexOf(","));
                                        } else {
                                            attNameNotFound = attNameInputTmp;
                                        }
                                    } else {
                                        if (attNameFix.isEmpty()) {
                                            attNameFix = attNameFound;
                                        } else {
                                            attNameFix = attNameFix + ", " + attNameFound;
                                        }
                                    }

                                    if (attNameInputTmp.contains(" ")) {
                                        attNameInputTmp = attNameInputTmp.substring(attNameInputTmp.indexOf(" ") + 1);
                                    } else if (attNameInputTmp.contains(",")) {
                                        attNameInputTmp = attNameInputTmp.substring(attNameInputTmp.indexOf(",") + 1);
                                    } else {
                                        attNameInputTmp = "";
                                    }
                                }

                                if (!isAttributeFound) {
                                    outputQuery = "SQL Error (attribute " + attNameNotFound + " not found on table " + inputTableName + ")";
                                } else {
                                    outputQuery = "\n";
                                    outputQuery = outputQuery + ">> Tabel (1) : " + inputTableName + "\n";
                                    outputQuery = outputQuery + "   List kolom : " + attNameFix;

                                    checkQepBasic(attNameFix, inputTableName);
                                }
                            }
                        }
                    }

                    index++;
                }
            }

            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void checkSqlQuerySelectWhere() throws IOException {
        String fileNameDefined = "db.txt";
        File file = new File(fileNameDefined);

        try {
            isTableFound = false;
            isAttributeFound = false;
            inputStream = new Scanner(file);
            int index = 0;
            while (inputStream.hasNext()) {
                String dataTemp = inputStream.next();
                if (index > 0) {
                    String tableName = dataTemp.substring(0, dataTemp.indexOf(";"));
                    if (inputTableName.equals(tableName)) {
                        isTableFound = true;
                    }
                }
                index++;
            }
            if (!isTableFound) {
                outputQuery = "SQL Error (table " + inputTableName + " not found)";
            } else {
                index = 0;
                inputStream = new Scanner(file);
                while (inputStream.hasNext()) {
                    String dataTemp = inputStream.next();
                    if (index > 0) {
                        if (inputTableName.equals(dataTemp.substring(0, dataTemp.indexOf(";")))) {
                            dataTemp = dataTemp.substring(dataTemp.indexOf(";") + 1);
                            String attributeName = dataTemp.substring(0, dataTemp.indexOf(";"));
                            if (inputAtributeName.equals("*")) {
                                outputQuery = "\n";
                                outputQuery = outputQuery + ">> Tabel : " + inputTableName + "\n";
                                outputQuery = outputQuery + "   List kolom : " + attributeName;

                                String where = inputWhereName + " = " + inputWhereValue;
                                checkQepBasicWhere(attributeName, inputTableName, where);
                            } else if (!inputAtributeName.contains(",")) {
                                String attNameTmp = attributeName;
                                while (!attNameTmp.isEmpty()) {
                                    if (attNameTmp.contains(",")) {
                                        if (attNameTmp.substring(0, attNameTmp.indexOf(",")).equals(inputAtributeName)) {
                                            isAttributeFound = true;
                                        }
                                        attNameTmp = attNameTmp.substring(attNameTmp.indexOf(",") + 1);
                                    } else {
                                        if (attNameTmp.equals(inputAtributeName)) {
                                            isAttributeFound = true;
                                        }
                                        attNameTmp = "";
                                    }
                                }
                                if (isAttributeFound) {
                                    outputQuery = "\n";
                                    outputQuery = outputQuery + ">> Tabel : " + inputTableName + "\n";
                                    outputQuery = outputQuery + "   List kolom : " + inputAtributeName;

                                    String where = inputWhereName + " = " + inputWhereValue;
                                    checkQepBasicWhere(inputAtributeName, inputTableName, where);
                                } else {
                                    outputQuery = "SQL Error (attribute " + inputAtributeName + " not found on table " + inputTableName + ")";
                                }
                            } else {
                                String attNameFix = "";
                                isAttributeFound = true;
                                String attNameNotFound = "";
                                String attNameInputTmp = inputAtributeName;
                                while (!attNameInputTmp.isEmpty()) {
                                    String attNameTmp = attributeName;
                                    String attNameFound = "";
                                    while (!attNameTmp.isEmpty()) {
                                        String s1, s2;
                                        if (attNameInputTmp.contains(",") && attNameTmp.contains(",")) {
                                            s1 = attNameInputTmp.substring(0, attNameInputTmp.indexOf(","));
                                            s2 = attNameTmp.substring(0, attNameTmp.indexOf(","));
                                        } else if (attNameInputTmp.contains(",") && !attNameTmp.contains(",")) {
                                            s1 = attNameInputTmp.substring(0, attNameInputTmp.indexOf(","));
                                            s2 = attNameTmp;
                                        } else if (!attNameInputTmp.contains(",") && attNameTmp.contains(",")) {
                                            s1 = attNameInputTmp;
                                            s2 = attNameTmp.substring(0, attNameTmp.indexOf(","));
                                        } else {
                                            s1 = attNameInputTmp;
                                            s2 = attNameTmp;
                                        }

                                        if (s1.equals(s2)) {
                                            attNameFound = s1;
                                        }

                                        if (attNameTmp.contains(",")) {
                                            attNameTmp = attNameTmp.substring(attNameTmp.indexOf(",") + 1);
                                        } else {
                                            attNameTmp = "";
                                        }
                                    }

                                    if (attNameFound.isEmpty()) {
                                        isAttributeFound = false;
                                        if (attNameInputTmp.contains(",")) {
                                            attNameNotFound = attNameInputTmp.substring(0, attNameInputTmp.indexOf(","));
                                        } else {
                                            attNameNotFound = attNameInputTmp;
                                        }
                                    } else {
                                        if (attNameFix.isEmpty()) {
                                            attNameFix = attNameFound;
                                        } else {
                                            attNameFix = attNameFix + ", " + attNameFound;
                                        }
                                    }

                                    if (attNameInputTmp.contains(" ")) {
                                        attNameInputTmp = attNameInputTmp.substring(attNameInputTmp.indexOf(" ") + 1);
                                    } else if (attNameInputTmp.contains(",")) {
                                        attNameInputTmp = attNameInputTmp.substring(attNameInputTmp.indexOf(",") + 1);
                                    } else {
                                        attNameInputTmp = "";
                                    }
                                }

                                if (!isAttributeFound) {
                                    outputQuery = "SQL Error (attribute " + attNameNotFound + " not found on table " + inputTableName + ")";
                                } else {
                                    outputQuery = "\n";
                                    outputQuery = outputQuery + ">> Tabel (1) : " + inputTableName + "\n";
                                    outputQuery = outputQuery + "   List kolom : " + attNameFix;

                                    String where = inputWhereName + " = " + inputWhereValue;
                                    checkQepBasicWhere(attNameFix, inputTableName, where);
                                }
                            }
                        }
                    }

                    index++;
                }
            }

            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void checkSqlQuerryJoin() throws IOException {
        String fileNameDefined = "db.txt";
        File file = new File(fileNameDefined);

        try {
            isTable1Found = false;
            isTable2Found = false;
            isPk1Found = false;
            isPk2Found = false;
            inputStream = new Scanner(file);
            int index = 0;
            while (inputStream.hasNext()) {
                String dataTemp = inputStream.next();
                String tableName = dataTemp.substring(0, dataTemp.indexOf(";"));
                if (inputTable1.equals(tableName)) {
                    isTable1Found = true;
                }
                if (inputTable2.equals(tableName)) {
                    isTable2Found = true;
                }

                if (index > 0) {
                    dataTemp = dataTemp.substring(dataTemp.indexOf(";") + 1);
                    while (!dataTemp.isEmpty()) {
                        String attribute = "?";
                        if (dataTemp.contains(",")) {
                            attribute = dataTemp.substring(0, dataTemp.indexOf(","));
                            dataTemp = dataTemp.substring(dataTemp.indexOf(",") + 1);
                        } else {
                            attribute = dataTemp.substring(0, dataTemp.indexOf(";"));
                            dataTemp = "";
                        }

                        if (attribute.equals(pkOn) && !isPk1Found) {
                            isPk1Found = true;
                        } else if (attribute.equals(pkOn)) {
                            isPk2Found = true;
                        }
                    }
                }
                index++;
            }

            if (!isTable1Found) {
                outputQuery = "SQL Error (table " + inputTable1 + " not found)";
            } else if (!isTable2Found) {
                outputQuery = "SQL Error (table " + inputTable2 + " not found)";
            } else if (!isPk1Found || !isPk2Found) {
                outputQuery = "SQL Error (attribute Primary key " + pkOn + " not found)";
            } else {
                if (inputAtributeName.equals("*")) {
                    String attributeName1 = "", attributeName2 = "";
                    inputStream = new Scanner(file);
                    index = 0;
                    while (inputStream.hasNext()) {
                        String dataTemp = inputStream.next();
                        if (index > 0) {
                            if (inputTable1.equals(dataTemp.substring(0, dataTemp.indexOf(";")))) {
                                dataTemp = dataTemp.substring(dataTemp.indexOf(";") + 1);
                                attributeName1 = dataTemp.substring(0, dataTemp.indexOf(";"));
                            } else if (inputTable2.equals(dataTemp.substring(0, dataTemp.indexOf(";")))) {
                                dataTemp = dataTemp.substring(dataTemp.indexOf(";") + 1);
                                attributeName2 = dataTemp.substring(0, dataTemp.indexOf(";"));
                            }
                        }
                        index++;
                    }

                    outputQuery = "\n";
                    outputQuery = outputQuery + ">> Tabel (1) : " + inputTable1 + "\n";
                    outputQuery = outputQuery + "   List kolom : " + attributeName1 + "\n";
                    outputQuery = outputQuery + "   Tabel (2) : " + inputTable2 + "\n";
                    outputQuery = outputQuery + "   List kolom : " + attributeName2;

                    checkQepJoin(inputAtributeName, inputTable1, inputTable2, pkOn);
                } else {
                    String att1Name = "", att2Name = "";
                    String inputAttNameTmp = inputAtributeName;
                    boolean isAttFound = true;
                    while (!inputAttNameTmp.isEmpty()) {
                        String attTmp;
                        boolean isAtt1Found = false, isAtt2Found = false;
                        if (inputAttNameTmp.contains(",")) {
                            attTmp = inputAttNameTmp.substring(0, inputAttNameTmp.indexOf(","));
                            inputAttNameTmp = inputAttNameTmp.substring(inputAttNameTmp.indexOf(",") + 1);
                        } else {
                            attTmp = inputAttNameTmp;
                            inputAttNameTmp = "";
                        }

                        inputStream = new Scanner(file);
                        while (inputStream.hasNext()) {
                            String dataTemp = inputStream.next();

                            if (inputTable1.equals(dataTemp.substring(0, dataTemp.indexOf(";")))) {
                                dataTemp = dataTemp.substring(dataTemp.indexOf(";") + 1);
                                dataTemp = dataTemp.substring(0, dataTemp.indexOf(";"));
                                while (!dataTemp.isEmpty()) {
                                    String attName;
                                    if (dataTemp.contains(",")) {
                                        attName = dataTemp.substring(0, dataTemp.indexOf(","));
                                        dataTemp = dataTemp.substring(dataTemp.indexOf(",") + 1);
                                    } else {
                                        attName = dataTemp;
                                        dataTemp = "";
                                    }

                                    if (attTmp.equals(attName)) {
                                        if (att1Name.isEmpty()) {
                                            att1Name = attName;
                                        } else {
                                            att1Name = att1Name + ", " + attName;
                                        }
                                        isAtt1Found = true;
                                    }
                                }
                            } else if (inputTable2.equals(dataTemp.substring(0, dataTemp.indexOf(";")))) {
                                dataTemp = dataTemp.substring(dataTemp.indexOf(";") + 1);
                                dataTemp = dataTemp.substring(0, dataTemp.indexOf(";"));
                                while (!dataTemp.isEmpty()) {
                                    String attName;
                                    if (dataTemp.contains(",")) {
                                        attName = dataTemp.substring(0, dataTemp.indexOf(","));
                                        dataTemp = dataTemp.substring(dataTemp.indexOf(",") + 1);
                                    } else {
                                        attName = dataTemp;
                                        dataTemp = "";
                                    }

                                    if (attTmp.equals(attName)) {
                                        if (att2Name.isEmpty()) {
                                            att2Name = attName;
                                        } else {
                                            att2Name = att2Name + ", " + attName;
                                        }
                                        isAtt2Found = true;
                                    }
                                }
                            }
                        }

                        if (!isAtt1Found && !isAtt2Found) {
                            inputAttNameTmp = "";
                            outputQuery = "SQL Error (attribute " + attTmp + " not found in any table)";
                            isAttFound = false;
                        }
                    }
                    if (isAttFound) {
//                        outputQuery = "\n";
                        outputQuery = outputQuery + ">> Tabel (1) : " + inputTable1 + "\n";
                        outputQuery = outputQuery + "   List kolom : " + att1Name + "\n";
                        outputQuery = outputQuery + "   Tabel (2) : " + inputTable2 + "\n";
                        outputQuery = outputQuery + "   List kolom : " + att2Name;

                        checkQepJoin(inputAtributeName, inputTable1, inputTable2, pkOn);
                    }
                }
            }

            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------check QEP
    private static void checkQepBasic(String attName, String tabName) throws IOException {
        int cost1, cost2; // bakal itung dua qep
        getData getter = new getData();
        String qepOptimal;
        double valueN, valueR, valueV;
        double valueB = getter.getB();
        double valueP = getter.getP();
        if (tabName.equals("movies")) {
            valueR = getter.getRmovie();
            valueN = getter.getNmovie();
            valueV = getter.getVmovie();
        } else if (tabName.equals("user")) {
            valueR = getter.getRuser();
            valueN = getter.getNuser();
            valueV = getter.getVuser();
        } else {
            valueR = getter.getRuserMovie();
            valueN = getter.getNuserMovie();
            valueV = getter.getVuserMovie();
        }

        double valueBfr = Math.floor(valueB / valueR);
//        System.out.println(valueBfr);
        int valueTotalBlok = (int) Math.ceil(valueN / valueBfr);

        cost1 = valueTotalBlok; // 1 nonkey

        outputQuery = outputQuery + "\n>> QEP #1";
        if (!attName.equals("*")) {
            outputQuery = outputQuery + "\n   PROJECTION " + attName + " -- on the fly";
        }
        outputQuery = outputQuery + "\n   " + tabName + " --A1";
        outputQuery = outputQuery + "\n   Cost : " + cost1 + " blok";
        outputQuery = outputQuery + "\n>> QEP #2";

        String outputShared = "sharedpool1";
        outputShared = outputShared + "\nQuery : " + inputQuery;
        if (!attName.equals("*")) {
            outputShared = outputShared + "\n   PROJECTION " + attName + " -- on the fly";
        }
        outputShared = outputShared + "\n   " + tabName + " --A1";
        outputShared = outputShared + "\n   Cost : " + cost1 + " blok";
        printToSharedPool1(outputShared);
    }

    private static void checkQepBasicWhere(String attName, String tabName, String where) throws IOException {
        int cost1, cost2; // bakal itung dua qep
        System.out.println("terpanggil");
        getData getter = new getData();
        String qepOptimal;
        double valueN, valueR, valueV;
        double valueB = getter.getB();
        double valueP = getter.getP();
        if (tabName.equals("movies")) {
            valueR = getter.getRmovie();
            valueN = getter.getNmovie();
            valueV = getter.getVmovie();
        } else if (tabName.equals("user")) {
            valueR = getter.getRuser();
            valueN = getter.getNuser();
            valueV = getter.getVuser();
        } else {
            valueR = getter.getRuserMovie();
            valueN = getter.getNuserMovie();
            valueV = getter.getVuserMovie();
        }
        double valueBfr = Math.floor(valueB / valueR);
        int valueY = (int) Math.floor(valueB / (valueV + valueP));
        int valueTotalBlok = (int) Math.ceil(valueN / valueBfr);

        cost1 = valueTotalBlok / 2; //A1 key
        cost2 = (int) Math.ceil(Math.log(valueTotalBlok) / Math.log(valueY)); //A2 key
        if (cost1 <= cost2) {
            qepOptimal = "QEP#1";
        } else {
            qepOptimal = "QEP#2";
        }

        outputQuery = outputQuery + "\n>> QEP #1";
        if (!attName.equals("*")) {
            outputQuery = outputQuery + "\n   PROJECTION " + attName + " -- on the fly";
        }
        outputQuery = outputQuery + "\n   SELECTION " + where + " -- A1 key";
        outputQuery = outputQuery + "\n   " + tabName;
        outputQuery = outputQuery + "\n   Cost : " + cost1 + " blok";
        outputQuery = outputQuery + "\n>> QEP #2";
        if (!attName.equals("*")) {
            outputQuery = outputQuery + "\n   PROJECTION " + attName + " -- on the fly";
        }
        outputQuery = outputQuery + "\n   SELECTION " + where + " -- A2";
        outputQuery = outputQuery + "\n   " + tabName;
        outputQuery = outputQuery + "\n   Cost : " + cost2 + " blok";
        outputQuery = outputQuery + "\n>> QEP Optimal : " + qepOptimal;

        String outputShared = "sharedpool1";
        outputShared = outputShared + "\nQuery : " + inputQuery;
        if (!attName.equals("*")) {
            outputShared = outputShared + "\n   PROJECTION " + attName + " -- on the fly";
        }
        if (cost1 <= cost2) {
            outputShared = outputShared + "\n   SELECTION " + where + " -- A1 key";
            outputShared = outputShared + "\n   " + tabName;
            outputShared = outputShared + "\n   Cost : " + cost1 + " blok";
        } else {
            outputShared = outputShared + "\n   SELECTION " + where + " -- A2";
            outputShared = outputShared + "\n   " + tabName;
            outputShared = outputShared + "\n   Cost : " + cost2 + " blok";
        }
        printToSharedPool1(outputShared);
    }

    private static void checkQepJoin(String attName, String tabName1, String tabName2, String pk) throws IOException {
        int cost1, cost2; // bakal itung dua qep
        getData getter = new getData();
        String qepOptimal;
        double valueN, valueR, valueV;
        double valueB = getter.getB();
        double valueP = getter.getP();
        int valueTotalBlok1, valueTotalBlok2;
        //----------hitung total blok 1
        if (tabName1.equals("movies")) {
            valueR = getter.getRmovie();
            valueN = getter.getNmovie();
            double valueBfr = Math.floor(valueB / valueR);
            valueTotalBlok1 = (int) Math.ceil(valueN / valueBfr);
        } else if (tabName1.equals("user")) {
            valueR = getter.getRuser();
            valueN = getter.getNuser();
            double valueBfr = Math.floor(valueB / valueR);
            valueTotalBlok1 = (int) Math.ceil(valueN / valueBfr);
        } else {
            valueR = getter.getRuserMovie();
            valueN = getter.getNuserMovie();
            double valueBfr = Math.floor(valueB / valueR);

            valueTotalBlok1 = (int) Math.ceil(valueN / valueBfr);
        }
        //----hitung total blok 2
        if (tabName2.equals("movies")) {
            valueR = getter.getRmovie();
            valueN = getter.getNmovie();
            double valueBfr = valueB / valueR;
            valueTotalBlok2 = (int) Math.ceil(valueN / valueBfr);
        } else if (tabName2.equals("user")) {
            valueR = getter.getRuser();
            valueN = getter.getNuser();
            double valueBfr = valueB / valueR;
            valueTotalBlok2 = (int) Math.ceil(valueN / valueBfr);
        } else {
            valueR = getter.getRuserMovie();
            valueN = getter.getNuserMovie();
            double valueBfr = valueB / valueR;
            valueTotalBlok2 = (int) Math.ceil(valueN / valueBfr);
        }
        //cost = br*bs+br
        int br = valueTotalBlok1;
        int bs = valueTotalBlok2;
        cost1 = br * bs + br;
        br = valueTotalBlok2;
        bs = valueTotalBlok1;
        cost2 = br * bs + br;
        if (cost1 <= cost2) {
            qepOptimal = "QEP#1";
        } else {
            qepOptimal = "QEP#2";
        }

        outputQuery = outputQuery + "\n>> QEP #1";
        if (!attName.equals("*")) {
            outputQuery = outputQuery + "\n   PROJECTION " + attName + " -- on the fly";
        }
        outputQuery = outputQuery + "\n              JOIN " + tabName1 + "." + pk + " = " + tabName2 + "." + pk + " -- BNLJ";
        outputQuery = outputQuery + "\n   " + tabName1 + "     " + tabName2;
        outputQuery = outputQuery + "\n   Cost (worst case) : " + cost1 + " blok";
        outputQuery = outputQuery + "\n>> QEP #2";
        if (!attName.equals("*")) {
            outputQuery = outputQuery + "\n   PROJECTION " + attName + " -- on the fly";
        }
        outputQuery = outputQuery + "\n              JOIN " + tabName1 + "." + pk + " = " + tabName2 + "." + pk + " -- BNLJ";
        outputQuery = outputQuery + "\n   " + tabName1 + "     " + tabName2;
        outputQuery = outputQuery + "\n   Cost : " + cost2 + " blok";
        outputQuery = outputQuery + "\n>> QEP Optimal : " + qepOptimal;

        String outputShared = "sharedpool2";
        outputShared = outputShared + "\nQuery : " + inputQuery;
        if (!attName.equals("*")) {
            outputShared = outputShared + "\n   PROJECTION " + attName + " -- on the fly";
        }
        outputShared = outputShared + "\n              JOIN " + tabName1 + "." + pk + " = " + tabName2 + "." + pk + " -- Block Nested loop join";
        outputShared = outputShared + "\n   " + tabName1 + "     " + tabName2;
        if (cost1 <= cost2) {
            outputShared = outputShared + "\n   Cost (worst case) : " + cost1 + " blok";
        } else {
            outputShared = outputShared + "\n   Cost (worst case) : " + cost2 + " blok";
        }

        printToSharedPool2(outputShared);
    }
//-------------------------------------------------------------------------------------------print to shared pool

    private static void printToSharedPool1(String outputShared) {
        String sharedPoolBefore = "";
        boolean isGetSharedBefore = false;

        String fileNameDefined = "sharedPool.txt";
        File file = new File(fileNameDefined);
        try {
            inputStream = new Scanner(file);
            while (inputStream.hasNext()) {
                String dataTemp = inputStream.nextLine();

                if (dataTemp.equals("sharedpool2")) {
                    isGetSharedBefore = true;
                }
                if (isGetSharedBefore) {
                    sharedPoolBefore = sharedPoolBefore + "\n" + dataTemp;
                }
            }

            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter("sharedPool.txt");
                fileWriter.append(outputShared);
                if (!sharedPoolBefore.isEmpty()) {
                    fileWriter.append(sharedPoolBefore);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println("Error while flushing/closing fileWriter !!!");
                    e.printStackTrace();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void printToSharedPool2(String outputShared) {
        String sharedPoolBefore = "";
        boolean isGetSharedBefore = false;

        String fileNameDefined = "sharedPool.txt";
        File file = new File(fileNameDefined);
        try {
            inputStream = new Scanner(file);
            while (inputStream.hasNext()) {
                String dataTemp = inputStream.nextLine();

                if (dataTemp.equals("sharedpool1")) {
                    isGetSharedBefore = true;
                }
                if (dataTemp.equals("sharedpool2")) {
                    isGetSharedBefore = false;
                }
                if (isGetSharedBefore) {
                    if (sharedPoolBefore.isEmpty()) {
                        sharedPoolBefore = dataTemp;
                    } else {
                        sharedPoolBefore = sharedPoolBefore + "\n" + dataTemp;
                    }
                }
            }

            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter("sharedPool.txt");
                if (!sharedPoolBefore.isEmpty()) {
                    fileWriter.append(sharedPoolBefore);
                }
                fileWriter.append("\n" + outputShared);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println("Error while flushing/closing fileWriter !!!");
                    e.printStackTrace();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
