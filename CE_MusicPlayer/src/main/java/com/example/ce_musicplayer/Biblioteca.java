package com.example.ce_musicplayer;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Biblioteca {
    public Cancion Primero;

    public Cancion Ultimo;
    public String Nombre;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Biblioteca(String nombre) {
        this.Primero = null;
        this.Ultimo = null;
        this.Nombre = nombre;
    }


    public void ingresar(Cancion x) {
        //Cancion cancionN = new Cancion("", "", "", "", "", null, null, "");
        if (Primero == null) {
            Primero = x;
            Ultimo = x;
            Primero.Sig = Primero;
            Ultimo.Ant = Ultimo;
        } else {
            Ultimo.Sig = x;
            x.Sig = Primero;
            x.Ant = Ultimo;
            Ultimo = x;
            Primero.Ant = Ultimo;
        }
    }

    public void eliminar(String x) {
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
                } else if (actual == Ultimo) {
                    Ultimo = prev;
                    Primero.Ant = Ultimo;
                    Ultimo.Sig = Primero;
                } else {
                    prev.Sig = actual.Sig;
                    actual.Sig.Ant = prev;
                }
            }
            prev = actual;
            actual = actual.Sig;

        } while (actual != Primero);


    }

    public void GuardarBiblio() throws IOException {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse("C:\\Users\\Alvaro Duarte\\Documents\\GitHub\\CE_MUSICPLAYER\\CE_MusicPlayer\\Bibliotecas.xml");
            Element root = document.getDocumentElement();

            Collection<Biblioteca> bibliotecas = new ArrayList<Biblioteca>();
            bibliotecas.add(new Biblioteca(null));
            System.out.println("hola");

            for (Biblioteca biblioteca : bibliotecas) {
                // server elements
                Cancion Actual = new Cancion("", "", "", "", "", "", null, null, "");
                Actual = Ultimo;
                Element Bibliotecas = document.createElement("Bibliotecas");
                Element Biblioteca = document.createElement("Biblioteca");
                Bibliotecas.setAttribute("Nombre",Nombre);



                if (Ultimo != null) {
                    do {
                        Element Cancion = document.createElement("Cancion");
                        Element Nombre = document.createElement("Nombre");
                        Element Genero = document.createElement("Genero");
                        Element Artista = document.createElement("Artista");
                        Element Ano = document.createElement("Ano");
                        Element Letra = document.createElement("Letra");
                        Element Direccion = document.createElement("Direccion");
                        Text textNombre = document.createTextNode(Actual.getNombre());
                        Cancion.appendChild(textNombre);

                        Text textGenero = document.createTextNode(Actual.getGen());
                        Genero.appendChild(textGenero);
                        Cancion.appendChild(Genero);
                        Text textArtista = document.createTextNode(Actual.getArtista());
                        Artista.appendChild(textArtista);
                        Cancion.appendChild(Artista);
                        Text textAno = document.createTextNode(Actual.getAno());
                        Ano.appendChild(textAno);
                        Cancion.appendChild(Ano);
                        Text textLetra = document.createTextNode(Actual.getLetra());
                        Letra.appendChild(textLetra);
                        Cancion.appendChild(Letra);
                        Text textDireccion = document.createTextNode(Actual.getDireccion());
                        Direccion.appendChild(textDireccion);
                        Cancion.appendChild(Direccion);


                        Biblioteca.appendChild(Cancion);

                        Actual = Actual.Ant;

                    } while (Actual != Ultimo);
                } else {
                    System.out.println("La Biblioteca se encuentra vacia");
                }

                Bibliotecas.appendChild(Biblioteca);
                root.appendChild(Bibliotecas);
            }

            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult("Bibliotecas.xml");
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarBiblio() throws Exception {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            Document document = dbf.newDocumentBuilder().parse(new File("C:\\Users\\Alvaro Duarte\\Documents\\GitHub\\CE_MUSICPLAYER\\CE_MusicPlayer\\Bibliotecas.xml"));

            XPathFactory xpf = XPathFactory.newInstance();
            XPath xpath = xpf.newXPath();
            XPathExpression expression = xpath.compile("/Bibliotecas/Bibliotecas[contains(@Nombre,"+"'"+Nombre+"'"+")]");
            NodeList nodes = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
            if (nodes.getLength() > 0) {
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node node = nodes.item(i);
                    node.getParentNode().removeChild(node);
                }
            }
            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult("Bibliotecas.xml");
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }


    public void EditarNombreBiblio(){
        String archvo = "src/main/resources/staff-simple.xml";
    }
}






