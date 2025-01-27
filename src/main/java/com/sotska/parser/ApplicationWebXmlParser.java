package com.sotska.parser;

import com.sotska.entity.ApplicationSettings;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ApplicationWebXmlParser {

    private static final String SERVLET_TAG = "servlet";
    private static final String SERVLET_MAPPING_TAG = "servlet-mapping";
    private static final String SERVLET_CLASS_TAG = "servlet-class";
    private static final String SERVLET_NAME_TAG = "servlet-name";
    private static final String URL_PATTERN_TAG = "url-pattern";

    private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();

    public ApplicationSettings parse(String applicationPath) {
        File file = new File(applicationPath + "/WEB-INF/web.xml");
        if (!file.exists()) {
            throw new RuntimeException("File web.xml not exist by path: " + file.getPath());
        }

        String appName = new File(applicationPath).getName();
        return new ApplicationSettings(appName, parseFile(file));
    }

    private Map<String, String> parseFile(File xmlFile) {
        try {
            Document document = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder().parse(xmlFile);
            document.getDocumentElement().normalize();

            Map<String, String> servletNameSettingsMap = new HashMap<>();

            NodeList servletList = document.getElementsByTagName(SERVLET_TAG);
            NodeList servletMappingList = document.getElementsByTagName(SERVLET_MAPPING_TAG);

            parseNodes(servletList, servletMappingList, servletNameSettingsMap);
            return servletNameSettingsMap;
        } catch (Exception e) {
            throw new RuntimeException("Can't parse file: " + xmlFile.getName(), e);
        }
    }

    private static void parseNodes(NodeList servletList, NodeList servletMappingList, Map<String, String> servletNameSettingsMap) {
        for (int servletListIndex = 0; servletListIndex < servletList.getLength(); servletListIndex++) {
            Element servletElement = (Element) servletList.item(servletListIndex);
            String servletClass = extractTag(servletElement, SERVLET_CLASS_TAG);
            String servletName = extractTag(servletElement, SERVLET_NAME_TAG);

            for (int servletMappingListIndex = 0; servletMappingListIndex < servletMappingList.getLength(); servletMappingListIndex++) {
                Element mappingElement = (Element) servletMappingList.item(servletMappingListIndex);
                String mappedServletName = extractTag(mappingElement, SERVLET_NAME_TAG);
                String servletPath = extractTag(mappingElement, URL_PATTERN_TAG);

                if (servletName.equals(mappedServletName)) {
                    servletNameSettingsMap.put(servletClass, servletPath);
                }
            }
        }
    }

    private static String extractTag(Element servletElement, String tag) {
        return servletElement.getElementsByTagName(tag).item(0).getTextContent();
    }
}
