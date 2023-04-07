package com.example.calculatrice.models;


public interface AbstractCalculBuilder {

    public void addToFirstValue(String a);
    public void subToFirstValue();

    public void addToSecondValue(String b);
    public void subToSecondValue();

    public void setOperation(String op);
    public Calcul build();

}
