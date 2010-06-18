package org.iitk.brihaspati.modules.utils;

/*@(#)CreateIndexer.java
 *  Copyright (c) 2005 ETRG,IIT Kanpur. http://www.iitk.ac.in/
 *  All Rights Reserved.
 *
 *  Redistribution and use in source and binary forms, with or
 *  without modification, are permitted provided that the following
 *  conditions are met:
 *
 *  Redistributions of source code must retain the above copyright
 *  notice, this  list of conditions and the following disclaimer.
 *
 *  Redistribution in binary form must reproducuce the above copyright
 *  notice, this list of conditions and the following disclaimer in
 *  the documentation and/or other materials provided with the
 *  distribution.
 *
 *  THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED.  IN NO EVENT SHALL ETRG OR ITS CONTRIBUTORS BE LIABLE
 *  FOR ANY DIRECT, INDIRECT, INCIDENTAL,SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 *  OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 *  BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 *  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 *  OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 *  EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
/**
 *  @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 *  @author <a href="mailto:sunil_gupta20801@rediffmail.com">Sunil Kumar Gupta</a>
 */


import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;

/** This class is responsible for creating
 *  The index of the courses exists and tell
 * the types of file that can be indexed
*/

public class CreateIndexer {
    public static void index(File indexDir, File dataDir) throws IOException {
        if (!dataDir.exists() || !dataDir.isDirectory()) {
            throw new IOException(dataDir + " does not exist or is not a directory");
        }
        IndexWriter writer = new IndexWriter(indexDir, new StandardAnalyzer(), true);
        indexDirectory(writer, dataDir);
        writer.close();
    }

   /** This method is determining which type of files ( html, txt, doc, pdf )
    * can be indexed and call indexFile() mehod to create 
    * the index.
   */
    private static void indexDirectory(IndexWriter writer, File dir) throws IOException {
        File[] files = dir.listFiles();

        for (int i=0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory()) {
                indexDirectory(writer, f);  // recurse
            } else if (f.getName().endsWith(".html") ||(f.getName().endsWith(".txt"))||(f.getName().endsWith(".doc"))||(f.getName().endsWith(".pdf"))) {
                indexFile(writer, f);
            }
        }
    }

    /** This method creates the index and
     * and maintain the documents list which 
     * is to be added in the index
    */

    private static void indexFile(IndexWriter writer, File f) throws IOException {
        System.out.println("Indexing " + f.getName());

        Document doc = new Document();
        doc.add(Field.Text("contents", new FileReader(f)));
        doc.add(Field.Keyword("filename", f.getCanonicalPath()));
        writer.addDocument(doc);
    }

    /**This method determines the directories
     * for the index and the data.It points to
     * the data and index location to start the index process
    */

    public static void StartIndex(String arg1,String arg2) throws Exception {
        if (arg1.equals("")||(arg2.equals(""))) {
            throw new Exception("Usage: " + CreateIndexer.class.getName() + " <index dir> <data dir>");
        }
        File indexDir = new File(arg1);
        File dataDir = new File(arg2);
        index(indexDir, dataDir);
    }
}
