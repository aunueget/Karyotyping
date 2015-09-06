import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.LinkedList;


public class ScriptParser {
	public enum filePosition{start,possibleName,notName,readNameWords,readParenth,readNameWordsParenth};

	public ScriptParser() {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length < 1) {
			System.out
					.println("add the commandline arugments <textFileLocation>");
			System.out.println("Ex.  java -jar *.jar /home/arc/images/");
		} else {
			System.out.println(args[0]);
			String filename;
			// initialize the que
			ImageQueue files = new ImageQueue();
			ScriptParser parser = new ScriptParser();

			filename = files.getNextFile(args[0]);
			while (filename != null) {
				// TODO: parse here
				parser.writeParsedFile(filename);
				filename = files.getNextFile(args[0]);

			}
		}
	}
	public void writeParsedFile(String filename){
		File currentFile= new File(filename);
		filePosition fileState=filePosition.start;
		String buffer="";
		char lastChar='a';
		boolean lastCharNewline=false;
		if(currentFile.exists()){
			try{
				BufferedWriter out = new BufferedWriter(new FileWriter("output//new_parsed.txt"));
		      FileInputStream fis = new FileInputStream(currentFile);
		      char ch;
		      while (fis.available() > 0) {
		        ch = (char) fis.read();
		        switch(fileState){
		        case start:
		        	if(ch>='A'||ch<='Z'){
		        		fileState=filePosition.possibleName;
		        	}
		        	else if((ch=='\n'||ch=='\r')&&this.isNewline(lastChar, ch)){
			        		if(lastCharNewline){
			        			buffer="";
			        		}
		        	}
		        	break;
		        case possibleName:
		        	if(ch=='('){
		        		fileState=filePosition.readParenth;
		        	}
		        	else if(ch=='\n'||ch=='\r'){
		        			if(this.isNewline(lastChar, ch)){
				        		if(lastCharNewline){
				        			buffer="";
				        			fileState=filePosition.start;
				        		}
				        		else{
				        			buffer=">>";
				        			fileState=filePosition.readNameWords;
				        		}
		        			}
		        	}
		        	else if((ch>='A'&&ch<='Z')||Character.isWhitespace(ch)){
		        		fileState=filePosition.possibleName;
		        	}
		        	else{
		        		fileState=filePosition.notName;
		        	}

		        	break;
		        case notName:
		        	if((ch=='\n'||ch=='\r')&&this.isNewline(lastChar, ch)){
			        		if(lastCharNewline){
			        			buffer="";
			        			fileState=filePosition.start;
			        		}
		        	}
		        	break;
		        case readNameWords:
		        	if(ch=='('){
		        		fileState=filePosition.readNameWordsParenth;
		        	}
		        	else if(ch=='\n'||ch=='\r'){
	        			if(this.isNewline(lastChar, ch)){
	        				buffer+=" ";
			        		if(lastCharNewline){
			        			buffer=buffer.substring(0,buffer.length()-1);
			        			out.write(buffer+"\r\n");
			        			fileState=filePosition.start;
			        			buffer="";
			        		}
		        		}
		        	}
		        	else{
		        		buffer+=ch;
		        	}

		        	break;
		        case readParenth:
		        	if(ch==')'){
		        		fileState=filePosition.possibleName;
		        	}
		        	else if((ch=='\n'||ch=='\r')&&this.isNewline(lastChar, ch)){
		        		if(lastCharNewline){
		        			buffer="";
		        			fileState=filePosition.start;
		        		}
		        	}

		        	break;
		        case readNameWordsParenth:
		        	if(ch==')'){
		        		fileState=filePosition.readNameWords;
		        	}
		        	else if((ch=='\n'||ch=='\r')&&this.isNewline(lastChar, ch)){
		        		if(lastCharNewline){
		        			out.write(buffer);
		        			fileState=filePosition.start;
		        			buffer="";
		        		}
		        	}
		        	break;
		        }
		        if((ch=='\n'||ch=='\r')){
		        	if(this.isNewline(lastChar, ch)){
		        		lastCharNewline=true;
		        	}
		        }
		        else{
		        	lastCharNewline=false;
		        }
		        lastChar=ch;
		      }
			//read line ignoring all parenths and spaces
			//if line all caps possible speaker
			//if line ends with two end lines disregard all
			//if line ends with one newline and possible speaker true
			//read until two newlines
			//reached,ignoring parenthns, and start with >> and text
		      out.close();
			}
			catch(Exception e){
				System.out.println(e);
			}
		}
	}
	public boolean isNewline(char lastChar, char currentChar){
		if(lastChar=='\r'&&currentChar=='\n'){
			return true;
		}
		else{
			return false;
		}
	}
}
