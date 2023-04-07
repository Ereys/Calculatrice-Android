package com.example.calculatrice.models;

import java.util.ArrayList;

/**
 * This class represent the history of the calculator // Singleton
 */
public class HistoryCalcul {

    private ArrayList<Calcul> historyCalcul = new ArrayList<>();
    private static HistoryCalcul history = null;

    private HistoryCalcul(){}
    public static HistoryCalcul getHistoryInstance(){
        if(history == null){
            history = new HistoryCalcul();
        }
        return history;
    }
    public Calcul getLastCalcul(){
        if(historyCalcul.size() > 0) {
            return this.historyCalcul.get(historyCalcul.size() - 1);
        }
        throw new IllegalArgumentException("Erreur, pas de calcul en mémoire");
    }

    public void addCalcul(Calcul c){
        this.historyCalcul.add(c);
    }

    public ArrayList<Calcul> getHistoryCalcul() {
        return historyCalcul;
    }

    public void clearHistory(){
        this.historyCalcul.clear();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("___________________________").append("\n");
        for (Calcul item: this.historyCalcul) {
            s.append(item.toString()).append("\n");
        }
        System.out.println("___________________________\n");
        return s.toString();
    }
}