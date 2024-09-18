package gr.ed.ch.tsilikafeneio.service;

import java.io.File;

public interface XsdValidationService {

    boolean validateXmlAgainstXsd(File xmlFile, File xsdFile) throws Exception;
}
