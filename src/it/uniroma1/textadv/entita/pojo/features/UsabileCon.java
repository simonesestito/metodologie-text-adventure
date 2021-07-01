package it.uniroma1.textadv.entita.pojo.features;

import it.uniroma1.textadv.engine.CommandException;

public interface UsabileCon<T> {
    void usaCon(T oggetto) throws CommandException;
}
