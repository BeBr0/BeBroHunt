package yt.bebr0.bebr0hunt.arena;

public enum Role {

    TARGET("Цель"),
    GUARD("Охранник"),
    ATTACKER("Атакующий");

    final String name;

    Role(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
