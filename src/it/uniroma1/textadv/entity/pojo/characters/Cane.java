package it.uniroma1.textadv.entity.pojo.characters;

/**
 * Cane, personaggio animale nel gioco
 */
public class Cane extends Animale {
    /**
     * Stringa che rappresenta il verso del cane
     */
    private static final String ABBAIO = """
                                        __
                 ,                    ," e`--o  BAU
                ((                   (  | __,'
                 \\\\~----------------' \\_;/
                 (                      /
                 /) ._______________.  )
                (( (               (( (
                 ``-'               ``-'
            """;

    /**
     * Crea un nuovo cane con un nome
     * @param name Nome del cane
     */
    public Cane(String name) {
        super(name);
    }

    /**
     * Verso del cane
     * @return Verso del cane
     */
    @Override
    public String getVerso() {
        return ABBAIO;
    }

    /**
     * Controlla se i due cani sono uguali
     * @param obj Altro oggetto da controllare
     * @return <code>true</code> se i cani sono uguali
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        return obj.getClass() == getClass();
    }

}
