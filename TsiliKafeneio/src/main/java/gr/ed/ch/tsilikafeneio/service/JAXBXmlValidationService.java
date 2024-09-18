package gr.ed.ch.tsilikafeneio.service.impl;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXException;

@Slf4j
public class JAXBXmlValidationService {

 
    public boolean validateXml(String xmlFilePath, String xsdFilePath, Class<?> xmlClass) {
        boolean isValid = false;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(xmlClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(xsdFilePath));
            unmarshaller.setSchema(schema);

            File xmlFile = new File(xmlFilePath);
            unmarshaller.unmarshal(xmlFile);
            isValid = true;
            log.info("XML validation succeeded.");

        } catch (JAXBException | SAXException e) {
            log.error("XML validation failed.", e);
        }
        return isValid;
    }
}
// did not use it 