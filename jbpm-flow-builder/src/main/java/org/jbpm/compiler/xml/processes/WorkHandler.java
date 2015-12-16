/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.jbpm.compiler.xml.processes;

import java.util.HashSet;

import org.drools.core.process.core.Work;
import org.drools.core.process.core.impl.WorkImpl;
import org.drools.core.xml.BaseAbstractHandler;
import org.drools.core.xml.ExtensibleXmlParser;
import org.drools.core.xml.Handler;
import org.jbpm.workflow.core.node.WorkItemNode;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class WorkHandler extends BaseAbstractHandler implements Handler {
    
    public WorkHandler() {
        if ((this.validParents == null) && (this.validPeers == null)) {
            this.validParents = new HashSet();
            this.validParents.add(WorkItemNode.class);

            this.validPeers = new HashSet();
            this.validPeers.add(null);

            this.allowNesting = false;
        }
    }
    
    public Object start(final String uri,
                        final String localName,
                        final Attributes attrs,
                        final ExtensibleXmlParser parser) throws SAXException {
        parser.startElementBuilder( localName,
                                    attrs );
        WorkItemNode workItemNode = (WorkItemNode) parser.getParent();
        final String name = attrs.getValue("name");
        emptyAttributeCheck(localName, "name", name, parser);
        Work work = new WorkImpl();
        work.setName(name);
        workItemNode.setWork(work);
        return work;
    }    
    
    public Object end(final String uri,
                      final String localName,
                      final ExtensibleXmlParser parser) throws SAXException {
        parser.endElementBuilder();
        return null;
    }

    public Class generateNodeFor() {
        return Work.class;
    }    

}
