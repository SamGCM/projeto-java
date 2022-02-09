package com.account;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;

import com.interfaces.AccountInterface;
import com.operation.Operation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Account implements AccountInterface {
    private String id;
    private String bank;
    private String agency;
    private String account;
    private String operator;
    private String balance = "0000";
    Locale localeBR = new Locale("pt","BR");
    NumberFormat dinheiro = NumberFormat.getCurrencyInstance(localeBR);
    private ArrayList<Operation> extrato = new ArrayList<>();

    public void adicionarTransacao(Operation op) {
        this.extrato.add(op);
    }

    public String pegar() {
        return this.extrato.toString();
    }


    @Override
    public String toString() {
        return id + " " + bank + " " + agency + " " + account + " " + operator + " " + balance;
    }

    public static String formatNumero(Integer numero){
        String num = numero.toString();
        String dec = num.substring(num.length() - 2);
        String intnum = num.substring(0, num.length() - 2);
        return intnum.concat(",").concat(dec);
    }

    @Override
    public String sum(String value) {
        if(!value.matches("[A-Z]*")) {
            int balance = Integer.parseInt(this.balance) * 100;
            String money = value.replace(".", "");
            int sum = Integer.parseInt(money) * 100;
            this.balance = this.formatNumero((balance + sum) / 100);
        }
        return this.balance;
    }

    @Override
    public String subtract(String value) {
        if(!value.matches("[A-Z]*")) {
            int balance = Integer.parseInt(this.balance) * 100;
            String money = value.replace(".", "");
            int sub = Integer.parseInt(money) * 100;
            this.balance = this.formatNumero((balance - sub) / 100);
        }
        return this.balance;
    }

    @Override
    public String balance() {
        return this.balance;
    }
}
