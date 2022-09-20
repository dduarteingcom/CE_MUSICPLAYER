package com.example.ce_musicplayer;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ListaMayor {
    public Lista_bibliotecas listabibliotecas =new Lista_bibliotecas();




    public void Guardar(String nombre) {
        if(nombre.equals("Mauricio")){
            archivo = "CE_MusicPlayer/BibliotecasMauricio.xml";
        }
        else if(nombre.equals("Daniel")){
            archivo = "CE_MusicPlayer/BibliotecasDaniel.xml";
        }
        else{
            archivo = "CE_MusicPlayer/BibliotecasMbappe.xml";
        }
        try {
            Biblioteca temporal = new Biblioteca("");
            temporal = listabibliotecas.Primero;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document documento = implementation.createDocument(null, "Bibliotecas", null);
            documento.setXmlVersion("1.0");
            while (temporal != null) {
                Cancion Actual = new Cancion("", "", "", "", "", "", null, null, "");
                Actual = temporal.Ultimo;
                Element Biblioteca = documento.createElement("Biblioteca");
                Biblioteca.setAttribute("Nombre", temporal.Nombre);
                if (temporal.Ultimo != null) {
                    do {
                        Element Cancion = documento.createElement("Cancion");
                        Element Genero = documento.createElement("Genero");
                        Element Artista = documento.createElement("Artista");
                        Element Album= documento.createElement("Album");
                        Element Ano = documento.createElement("Ano");
                        Element Letra = documento.createElement("Letra");
                        Element Direccion = documento.createElement("Direccion");
                        Cancion.setAttribute("Nombre", Actual.getNombre());
                        Biblioteca.appendChild(Cancion);
                        Text textGenero = documento.createTextNode(Actual.getGen());
                        Genero.appendChild(textGenero);
                        Cancion.appendChild(Genero);
                        Text textArtista = documento.createTextNode(Actual.getArtista());
                        Artista.appendChild(textArtista);
                        Cancion.appendChild(Artista);
                        Text textAlbum = documento.createTextNode(Actual.getAlbum());
                        Album.appendChild(textAlbum);
                        Cancion.appendChild(Album);
                        Text textAno = documento.createTextNode(Actual.getAno());
                        Ano.appendChild(textAno);
                        Cancion.appendChild(Ano);
                        Text textLetra = documento.createTextNode(Actual.getLetra());
                        Letra.appendChild(textLetra);
                        Cancion.appendChild(Letra);
                        Text textDireccion = documento.createTextNode(Actual.getDireccion());
                        Direccion.appendChild(textDireccion);
                        Cancion.appendChild(Direccion);
                        Biblioteca.appendChild(Cancion);
                        Actual = Actual.Ant;

                    } while (Actual != temporal.Ultimo);
                } else {
                    System.out.println("La Biblioteca se encuentra vacia");
                }
                documento.getDocumentElement().appendChild(Biblioteca);
                temporal=temporal.Sig;
            }
            Source source = new DOMSource(documento);
            Result result = new StreamResult(new File(archivo));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public String archivo;
    public void LectorBM(String nombre) {
        if(nombre.equals("Mauricio")){
            archivo = "BibliotecasMauricio.xml";
        }
        else if(nombre.equals("Daniel")){
            archivo = "BibliotecasDaniel.xml";
        }
        else{
            archivo = "BibliotecasMbappe.xml";
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try (InputStream is = new FileInputStream(archivo)) {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(is);
            NodeList ListaBibliotecas = document.getElementsByTagName("Biblioteca");
            for (int i = 0; i < ListaBibliotecas.getLength(); i++) {
                Node Biblioteca = ListaBibliotecas.item(i);

                if (Biblioteca.getNodeType() == Node.ELEMENT_NODE) {
                    String id = Biblioteca.getAttributes().getNamedItem("Nombre").getTextContent();
                    Biblioteca bibliotecatmp = new Biblioteca(id);
                    this.LeerCanciones(bibliotecatmp);
                    listabibliotecas.insertBiblio(bibliotecatmp);


                }

            }

        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void LeerCanciones(Biblioteca x) {
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
                        NodeList childBibliotecas = Biblioteca.getChildNodes();
                        System.out.println(childBibliotecas);
                        for (int j = 0; j < childBibliotecas.getLength(); j++) {
                            Node item = childBibliotecas.item(j);


                            if (item.getNodeType() == Node.ELEMENT_NODE&&x.getNombre().equals(id.trim())) {
                                String id2 = item.getAttributes().getNamedItem("Nombre").getTextContent();
                                Cancion cancion = new Cancion(id2, "", "", "", "", "", null, null, "");
                                NodeList childNodes2 = item.getChildNodes();
                                for (int z = 0; z < childNodes2.getLength(); z++) {
                                    Node item2 = childNodes2.item(z);
                                    System.out.println(item2.getTextContent());

                                    if ("Genero".equalsIgnoreCase(item2.getNodeName())) {
                                        cancion.setGen(item2.getTextContent());
                                    } else if ("Artista".equalsIgnoreCase(item2.getNodeName())) {
                                        cancion.setArtista(item2.getTextContent());
                                    } else if ("Album".equalsIgnoreCase(item2.getNodeName())) {
                                        cancion.setAlbum(item2.getTextContent());
                                    } else if ("Ano".equalsIgnoreCase(item2.getNodeName())) {
                                        cancion.setAno(item2.getTextContent());
                                    } else if ("Letra".equalsIgnoreCase(item2.getNodeName())) {
                                        cancion.setLetra(item2.getTextContent());
                                    } else if ("Direccion".equalsIgnoreCase(item2.getNodeName())) {
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