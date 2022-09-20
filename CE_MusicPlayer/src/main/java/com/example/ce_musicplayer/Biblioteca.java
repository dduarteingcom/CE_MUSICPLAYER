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

public class Biblioteca {
    public Cancion Primero;
    public Cancion Ultimo;
    public String Nombre;
    public Biblioteca Sig;
    public int Tamano;
    public String FechaC;

    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    public String getFechaC() {
        return FechaC;
    }
    public void setFechaC(String fecha) {
        FechaC = fecha;
    }

    public int getTamano() {
        return Tamano;
    }

    public Biblioteca(String nombre) {
        this.Primero = null;
        this.Ultimo = null;
        this.Sig=null;
        this.Tamano=0;
        this.Nombre = nombre;
        this.FechaC="";
    }
    public void InsertarCan(Cancion x) {
        //Cancion cancionN = new Cancion("", "", "", "", "", null, null, "");
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
    public void InsertnewSong(String x) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Cancion cancionN = new Cancion("", "", "", "", "", "", null, null, "");
        if (Primero == null) {
            try (InputStream is = new FileInputStream("CE_MusicPlayer/MetadataOriginal.xml")) {
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document document = db.parse(is);
                NodeList ListaCanciones = document.getElementsByTagName("Cancion");
                for (int i = 0; i < ListaCanciones.getLength(); i++) {
                    Node Cancion = ListaCanciones.item(i);
                    if (Cancion.getNodeType() == Node.ELEMENT_NODE) {
                        String id = Cancion.getAttributes().getNamedItem("Nombre").getTextContent();
                        cancionN.setNombre(id);
                        if (x.equals(id.trim())) {
                            NodeList childNodes = Cancion.getChildNodes();
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
        }
        else{
            try (InputStream is = new FileInputStream("CE_MusicPlayer/MetadataOriginal.xml")) {
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document document = db.parse(is);
                NodeList ListaCanciones = document.getElementsByTagName("Cancion");
                for (int i = 0; i < ListaCanciones.getLength(); i++) {
                    Node Cancion = ListaCanciones.item(i);
                    if (Cancion.getNodeType() == Node.ELEMENT_NODE) {
                        String id = Cancion.getAttributes().getNamedItem("Nombre").getTextContent();
                        cancionN.setNombre(id);
                        if (x.equals(id.trim())) {
                            NodeList childNodes = Cancion.getChildNodes();
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

        }
        Ultimo.Sig = cancionN;
        cancionN.Sig = Primero;
        cancionN.Ant = Ultimo;
        Ultimo = cancionN;
        Primero.Ant = Ultimo;
        this.Tamano++;

        }


        public void eliminarCan (String x){
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


}








