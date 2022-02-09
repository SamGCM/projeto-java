package com.actions;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.account.Account;
import com.interfaces.ActionsInterface;
import com.operation.Operation;

public class Actions implements ActionsInterface {

    LinkedList<Account> listAccounts = new LinkedList<>();
    List<Operation> operation = new ArrayList<>();
    ArrayList<Operation> listOperations = new ArrayList<>();
    String currentLine;
    private LinkedList<LinkedList<Operation>> list = new LinkedList<>();
    
    public void Conjunto() {
        for (int i = 0; i < 26; i++) {
            list.add(new LinkedList<Operation>());
        }
    }

    private String convertDate(String date) { 
        String oldstring = date.replace("T", " ");
        String newstring = date;
        try {
            LocalDateTime datetime = LocalDateTime.parse(oldstring, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            newstring = datetime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return newstring;
    }

    private boolean contem(Operation o) {
        int indice = encontrarIndice(o.getAccount().getId());
        return this.list.get(indice).contains(o);
    }

    private int encontrarIndice(String o){
        return o.toLowerCase().charAt(0) % 25;
    }

    @Override
    public void loadFile() {
        Path path = Paths.get("transactions.csv");
            try (BufferedReader br = Files.newBufferedReader(path)) {
                while((currentLine = br.readLine()) != null) {
                    String[] detailed = currentLine.split(",");
                    Operation op = new Operation();
                    Account acc = new Account();
                    op.setDate(convertDate(detailed[0]));
                    acc.setId(detailed[1]);
                    acc.setBank(detailed[2]);
                    acc.setAgency(detailed[3]);
                    acc.setAccount(detailed[4]);
                    acc.setOperator(detailed[5]);
                    op.setAccount(acc);
                    op.setType(detailed[6]);
                    op.setValue(detailed[7]);
                    
                    listAccounts.add(acc);
                    listOperations.add(op);
                }
                Collections.sort(listOperations);
                
                for (int i = 0; i < listOperations.size(); i++) {
                    this.adicionar(listOperations.get(i));
                }

                System.out.println("Carregado com sucesso");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Erro ao carregar arquivo");
            }
    }

    

    private void adicionar(Operation o){ 
        if(!contem(o)){
            int indice = encontrarIndice(o.getAccount().getId());
            LinkedList<Operation> lista = list.get(indice);
            lista.add(o);
        }
    }

    @Override
    public void listAll() {
        for (int i = 0; i < listAccounts.size(); i++) {
            System.out.println(listAccounts.get(i).toString());
            System.out.println();
        }
        System.out.println("--------------");
    }

    @Override
    public void total() {
        for (int i = 0; i < listOperations.size(); i++) {
            String type = listOperations.get(i).getType();
            String value = listOperations.get(i).getValue();
            listAccounts.get(encontrarIndice(listOperations.get(i).getAccount().getId())).adicionarTransacao(listOperations.get(i));
            switch (type) {
                case "DEPOSITO":
                    listOperations.get(i).deposit(value);
                    break;
            
                default:
                    listOperations.get(i).withdraw(value);
                    break;
            }
        }
        // listAll();
    }

    @Override
    public void saveData() {
        for (int i = 0; i < listAccounts.size(); i++) {
            System.out.println(listAccounts.get(i).pegar());
            System.out.println("--------------");
        }
    }
}
