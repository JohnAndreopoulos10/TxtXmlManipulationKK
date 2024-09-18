package gr.ed.ch.tsilikafeneio.service.impl;

import gr.ed.ch.tsilikafeneio.model.Book;
import gr.ed.ch.tsilikafeneio.model.Chapter;
import gr.ed.ch.tsilikafeneio.model.Paragraph;
import gr.ed.ch.tsilikafeneio.service.XmlWriterService;
import java.io.File;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XmlWriterServiceImpl implements XmlWriterService {

    private static final Logger LOGGER = Logger.getLogger(XmlWriterServiceImpl.class.getName());

    @Override
    public void writeBookToXml(Book book, File file) {
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();

        try (OutputStream outputStream = new FileOutputStream(file)) {
            XMLStreamWriter xmlWriter = xmlOutputFactory.createXMLStreamWriter(outputStream);

            xmlWriter.writeStartDocument();
            xmlWriter.writeStartElement("book");

            List<Chapter> chapters = book.getChapters();
            for (Chapter chapter : chapters) {
                xmlWriter.writeStartElement("chapter");
                xmlWriter.writeAttribute("number", Integer.toString(chapter.getChapterNumber()));

                List<Paragraph> paragraphs = chapter.getParagraphs();
                for (Paragraph paragraph : paragraphs) {
                    xmlWriter.writeStartElement("paragraph");
                    xmlWriter.writeAttribute("number", Integer.toString(paragraph.getParagraphNumber()));

                    for (String line : paragraph.getLines()) {
                        xmlWriter.writeStartElement("line");
                        xmlWriter.writeCharacters(line);
                        xmlWriter.writeEndElement(); 
                    }

                    xmlWriter.writeEndElement(); 
                }

                xmlWriter.writeEndElement(); 
            }

            xmlWriter.writeEndElement(); 
            xmlWriter.writeEndDocument();
            xmlWriter.flush();

            LOGGER.info("XML file written successfully.");

        } catch (XMLStreamException | IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing XML file", e);
        }
    }
}
// works p 