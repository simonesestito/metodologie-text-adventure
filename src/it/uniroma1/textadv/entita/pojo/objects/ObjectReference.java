package it.uniroma1.textadv.entita.pojo.objects;

import it.uniroma1.textadv.entita.pojo.features.Contenitore;

public record ObjectReference<T>(T object, Contenitore container) { }
