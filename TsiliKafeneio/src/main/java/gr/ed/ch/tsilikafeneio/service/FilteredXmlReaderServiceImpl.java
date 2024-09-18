package gr.ed.ch.tsilikafeneio.service.impl;

import gr.ed.ch.tsilikafeneio.model.Book;
import gr.ed.ch.tsilikafeneio.model.Chapter;
import gr.ed.ch.tsilikafeneio.model.Paragraph;
import gr.ed.ch.tsilikafeneio.service.FilteredXmlReaderService;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class FilteredXmlReaderServiceImpl implements FilteredXmlReaderService {

    @Override
    public Book readFilteredChaptersFromXml(File file) throws Exception {
        if (file == null) {
            throw new IllegalArgumentException("File object is null");
        }

        Book book = new Book();
        List<Chapter> chapters = new ArrayList<>();
        Chapter currentChapter = null;
        Paragraph currentParagraph = null;
        List<Paragraph> paragraphs = new ArrayList<>();
        int chapterCount = 0;

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try (FileInputStream inputStream = new FileInputStream(file)) {
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);

            while (xmlStreamReader.next() != XMLStreamConstants.END_DOCUMENT) {
                switch (xmlStreamReader.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        if ("chapter".equals(xmlStreamReader.getLocalName())) {
                            if (chapterCount >= 2) break; 
                            currentChapter = new Chapter();
                            currentChapter.setChapterNumber(Integer.parseInt(xmlStreamReader.getAttributeValue(null, "number")));
                            paragraphs = new ArrayList<>();
                            currentChapter.setParagraphs(paragraphs);
                            chapterCount++;
                        } else if ("paragraph".equals(xmlStreamReader.getLocalName()) && currentChapter != null) {
                            currentParagraph = new Paragraph();
                            currentParagraph.setParagraphNumber(Integer.parseInt(xmlStreamReader.getAttributeValue(null, "number")));
                            currentParagraph.setLines(new ArrayList<>());
                            paragraphs.add(currentParagraph);
                        } else if ("line".equals(xmlStreamReader.getLocalName()) && currentParagraph != null) {
                            currentParagraph.getLines().add(xmlStreamReader.getElementText());
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        if ("chapter".equals(xmlStreamReader.getLocalName()) && currentChapter != null) {
                            chapters.add(currentChapter);
                            currentChapter = null;
                        }
                        break;
                }
            }
            book.setChapters(chapters);
        } catch (Exception e) {
            throw new Exception("Error reading XML file", e);
        }

        return book;
    }
}
