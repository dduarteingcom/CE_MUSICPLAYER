package com.example.ce_musicplayer;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public class Biblioteca {
    public Cancion Primero;

    public Cancion Ultimo;
    public String Nombre;
    public Biblioteca Sig;
    public Biblioteca Ant;
    public int Tamaño;
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


    public Biblioteca(String nombre) {
        this.Primero = null;
        this.Ultimo = null;
        this.Sig=null;
        this.Ant=null;
        this.Tamaño=0;
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
            this.Tamaño++;
        } else {
            Ultimo.Sig = x;
            x.Sig = Primero;
            x.Ant = Ultimo;
            Ultimo = x;
            Primero.Ant = Ultimo;
            this.Tamaño++;
        }
    }

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
                    this.Tamaño--;
                } else if (actual == Ultimo) {
                    Ultimo = prev;
                    Primero.Ant = Ultimo;
                    Ultimo.Sig = Primero;
                    this.Tamaño--;
                } else {
                    prev.Sig = actual.Sig;
                    actual.Sig.Ant = prev;
                    this.Tamaño--;
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
            Document document = documentBuilder.parse("Bibliotecas.xml");
            Element root = document.getDocumentElement();

            Collection<Biblioteca> bibliotecas = new ArrayList<Biblioteca>();
            bibliotecas.add(new Biblioteca(null));
            System.out.println("hola");

            for (Biblioteca biblioteca : bibliotecas) {
                // server elements
                Cancion Actual = new Cancion("", "", "", "", "", "", null, null, "");
                Actual = Ultimo;
                Element Biblioteca = document.createElement("Biblioteca");
                Biblioteca.setAttribute("Nombre", Nombre);


                if (Ultimo != null) {
                    do {
                        Element Cancion = document.createElement("Cancion");
                        Element Genero = document.createElement("Genero");
                        Element Artista = document.createElement("Artista");
                        Element Ano = document.createElement("Ano");
                        Element Letra = document.createElement("Letra");
                        Element Direccion = document.createElement("Direccion");
                        Cancion.setAttribute("Nombre", Actual.getNombre());

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
                root.appendChild(Biblioteca);
            }
            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult("Bibliotecas.xml");
            transformer.transform(source, result);
        } catch (SAXException | TransformerException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarBiblio() throws Exception {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            Document document = dbf.newDocumentBuilder().parse(new File("CE_MusicPlayer/Bibliotecas.xml"));

            XPathFactory xpf = XPathFactory.newInstance();
            XPath xpath = xpf.newXPath();
            XPathExpression expression = xpath.compile("/Bibliotecas/Biblioteca[contains(@Nombre," + "'" + Nombre + "'" + ")]");
            NodeList nodes = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
            if (nodes.getLength() > 0) {
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node node = nodes.item(i);
                    node.getParentNode().removeChild(node);
                }
                DOMSource source = new DOMSource(document);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                StreamResult result = new StreamResult("Bibliotecas.xml");
                transformer.transform(source, result);
            }

        } catch (SAXException | TransformerException | ParserConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void EditarNombreBiblio(String nuevoN) {
        String archivo = "CE_MusicPlayer/Bibliotecas.xml";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try (InputStream is = new FileInputStream(archivo)) {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(is);
            NodeList ListaBibliotecas = document.getElementsByTagName("Biblioteca");
            for (int i = 0; i < ListaBibliotecas.getLength(); i++) {
                Node Biblioteca = ListaBibliotecas.item(i);
                if (Biblioteca.getNodeType() == Node.ELEMENT_NODE) {
                    String id = Biblioteca.getAttributes().getNamedItem("Nombre").getTextContent();

                    if (Nombre.equals(id.trim())) {
                        Biblioteca.getAttributes().getNamedItem("Nombre").setTextContent(nuevoN);
                        System.out.println(Biblioteca.getAttributes().getNamedItem("Nombre"));


                    }
                }
            }
            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult("Bibliotecas.xml");
            transformer.transform(source, result);

        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    public void EditarGeneroC(String nuevoGen, String nomCancion) {
        String archivo = "CE_MusicPlayer/Bibliotecas.xml";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try (InputStream is = new FileInputStream(archivo)) {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(is);
            NodeList ListaBibliotecas = document.getElementsByTagName("Biblioteca");
            for (int i = 0; i < ListaBibliotecas.getLength(); i++) {
                Node Biblioteca = ListaBibliotecas.item(i);

                if (Biblioteca.getNodeType() == Node.ELEMENT_NODE) {
                    String id = Biblioteca.getAttributes().getNamedItem("Nombre").getTextContent();
                    if (Nombre.equals(id.trim())) {
                        NodeList ListaBiblioteca = document.getElementsByTagName("Cancion");
                        for (int j = 0; j < ListaBiblioteca.getLength(); j++) {
                            Node item = ListaBiblioteca.item(j);
                            String id2 = item.getAttributes().getNamedItem("Nombre").getTextContent();
                            System.out.println(id2);
                            if (item.getNodeType() == Node.ELEMENT_NODE && nomCancion.equals(id2.trim())) {
                                NodeList childNodes2 = item.getChildNodes();
                                for (int z = 0; z < childNodes2.getLength(); z++) {
                                    Node item2 = childNodes2.item(z);
                                    System.out.println(item2.getNodeName());
                                    if ("Genero".equalsIgnoreCase(item2.getNodeName())) {
                                        item2.setTextContent(nuevoGen);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult("Bibliotecas.xml");
            transformer.transform(source, result);
        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e) {
            throw new RuntimeException(e);
        }


    }
    public void EditarArtista(String nuevoArtista, String nomCancion) {
        String archivo = "CE_MusicPlayer/Bibliotecas.xml";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try (InputStream is = new FileInputStream(archivo)) {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(is);
            NodeList ListaBibliotecas = document.getElementsByTagName("Biblioteca");
            for (int i = 0; i < ListaBibliotecas.getLength(); i++) {
                Node Biblioteca = ListaBibliotecas.item(i);

                if (Biblioteca.getNodeType() == Node.ELEMENT_NODE) {
                    String id = Biblioteca.getAttributes().getNamedItem("Nombre").getTextContent();
                    if (Nombre.equals(id.trim())) {
                        NodeList ListaBiblioteca = document.getElementsByTagName("Cancion");
                        for (int j = 0; j < ListaBiblioteca.getLength(); j++) {
                            Node item = ListaBiblioteca.item(j);
                            String id2 = item.getAttributes().getNamedItem("Nombre").getTextContent();
                            System.out.println(id2);
                            if (item.getNodeType() == Node.ELEMENT_NODE && nomCancion.equals(id2.trim())) {
                                NodeList childNodes2 = item.getChildNodes();
                                for (int z = 0; z < childNodes2.getLength(); z++) {
                                    Node item2 = childNodes2.item(z);
                                    System.out.println(item2.getNodeName());
                                    if ("Artista".equalsIgnoreCase(item2.getNodeName())) {
                                        item2.setTextContent(nuevoArtista);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult("Bibliotecas.xml");
            transformer.transform(source, result);
        } catch (IOException | ParserConfigurationException | TransformerException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
    public void EditarAlbum(String nuevoAlbum, String nomCancion) {
        String archivo = "CE_MusicPlayer/Bibliotecas.xml";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try (InputStream is = new FileInputStream(archivo)) {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(is);
            NodeList ListaBibliotecas = document.getElementsByTagName("Biblioteca");
            for (int i = 0; i < ListaBibliotecas.getLength(); i++) {
                Node Biblioteca = ListaBibliotecas.item(i);
                if (Biblioteca.getNodeType() == Node.ELEMENT_NODE) {
                    String id = Biblioteca.getAttributes().getNamedItem("Nombre").getTextContent();
                    if (Nombre.equals(id.trim())) {
                        NodeList ListaBiblioteca = document.getElementsByTagName("Cancion");
                        for (int j = 0; j < ListaBiblioteca.getLength(); j++) {
                            Node item = ListaBiblioteca.item(j);
                            String id2 = item.getAttributes().getNamedItem("Nombre").getTextContent();
                            System.out.println(id2);
                            if (item.getNodeType() == Node.ELEMENT_NODE && nomCancion.equals(id2.trim())) {
                                NodeList childNodes2 = item.getChildNodes();
                                for (int z = 0; z < childNodes2.getLength(); z++) {
                                    Node item2 = childNodes2.item(z);
                                    System.out.println(item2.getNodeName());
                                    if ("Album".equalsIgnoreCase(item2.getNodeName())) {
                                        item2.setTextContent(nuevoAlbum);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult("Bibliotecas.xml");
            transformer.transform(source, result);
        } catch (IOException | ParserConfigurationException | TransformerException | SAXException e) {
            throw new RuntimeException(e);
        }


    }
    public void EditarAno(String nuevoAno, String nomCancion) {
        String archivo = "CE_MusicPlayer/Bibliotecas.xml";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try (InputStream is = new FileInputStream(archivo)) {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(is);
            NodeList ListaBibliotecas = document.getElementsByTagName("Biblioteca");
            for (int i = 0; i < ListaBibliotecas.getLength(); i++) {
                Node Biblioteca = ListaBibliotecas.item(i);

                if (Biblioteca.getNodeType() == Node.ELEMENT_NODE) {
                    String id = Biblioteca.getAttributes().getNamedItem("Nombre").getTextContent();
                    if (Nombre.equals(id.trim())) {
                        NodeList ListaBiblioteca = document.getElementsByTagName("Cancion");
                        for (int j = 0; j < ListaBiblioteca.getLength(); j++) {
                            Node item = ListaBiblioteca.item(j);
                            String id2 = item.getAttributes().getNamedItem("Nombre").getTextContent();
                            System.out.println(id2);
                            if (item.getNodeType() == Node.ELEMENT_NODE && nomCancion.equals(id2.trim())) {
                                NodeList childNodes2 = item.getChildNodes();
                                for (int z = 0; z < childNodes2.getLength(); z++) {
                                    Node item2 = childNodes2.item(z);
                                    System.out.println(item2.getNodeName());
                                    if ("Ano".equalsIgnoreCase(item2.getNodeName())) {
                                        item2.setTextContent(nuevoAno);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult("Bibliotecas.xml");
            transformer.transform(source, result);
        } catch (IOException | ParserConfigurationException | TransformerException | SAXException e) {
            throw new RuntimeException(e);
        }


    }
    public void EditarLetra(String nuevoLetra, String nomCancion) {
        String archivo = "CE_MusicPlayer/Bibliotecas.xml";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try (InputStream is = new FileInputStream(archivo)) {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(is);
            NodeList ListaBibliotecas = document.getElementsByTagName("Biblioteca");
            for (int i = 0; i < ListaBibliotecas.getLength(); i++) {
                Node Biblioteca = ListaBibliotecas.item(i);

                if (Biblioteca.getNodeType() == Node.ELEMENT_NODE) {
                    String id = Biblioteca.getAttributes().getNamedItem("Nombre").getTextContent();
                    if (Nombre.equals(id.trim())) {
                        NodeList ListaBiblioteca = document.getElementsByTagName("Cancion");
                        for (int j = 0; j < ListaBiblioteca.getLength(); j++) {
                            Node item = ListaBiblioteca.item(j);
                            String id2 = item.getAttributes().getNamedItem("Nombre").getTextContent();
                            System.out.println(id2);
                            if (item.getNodeType() == Node.ELEMENT_NODE && nomCancion.equals(id2.trim())) {
                                NodeList childNodes2 = item.getChildNodes();
                                for (int z = 0; z < childNodes2.getLength(); z++) {
                                    Node item2 = childNodes2.item(z);
                                    System.out.println(item2.getNodeName());
                                    if ("Letra".equalsIgnoreCase(item2.getNodeName())) {
                                        item2.setTextContent(nuevoLetra);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult("Bibliotecas.xml");
            transformer.transform(source, result);
        } catch (IOException | ParserConfigurationException | TransformerException | SAXException e) {
            throw new RuntimeException(e);
        }


    }




}








