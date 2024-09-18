package gr.ed.ch.tsilikafeneio.service;

import gr.ed.ch.tsilikafeneio.model.Book;

public interface TxtParserService {
    Book parseTextFile(String fileUrl)throws Exception;
}
