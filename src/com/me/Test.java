package com.me;

import net.htmlparser.jericho.Source;

public class Test {

	public static void main(String[] args) {
		Management m = new Management();
		if(!m.login()){
			System.out.println("login fail!");
			System.exit(0);
		}
		m.Do(new Source(m.getMainpage()), m.getC().getRoot());
	}
}
