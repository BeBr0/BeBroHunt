package yt.bebr0.bebr0hunt.arena;

public enum Role {

    TARGET("&aЦель", "&9Твоя цель - выжить"),
    GUARD("&6Охранник", "&9Твоя задача - защитить цель"),
    ATTACKER("&cАтакующий", "&9Твоя задача - убить цель");

    final String name;
    final String objective;

    Role(String name, String objective){
        this.name = name;
        this.objective = objective;
    }

    public String getName() {
        return name;
    }

    public String getObjective() {
        return objective;
    }
}
