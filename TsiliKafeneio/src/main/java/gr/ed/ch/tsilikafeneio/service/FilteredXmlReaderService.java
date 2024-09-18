package gr.ed.ch.tsilikafeneio.service;

import gr.ed.ch.tsilikafeneio.model.Book;

import java.io.File;

public interface FilteredXmlReaderService {
    Book readFilteredChaptersFromXml(File file) throws Exception;
}

