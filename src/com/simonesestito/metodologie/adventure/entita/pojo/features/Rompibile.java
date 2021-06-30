package com.simonesestito.metodologie.adventure.entita.pojo.features;

import com.simonesestito.metodologie.adventure.engine.CommandException;
import com.simonesestito.metodologie.adventure.entita.pojo.objects.Oggetto;

import java.util.List;

public interface Rompibile {
    List<? extends Oggetto> rompi(Rompitore rompitore) throws CommandException;
}
