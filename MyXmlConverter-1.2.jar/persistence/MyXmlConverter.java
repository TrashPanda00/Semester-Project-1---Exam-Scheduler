package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

/**
 * An XML converter for storing a java object in XML,
 * and parsing XML into a java object.
 * <br><br>
 * Example:<br>
 * <code>MyXmlConverter converter = new MyXmlConverter();</code><br>
 * <code>CdList list = converter.fromXml("cdList.xml", CdList.class);</code><br>
 * <code>System.out.println("list=\n" + list);</code><br>
 * <br>
 * <code>File file = converter.toXml(list, "cdList.xml");</code><br>
 * <code>System.out.println("Generated file: " + file.getAbsolutePath());</code><br>
 * <br>
 *
 * @author Steffen Vissing Andersen
 * @version Version 1.2 - Date: 05/11/2019
 */
public class MyXmlConverter
{
  private static final String HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
  private boolean noRoot;
  private Gson gson;
  private String header;
  private String rootNode;

  /**
   * Constructor definining a root node as the lowercase version of the class name
   * of the class to convert, and a header as: <br>
   * <code>&lt;?xml version="1.0" encoding="UTF-8" standalone="no"?&gt;</code>
   */
  public MyXmlConverter()
  {
    this("root", HEADER);
    noRoot = true;
  }

  /**
   * Constructor definining a header as: <br>
   * <code>&lt;?xml version="1.0" encoding="UTF-8" standalone="no"?&gt;</code>
   *
   * @param rootNode the name of the root node in the XML file
   */
  public MyXmlConverter(String rootNode)
  {
    this(rootNode, HEADER);
    noRoot = false;
  }

  /**
   * Constructor<br>
   *
   * @param rootNode the name of the root node in the XML file
   * @param header   the XML header. Use <code>null</code>, if no header
   */
  public MyXmlConverter(String rootNode, String header)
  {
    this.noRoot = false;
    this.rootNode = rootNode;
    this.header = header;
    this.gson = new GsonBuilder().setPrettyPrinting().create();
  }

  /**
   * Converting a Java object into an XML file. Tag indent size is 2.
   *
   * @param javaObject the object to convert
   * @param filename   the name of the output XML file
   * @return a File object for the output XML file
   * @throws XmlConverterException if any exceptions parsing, transfoming,
   *                               writing or reading. The exception tracktrace includes nested exception
   */
  public File toXml(Object javaObject, String filename)
      throws XmlConverterException
  {
    return toXml(javaObject, filename, 2);
  }

  /**
   * Converting a Java object into an XML file
   *
   * @param javaObject   the object to convert
   * @param filename     the name of the output XML file
   * @param indentAmount the XML tag indent character count
   * @return a File object for the output XML file
   * @throws XmlConverterException if any exceptions parsing, transfoming,
   *                               writing or reading. The exception tracktrace includes nested exception
   */
  public File toXml(Object javaObject, String filename, int indentAmount)
      throws XmlConverterException
  {
    try
    {
      String xml = toXml(javaObject, indentAmount);
      File file = new File(filename);
      FileWriter writer = new FileWriter(file);
      writer.write(xml);
      writer.close();
      return file;
    }
    catch (Exception e)
    {
      throw new XmlConverterException(e);
    }
  }

  /**
   * Converting a Java object into an XML string. Tag indent size is 2.
   *
   * @param javaObject the object to convert
   * @return a string containing the XML output
   * @throws XmlConverterException if any exceptions parsing, transfoming,
   *                               writing or reading. The exception tracktrace includes nested exception
   */
  public String toXml(Object javaObject) throws XmlConverterException
  {
    return toXml(javaObject, 2);
  }

  /*
   * Converting a Java object into an XML string
   *
   * @param <T> the type of the object to convert
   * @param javaObject   the object to convert
   * @param indentAmount the XML tag indent character count
   * @return a string containing the XML output
   * @throws XmlConverterException if any exceptions parsing, transfoming,
   *                               writing or reading. The exception tracktrace includes nested exception
   */
  public <T> String toXml(T javaObject, int indentAmount)
      throws XmlConverterException
  {
    try
    {
      if (noRoot)
      {
        rootNode = javaObject.getClass().getSimpleName().toLowerCase();
      }
      String jsonString = gson.toJson(javaObject);
      JSONObject json = new JSONObject(jsonString);
      String xml = prettyPrint(XML.toString(json, rootNode),
          indentAmount);
      if (header != null)
      {
        xml = header + "\n" + xml;
      }
      return xml;
    }
    catch (Exception e)
    {
      throw new XmlConverterException(e);
    }
  }

  /**
   * Parsing an XML file into a Java object
   *
   * @param <T> the type of the object to return
   * @param filename the name of the input XML file
   * @param type     the Class type of the object being returned
   * @return the object created from XML
   * @throws XmlConverterException if any exceptions parsing, transfoming,
   *                               writing or reading. The exception tracktrace includes nested exception
   */
  public <T> T fromXml(String filename, Class<T> type)
      throws XmlConverterException
  {
    try
    {
      if (noRoot)
      {
        rootNode = type.getSimpleName().toLowerCase();
      }
      String xmlString = new String(Files.readAllBytes(Paths.get(filename)));
      T javaObject = fromXmlString(xmlString, type);
      return javaObject;
    }
    catch (NoSuchFileException e)
    {
      throw new XmlConverterException(
          "File not found: " + new File(filename).getAbsolutePath(), e);
    }
    catch (Exception e)
    {
      throw new XmlConverterException(e);
    }

  }

  /**
   * Parsing an XML string into a Java object
   *
   * @param <T> the type of the object to return
   * @param type     the Class type of the object being returned
   * @param xmlString the XML string
   * @return the object created from the XML string
   * @throws XmlConverterException if any exceptions parsing, transfoming,
   *                               writing or reading. The exception tracktrace includes nested exception
   */
  public <T> T fromXmlString(String xmlString, Class<T> type) throws XmlConverterException
  {
    try
    {
      if (noRoot)
      {
        rootNode = type.getSimpleName().toLowerCase();
      }
      JSONObject xmlJSONObj = XML.toJSONObject(xmlString)
          .getJSONObject(rootNode);
      String json = xmlJSONObj.toString();
      T javaObject = gson.fromJson(json, type);
      return javaObject;
    }
    catch (Exception e)
    {
      throw new XmlConverterException(e);
    }

  }

  private String prettyPrint(String xmlString, int indentAmount)
      throws TransformerException, ParserConfigurationException, IOException,
      SAXException
  {
    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
        .newInstance();
    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
    Document document = docBuilder
        .parse(new InputSource(new StringReader(xmlString)));

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
        String.valueOf(indentAmount));
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    DOMSource source = new DOMSource(document);
    StringWriter strWriter = new StringWriter();
    StreamResult result = new StreamResult(strWriter);
    transformer.transform(source, result);
    return strWriter.getBuffer().toString();
  }
}

