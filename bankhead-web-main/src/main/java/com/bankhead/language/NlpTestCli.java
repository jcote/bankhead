package com.bankhead.language;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Singleton;

@ImplementedBy(ManagerImpl.class) // replaced by ManagerImpl automatically without bind(..)
interface Manager { }
 
class ManagerImpl implements Manager { }
 
class MyModule implements Module {
  public void configure(Binder binder) {
    binder.requestStaticInjection(NlpTestCli.class);
  }
}

@Singleton
public class NlpTestCli {
	@Inject
	private static Classifier c;
		
	public static void main(String[] args) {
		Guice.createInjector(new Module[] { new MyModule() });
		String text = new String();
		for ( String a : args) {
			text += " " + a;
		}

		Map<String, List<String>> m = c.classify(text);
		System.out.println(m.values().size());
		for (String k : m.keySet()) {
			for (String v : m.get(k)) {
				System.out.println(k + "->" + m.get(k));
			}
		}
		
	}

}
