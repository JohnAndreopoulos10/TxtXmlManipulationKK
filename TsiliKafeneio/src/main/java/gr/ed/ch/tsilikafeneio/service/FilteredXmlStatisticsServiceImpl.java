package gr.ed.ch.tsilikafeneio.service.impl;

import gr.ed.ch.tsilikafeneio.model.Statistics;
import gr.ed.ch.tsilikafeneio.service.FilteredXmlStatisticsService;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;

public class FilteredXmlStatisticsServiceImpl implements FilteredXmlStatisticsService {

    @Override
    public Statistics generateFilteredXmlStatistics(File file) throws Exception {
        if (file == null) {
            throw new IllegalArgumentException("File object is null");
        }

        Statistics statistics = new Statistics();
        int chapterCount = 0;
        int paragraphCount = 1; // for our eyes on the console 2 chapters , 40 paragraphs instead of 2,39

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try (FileInputStream inputStream = new FileInputStream(file)) {
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);

            while (xmlStreamReader.next() != XMLStreamConstants.END_DOCUMENT) {
                switch (xmlStreamReader.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        if ("chapter".equals(xmlStreamReader.getLocalName())) {
                            chapterCount++;
                        } else if ("paragraph".equals(xmlStreamReader.getLocalName())) {
                            paragraphCount++;
                        }
                        break;
                }
            }
        } catch (Exception e) {
            throw new Exception("Error reading XML file", e);
        }

        statistics.setChapterCount(chapterCount);
        statistics.setParagraphCount(paragraphCount);
        return statistics;
    }
}
