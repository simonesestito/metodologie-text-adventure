/**
 * Gioco testuale di avventura, in giro per vari posti e personaggi
 */
module it.uniroma1.textadv {
    exports it.uniroma1.textadv;
    exports it.uniroma1.textadv.entity.parser;
    exports it.uniroma1.textadv.entity.factory;
    exports it.uniroma1.textadv.entity.pojo;
    exports it.uniroma1.textadv.entity.pojo.characters;
    exports it.uniroma1.textadv.entity.pojo.objects;
    exports it.uniroma1.textadv.entity.pojo.features;
    exports it.uniroma1.textadv.entity.pojo.links;
    exports it.uniroma1.textadv.engine;
    exports it.uniroma1.textadv.locale;

    requires java.datatransfer;
    requires java.desktop;
}