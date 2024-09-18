package gr.ed.ch.tsilikafeneio.service.impl;

import gr.ed.ch.tsilikafeneio.service.XsdGenerationService;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class XsdGenerationServiceImpl implements XsdGenerationService {

    @Override
    public void generateXsdFromModels(File xsdFile) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Element schemaElement = doc.createElement("xs:schema");
        schemaElement.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema");
        schemaElement.setAttribute("elementFormDefault", "qualified");
        doc.appendChild(schemaElement);

        addComplexTypeWithElements(doc, schemaElement, "Book", "Chapter", "Statistics");
        addComplexTypeWithElements(doc, schemaElement, "Chapter", "Paragraph");
        addComplexTypeWithElements(doc, schemaElement, "Paragraph", "Line");
        addStatisticsType(doc, schemaElement); //statistics usage


        try (OutputStream outputStream = new FileOutputStream(xsdFile)) {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new DOMSource(doc), new StreamResult(outputStream));
        }
    }

    private void addComplexTypeWithElements(Document doc, Element schemaElement, String typeName, String... elements) {
        Element complexType = doc.createElement("xs:complexType");
        complexType.setAttribute("name", typeName); 
        Element sequence = doc.createElement("xs:sequence");
        complexType.appendChild(sequence);
        schemaElement.appendChild(complexType);


        for (String elementName : elements) {
            Element element = doc.createElement("xs:element");
            element.setAttribute("name", elementName);
            element.setAttribute("type", "xs:string"); 
            sequence.appendChild(element);
        }
    }

    private void addStatisticsType(Document doc, Element schemaElement) {

        Element complexType = doc.createElement("xs:complexType");
        complexType.setAttribute("name", "Statistics"); 
        Element sequence = doc.createElement("xs:sequence");
        complexType.appendChild(sequence);
        schemaElement.appendChild(complexType);

        addElement(doc, sequence, "lineCount", "xs:int");
        addElement(doc, sequence, "paragraphCount", "xs:int");
        addElement(doc, sequence, "wordCount", "xs:int");
        addElement(doc, sequence, "distinctWordCount", "xs:int");
        addElement(doc, sequence, "chapterCount", "xs:int");
        addElement(doc, sequence, "creationTime", "xs:dateTime");
        addElement(doc, sequence, "authorName", "xs:string");
        addElement(doc, sequence, "applicationClassName", "xs:string");
    }

    private void addElement(Document doc, Element sequence, String name, String type) {

        Element element = doc.createElement("xs:element");
        element.setAttribute("name", name); 
        element.setAttribute("type", type); 
        sequence.appendChild(element);
    }
}

