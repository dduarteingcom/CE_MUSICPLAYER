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

public class Bibliotecas {
    public Biblioteca Primero;

    public Bibliotecas(){
        this.Primero=null;

    }
    public void insertBiblio(Biblioteca x){
        x.Sig=Primero;
        this.Primero= x;


    }
    public void eliminarBiblio(Biblioteca x){
        Biblioteca actual= new Biblioteca("");
        actual= this.Primero;
        while(actual!=null){
            if(x.getNombre().equals(this.Primero.getNombre())){
                Primero=Primero.Sig;
            }
            else if(actual.Sig.getNombre().equals(x.getNombre())){
                actual.Sig=actual.Sig.Sig;
                break;
            }
            else{
                actual=actual.Sig;
            }
        }
    }
    public void LectorBM(){
        String archivo = "C:\\Users\\Alvaro Duarte\\Documents\\GitHub\\CE_MUSICPLAYER\\CE_MusicPlayer\\BibliotecasMauricio.xml";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try (InputStream is = new FileInputStream(archivo)) {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(is);
            NodeList ListaBibliotecas = document.getElementsByTagName("Biblioteca");
            for (int i = 0; i < ListaBibliotecas.getLength(); i++) {
                Node Biblioteca = ListaBibliotecas.item(i);

                if (Biblioteca.getNodeType() == Node.ELEMENT_NODE) {
                    String id = Biblioteca.getAttributes().getNamedItem("Nombre").getTextContent();
                    Biblioteca biblioteca=new Biblioteca(id);
                    this.LeerCanciones(biblioteca);
                    this.insertBiblio(biblioteca);


                }

            }


        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void LeerCanciones(Biblioteca x) {
        String archivo = "C:\\Users\\Alvaro Duarte\\Documents\\GitHub\\CE_MUSICPLAYER\\CE_MusicPlayer\\BibliotecasMauricio.xml";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();


        try (InputStream is = new FileInputStream(archivo)) {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(is);
            NodeList ListaBibliotecas = document.getElementsByTagName("Biblioteca");
            for (int i = 0; i < ListaBibliotecas.getLength(); i++) {
                Node Biblioteca = ListaBibliotecas.item(i);

                if (Biblioteca.getNodeType() == Node.ELEMENT_NODE) {
                    String id = Biblioteca.getAttributes().getNamedItem("Nombre").getTextContent();
                    if (x.getNombre().equals(id.trim())) {
                        NodeList ListaBiblioteca = document.getElementsByTagName("Cancion");
                        for (int j = 0; j < ListaBiblioteca.getLength(); j++) {
                            Node item = ListaBiblioteca.item(j);
                            String id2 = item.getAttributes().getNamedItem("Nombre").getTextContent();
                            if (item.getNodeType() == Node.ELEMENT_NODE ) {
                                Cancion cancion=new Cancion(id2,"","","","","",null,null,"");
                                NodeList childNodes2 = item.getChildNodes();
                                for (int z = 0; z < childNodes2.getLength(); z++) {
                                    Node item2 = childNodes2.item(z);

                                    if ("Genero".equalsIgnoreCase(item2.getNodeName())) {
                                        cancion.setGen(item2.getTextContent());
                                    }
                                    else if ("Artista".equalsIgnoreCase(item2.getNodeName())){
                                        cancion.setArtista(item2.getTextContent());
                                    }
                                    else if("Album".equalsIgnoreCase(item2.getNodeName())){
                                        cancion.setAlbum(item2.getTextContent());
                                    }
                                    else if("Ano".equalsIgnoreCase(item2.getNodeName())) {
                                        cancion.setAno(item2.getTextContent());
                                    }
                                    else if("Letra".equalsIgnoreCase(item2.getNodeName())) {
                                        cancion.setLetra(item2.getTextContent());
                                    }
                                    else if("Direccion".equalsIgnoreCase(item2.getNodeName())) {
                                        cancion.setDireccion(item2.getTextContent());
                                    }
                                }
                                x.InsertarCan(cancion);
                            }
                        }
                    }
                }
            }

        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }


    }


}
