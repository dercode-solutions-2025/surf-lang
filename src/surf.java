/* 

Welcome to Surf 0.2.1! Surf, released under the GNU AGPL v3.0 license, compiles to basic Java scripts. It was designed as an easier way to use Java.

2026 Surf. All rights reserved.

*/
package surf;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

enum Token {
    PUBLIC,
    CLASS,
    IDENTIFIER,
    LCURLY,
    RCURLY,
    FN,
    LPAREN,
    ARGV,
    RPAREN,
    RIGHT_ARROW,
    INT,
    PRINTLN,
    CHAR_LIT,
    STRING_LIT,
    VOID,
    STR,
    CHAR,
    COMMA,
    RETURN,
    PLUS,
    MINUS,
    TIMES,
    DIVIDE,
    NEWLINE,
    TAB,
    COLON,
    SEMI,
    PERIOD,
    DEFINE,
    LET
}

class Lexer {
    public static List<Object> lex(String script) {
        int ptr = 0;
        List<Object> out = new ArrayList<>();
        while (ptr != script.length()) {
            switch (script.charAt(ptr)) {
                case '\n':
                	out.add(Token.NEWLINE);
                	ptr++;
                	continue;
                case '\t':
                	out.add(Token.TAB);
                    ptr++;
                    continue;
                case '{':
                    out.add(Token.LCURLY);
                    ptr++;
                    continue;
                case '}':
                    out.add(Token.RCURLY);
                    ptr++;
                    continue;
                case '"':
                    out.add(Token.STRING_LIT);
                    String temp = "";
                    ptr++;
                    while (script.charAt(ptr) != '"') {
                        temp += script.charAt(ptr);
                        ptr++;
                    }
                    out.add(temp);
                    ptr++;
                    continue;
                case '\'':
                    String temp2 = "";
                    out.add(Token.CHAR_LIT);
                    ptr++;
                    temp2 += script.charAt(ptr);
                    ptr++;
                    out.add(temp2);
                    ptr++;
                    continue;
                case '(':
                    out.add(Token.LPAREN);
                    ptr++;
                    continue;
                case ')':
                    out.add(Token.RPAREN);
                    ptr++;
                    continue;
                case ',':
                	out.add(Token.COMMA);
                	ptr++;
                	continue;
                case '+':
                    out.add(Token.PLUS);
                    ptr++;
                    continue;
                case '-':
                    out.add(Token.MINUS);
                    ptr++;
                	continue;
                case '*':
                	out.add(Token.TIMES);
                	ptr++;
                	continue;
               case '/':
               	if (script.charAt(ptr + 1) == '/') {
               		while (ptr <= script.length() && script.charAt(ptr) != '\n') {
               			ptr++;
               		}
               		ptr++;
               		continue;
               	} else {
               		out.add(Token.DIVIDE);
               		ptr++;
               		continue;
               	}
               case ':':
               	out.add(Token.COLON);
               	ptr++;
               	continue;
               case ';':
               	out.add(Token.SEMI);
               	ptr++;
               	continue;
               case '.':
               	out.add(Token.PERIOD);
               	ptr++;
               	continue;
            }
            if (ptr <= script.length() - 4 && script.substring(ptr, ptr + 4).equals("pub ")) {
                ptr += 4;
                out.add(Token.PUBLIC);
                continue;
            } else if (ptr <= script.length() - 6 && script.substring(ptr, ptr + 6).equals("class ")) {
                ptr += 6;
                out.add(Token.CLASS);
                while (script.charAt(ptr) == ' ') {
                	ptr++;
                }
                continue;
            } else if (ptr <= script.length() - 3 && script.substring(ptr, ptr + 3).equals("fn ")) {
                ptr += 3;
                out.add(Token.FN);
                continue;
            } else if (ptr <= script.length() - 4 && script.substring(ptr, ptr + 4).equals("argv")) {
                ptr += 4;
                out.add(Token.ARGV);
                continue;
            } else if (ptr <= script.length() - 4 && script.substring(ptr, ptr + 4).equals("int ")) {
                ptr += 4;
                out.add(Token.INT);
                continue;
            } else if (ptr <= script.length() - 7 && script.substring(ptr, ptr + 7).equals("println")) {
                ptr += 7;
                out.add(Token.PRINTLN);
                continue;
            } else if (ptr <= script.length() - 5 && script.substring(ptr, ptr + 5).equals("void ")) {
                ptr += 5;
                out.add(Token.VOID);
                continue;
            }  else if (ptr <= script.length() - 5 && script.substring(ptr, ptr + 5).equals("char ")) {
                ptr += 5;
                out.add(Token.CHAR);
                continue;
            } else if (ptr <= script.length() - 4 && script.substring(ptr, ptr + 4).equals("str ")) {
                ptr += 4;
                out.add(Token.STR);
                continue;
            } else if (ptr <= script.length() - 4 && script.substring(ptr, ptr + 4).equals("    ")) {
                ptr += 4;
                out.add(Token.TAB);
                continue;
            } else if (ptr <= script.length() - 4 && script.substring(ptr, ptr + 4).equals("let ")) {
                ptr += 4;
                out.add(Token.LET);
                continue;
            } else if (ptr <= script.length() - 7 && script.substring(ptr, ptr + 7).equals("return ")) {
                ptr += 7;
                out.add(Token.RETURN);
                continue;
               // sorry for verbosity but I am too lazy at the moment to do anything else
            } else if (script.charAt(ptr) != ';' && script.charAt(ptr) != '-' && script.charAt(ptr) != '>' && script.charAt(ptr) != '{' && script.charAt(ptr) != '}' && script.charAt(ptr) != '"' && script.charAt(ptr) != '\'' && script.charAt(ptr) != '"' && script.charAt(ptr) != '(' && script.charAt(ptr) != ')' && script.charAt(ptr) != ' ' && script.charAt(ptr) != ',' && script.charAt(ptr) != ':') {
            	String temp4 = "";
            	out.add(Token.IDENTIFIER);
            	while (ptr != script.length() && script.charAt(ptr) != ';' && script.charAt(ptr) != '-' && script.charAt(ptr) != '>' && script.charAt(ptr) != '{' && script.charAt(ptr) != '}' && script.charAt(ptr) != '"' && script.charAt(ptr) != '\'' && script.charAt(ptr) != '"' && script.charAt(ptr) != '(' && script.charAt(ptr) != ')' && script.charAt(ptr) != ' ' && script.charAt(ptr) != ',' && script.charAt(ptr) != ':') {
            		temp4 += script.charAt(ptr);
            		ptr++;
            	}
            	out.add(temp4);
            	continue;
            } else {
            	ptr++;
            	continue;
            }
        }

        return out;
    }
}

class Parser {
	public static String compileJava(List<Object> tokens) {
		String out = "/*\n\nGenerated by Surf version 0.2.1\nThe source code for the Surf lexer and compiler (excluding the Java generated by it) are released under the GNU AGPL v3.0 license.\n2026 Surf. All rights reserved.\n\n*/\n\n";
		int ptr = 0;
		int funcTracker = 0;
		boolean fn = false;
		boolean main = false;
		while (ptr < tokens.size()) {
			Object t = tokens.get(ptr);
			if (t == Token.CLASS) {
				out += "class ";
				ptr++;
				continue;
			} else if (t == Token.COLON) {
				if (tokens.get(ptr + 1) == Token.COLON) {
					out += ".";
					ptr += 2;
					continue;
				} else {
					out += "extends ";
					ptr++;
					continue;
				}
			} else if (t == Token.IDENTIFIER) {
				out += tokens.get(ptr + 1) + " ";
				ptr += 2;
				continue;
			} else if (t == Token.PUBLIC) {
				out += "public ";
				ptr++;
				continue;
			} else if (t == Token.LCURLY) {
				out += " {";
				ptr++;
				continue;
			} else if (t == Token.RCURLY) {
				if (fn && main == false && funcTracker == 69420) {
					out += "\n\t\treturn null;\n";
					fn = false;
				}
				out += "}";
				ptr++;
				continue;
			} else if (t == Token.LPAREN) {
				out += "(";
				ptr++;
				continue;
			} else if (t == Token.RPAREN) {
				out += ")";
				ptr++;
				continue;
			} else if (t == Token.NEWLINE) {
				out += "\n";
				ptr++;
				continue;
			} else if (t == Token.TAB) {
				out += "\t";
				ptr++;
				continue;
			} else if (t == Token.FN) {
				out += "static ";
				ptr++;
				if (tokens.get(ptr + 1).equals("main")) {
					out += "void main";
					main = true;
					ptr++;
					continue;
				} else {
					out += "Optional " + tokens.get(ptr + 1);
					ptr++;
					funcTracker = 69420;
					fn = true;
					continue;
				}
			} else if (t == Token.INT) {
				out += "int ";
				ptr++;
				continue;
			} else if (t == Token.VOID) {
				out += "void ";
				ptr++;
				continue;
			} else if (t == Token.STR) {
				out += "String ";
				ptr++;
				continue;
			} else if (t == Token.ARGV) {
				out += "String[] args";
				ptr++;
				continue;
			} else if (t == Token.COMMA) {
				out += ", ";
				ptr++;
				continue;
			} else if (t == Token.RETURN) {
				out += "return ";
				ptr++;
				funcTracker = 2;
				continue;
			} else if (t == Token.SEMI) {
				out += ";";
				ptr++;
				continue;
			} else if (t == Token.PRINTLN) {
				out += "System.out.println";
				ptr++;
				continue;
			} else if (t == Token.STRING_LIT) {
				out += "\"";
				ptr++;
				out += tokens.get(ptr);
				out += "\"";
				ptr++;
				continue;
			} else if (t == Token.PLUS) {
				out += "+ ";
				ptr++;
				continue;
			} else if (t == Token.MINUS) {
				out += "- ";
				ptr++;
				continue;
			} else if (t == Token.TIMES) {
				out += "* ";
				ptr++;
				continue;
			} else if (t == Token.DIVIDE) {
				out += "/ ";
				ptr++;
				continue;
			} else if (t == Token.PERIOD) {
				out += ".";
				ptr++;
				continue;
			} else if(t == Token.LET) {
				out += "var ";
				ptr++;
				continue;
			} else {
				ptr++;
				continue;
			}
		}
		return out;
	}
}
class std {
	public static String getSurfFileName(String file) {
		return file.replace(".surf", "");
	}
}
public class Main extends std{
    public static void main(String[] args) {
    	if (args.length < 1) {
    		System.out.println("<usage>: java surf.java main.surf");
    		System.exit(1);
    	} else {
			Path path = Path.of(args[0]);
			if (!Files.exists(path)) {
				System.out.println("File " + path + " doesn't exist.\nThis example should be saved before running.");
				return;
			}
        	try {
				Path filePath = Path.of(getSurfFileName(args[0]) + ".java");
				String content = Parser.compileJava(Lexer.lex(Files.readString(path)));
            	Files.writeString(filePath, content);
            	System.out.print("Successfully wrote to ");
            	System.out.print(getSurfFileName(args[0]) + ".java");
            	System.out.println("!");
			} catch (IOException e) {
				System.out.println(e);
			}
    	}
    }
}