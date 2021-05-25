package com.simonesestito.metodologie.adventure;

import com.simonesestito.metodologie.adventure.entita.pojo.Direction;
import com.simonesestito.metodologie.adventure.entita.pojo.Mondo;

import java.nio.file.Files;

public class Main {

    public static void main(String[] args) throws Exception {
        var file = Files.createTempFile(null, null);
        Files.writeString(file, """
                [world:mondoBob]
                description\tdescrizione testuale del mondo
                start\ts1
                   
                [room:s1]
                description\tdescrizione testuale
                links\tN:p1,W:p2
                objects\tsushi nella boccia,chiave
                                
                [room:s2]
                description\tCiao sono bello
                links\tS:p1
                 
                [links]
                p1\tPorta\ts1\ts2
                p2\tPorta\ts1\ts2
                
                [objects]
                sushi nella boccia\tPesce
                chiave\tChiave\tp1
                chiave 2\tChiave\tp2
                """);
        try {
            var mondo = Mondo.fromFile(file);
            System.out.println(
                    mondo.getStart()
                            .getLink(Direction.OVEST)
                            .orElseThrow()
                            .getDestinazione(mondo.getStart())
                            .getDescription()
            );
        } finally {
            Files.delete(file);
        }
    }
}
