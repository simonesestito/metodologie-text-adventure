package it.uniroma1.textadv.entita.pojo.characters;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entita.pojo.Giocatore;
import it.uniroma1.textadv.entita.pojo.features.*;

public class Gatto extends Personaggio implements Accarezzabile, Parla, Posizionabile {
    private static final String MIAGOLIO = "Miao!";
    private final PosizionabileUnico posizionabileUnico = new PosizionabileUnico(this);

    public Gatto(String name) {
        super(name);
    }

    @Override
    public void accarezza() {
        // Easter egg...
        Giocatore.getInstance().rispondiUtente(
                """
                        (_＼ヽ
                        　 ＼＼ .Λ＿Λ.
                        　　 ＼(　ˇωˇ)　
                        　　　 >　⌒ヽ
                        　　　/ 　 へ＼
                        　　 /　　/　＼＼
                        　　 ﾚ　ノ　　 ヽ_つ
                        　　/　/
                        　 /　/|
                        　(　(ヽ
                        　|　|、＼
                        　| 丿 ＼ ⌒)
                        　| |　　) /
                        `ノ ) 　 Lﾉ
                        (_／\040\040\040\040
                        """
        );
        Giocatore.getInstance().rispondiUtente(MIAGOLIO);
    }

    @Override
    public String parla() {
        return """
                          
                              _/((
                     _.---. .'   `\\
                   .'      `     ^ T=
                  /     \\       .--'
                 |      /       )'-.
                 ; ,   <__..-(   '-.)
                  \\ \\-.__)    ``--._)
                   '.'-.__.-.
                     '-...-'
                """ + MIAGOLIO;
    }

    @Override
    public Contenitore getPosizione() {
        return posizionabileUnico.getPosizione();
    }

    @Override
    public void spostaIn(Contenitore contenitore) throws CommandException {
        posizionabileUnico.spostaIn(contenitore);
    }
}
