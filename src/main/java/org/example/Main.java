package org.example;

import net.sf.saxon.lib.Feature;
import net.sf.saxon.s9api.*;
import org.xml.sax.InputSource;

import javax.xml.transform.sax.SAXSource;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws SaxonApiException{

        Processor processor = new Processor(false);

        processor.setConfigurationProperty(Feature.TIMING, true);

        String cwd = System.getProperty("user.dir");
        String datapath = cwd; //+ "/src/test/testdata/resolvers";

        String catalog = datapath + "catalog.xml";
        processor.setCatalogFiles(catalog);

        InputSource docsrc = new InputSource(Path.of(cwd, "input.xml").toUri().toString());
        InputSource xslsrc = new InputSource(Path.of(cwd, "stylesheet.xsl").toUri().toString());

        XsltCompiler compiler = processor.newXsltCompiler();
        XsltExecutable exec = compiler.compile(new SAXSource(xslsrc));
        Xslt30Transformer transformer = exec.load30();

        XdmDestination destination = new XdmDestination();

        transformer.transform(new SAXSource(docsrc), destination);

        System.out.println(destination.getXdmNode());
    }
}