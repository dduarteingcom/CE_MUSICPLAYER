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

public class UserMbappe extends Usuario {
    public Bibliotecas bibliotecas=new Bibliotecas();
    public UserMbappe(String nombre, String correo, String provincia, String contrasena) {
        super(nombre, correo, provincia, contrasena);
    }
    public void Guardar() {
        try {
            Biblioteca temporal = new Biblioteca("");
            temporal = bibliotecas.Primero;
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
            Result result = new StreamResult(new File("BibliotecasMbappe.xml"));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void LectorBMB() {
        String archivo = "BibliotecasMbappe.xml";
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
                    bibliotecas.insertBiblio(bibliotecatmp);


                }

            }


        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void LeerCanciones(Biblioteca x) {
        String archivo = "BibliotecasMbappe.xml";
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
                            if (item.getNodeType() == Node.ELEMENT_NODE) {
                                Cancion cancion = new Cancion(id2, "", "", "", "", "", null, null, "");
                                NodeList childNodes2 = item.getChildNodes();
                                for (int z = 0; z < childNodes2.getLength(); z++) {
                                    Node item2 = childNodes2.item(z);

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
