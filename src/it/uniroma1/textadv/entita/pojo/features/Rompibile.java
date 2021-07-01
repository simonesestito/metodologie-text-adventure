package it.uniroma1.textadv.entita.pojo.features;

import it.uniroma1.textadv.engine.CommandException;

public interface Rompibile extends UsabileCon<Rompitore> {
    void rompi(Rompitore rompitore) throws CommandException;

    @Override
    default void usaCon(Rompitore oggetto) throws CommandException {
        rompi(oggetto);
    }
}
