package gr.ed.ch.tsilikafeneio.service.impl;

import gr.ed.ch.tsilikafeneio.model.Book;
import gr.ed.ch.tsilikafeneio.model.Chapter;
import gr.ed.ch.tsilikafeneio.model.Paragraph;
import gr.ed.ch.tsilikafeneio.model.Statistics;
import gr.ed.ch.tsilikafeneio.service.TxtParserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TxtParserServiceImpl implements TxtParserService {

    @Override
    public Book parseTextFile(String fileUrl) {
        Book book = new Book();
        List<Chapter> chapters = new ArrayList<>();
        List<Paragraph> paragraphs = new ArrayList<>();
        Chapter currentChapter = null;
        int paragraphCounter = 1;
        int chapterCounter = 1;
        int lineCounter = 1;  
        int textLineCounter = 1; 

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(fileUrl).openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCounter++; 

                if (line.trim().isEmpty()) {
                    continue;
                }

                paragraphs.add(new Paragraph(paragraphCounter++, List.of(line.trim())));
                textLineCounter++;

                if (textLineCounter % 20 == 0) {
                    currentChapter = new Chapter(chapterCounter++, new ArrayList<>(paragraphs));
                    chapters.add(currentChapter);
                    paragraphs.clear();
                }
            }

            if (!paragraphs.isEmpty()) {
                currentChapter = new Chapter(chapterCounter++, new ArrayList<>(paragraphs));
                chapters.add(currentChapter);
            }

            book.setChapters(chapters);

            StatisticsServiceImpl statisticsService = new StatisticsServiceImpl();
            Statistics statistics = statisticsService.generateStatistics(book, lineCounter);
            book.setStatistics(statistics);

        } catch (Exception e) {
            System.err.println("Error reading the text file: " + e.getMessage());
        }

        return book;
    }
}
