package kr.ac.green.cmd;

import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class CmdFactory {
	private static Hashtable<String, ICmd> cmds = 
		new Hashtable<String, ICmd>();
	public static void init(Properties prop) {		
		Set<?> keys = prop.keySet();
		for(Object temp : keys) {
			String key = temp.toString();
			String className = prop.getProperty(key);			
			try {
				Class<?> klass = Class.forName(className);
				ICmd cmdObj = (ICmd)klass.newInstance();
				cmds.put(key, cmdObj);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public static String searchAndDo(
		HttpServletRequest request, String cmd) {
		return cmds.get(cmd).action(request);		
	}
}