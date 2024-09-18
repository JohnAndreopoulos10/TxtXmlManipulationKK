package gr.ed.ch.tsilikafeneio.service.impl;

import gr.ed.ch.tsilikafeneio.model.Book;
import gr.ed.ch.tsilikafeneio.model.Chapter;
import gr.ed.ch.tsilikafeneio.model.Paragraph;
import gr.ed.ch.tsilikafeneio.service.FilteredXmlWriterService;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLOutputFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FilteredXmlWriterServiceImpl implements FilteredXmlWriterService {

    @Override
    public void writeFilteredBookToXml(Book book, File file) throws Exception {
        if (book == null) {
            throw new IllegalArgumentException("Book object is null");
        }
        if (file == null) {
            throw new IllegalArgumentException("File object is null");
        }

        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(outputStream, "UTF-8");
            
            xmlStreamWriter.writeStartDocument("UTF-8", "1.0");
            xmlStreamWriter.writeStartElement("book");

            for (Chapter chapter : book.getChapters()) {
                if (chapter.getChapterNumber() > 2) break;
                xmlStreamWriter.writeStartElement("chapter");
                xmlStreamWriter.writeAttribute("number", String.valueOf(chapter.getChapterNumber()));

                for (Paragraph paragraph : chapter.getParagraphs()) {
                    xmlStreamWriter.writeStartElement("paragraph");
                    xmlStreamWriter.writeAttribute("number", String.valueOf(paragraph.getParagraphNumber()));
                    for (String line : paragraph.getLines()) {
                        if (line != null && !line.trim().isEmpty()) {
                            xmlStreamWriter.writeStartElement("line");
                            xmlStreamWriter.writeCharacters(line);
                            xmlStreamWriter.writeEndElement(); 
                        }
                    }

                    xmlStreamWriter.writeEndElement(); 
                }

                xmlStreamWriter.writeEndElement();
            }

            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.flush();
        } catch (XMLStreamException e) {
            throw new Exception("Error writing XML file", e);
        } catch (IOException e) {
            throw new Exception("Error with file output stream", e);
        }
    }
}
