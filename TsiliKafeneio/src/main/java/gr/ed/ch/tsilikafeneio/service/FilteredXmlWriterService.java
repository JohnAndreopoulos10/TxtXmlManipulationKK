package gr.ed.ch.tsilikafeneio.service;

import gr.ed.ch.tsilikafeneio.model.Book;

import java.io.File;

public interface FilteredXmlWriterService {
    void writeFilteredBookToXml(Book book, File file) throws Exception;
}
