package com.googlecode.htmlcompressor.velocity;

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.TemplateInitException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.log.Log;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;

/**
 * Velocity directive that compresses an HTML content within #compressHtml ... #end block.
 * Compression parameters are set by default (no JavaScript and CSS compression).
 * 
 * @see HtmlCompressor
 * 
 * @author <a href="mailto:serg472@gmail.com">Sergiy Kovalchuk</a>
 */
public class HtmlCompressorDirective extends Directive {
	
	private static final HtmlCompressor htmlCompressor = new HtmlCompressor();
	
	private Log log;

	public String getName() {
		return "compressHtml";
	}

	public int getType() {
		return BLOCK;
	}
	
	@Override
	public void init(RuntimeServices rs, InternalContextAdapter context, Node node) throws TemplateInitException {
		super.init(rs, context, node);
		log = rs.getLog();
		
		//set compressor properties
		htmlCompressor.setEnabled(rs.getBoolean("userdirective.compressHtml.enabled", true));
		htmlCompressor.setRemoveComments(rs.getBoolean("userdirective.compressHtml.removeComments", true));
		htmlCompressor.setRemoveMultiSpaces(rs.getBoolean("userdirective.compressHtml.removeMultiSpaces", true));
		htmlCompressor.setRemoveIntertagSpaces(rs.getBoolean("userdirective.compressHtml.removeIntertagSpaces", false));
		htmlCompressor.setRemoveQuotes(rs.getBoolean("userdirective.compressHtml.removeQuotes", false));
		htmlCompressor.setCompressJavaScript(rs.getBoolean("userdirective.compressHtml.compressJavaScript", false));
		htmlCompressor.setCompressCss(rs.getBoolean("userdirective.compressHtml.compressCss", false));
		htmlCompressor.setYuiJsNoMunge(rs.getBoolean("userdirective.compressHtml.yuiJsNoMunge", false));
		htmlCompressor.setYuiJsPreserveAllSemiColons(rs.getBoolean("userdirective.compressHtml.yuiJsPreserveAllSemiColons", false));
		htmlCompressor.setYuiJsLineBreak(rs.getInt("userdirective.compressHtml.yuiJsLineBreak", -1));
		htmlCompressor.setYuiCssLineBreak(rs.getInt("userdirective.compressHtml.yuiCssLineBreak", -1));
	}

    public boolean render(InternalContextAdapter context, Writer writer, Node node) 
    		throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
    	
    	//render content
    	StringWriter content = new StringWriter();
		node.jjtGetChild(0).render(context, content);
		
		//compress
		try {
			writer.write(htmlCompressor.compress(content.toString()));
		} catch (Exception e) {
			writer.write(content.toString());
			String msg = "Failed to compress content: "+content.toString();
            log.error(msg, e);
            throw new RuntimeException(msg, e);
            
		}
		return true;
    	
    }

}
