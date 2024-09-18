package gr.ed.ch.tsilikafeneio.service;

import gr.ed.ch.tsilikafeneio.model.Book;
import gr.ed.ch.tsilikafeneio.model.Statistics;

public interface StatisticsService {
    Statistics generateStatistics(Book book, int totalLines);
}
