package epam.webtech.services;

import epam.webtech.exceptions.ValidationException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XsdValidationService {

    private XsdValidationService() {
    }

    private static class SingletonHandler {
        static final XsdValidationService INSTANCE = new XsdValidationService();
    }

    public static XsdValidationService getInstance() {
        return SingletonHandler.INSTANCE;
    }

    public void validate(File xmlFile, File xsdFile) throws ValidationException {
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsdFile);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
            throw new ValidationException("Validation failed " + e.getMessage());
        } catch (org.xml.sax.SAXException e) {
            throw new ValidationException(xmlFile.getName() + " is NOT valid reason:" + e);
        }
    }
}
