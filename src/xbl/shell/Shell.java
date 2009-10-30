/*
 * Copyright (c) 2009. The Frontside Software, Inc.
 *
 * The contents of this file are subject to the Gnu General Public License version 2
 * or later (the "License"); You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

package xbl.shell;

import jline.ConsoleReader;
import jline.ConsoleReaderInputStream;
import jline.History;
import org.mozilla.javascript.*;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.io.File;
import java.io.IOException;

import xbl.Session;

public class Shell implements SignalHandler {
	private ConsoleReader reader;
	private Scriptable scope;
	private String noInputPrompt;
	private String prompt;
	private StringBuilder input;
	private Context cx;
	private Object result;
	private int line;

	public Shell() {
		try {
			this.reader = new ConsoleReader();
			this.reader.setHistory(new History(new File(System.getProperty("user.home"), ".xbox.history")));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.prompt = this.noInputPrompt = "xbox>";
		this.input = new StringBuilder();
	}

	public static void main(String[] args) {
		Shell shell = new Shell();
		Runtime.getRuntime().addShutdownHook(new Thread(new NewlineShutdownHook()));
		Signal.handle(new Signal("INT"), shell);
		ConsoleReaderInputStream.setIn(shell.reader);
		shell.repl();
	}

	private int repl() {
		return (Integer) new ContextFactory().call(new ContextAction() {
			public Object run(Context context) {
				cx = context;
				scope = context.initStandardObjects();
				Quit quit = new Quit();
				ScriptableObject.putProperty(scope, "quit", quit);
				ScriptableObject.putProperty(scope, "exit", quit);
				ScriptableObject.putConstProperty(scope, "xbl", new Session());
				System.out.println("//quit() or exit() to leave shell");
				System.out.println("var xbl = new Packages.xbl.Session()");

				//noinspection InfiniteLoopStatement
				for (; ;) {
					if (read()) {
						execute();
						print();
					}
				}
			}
		});
	}

	private boolean read() {
		try {
			String line = this.reader.readLine(this.prompt + " ");
			this.input.append(line);
			if (cx.stringIsCompilableUnit(this.input.toString())) {
				this.prompt = this.noInputPrompt;
				return true;
			} else {
				this.prompt = "*";
				return false;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void execute() {
		result = Undefined.instance;
		try {
			result = cx.evaluateString(scope, input.toString(), "shell", this.line++, null);
		} catch (EcmaError e) {
			System.err.println(e.details());
		} catch (EvaluatorException e) {
			System.err.println(e.details());
		} catch (Exception e) {
			e.printStackTrace(System.err);
		} finally {
			this.input.setLength(0);
		}
	}

	private void print() {
		if (result == null) {
			System.out.println("null");
		} else if (result instanceof Undefined) {
			//print nothing
		} else if (result instanceof Wrapper) {
			Wrapper wrapper = (Wrapper) result;
			Object result = wrapper.unwrap();
			System.out.println("[" + result + "]");
		} else if (result instanceof Scriptable) {
			Scriptable scriptable = (Scriptable) result;
			Object toString;
			try {
				toString = ScriptableObject.getProperty(scriptable, "toString");
			} catch (Exception e) {
				System.out.println(scriptable.toString());
				return;
			}
			if (toString instanceof Function) {
				Function callableToString = (Function) toString;
				System.out.println(callableToString.call(cx, scope, scriptable, new Object[0]));
			} else {
				System.out.println(result.toString());
			}
		} else {
			System.out.println(result.toString());
		}
	}


	public void handle(Signal signal) {
		try {
			this.reader.setCursorPosition(0);
			this.reader.killLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private static class NewlineShutdownHook implements Runnable {
		public void run() {
			System.out.println();
		}
	}

	private class Quit extends BaseFunction {
		@Override
		public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
			System.exit(0);
			return null;
		}

		@Override
		public String toString() {
			return "function() {\t[Native Call: System.exit(0)]}";
		}
	}
}
