package gr.ed.ch.tsilikafeneio.service;

import java.io.File;

public interface XsdGenerationService {
    void generateXsdFromModels(File xsdFile) throws Exception;
}
