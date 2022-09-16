package com.example.ce_musicplayer;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

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

    public void Guardar() {


        try {
            // Creo una instancia de DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Creo un documentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Creo un DOMImplementation
            DOMImplementation implementation = builder.getDOMImplementation();

            // Creo un documento con un elemento raiz
            Document documento = implementation.createDocument(null, "Bibliotecas", null);
            documento.setXmlVersion("1.0");
            Cancion Actual = new Cancion("", "", "", "", "", "", null, null, "");
            Actual = Ultimo;
            Element Bibliotecas = documento.createElement("Bibliotecas");
            Element Biblioteca = documento.createElement("Biblioteca");
            Text textBiblioteca = documento.createTextNode(Nombre);
            Biblioteca.appendChild(textBiblioteca);
            Element Cancion = documento.createElement("Cancion");
            Element Nombre = documento.createElement("Nombre");
            Element Genero = documento.createElement("Genero");
            Element Artista = documento.createElement("Artista");
            Element Ano = documento.createElement("Ano");
            Element Letra = documento.createElement("Letra");
            Element Direccion = documento.createElement("Direccion");

            if (Ultimo != null) {
                do {
                    Text textNombre = documento.createTextNode(Actual.getNombre());
                    Cancion.appendChild(textNombre);
                    Biblioteca.appendChild(Cancion);
                    Text textGenero = documento.createTextNode(Actual.getGen());
                    Cancion.appendChild(textGenero);
                    Text textArtista = documento.createTextNode(Actual.getArtista());
                    Cancion.appendChild(textArtista);
                    Text textAno = documento.createTextNode(Actual.getAno());
                    Cancion.appendChild(textAno);
                    Text textLetra = documento.createTextNode(Actual.getLetra());
                    Cancion.appendChild(textLetra);
                    Text textDireccion = documento.createTextNode(Actual.getDireccion());
                    Cancion.appendChild(textDireccion);


                    Biblioteca.appendChild(Cancion);
                    Actual = Actual.Ant;

                } while (Actual != Ultimo);
            }
            else {
                System.out.println("La Biblioteca se encuentra vacia");
            }
            // Añado al elemento coches el elemento coche
            Bibliotecas.appendChild(Biblioteca);

            // Añado al root el elemento coches
            documento.getDocumentElement().appendChild(Bibliotecas);

            // Asocio el source con el Document
            Source source = new DOMSource(documento);
            // Creo el Result, indicado que fichero se va a crear
            Result result = new StreamResult(new File("Bibliotecas.xml"));

            // Creo un transformer, se crea el fichero XML
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException ex) {
            System.out.println(ex.getMessage());
        }
    }
}




