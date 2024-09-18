package gr.ed.ch.tsilikafeneio.service.impl;

import gr.ed.ch.tsilikafeneio.model.Book;
import gr.ed.ch.tsilikafeneio.model.Chapter;
import gr.ed.ch.tsilikafeneio.model.Paragraph;
import gr.ed.ch.tsilikafeneio.model.Statistics;
import gr.ed.ch.tsilikafeneio.service.StatisticsService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class StatisticsServiceImpl implements StatisticsService {

    @Override
    public Statistics generateStatistics(Book book, int totalLines) {
        int wordCount = 0;
        Set<String> distinctWords = new HashSet<>();

        for (Chapter chapter : book.getChapters()) {
            for (Paragraph paragraph : chapter.getParagraphs()) {
                for (String line : paragraph.getLines()) {
                    String[] words = line.split("\\s+");
                    wordCount += words.length;

                    for (String word : words) {
                        distinctWords.add(word.toLowerCase()); 
                    }
                }
            }
        }

        Statistics statistics = new Statistics();
        statistics.setParagraphCount(book.getChapters().stream().mapToInt(ch -> ch.getParagraphs().size()).sum());
        statistics.setLineCount(totalLines); 
        statistics.setWordCount(wordCount);
        statistics.setDistinctWordCount(distinctWords.size());
        statistics.setChapterCount(book.getChapters().size());
        statistics.setCreationTime(LocalDateTime.now());
        statistics.setAuthorName("John Andreopoulos");
        statistics.setApplicationClassName("TsiliKafeneio");

        return statistics;
    }
}
