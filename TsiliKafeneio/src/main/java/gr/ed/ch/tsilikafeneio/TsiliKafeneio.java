package gr.ed.ch.tsilikafeneio;

import gr.ed.ch.tsilikafeneio.model.Book;
import gr.ed.ch.tsilikafeneio.model.Statistics;
import gr.ed.ch.tsilikafeneio.service.FilteredXmlReaderService;
import gr.ed.ch.tsilikafeneio.service.FilteredXmlStatisticsService;
import gr.ed.ch.tsilikafeneio.service.TxtParserService;
import gr.ed.ch.tsilikafeneio.service.XmlWriterService;
import gr.ed.ch.tsilikafeneio.service.XsdValidationService;
import gr.ed.ch.tsilikafeneio.service.impl.FilteredXmlReaderServiceImpl;
import gr.ed.ch.tsilikafeneio.service.impl.FilteredXmlStatisticsServiceImpl;
import gr.ed.ch.tsilikafeneio.service.impl.TxtParserServiceImpl;
import gr.ed.ch.tsilikafeneio.service.impl.XmlWriterServiceImpl;
import gr.ed.ch.tsilikafeneio.service.impl.XsdValidationServiceImpl;

import java.io.File;


public class TsiliKafeneio {

    public static void main(String[] args) {
        String textFileUrl = "https://github.com/iracleous/xmlDemo/raw/master/xml_files/sample-lorem-ipsum-text-file.txt";
        File bookXmlFile = new File("xml/book.xml");
        File filteredXmlFile = new File("xml/filtered-book.xml");
        File xsdFile = new File("xml/book-schema.xsd");

        TxtParserService txtParserService = new TxtParserServiceImpl();
        XmlWriterService xmlWriterService = new XmlWriterServiceImpl();
        FilteredXmlReaderService filteredXmlReaderService = new FilteredXmlReaderServiceImpl();
        FilteredXmlStatisticsService filteredXmlStatisticsService = new FilteredXmlStatisticsServiceImpl();
        XsdValidationService xmlValidationService = new XsdValidationServiceImpl();

        Book book = null;
        try {
            book = txtParserService.parseTextFile(textFileUrl);
            System.out.println("Text file parsed successfully.");
        } catch (Exception e) {
            System.err.println("Error parsing the text file: " + e.getMessage());
            return;
        }

        try {
            xmlWriterService.writeBookToXml(book, bookXmlFile);
            System.out.println("Book XML file written successfully.");
        } catch (Exception e) {
            System.err.println("Error writing XML file: " + e.getMessage());
            return;
        }

        Statistics statistics = null;
        try {
            statistics = book.getStatistics();
            System.out.println("Full XML statistics:");
            System.out.println("Total Lines: " + statistics.getLineCount());
            System.out.println("Paragraph Count: " + statistics.getParagraphCount());
            System.out.println("Word Count: " + statistics.getWordCount());
            System.out.println("Distinct Word Count: " + statistics.getDistinctWordCount());
            System.out.println("Chapter Count: " + statistics.getChapterCount());
            System.out.println("Creation Time: " + statistics.getCreationTime());
            System.out.println("Author Name: " + statistics.getAuthorName());
            System.out.println("Application Class Name: " + statistics.getApplicationClassName());
        } catch (Exception e) {
            System.err.println("Error generating statistics for the full XML file: " + e.getMessage());
        }

        Book filteredBook = null;
        try {
            filteredBook = filteredXmlReaderService.readFilteredChaptersFromXml(bookXmlFile);
            System.out.println("Filtered chapters read successfully.");
        } catch (Exception e) {
            System.err.println("Error reading filtered chapters: " + e.getMessage());
            return;
        }

        try {
            xmlWriterService.writeBookToXml(filteredBook, filteredXmlFile);
            System.out.println("Filtered XML file written successfully.");
        } catch (Exception e) {
            System.err.println("Error writing filtered XML file: " + e.getMessage());
            return;
        }

        Statistics filteredStatistics = null;
        try {
            filteredStatistics = filteredXmlStatisticsService.generateFilteredXmlStatistics(filteredXmlFile);
            System.out.println("Filtered XML statistics generated successfully.");
            System.out.println("Filtered XML Statistics:");
            System.out.println("Chapter Count: " + filteredStatistics.getChapterCount());
            System.out.println("Paragraph Count: " + filteredStatistics.getParagraphCount());
        } catch (Exception e) {
            System.err.println("Error generating statistics for filtered XML: " + e.getMessage());
        }

        try {
            boolean isValid = xmlValidationService.validateXmlAgainstXsd(filteredXmlFile, xsdFile);
            if (isValid) {
                System.out.println("Filtered XML file is valid according to the XSD.");
            } else {
                System.out.println("Filtered XML file is not valid according to the XSD.");
            }
        } catch (Exception e) {
            System.err.println("Error validating XML file: " + e.getMessage());
        }
    }
}
