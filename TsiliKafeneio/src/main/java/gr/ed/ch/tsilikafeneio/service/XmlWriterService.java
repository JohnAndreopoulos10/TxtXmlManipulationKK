package gr.ed.ch.tsilikafeneio.service;

import gr.ed.ch.tsilikafeneio.model.Book;

import java.io.File;

public interface XmlWriterService {
    void writeBookToXml(Book book, File file);
}
