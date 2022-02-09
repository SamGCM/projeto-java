package com.operation;

import com.account.Account;
import com.interfaces.OperationInterface;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Operation implements Comparable{
    String operator;
    String type;
    String value;
    String date;
    Account account;

    public Operation(String operator, String type, String value, String date, Account account) {
        this.operator = operator;
        this.type = type;
        this.account = account;
        this.date = date;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;

        Operation obj = (Operation)o;

        return this.toString().equalsIgnoreCase(obj.toString());
    }

    public void deposit(String value) {
        this.account.sum(value);
    }

    public void withdraw(String value) {
        this.account.subtract(value);
        
    }

    public void total() {
        this.account.balance();
    }

    @Override
    public String toString() {
        return date + " " + account.getId() + " " + account.getBank() + " " + account.getAgency() + " " + account.getAccount() + " " + account.getOperator() + " " + type + " " + value;
    }

    @Override
    public int compareTo(Object o) {
        Operation comparingTo = (Operation)o;
        int difference =  this.toString().compareTo(comparingTo.toString());
        return difference;
    }
}
