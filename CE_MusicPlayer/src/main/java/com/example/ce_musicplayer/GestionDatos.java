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

/**
 * En esta Clase se hace el guardado y lectura de datos de los xml. La información presente guardada es necesaria para
 * crear nuevas bibliotecas que contengan la información de los xml que luego serán añadidos a una nueva instancia de
 * la clase Lista_bibliotecas.
 */
public class GestionDatos {
    /**
     * String que posee el nombre del xml con el que se está trabajando.
     */
    public String archivo;
    public Lista_bibliotecas listabibliotecas =new Lista_bibliotecas();

    /**
     * Método para guardar la lista que contiene las bibliotecas en el xml que corresponda.
     * @param nombre
     */
    public void Guardar(String nombre) {
        if(nombre.equals("Mauricio")){
            archivo = "C:\\JavaProjects\\CE_MUSICPLAYER\\CE_MusicPlayer\\BibliotecasMauricio.xml";
        }
        else if(nombre.equals("Daniel")){
            archivo = "C:\\JavaProjects\\CE_MUSICPLAYER\\CE_MusicPlayer\\BibliotecasDaniel.xml";
        }
        else{
            archivo = "C:\\JavaProjects\\CE_MUSICPLAYER\\CE_MusicPlayer\\BibliotecasMbappe.xml";
        }
        try {
            //Se crea una instancia para recorrer la biblioteca
            Biblioteca temporal = new Biblioteca("");
            //Se iguala a la cabeza de la lista para recorrerla desde el principio.
            temporal = listabibliotecas.Primero;
            //Se crea una instancia de DocumentBuilderFactory.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //Se crea un DocumentBuilder.
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Nueva instancia de DOMImplementation.
            DOMImplementation implementation = builder.getDOMImplementation();
            //Se crea un nuevo xml.
            Document documento = implementation.createDocument(null, "Bibliotecas", null);
            documento.setXmlVersion("1.0");
            while (temporal != null) {
                //Se crea una instancia de canción para recorrer la biblioteca.
                Cancion Actual = new Cancion("", "", "", "", "", "", null, null, "");
                //Se comienza a recorrer desde la última canción.
                Actual = temporal.Ultimo;
                //Se crea un elemento llamado biblioteca en el xml.
                Element Biblioteca = documento.createElement("Biblioteca");
                //Al elemento biblioteca se le asigna un atributo que corresponde al nombre de la biblioteca.
                Biblioteca.setAttribute("Nombre", temporal.Nombre);
                //Creación de un elemento que corresponde a la fecha de creación de la biblioteca.
                Element Fecha=documento.createElement("Fecha");;
                //Texto que va a poseer el elemento fecha.
                Text textFecha=documento.createTextNode(temporal.getFechaC());
                //Se le asigna el texto como un nodo hijo al elemento fecha.
                Fecha.appendChild(textFecha);
                //Se le asigna el elemento fecha como un nodo hijo al elemento Biblioteca.
                Biblioteca.appendChild(Fecha);
                //Mientras que el temporal sea diferente a nulo se van a guardar las bibliotecas en el xml.
                if (temporal.Ultimo != null) {
                    do {
                        /*Se crean los elementos que corresponden a los atributos de la canción.
                        Se crean sus respectivos textos que serán añadidos como nodos hijos.
                        Luego los elementos creados son añadidos como hijos al elemento canción.
                        */
                        Element Cancion = documento.createElement("Cancion");
                        Element Nombre =documento.createElement("Nombre");
                        Element Genero = documento.createElement("Genero");
                        Element Artista = documento.createElement("Artista");
                        Element Album= documento.createElement("Album");
                        Element Ano = documento.createElement("Ano");
                        Element Letra = documento.createElement("Letra");
                        Element Direccion = documento.createElement("Direccion");
                        Element Favorita= documento.createElement("Favorita");
                        Text textNombre = documento.createTextNode(Actual.getNombre());
                        Nombre.appendChild(textNombre);
                        Cancion.appendChild(Nombre);
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
                        Text textFavorita = documento.createTextNode(String.valueOf(Actual.isFavorita()));
                        Favorita.appendChild(textFavorita);
                        Cancion.appendChild(Favorita);
                        //Después de añadir todos los nodos hijos a la canción este elemento pasa a ser añadido al elemento
                        //biblioteca
                        Biblioteca.appendChild(Cancion);
                        //La canción actual pasa a ser la que es anterior a ella.
                        Actual = Actual.Ant;

                    } while (Actual != temporal.Ultimo);
                } else {
                    System.out.println("La Biblioteca se encuentra vacia");
                }
                //El elemento creado que correspondía a la biblioteca pasa a ser añadido al documento que se había creado
                //previamente.
                documento.getDocumentElement().appendChild(Biblioteca);
                //Se pasa a evaluar la biblioteca siguiente.
                temporal=temporal.Sig;
            }
            //Se asocia el source al documento.
            Source source = new DOMSource(documento);
            //Se leen los streams del archivo xml.
            Result result = new StreamResult(new File(archivo));
            //Nueva instancia de Transformer.
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            //Convierto los strings a instancias de objetos.
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método que recorre el archivo xml creando nuevas bibliotecas y cambiando los nombres de las bibliotecas creadas
     * por las que corresponden del xml. Además, llama a otro método que le va añadiendo las canciones pertenecientes
     * al la bilioteca según el xml.
     * @param nombre
     */
    public void Lector(String nombre) {
        if(nombre.equals("Mauricio")){
            archivo = "C:\\Users\\Alvaro Duarte\\Documents\\GitHub\\CE_MUSICPLAYER\\CE_MusicPlayer\\BibliotecasMauricio.xml";
        }
        else if(nombre.equals("Daniel")){
            archivo = "C:\\Users\\Alvaro Duarte\\Documents\\GitHub\\CE_MUSICPLAYER\\CE_MusicPlayer\\BibliotecasDaniel.xml";
        }
        else{
            archivo = "C:\\Users\\Alvaro Duarte\\Documents\\GitHub\\CE_MUSICPLAYER\\CE_MusicPlayer\\BibliotecasMbappe.xml";
        }
        //Se crea una instancia de DocumentBuilderFactory.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //Se leen los streams del archivo xml
        try (InputStream input = new FileInputStream(archivo)) {
            //Se crea una instancia de DocumentBuilder.
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Analiza el contenido del xml y crea un nuevo documento DOM.
            Document documento = builder.parse(input);
            //Se crea una lista de nodos con todos los elementos que lleven la etiqueta Biblioteca.
            NodeList ListaBibliotecas = documento.getElementsByTagName("Biblioteca");
            //Se procede a recorrer la lista de nodos con la etiqueta Biblioteca.
            for (int i = 0; i < ListaBibliotecas.getLength(); i++) {
                //Se analiza el nodo que se encuentre en la posición i.
                Node Biblioteca = ListaBibliotecas.item(i);
                //Se verifica que el nodo recibido sea un elemento.
                if (Biblioteca.getNodeType() == Node.ELEMENT_NODE) {
                    //Se obtiene el nombre de la biblioteca
                    String id = Biblioteca.getAttributes().getNamedItem("Nombre").getTextContent();
                    //Se crea una nueva instancia de Biblioteca.
                    Biblioteca bibliotecatmp = new Biblioteca(id);
                    if (bibliotecatmp.getNombre().equals(id.trim())) {
                        NodeList childBibliotecas = Biblioteca.getChildNodes();

                        for (int j = 0; j < childBibliotecas.getLength(); j++) {
                            Node item = childBibliotecas.item(j);
                            if (item.getNodeName().equals("Fecha")){
                                bibliotecatmp.setFechaC(item.getTextContent());
                            }
                            if (item.getNodeType() == Node.ELEMENT_NODE&&bibliotecatmp.getNombre().equals(id.trim())) {
                                NodeList childNodes2 = item.getChildNodes();

                                Cancion cancion = new Cancion("A", "B", "C", "D", "E", "F", null, null, "G");
                                for (int z = 0; z < childNodes2.getLength(); z++) {
                                    Node item2 = childNodes2.item(z);

                                    if("Nombre".equalsIgnoreCase(item2.getNodeName())){
                                        cancion.setNombre(item2.getTextContent());

                                    }
                                    else if ("Genero".equalsIgnoreCase(item2.getNodeName())) {
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
                                    else if ("Favorita".equalsIgnoreCase(item2.getNodeName())) {
                                        cancion.setFavorita(Boolean.valueOf(item2.getTextContent()));
                                    }

                                }
                                if(!cancion.getNombre().equals("A")){
                                    bibliotecatmp.InsertarCan(cancion);
                                    bibliotecatmp.Tamano++;
                                }
                            }
                        }
                    }
                    //Se inserta a listaBibliotecas la instancia de la biblioteca creada.
                    listabibliotecas.insertBiblio(bibliotecatmp);
                }
            }
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
