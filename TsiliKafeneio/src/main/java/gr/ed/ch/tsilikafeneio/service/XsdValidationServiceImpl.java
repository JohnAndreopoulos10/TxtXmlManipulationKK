package gr.ed.ch.tsilikafeneio.service.impl;

import gr.ed.ch.tsilikafeneio.service.XsdValidationService;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;


public class XsdValidationServiceImpl implements XsdValidationService {

    @Override
    public boolean validateXmlAgainstXsd(File xmlFile, File xsdFile) throws Exception {

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        Schema schema = schemaFactory.newSchema(xsdFile);

        Validator validator = schema.newValidator();
        try {
            validator.validate(new StreamSource(xmlFile));
            return true; // valid
        } catch (Exception e) {
            System.err.println("XML validation error: " + e.getMessage());
            return false; // not valid
        }
    }
}
