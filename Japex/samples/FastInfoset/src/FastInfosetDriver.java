/*
 * Japex ver. 0.1 software ("Software")
 * 
 * Copyright, 2004-2005 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This Software is distributed under the following terms:
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * Redistribution in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc., 'Java', 'Java'-based names,
 * nor the names of contributors may be used to endorse or promote products
 * derived from this Software without specific prior written permission.
 * 
 * The Software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS
 * SHALL NOT BE LIABLE FOR ANY DAMAGES OR LIABILITIES SUFFERED BY LICENSEE
 * AS A RESULT OF OR RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THE
 * SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE
 * LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED
 * AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF OR
 * INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that the Software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */

import java.io.*;
import org.xml.sax.InputSource;
import javax.xml.parsers.*;
import java.util.Properties;

import com.sun.xml.fastinfoset.sax.*;
import com.sun.xml.fastinfoset.vocab.*;

import com.sun.japex.*;

public class FastInfosetDriver extends JapexDriverBase {
    
    String _xmlFile;
    byte[] _fastInfosetDocumentByteArray;
    
    SAXDocumentParser _fidp;
    
    public FastInfosetDriver() {
    }

    public void initializeDriver() {
        try {
            _fidp = new SAXDocumentParser();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }   
    
    public void prepare(TestCase testCase) {
        _xmlFile = testCase.getParam("xmlfile");
        if (_xmlFile == null) {
            throw new RuntimeException("xmlfile not specified");
        }
        
        // Load file into byte array to factor out IO
        try {
	    SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser parser = spf.newSAXParser();

	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    SAXDocumentSerializer ds = new SAXDocumentSerializer();
            
            ds.setOutputStream(baos);

            // TODO must use URL here
            FileInputStream fis = new FileInputStream(new File(_xmlFile));
            parser.parse(fis, ds);
            fis.close();

            _fastInfosetDocumentByteArray = baos.toByteArray();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void warmup(TestCase testCase) {
        try {
            _fidp.reset();
            _fidp.parse(
                new InputSource(
                    new ByteArrayInputStream(_fastInfosetDocumentByteArray)));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void run(TestCase testCase) {
        try {
            _fidp.reset();
            _fidp.parse(
                new InputSource(
                    new ByteArrayInputStream(_fastInfosetDocumentByteArray)));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void finish(TestCase testCase) {
    }
    
    public void terminateDriver() {
    }
}
