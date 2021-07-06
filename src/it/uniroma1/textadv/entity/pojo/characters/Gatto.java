package it.uniroma1.textadv.entity.pojo.characters;

import it.uniroma1.textadv.engine.CommandException;
import it.uniroma1.textadv.entity.pojo.features.Contenitore;
import it.uniroma1.textadv.entity.pojo.features.Posizionabile;
import it.uniroma1.textadv.entity.pojo.features.PosizionabileUnico;

/**
 * Rappresentazione del gatto, animale nel gioco.
 * <p>
 * Inoltre, è {@link Posizionabile} perchè prendibile e spostabile nell'{@link it.uniroma1.textadv.entity.pojo.objects.Inventario}
 */
public class Gatto extends Animale implements Posizionabile {
    /**
     * Miagolio del gatto di base
     */
    private static final String MIAGOLIO = "Miao!";

    /**
     * Stategy pattern per l'implementazione di {@link Posizionabile}
     */
    private final PosizionabileUnico posizionabileUnico = new PosizionabileUnico(this);

    /**
     * Crea un nuovo gatto
     *
     * @param name Nome del gatto
     */
    public Gatto(String name) {
        super(name);
    }

    /**
     * Ottieni il verso del gatto
     *
     * @return verso del gatto
     */
    @Override
    public String getVerso() {
        return MIAGOLIO;
    }

    /**
     * Accarezza il gatto
     */
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
        super.accarezza();
    }

    /**
     * "Parla" con il gatto, che miagola
     *
     * @return Miagolio risposta del gatto
     */
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
                """ + super.parla();
    }

    /**
     * Ottieni la posizione attuale del gatto
     *
     * @return Posizione del gatto
     */
    @Override
    public Contenitore getPosizione() {
        return posizionabileUnico.getPosizione();
    }

    /**
     * Sposta il gatto in un'altra posizione
     *
     * @param contenitore Dove spostare il gatto
     * @throws CommandException Errore nello spostamento
     */
    @Override
    public void spostaIn(Contenitore contenitore) throws CommandException {
        posizionabileUnico.spostaIn(contenitore);
    }
}
