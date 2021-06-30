package com.simonesestito.metodologie.adventure.entita.pojo.objects;

import com.simonesestito.metodologie.adventure.entita.pojo.features.Contenitore;

public record ObjectReference<T>(T object, Contenitore container) { }
