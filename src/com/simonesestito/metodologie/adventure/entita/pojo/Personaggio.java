package com.simonesestito.metodologie.adventure.entita.pojo;

public class Personaggio extends Entity implements Posizionabile {
    private Stanza currentRoom;

    public Personaggio(String name) {
        super(name);
    }

    @Override
    public Stanza getCurrentLocation() {
        return currentRoom;
    }

    @Override
    public void moveTo(Stanza stanza) {
        if (currentRoom != null) {
            currentRoom.removeCharacter(this);
        }

        currentRoom = stanza;
        currentRoom.addCharacter(this);
    }
}
