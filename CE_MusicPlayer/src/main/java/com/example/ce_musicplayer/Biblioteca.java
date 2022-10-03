package com.example.ce_musicplayer;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Clase que corresponde a una lista circular doblemente enlazada, la cual contiene las canciones que serán reproducidas. Así como la gestión de estas canciones. Cabe aclarar que además de ser una lista esta clase también corresponde a un nodo, ya que esta va dentro de la clase Lista_bibliotecas
 */
public class Biblioteca {
    /**
     * Atributo de tipo canción que corresponde a la primera canción de la biblioteca.
     */
    public Cancion Primero;
    /**
     * Atributo de tipo canción que corresponde a la última canción de la biblioteca.
     */
    public Cancion Ultimo;
    /**
     * Atributo de tipo string que corresponde al nombre de la biblioteca.
     */
    public String Nombre;
    /**
     * Atributo de tipo string que corresponde a la fecha de creación de la biblioteca.
     */
    public String FechaC;
    /**
     * Atributo de tipo Biblioteca que corresponde a la biblioteca que le sigue.
     */
    public Biblioteca Sig;
    /**
     * Atributo de tipo Integer que corresponde al tamaño de la biblioteca.
     */
    public int Tamano;

    /**
     * Método que retorna el string correspondiente al nombre de la biblioteca. @return Nombre de la biblioteca.
     */
    public String getNombre() {
        return Nombre;
    }

    /**
     * Método que modifica el nombre de la biblioteca.
     * @param nombre String del nombre a cambiar
     */
    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    /**
     * Método que retorna el string correspondiente a la fecha de creación de la biblioteca. @return Fecha de creación de la biblioteca.
     */
    public String getFechaC() {
        return FechaC;
    }

    /**
     * Método que modifica la fecha de creación de la biblioteca.
     * @param fecha String de nueva fecha
     */
    public void setFechaC(String fecha) {
        FechaC = fecha;
    }

    /**
     * Método que retorna el int correspondiente al número de canciones de la biblioteca. @return Nombre de la biblioteca.
     */
    public int getTamano() {
        return Tamano;
    }

    /**
     * Constructor que permite crear nuevas instancias de la clase biblioteca
     * @param nombre Nombre de la biblioteca.
     */
    public Biblioteca(String nombre) {
        this.Primero = null;
        this.Ultimo = null;
        this.Sig = null;
        this.Tamano = 0;
        this.Nombre = nombre;
        this.FechaC = "";
    }

    /**
     * Método que permite añadir una nueva canción a la biblioteca.
     * @param x Nombre de la cancion
     */
    public void InsertarCan(Cancion x) {        /*Cancion cancionN = new Cancion("", "", "", "", "", null, null, "");*/
        if (Primero == null) {
            Primero = x;
            Ultimo = x;
            Primero.Sig = Primero;
            Ultimo.Ant = Ultimo;
            this.Tamano++;
        } else {
            Ultimo.Sig = x;
            x.Sig = Primero;
            x.Ant = Ultimo;
            Ultimo = x;
            Primero.Ant = Ultimo;
            this.Tamano++;
        }
    }

    /**
     * Método que busca en la metadata original la canción que quiere ser añadida a la biblioteca.
     * @param x Nombre de la cancion
     */
    public void InsertnewSong(String x) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Cancion cancionN = new Cancion("", "", "", "", "", "", null, null, "");
        if (Primero == null) {
            try (InputStream is = new FileInputStream("C:\\JavaProjects\\CE_MUSICPLAYER\\CE_MusicPlayer\\MetadataOriginal.xml")) {
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document document = db.parse(is);
                NodeList ListaCanciones = document.getElementsByTagName("Cancion");
                for (int i = 0; i < ListaCanciones.getLength(); i++) {
                    Node Cancion = ListaCanciones.item(i);
                    if (Cancion.getNodeType() == Node.ELEMENT_NODE) {
                        String id = Cancion.getAttributes().getNamedItem("Nombre").getTextContent();
                        if (x.equals(id.trim())) {
                            NodeList childNodes = Cancion.getChildNodes();
                            cancionN.setNombre(id);
                            for (int j = 0; j < childNodes.getLength(); j++) {
                                Node item = childNodes.item(j);
                                if (item.getNodeType() == Node.ELEMENT_NODE) {
                                    if ("Genero".equalsIgnoreCase(item.getNodeName())) {
                                        cancionN.setGen(item.getTextContent());
                                    } else if ("Artista".equalsIgnoreCase(item.getNodeName())) {
                                        cancionN.setArtista(item.getTextContent());
                                    } else if ("Album".equalsIgnoreCase(item.getNodeName())) {
                                        cancionN.setAlbum(item.getTextContent());
                                    } else if ("Ano".equalsIgnoreCase(item.getNodeName())) {
                                        cancionN.setAno(item.getTextContent());
                                    } else if ("Letra".equalsIgnoreCase(item.getNodeName())) {
                                        cancionN.setLetra(item.getTextContent());
                                    } else if ("Direccion".equalsIgnoreCase(item.getNodeName())) {
                                        cancionN.setDireccion(item.getTextContent());
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (SAXException | ParserConfigurationException | IOException e) {
                throw new RuntimeException(e);
            }
            Primero = cancionN;
            Ultimo = cancionN;
            Primero.Sig = Primero;
            Ultimo.Ant = Ultimo;
            this.Tamano++;
        } else {
            try (InputStream is = new FileInputStream("C:\\JavaProjects\\CE_MUSICPLAYER\\CE_MusicPlayer\\MetadataOriginal.xml")) {
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document document = db.parse(is);
                NodeList ListaCanciones = document.getElementsByTagName("Cancion");
                for (int i = 0; i < ListaCanciones.getLength(); i++) {
                    Node Cancion = ListaCanciones.item(i);
                    if (Cancion.getNodeType() == Node.ELEMENT_NODE) {
                        String id = Cancion.getAttributes().getNamedItem("Nombre").getTextContent();
                        if (x.equals(id.trim())) {
                            NodeList childNodes = Cancion.getChildNodes();
                            cancionN.setNombre(id);
                            for (int j = 0; j < childNodes.getLength(); j++) {
                                Node item = childNodes.item(j);
                                if (item.getNodeType() == Node.ELEMENT_NODE) {
                                    if ("Genero".equalsIgnoreCase(item.getNodeName())) {
                                        cancionN.setGen(item.getTextContent());
                                    } else if ("Artista".equalsIgnoreCase(item.getNodeName())) {
                                        cancionN.setArtista(item.getTextContent());
                                    } else if ("Album".equalsIgnoreCase(item.getNodeName())) {
                                        cancionN.setAlbum(item.getTextContent());
                                    } else if ("Ano".equalsIgnoreCase(item.getNodeName())) {
                                        cancionN.setAno(item.getTextContent());
                                    } else if ("Letra".equalsIgnoreCase(item.getNodeName())) {
                                        cancionN.setLetra(item.getTextContent());
                                    } else if ("Direccion".equalsIgnoreCase(item.getNodeName())) {
                                        cancionN.setDireccion(item.getTextContent());
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (SAXException | ParserConfigurationException | IOException e) {
                throw new RuntimeException(e);
            }
            this.Tamano++;
        }
        Ultimo.Sig = cancionN;
        cancionN.Sig = Primero;
        cancionN.Ant = Ultimo;
        Ultimo = cancionN;
        Primero.Ant = Ultimo;
    }

    /**
     * Método que elimina una canción de la biblioteca, busca la canción que debe ser elimina en toda la biblioteca, para esto compara el string recibido por el parámetro y los nombres de las canciones.
     * @param x Nombre de la cancion
     */
    public void eliminarCan(String x) {
        Cancion actual = new Cancion("", "", "", "", "", "", null, null, "");
        Cancion prev = new Cancion("", "", "", "", "", "", null, null, "");
        actual = Primero;
        prev = Ultimo;
        do {
            if (actual.Nombre.equals(x)) {
                if (actual == Primero) {
                    Primero = Primero.Sig;
                    Ultimo.Sig = Primero;
                    Primero.Ant = Ultimo;
                    this.Tamano--;
                } else if (actual == Ultimo) {
                    Ultimo = prev;
                    Primero.Ant = Ultimo;
                    Ultimo.Sig = Primero;
                    this.Tamano--;
                } else {
                    prev.Sig = actual.Sig;
                    actual.Sig.Ant = prev;
                    this.Tamano--;
                }
            }
            prev = actual;
            actual = actual.Sig;
        } while (actual != Primero);
    }

    /**
     * Método que se encarga de recorrer la biblioteca para determinar si esta posee la canción proveniente del parámetro.
     * @param x
     * @return true o false.
     */
    public boolean CompRep(String x) {
        boolean result = false;
        Cancion actual = new Cancion("", "", "", "", "", "", null, null, ""), prev = new Cancion("", "", "", "", "", "", null, null, "");
        actual = this.Primero;
        prev = this.Ultimo;
        do if (actual.getNombre().equals(x)) {
            result = true;
            break;
        } else {
            prev = actual;
            actual = actual.Sig;
        } while (actual != Primero);
        return result;
    }
}
